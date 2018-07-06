package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.SlidingTabLayout;

/**
 * Created by liugongce on 2017/10/11.
 */

public class SlidingTabDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sliding_tab;
    }

    public static class ViewHolder {
        public View rootView;
        public SlidingTabLayout tl_1;
        public SlidingTabLayout tl_2;
        public SlidingTabLayout tl_3;
        public SlidingTabLayout tl_4;
        public SlidingTabLayout tl_5;
        public SlidingTabLayout tl_6;
        public SlidingTabLayout tl_7;
        public SlidingTabLayout tl_8;
        public SlidingTabLayout tl_9;
        public SlidingTabLayout tl_10;
        public ViewPager vp;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_1 = (SlidingTabLayout) rootView.findViewById(R.id.tl_1);
            this.tl_2 = (SlidingTabLayout) rootView.findViewById(R.id.tl_2);
            this.tl_3 = (SlidingTabLayout) rootView.findViewById(R.id.tl_3);
            this.tl_4 = (SlidingTabLayout) rootView.findViewById(R.id.tl_4);
            this.tl_5 = (SlidingTabLayout) rootView.findViewById(R.id.tl_5);
            this.tl_6 = (SlidingTabLayout) rootView.findViewById(R.id.tl_6);
            this.tl_7 = (SlidingTabLayout) rootView.findViewById(R.id.tl_7);
            this.tl_8 = (SlidingTabLayout) rootView.findViewById(R.id.tl_8);
            this.tl_9 = (SlidingTabLayout) rootView.findViewById(R.id.tl_9);
            this.tl_10 = (SlidingTabLayout) rootView.findViewById(R.id.tl_10);
            this.vp = (ViewPager) rootView.findViewById(R.id.vp);
        }

    }
}
