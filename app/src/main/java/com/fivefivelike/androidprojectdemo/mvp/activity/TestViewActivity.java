package com.fivefivelike.androidprojectdemo.mvp.activity;

import com.fivefivelike.androidprojectdemo.mvp.delegate.TestViewDelete;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

/**
 * Created by liugongce on 2017/11/16.
 */

public class TestViewActivity extends BaseActivity<TestViewDelete> {
    @Override
    protected Class<TestViewDelete> getDelegateClass() {
        return TestViewDelete.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("测试视图"));
    }
}
