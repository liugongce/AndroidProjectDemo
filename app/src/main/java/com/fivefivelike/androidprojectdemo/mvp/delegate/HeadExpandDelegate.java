package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.widget.ImageView;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.DeviceUtils;
import com.fivefivelike.mybaselibrary.view.HeadZoomScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liugongce on 2017/7/28.
 */

public class HeadExpandDelegate extends BaseDelegate {
    public ImageView iv_head;
    public CircleImageView iv_icon;
    public HeadZoomScrollView scrollView;

    @Override
    public void initView() {
        iv_head = getViewById(R.id.iv_head);
        iv_icon = getViewById(R.id.iv_icon);
        scrollView = getViewById(R.id.scrollView);
        scrollView.setZoomView(iv_head);
        iv_head.getLayoutParams().height = DeviceUtils.getScreenWidth() * 510 / 960;
        iv_head.requestLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_expand;
    }
}
