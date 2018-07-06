package com.fivefivelike.mybaselibrary.mywebview;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;

/**
 * Created by liugongce on 2017/11/30.
 */

public class MyWebChromeClient extends WebChromeClient {
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private View mXProgressVideo;
    private OnWebviewLoadListener onWebviewLoadListener;
    private View mCustomView;
    private CustomViewCallback mXCustomViewCallback;
    private WebView mWebView;

    public MyWebChromeClient(WebView webView, OnWebviewLoadListener onWebviewLoadListener) {
        this.onWebviewLoadListener = onWebviewLoadListener;
        this.mWebView = webView;
    }

    // 播放网络视频时全屏会被调用的方法
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        // 如果一个视图已经存在，那么立刻终止并新建一个
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        mCustomView = view;
        onWebviewLoadListener.webAddVideoView(mCustomView);
        mXCustomViewCallback = callback;
        mWebView.setVisibility(View.GONE);
        onWebviewLoadListener.webSetRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    // 视频播放退出全屏会被调用的
    @Override
    public void onHideCustomView() {
        mWebView.setVisibility(View.VISIBLE);
        if (mCustomView == null)// 不是全屏播放状态
            return;
        mCustomView.setVisibility(View.GONE);
        onWebviewLoadListener.webRemoveVideoView(mCustomView);
        mXCustomViewCallback.onCustomViewHidden();
        mCustomView = null;
        onWebviewLoadListener.webSetRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onHideCustomView();
    }

    // 视频加载时进程loading
    @Override
    public View getVideoLoadingProgressView() {
        if (mXProgressVideo == null) {
            LayoutInflater inflater = LayoutInflater.from(mWebView.getContext());
            mXProgressVideo = inflater.inflate(R.layout.video_loading_progress, null);
        }
        return mXProgressVideo;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        onWebviewLoadListener.webProgressChanged(newProgress);
    }

    /**
     * 判断是否是全屏  重写返回键
     */
    public boolean inCustomView() {
        return (mCustomView != null);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // 设置title
        onWebviewLoadListener.webGetTitle(title);
    }

    //扩展浏览器上传文件
    //3.0++版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooserImpl(uploadMsg);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooserImpl(uploadMsg);
    }

    // For Android > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooserImpl(uploadMsg);
    }

    // For Android > 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
        openFileChooserImplForAndroid5(uploadMsg);
        return true;
    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        onWebviewLoadListener.webStartTakePhoto();

    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadCallbackAboveL = uploadMsg;
        onWebviewLoadListener.webStartTakePhoto();
    }

    /**
     * 在获取图片成功后调用此方法
     *
     * @param uri 图片uri路径
     */
    public void mUploadMessage(Uri uri) {
        if (mUploadCallbackAboveL != null) {
            Uri[] uris = new Uri[]{uri};
            mUploadCallbackAboveL.onReceiveValue(uris);
            mUploadCallbackAboveL = null;
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
        } else {
            ToastUtil.show("无法获取数据");
        }

    }
}