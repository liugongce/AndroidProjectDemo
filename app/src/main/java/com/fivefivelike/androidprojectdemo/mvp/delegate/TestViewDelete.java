package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.view.View;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.util.GlideUtils;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by liugongce on 2017/11/16.
 */

public class TestViewDelete extends BaseDelegate {
    ViewHolder viewHolder;
    @Override
    public void initView() {
        viewHolder=new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_testview;
    }

    public static class ViewHolder {
        public View rootView;
        public RoundedImageView iv_icon;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_icon = (RoundedImageView) rootView.findViewById(R.id.iv_icon);
        }

    }
}
