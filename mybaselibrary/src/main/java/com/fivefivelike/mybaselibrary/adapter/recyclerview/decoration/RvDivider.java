package com.fivefivelike.mybaselibrary.adapter.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/11/16.
 */

public class RvDivider extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private Drawable mDivider;
    private int mDivierSize;
    private Paint mPaint;
    private int mOrientation = HORIZONTAL;
    private final Rect mBounds = new Rect();
    private Context mContext = GlobleContext.getInstance().getApplicationContext();
    private List<Integer> ignorePositionList = new ArrayList<>();

    public RvDivider setContext(Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * 设置分割线的粗细
     *
     * @param divierSize
     * @return
     */
    public RvDivider setDivierSize(int divierSize) {
        initPaint();
        this.mDivierSize = divierSize;
        return this;
    }

    /**
     * 设置分割线的粗细
     *
     * @param divierSizeRes dimen资源
     * @return
     */
    public RvDivider setDivierSizeRes(@DimenRes int divierSizeRes) {
        return setDivierSize(mContext.getResources().getDimensionPixelSize(divierSizeRes));
    }

    /**
     * 设置为竖向分割线，建议用于横向recycleview
     *
     * @return
     */
    public RvDivider setHorizontal() {
        mOrientation = HORIZONTAL;
        return this;
    }

    /**
     * 设置为横向分割线，建议用竖横向recycleview
     *
     * @return
     */
    public RvDivider setVertical() {
        mOrientation = VERTICAL;
        return this;
    }

    /**
     * 设置分割线颜色
     *
     * @param color
     * @return
     */
    public RvDivider setColor(@ColorInt int color) {
        initPaint();
        mPaint.setColor(color);
        return this;
    }

    /**
     * 设置颜色资源
     *
     * @param colorRes 颜色资源
     * @return
     */
    public RvDivider setColorRes(@ColorRes int colorRes) {
        return setColor(ContextCompat.getColor(mContext, colorRes));
    }

    /**
     * 获取默认竖向的样式
     *
     * @return
     */
    public RvDivider defaultVertical() {
        initPaint();
        mOrientation = VERTICAL;
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.basic_line));
        mDivierSize = mContext.getResources().getDimensionPixelSize(R.dimen.trans_1px);
        return this;
    }

    /**
     * 获取默认横向的样式
     *
     * @return
     */
    public RvDivider defaultHorizontal() {
        initPaint();
        mOrientation = HORIZONTAL;
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.basic_line));
        mDivierSize = mContext.getResources().getDimensionPixelSize(R.dimen.trans_1px);
        return this;
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
    }

    /**
     * 设置 分割线的drawable   设置了这个则{@link #mPaint} 相关设置则无效包括mDivierSize和color
     *
     * @param drawable
     * @return
     */
    public RvDivider setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
        return this;
    }

    /**
     * @param drawableRes drawable 资源
     * @return
     */
    public RvDivider setDrawableRes(@DrawableRes int drawableRes) {
        return setDrawable(ContextCompat.getDrawable(mContext, drawableRes));
    }

    public RvDivider setIgnorePosition(List<Integer> ingorePositionList) {
        this.ignorePositionList = ingorePositionList;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null && mPaint == null) {
            KLog.e(new NullPointerException("未设置正确的参数"));
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - (mDivider != null ? mDivider.getIntrinsicHeight() : mDivierSize);
            finalDraw(canvas, top, bottom, right, left);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - (mDivider != null ? mDivider.getIntrinsicWidth() : mDivierSize);
            finalDraw(canvas, top, bottom, right, left);
        }
        canvas.restore();
    }

    private void finalDraw(Canvas canvas, int top, int bottom, int right, int left) {
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        } else if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        if (mDivider == null && mPaint == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            int height;
            if (mDivider != null) {
                height = mDivider.getIntrinsicHeight();
            } else {
                height = mDivierSize;
            }
            if (!ignorePositionList.isEmpty()) {
                if (ignorePositionList.contains(childLayoutPosition)) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, 0, 0, height);
                }
            } else {
                outRect.set(0, 0, 0, height);
            }
        } else {
            int width;
            if (mDivider != null) {
                width = mDivider.getIntrinsicWidth();
            } else {
                width = mDivierSize;
            }

            if (!ignorePositionList.isEmpty()) {
                if (ignorePositionList.contains(childLayoutPosition)) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, 0, width, 0);
                }
            } else {
                outRect.set(0, 0, width, 0);
            }
        }
    }
}
