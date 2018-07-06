package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.WaveSideBar;
import com.yanzhenjie.recyclerview.swipe.widget.StickyNestedScrollView;

/**
 * Created by liugongce on 2017/8/3.
 */

public class WavebarDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wavebar;
    }

    public static class ViewHolder {
        public View rootView;
        public FrameLayout layout_search;
        public LinearLayout layout_buy;
        public LinearLayout layout_sale;
        public LinearLayout layout_sale_order;
        public RecyclerView recycler_view;
        public WaveSideBar side_bar;
        public StickyNestedScrollView scrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.layout_search = (FrameLayout) rootView.findViewById(R.id.layout_search);
            this.layout_buy = (LinearLayout) rootView.findViewById(R.id.layout_buy);
            this.layout_sale = (LinearLayout) rootView.findViewById(R.id.layout_sale);
            this.layout_sale_order = (LinearLayout) rootView.findViewById(R.id.layout_sale_order);
            this.recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            this.side_bar = (WaveSideBar) rootView.findViewById(R.id.side_bar);
            this.scrollView = (StickyNestedScrollView) rootView.findViewById(R.id.scrollView);
        }

    }
}
