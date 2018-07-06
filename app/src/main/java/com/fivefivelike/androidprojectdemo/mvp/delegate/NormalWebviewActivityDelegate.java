package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class NormalWebviewActivityDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_webview_activity;
    }


    public static class ViewHolder {
        public View rootView;
        public WebView webView;
        public ProgressBar progressBar;
        public SmartRefreshLayout smartRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.webView = (WebView) rootView.findViewById(R.id.webView);
            this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            this.smartRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.smartRefreshLayout);
        }

    }
}