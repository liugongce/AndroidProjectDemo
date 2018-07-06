package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wheelview.Wheel3DView;
import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;

/**
 * Created by liugongce on 2017/10/17.
 */

public class TestDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    @Override
    public void initView() {
        viewHolder=new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wheelview3d;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView textView;
        public TextView textView2;
        public Wheel3DView wv_year;
        public Wheel3DView wv_month;
        public Wheel3DView wv_day;
        public RelativeLayout root;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.textView = (TextView) rootView.findViewById(R.id.textView);
            this.textView2 = (TextView) rootView.findViewById(R.id.textView2);
            this.wv_year = (Wheel3DView) rootView.findViewById(R.id.wv_year);
            this.wv_month = (Wheel3DView) rootView.findViewById(R.id.wv_month);
            this.wv_day = (Wheel3DView) rootView.findViewById(R.id.wv_day);
            this.root = (RelativeLayout) rootView.findViewById(R.id.root);
        }

    }
}
