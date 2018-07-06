package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.CommonTabLayout;

/**
 * Created by liugongce on 2017/10/11.
 */

public class CommonTabDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_tab;
    }

    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl_1;
        public ViewPager vp_2;
        public CommonTabLayout tl_2;
        public FrameLayout fl_change;
        public CommonTabLayout tl_3;
        public CommonTabLayout tl_4;
        public CommonTabLayout tl_5;
        public CommonTabLayout tl_6;
        public CommonTabLayout tl_7;
        public CommonTabLayout tl_8;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.vp_2 = (ViewPager) rootView.findViewById(R.id.vp_2);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.fl_change = (FrameLayout) rootView.findViewById(R.id.fl_change);
            this.tl_3 = (CommonTabLayout) rootView.findViewById(R.id.tl_3);
            this.tl_4 = (CommonTabLayout) rootView.findViewById(R.id.tl_4);
            this.tl_5 = (CommonTabLayout) rootView.findViewById(R.id.tl_5);
            this.tl_6 = (CommonTabLayout) rootView.findViewById(R.id.tl_6);
            this.tl_7 = (CommonTabLayout) rootView.findViewById(R.id.tl_7);
            this.tl_8 = (CommonTabLayout) rootView.findViewById(R.id.tl_8);
        }

    }
}
