package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fivefivelike.androidprojectdemo.mvp.delegate.NormalWebviewActivityDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.base.BaseWebViewActivity;


public class NormalWebviewActivityActivity<D extends BaseDataBind> extends BaseWebViewActivity<NormalWebviewActivityDelegate,D> {

    private String webUrl;

    @Override
    protected Class<NormalWebviewActivityDelegate> getDelegateClass() {
        return NormalWebviewActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        mWebview.loadUrl(webUrl);

    }

    @Override
    protected WebView initWebview() {
        return viewDelegate.viewHolder.webView;
    }

    @Override
    protected ProgressBar initProgressbar() {
        return viewDelegate.viewHolder.progressBar;
    }


    @Override
    public D getDataBinder(NormalWebviewActivityDelegate viewDelegate) {
        return null;
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }


    public static void startAct(Fragment fragment, String webUrl, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), NormalWebviewActivityActivity.class);
        intent.putExtra("webUrl", webUrl);
        if (requestCode == -1) {
            fragment.startActivity(intent);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public static void startAct(Activity activity, String webUrl, int requestCode) {
        Intent intent = new Intent(activity, NormalWebviewActivityActivity.class);
        intent.putExtra("webUrl", webUrl);
        if (requestCode == -1) {
            activity.startActivity(intent);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("webUrl");
    }


}
