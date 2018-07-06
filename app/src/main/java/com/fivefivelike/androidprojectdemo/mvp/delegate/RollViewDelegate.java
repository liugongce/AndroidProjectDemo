package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.widget.RelativeLayout;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;

/**
 * Created by liugongce on 2017/7/28.
 */

public class RollViewDelegate extends BaseDelegate {
    public RelativeLayout rl_banner,rl_banner1;
    @Override
    public void initView() {
        rl_banner = getViewById(R.id.rl_banner1);
        rl_banner1 = getViewById(R.id.rl_banner2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_roll_view;
    }
}
