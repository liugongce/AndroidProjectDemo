package com.fivefivelike.androidprojectdemo.util;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.fivefivelike.androidprojectdemo.R;

/**
 * Created by liugongce on 2017/11/16.
 */

public class WeekView extends FrameLayout implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    String[] titles = {"一", "二", "三", "四", "五", "六", "七"};
    private ValueAnimator animatorStart;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private float animatedValue;
    private boolean isAniming = false;

    public WeekView(@NonNull Context context) {
        this(context, null);
    }

    public WeekView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    float x, y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float absx = Math.abs(x - event.getX());
                float absy = Math.abs(y - event.getY());
                if (absx < 20 && absy < 20) {//便宜量不大执行切换动画
                    startAnimator();
                }
                break;
        }
        return true;
    }

    /**
     * 开始动画
     */
    public void startAnimator() {
        if (animatorStart != null) {
            if (animatorStart.isRunning()) {
                return;
            }
            animatorStart.setFloatValues(animatedValue, x);
            animatorStart.start();
        } else {
            animatorStart = ValueAnimator.ofFloat(animatedValue, x).setDuration(500);
            animatorStart.setInterpolator(timeInterpolator);
            animatorStart.addUpdateListener(this);
            animatorStart.addListener(this);
            animatorStart.start();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int itemWid = measuredWidth / titles.length;
        drawBg(canvas);
        for (int i = 0; i < titles.length; i++) {
            canvas.save();
            drawItems(canvas, titles[i], itemWid * i, itemWid);
            canvas.restore();
        }

    }

    private void drawBg(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int itemWidth = measuredWidth / titles.length;
        int index = 0;//第几个条目
        if (x == measuredWidth) {
            index = titles.length - 1;
        } else {
            index = measuredWidth / itemWidth;
        }

//        String item = titles[index];
//
//        int measuredHeight = getMeasuredHeight();
//        //位置
//        int itemStart = index * itemWid;
//        int itemEnd = itemStart + itemWid;
//
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.GREEN);
//        Rect rect = new Rect();
//        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//        textPaint.getTextBounds(item, 0, item.length(), rect);
//        int width = rect.width();
//        int height = rect.height();
//
//        RectF rectF = new RectF(itemStart + itemWid / 2 - width / 2, 0, itemStart + itemWid / 2 + width / 2, measuredHeight);
//        canvas.drawRoundRect(rectF, 360, 360, paint);
    }

    private void drawItems(Canvas canvas, String item, int start, int itemWid) {
        int measuredHeight = getMeasuredHeight();
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        Rect rect = new Rect();
        textPaint.getTextBounds(item, 0, item.length(), rect);
        int width = rect.width();
        int height = rect.height();
        textPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.trans_24px));
        canvas.drawText(item, 0, item.length(), start + itemWid / 2 - width / 2, measuredHeight / 2 + height / 2, textPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        isAniming = true;
        animatedValue = (float) valueAnimator.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        isAniming = true;
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        isAniming = false;
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        isAniming = false;
    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
