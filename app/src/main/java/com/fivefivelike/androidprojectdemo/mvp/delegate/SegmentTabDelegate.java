package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.SegmentTabLayout;

/**
 * Created by liugongce on 2017/10/11.
 */

public class SegmentTabDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_segment_tab;
    }

    public static class ViewHolder {
        public View rootView;
        public SegmentTabLayout tl_1;
        public SegmentTabLayout tl_2;
        public SegmentTabLayout tl_3;
        public ViewPager vp_2;
        public SegmentTabLayout tl_4;
        public FrameLayout fl_change;
        public SegmentTabLayout tl_5;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_1 = (SegmentTabLayout) rootView.findViewById(R.id.tl_1);
            this.tl_2 = (SegmentTabLayout) rootView.findViewById(R.id.tl_2);
            this.tl_3 = (SegmentTabLayout) rootView.findViewById(R.id.tl_3);
            this.vp_2 = (ViewPager) rootView.findViewById(R.id.vp_2);
            this.tl_4 = (SegmentTabLayout) rootView.findViewById(R.id.tl_4);
            this.fl_change = (FrameLayout) rootView.findViewById(R.id.fl_change);
            this.tl_5 = (SegmentTabLayout) rootView.findViewById(R.id.tl_5);
        }

    }
}
