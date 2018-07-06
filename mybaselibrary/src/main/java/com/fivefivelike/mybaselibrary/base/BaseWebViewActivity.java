package com.fivefivelike.mybaselibrary.base;

import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.mywebview.ImageClickInterface;
import com.fivefivelike.mybaselibrary.mywebview.MyWebChromeClient;
import com.fivefivelike.mybaselibrary.mywebview.MyWebViewClient;
import com.fivefivelike.mybaselibrary.mywebview.OnWebviewLoadListener;
import com.fivefivelike.mybaselibrary.mywebview.WebUtils;

/**
 * Created by liugongce on 2017/10/13.
 */

public abstract class BaseWebViewActivity<T extends BaseDelegate, D extends BaseDataBind>
        extends BaseDataBindActivity<T, D> implements OnWebviewLoadListener {
    private MyWebViewClient webViewClient;
    private MyWebChromeClient webChromeClient;
    public WebView mWebview;
    public ProgressBar mProgressBar;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        mWebview = initWebview();
        mProgressBar = initProgressbar();
        WebUtils.webviewRegister(mWebview, this);
        webChromeClient = new MyWebChromeClient(mWebview, this);
        mWebview.setWebChromeClient(webChromeClient);
        webViewClient = new MyWebViewClient(this, this);
        mWebview.setWebViewClient(webViewClient);
        mWebview.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");

    }

    /**
     * @return webview
     */
    protected abstract WebView initWebview();

    protected abstract ProgressBar initProgressbar();


    @Override
    public void webHindProgressBar() {

        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void webStartProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void webProgressChanged(int newProgress) {
        if (mProgressBar != null)
            mProgressBar.setProgress(newProgress);
    }

    @Override
    public void webAddImageClickListener() {
        WebUtils.addWebImageClickJs(mWebview);
    }

    @Override
    public void webAddVideoView(View view) {
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.addView(view);
    }

    @Override
    public void webRemoveVideoView(View view) {
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.removeView(view);
    }

    @Override
    public void webStartTakePhoto() {

    }

    @Override
    public void webGetTitle(String title) {
        initToolbar(new ToolbarBuilder().setTitle(title));
    }

    @Override
    public void webSetRequestedOrientation(int screen_orientation) {
        setRequestedOrientation(screen_orientation);
    }

    @Override
    public void webLoadError() {

    }

    @Override
    public void onBackPressed() {
        if (webChromeClient.inCustomView()) {
            webChromeClient.onHideCustomView();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        WebUtils.onResume(mWebview);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebUtils.onPause(mWebview);
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        WebUtils.onDestroy(mWebview);
        super.onDestroy();
    }
}
