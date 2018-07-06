package com.fivefivelike.mybaselibrary.mywebview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by liugongce on 2017/11/30.
 */

public class MyWebViewClient extends WebViewClient {
    private OnWebviewLoadListener onWebviewLoadListener;
    private Context mContext;

    public MyWebViewClient(Context context, OnWebviewLoadListener onWebviewLoadListener) {
        this.onWebviewLoadListener = onWebviewLoadListener;
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            url = request.getUrl().toString();
        } else {
            url = request.toString();
        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url);
            return false;
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(intent);
            return true;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (onWebviewLoadListener!=null)
            onWebviewLoadListener.webHindProgressBar();
        // html加载完成之后，添加监听图片的点击js函数
        super.onPageFinished(view, url);
        onWebviewLoadListener.webAddImageClickListener();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (onWebviewLoadListener!=null) {
            onWebviewLoadListener.webStartProgress();
        }
        super.onPageStarted(view, url, favicon);
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();  // 接受所有网站的证书 加载https地址
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        onWebviewLoadListener.webLoadError();
    }
}
