package com.fivefivelike.androidprojectdemo.mvp.delegate;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.ViewPagerFixed;

/**
 * Created by liugongce on 2017/7/19.
 */

public class ImageDelegate extends BaseDelegate {
    public ViewPagerFixed viewPager;

    @Override
    public void initView() {
        viewPager=getViewById(R.id.viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bigimage;
    }
}
