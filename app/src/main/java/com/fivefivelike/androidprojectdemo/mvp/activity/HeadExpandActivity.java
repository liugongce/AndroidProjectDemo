package com.fivefivelike.androidprojectdemo.mvp.activity;

import com.fivefivelike.androidprojectdemo.mvp.delegate.HeadExpandDelegate;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
/**
 * Created by liugongce on 2017/7/28.
 */

public class HeadExpandActivity extends BaseActivity<HeadExpandDelegate> {
    @Override
    protected Class<HeadExpandDelegate> getDelegateClass() {
        return HeadExpandDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("头部拉伸"));
    }
}
