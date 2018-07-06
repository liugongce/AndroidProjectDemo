package com.fivefivelike.mybaselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.fivefivelike.mybaselibrary.R;


/**
 * Created by liugongce on 2017/12/1.
 * 自定义背景textView
 */

public class DrawableTextView extends AppCompatTextView {
    GradientDrawable backDrawable;
    private float stroke_width;
    private int stroke_color;
    private int solid;
    private float radius;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public GradientDrawable getBackDrawable() {
        return backDrawable;
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        solid = typedArray.getColor(R.styleable.DrawableTextView_solid, Color.WHITE);
        radius = typedArray.getDimension(R.styleable.DrawableTextView_radius, 0);
        stroke_color = typedArray.getColor(R.styleable.DrawableTextView_stroke_color, Color.WHITE);
        stroke_width = typedArray.getDimension(R.styleable.DrawableTextView_stroke_width, 0);
        typedArray.recycle();
        setSolid(solid);
        setRadius(radius);
        setStrokeColor(stroke_color);
        setStrokeWith(stroke_width);
    }


    public void setSolid(@ColorInt int solid) {
        this.solid = solid;
        initDrawable();
        backDrawable.setColor(solid);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        initDrawable();
        backDrawable.setCornerRadius(radius);
    }


    public void setStrokeColor(@ColorInt int strokeColor) {
        this.stroke_color = strokeColor;
        initDrawable();
        backDrawable.setStroke((int) stroke_width, stroke_color);
    }

    private void initDrawable() {
        if (backDrawable == null) {
            backDrawable = new GradientDrawable();
        }
    }

    public void setStrokeWith(float strokeWith) {
        this.stroke_width = strokeWith;
        initDrawable();
        backDrawable.setStroke((int) stroke_width, stroke_color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackground(backDrawable);
    }
}
