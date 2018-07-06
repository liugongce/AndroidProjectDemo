package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.widget.FrameLayout;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.CommonTabLayout;

/**
 * Created by liugongce on 2017/10/11.
 */

public class RequestDelegate extends BaseDelegate {
    public CommonTabLayout tablayout;
    public FrameLayout fragmelayout;

    @Override
    public void initView() {
        tablayout=getViewById(R.id.tablayout);
        fragmelayout=getViewById(R.id.fragmelayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_request;
    }
}
