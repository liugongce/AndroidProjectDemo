package com.fivefivelike.androidprojectdemo.mvp.delegate;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BasePullDelegate;

/**
 * Created by liugongce on 2017/7/28.
 */

public class PageRequestDelegate extends BasePullDelegate {

    @Override
    public void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.public_pull_recycleview;
    }
}
