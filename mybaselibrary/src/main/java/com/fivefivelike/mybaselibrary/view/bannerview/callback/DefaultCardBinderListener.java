package com.fivefivelike.mybaselibrary.view.bannerview.callback;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.view.RoundFrameLayout;

import java.util.List;

/**
 * Created by liugongce on 2017/11/29.
 */

public class DefaultCardBinderListener implements OnPagerAdapterBinerListener {
    private Context mContext;
    private int radius;

    public DefaultCardBinderListener(Context mContext, int radius) {
        this.mContext = mContext;
        this.radius = radius;
    }

    @Override
    public View bind(ViewGroup container, List bannerList, int position) {
        View view = bind(bannerList, position);
        RoundFrameLayout roundFrameLayout = new RoundFrameLayout(mContext);
        roundFrameLayout.setRadius(radius);
        roundFrameLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(roundFrameLayout);
        return roundFrameLayout;
    }

    private View bind(List bannerList, int position) {
        Object object = bannerList.get(position);
        if (object instanceof View) {
            View view = (View) object;
            ViewParent viewParent = view.getParent();
            if (viewParent != null) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(view);
            }
            return view;

        } else {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(object)
                    .apply(new RequestOptions()
                            .error(R.drawable.loaderror)).into(iv);
            return iv;
        }
    }

}
