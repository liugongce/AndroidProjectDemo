package com.fivefivelike.mybaselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.R;

/**
 * Created by liugongce on 2017/10/12.
 */

public class PopupBackView extends ViewGroup {
    private Paint mPaint;
    private Bitmap bufferBitmap;
    private Canvas bufferCanvas;
    private float arrowWidth;
    private int arrowGravity;
    private float arrowMarginLeft;
    private float arrowMarginRight;
    private int backageColor;
    private float roundConner;
    private float arrowHeight;
    private DisplayMetrics displayMetrics;
    public static final int ARROW_ALIGN_LEFT = 0;
    public static final int ARROW_ALIGN_CENTER = 1;
    public static final int ARROW_ALIGN_RIGHT = 2;

    public PopupBackView(Context context) {
        this(context, null);
    }

    public PopupBackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupBackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PopupBackView);
        arrowWidth = typedArray.getDimension(R.styleable.PopupBackView_arrowWidth, dp2px(10));
        arrowMarginLeft = typedArray.getDimension(R.styleable.PopupBackView_arrow_margin_left, 0);
        arrowMarginRight = typedArray.getDimension(R.styleable.PopupBackView_arrow_margin_right, 0);
        roundConner = typedArray.getDimension(R.styleable.PopupBackView_roundConner, dp2px(5));
        arrowGravity = typedArray.getInt(R.styleable.PopupBackView_arrow_gravity, ARROW_ALIGN_RIGHT);
        backageColor = typedArray.getColor(R.styleable.PopupBackView_backageColor, Color.parseColor("#77000000"));
        arrowHeight = (float) Math.sqrt(Math.pow(arrowWidth, 2) - Math.pow(arrowWidth / 2, 2));
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        init();
        // 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int layoutWidth = 0;
        int layoutHeight = 0;
        int cWidth = 0;
        int cHeight = 0;
        int count = getChildCount();

        // 计算出所有的childView的宽和高
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        CustomLayoutParams params = null;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果布局容器的宽度模式时确定的（具体的size或者match_parent）
            layoutWidth = sizeWidth;
        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();
                params = (CustomLayoutParams) child.getLayoutParams();
                //获取子控件宽度和左右边距之和，作为这个控件需要占据的宽度
                int marginWidth = cWidth + params.leftMargin + params.rightMargin;
                layoutWidth = marginWidth > layoutWidth ? marginWidth : layoutWidth;
            }
        }
        //高度很宽度处理思想一样
        if (heightMode == MeasureSpec.EXACTLY) {
            layoutHeight = sizeHeight;
        } else {
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                params = (CustomLayoutParams) child.getLayoutParams();
                int marginHeight = cHeight + params.topMargin + params.bottomMargin;
                layoutHeight = marginHeight > layoutHeight ? marginHeight : layoutHeight;
            }
        }
        // 测量并保存layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);
        //测量后绘制背景
        drawBuffer();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        CustomLayoutParams params = null;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            // 注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            params = (CustomLayoutParams) child.getLayoutParams();
            switch (params.position) {
                case CustomLayoutParams.POSITION_MIDDLE:    // 中间
                    left = (getWidth() - childMeasureWidth) / 2 - params.rightMargin + params.leftMargin;
                    top = (getHeight() - childMeasureHeight) / 2 + params.topMargin - params.bottomMargin;
                    break;
                case CustomLayoutParams.POSITION_LEFT:      // 左上方
                    left = 0 + params.leftMargin;
                    top = 0 + params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_RIGHT:     // 右上方
                    left = getWidth() - childMeasureWidth - params.rightMargin;
                    top = 0 + params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_BOTTOM:    // 左下角
                    left = 0 + params.leftMargin;
                    top = getHeight() - childMeasureHeight - params.bottomMargin;
                    break;
                case CustomLayoutParams.POSITION_RIGHTANDBOTTOM:// 右下角
                    left = getWidth() - childMeasureWidth - params.rightMargin;
                    top = getHeight() - childMeasureHeight - params.bottomMargin;
                    break;
                default:
                    break;
            }
            top = (int) (top + arrowHeight);
            // 确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, left + childMeasureWidth, top + childMeasureHeight);
        }
    }


    private void init() {
        bufferBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        bufferCanvas = new Canvas(bufferBitmap);
    }

    /**
     * 绘制背景
     */
    private void drawBuffer() {
        init();
        initPaint();
        drawTriangle();
        drawRound();
        Drawable drawable = new BitmapDrawable(getContext().getResources(), bufferBitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        setBackground(drawable);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(backageColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * 绘制三角形
     */
    private void drawTriangle() {
        int measuredWidth = getMeasuredWidth();
        float startYpoint = measuredWidth - roundConner;
        switch (arrowGravity) {
            case ARROW_ALIGN_LEFT://左边
                startYpoint = roundConner + arrowWidth;
                break;
            case ARROW_ALIGN_CENTER://中间
                startYpoint = measuredWidth / 2 + arrowWidth / 2;
                break;
            case ARROW_ALIGN_RIGHT://右边
                startYpoint = measuredWidth - roundConner;
                break;
        }
        if (arrowMarginLeft != 0) {//左边距
            if (startYpoint + arrowMarginLeft >= measuredWidth - roundConner) {
                startYpoint = measuredWidth - roundConner;
            } else {
                startYpoint += arrowMarginLeft;
            }
        } else {//右边距
            if (arrowMarginRight != 0) {
                if (startYpoint - arrowMarginLeft <= roundConner + arrowWidth) {
                    startYpoint = roundConner + arrowWidth;
                } else {
                    startYpoint -= arrowMarginLeft;
                }
            }
        }
        Path path = new Path();
        path.moveTo(startYpoint, arrowHeight);
        path.lineTo(startYpoint - arrowWidth / 2, 0);
        path.lineTo(startYpoint - arrowWidth, arrowHeight);
        path.lineTo(startYpoint, arrowHeight);
        path.close();
        bufferCanvas.drawPath(path, mPaint);
    }

    /**
     * 绘制矩形
     */
    private void drawRound() {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        RectF rectf = new RectF();
        rectf.left = 0;
        rectf.top = arrowHeight;
        rectf.right = measuredWidth;
        rectf.bottom = measuredHeight;
        bufferCanvas.drawRoundRect(rectf, roundConner, roundConner, mPaint);
    }

    private float dp2px(int dp) {
        displayMetrics = getContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    public static class CustomLayoutParams extends MarginLayoutParams {
        public static final int POSITION_MIDDLE = 0; // 中间
        public static final int POSITION_LEFT = 1; // 左上方
        public static final int POSITION_RIGHT = 2; // 右上方
        public static final int POSITION_BOTTOM = 3; // 左下角
        public static final int POSITION_RIGHTANDBOTTOM = 4; // 右下角

        public int position = POSITION_LEFT;  // 默认我们的位置就是左上角

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PopupBackView);
            //获取设置在子控件上的位置属性
            position = a.getInt(R.styleable.PopupBackView_layout_position, position);
            a.recycle();
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

    }


}
