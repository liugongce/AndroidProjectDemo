package com.fivefivelike.mybaselibrary.view.bannerview.view;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by liugongce on 2017/11/29.
 */

public class PointRoundDrawable extends GradientDrawable {
    public PointRoundDrawable(int color) {
        setShape(OVAL);
        setColor(color);
    }


}
