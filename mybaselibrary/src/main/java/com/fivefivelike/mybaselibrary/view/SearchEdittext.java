package com.fivefivelike.mybaselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.R;

public class SearchEdittext extends AppCompatEditText {
    private Drawable mRightDrawable;
    private boolean isHasFocus;
    private TextChangeListener listener;
    private int clearCirColor;//圆形背景色
    private boolean show_clear;
    private boolean isDoClear;

    public SearchEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SearchEdittext);
        clearCirColor = array.getColor(R.styleable.SearchEdittext_clear_color, Color.parseColor("#77000000"));
        show_clear = array.getBoolean(R.styleable.SearchEdittext_show_clear, true);
        array.recycle();
        init();
    }

    public void setClearCirColor(int clearCirColor) {
        this.clearCirColor = clearCirColor;
        invalidate();
    }

    public void setShow_clear(boolean show_clear) {
        this.show_clear = show_clear;
        invalidate();
    }

    public SearchEdittext(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SearchEdittext(Context context) {
        this(context, null);
    }

    private void init() {

        // getCompoundDrawables:
        // Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = this.getCompoundDrawables();
        mRightDrawable = drawables[2];
        setClearDrawableVisible(false);
        // 设置焦点变化的监听
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        // 设置EditText文字变化的监听
        this.addTextChangedListener(new TextWatcherImpl());
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    if (listener != null) {
                        listener.actionSearch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 绘制清除按钮
     *
     * @param height
     */
    private void initDrawable(int height) {
        int textSize = (int) getTextSize();
        Bitmap bitmap = Bitmap.createBitmap(textSize * 2, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        //绘制x文字
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize * 2 / 3);
        textPaint.setColor(Color.RED);
        String text = "X";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, 0, text.length(), textSize - textBounds.width() / 2, height / 2 + textBounds.height() / 2, textPaint);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //绘制背景区域正
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, textSize * 2, height, paint);
        //绘制背景圆形
        paint.setColor(clearCirColor);
        //使用xfermode 干掉x区域
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawCircle(textSize, height / 2, textSize / 2, paint);
        canvas.restore();
        mRightDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
        mRightDrawable.setBounds(0, 0, textSize * 2, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    isDoClear = true;
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = !TextUtils.isEmpty(getText().toString());
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    // 当输入结束后判断是否显示右边clean的图标
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = !TextUtils.isEmpty(getText().toString());
            setClearDrawableVisible(isVisible);
            if (listener != null) {
                listener.getInput(isDoClear, s.toString());
            }
            isDoClear = false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRightDrawable == null && getMeasuredHeight() > 0) {
            setClearDrawableVisible(false);
        }
    }


    protected void setClearDrawableVisible(boolean isVisible) {
        if (mRightDrawable == null) {
            synchronized (SearchEdittext.class) {
                if (mRightDrawable == null) {
                    int measuredHeight = getMeasuredHeight();
                    if (measuredHeight > 0) {
                        initDrawable(measuredHeight);
                        setClearDrawable(isVisible);
                    }
                }
            }
        } else {
            setClearDrawable(isVisible);
        }
    }

    // 隐藏或者显示右边clean的图标
    private void setClearDrawable(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible && show_clear) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        // 使用代码设置该控件left, top, right, and bottom处的图标
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], rightDrawable,
                getCompoundDrawables()[3]);
    }

    // 显示一个动画,以提示用户输入
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    // CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public interface TextChangeListener {
        /**
         * @param isDoClear 是否是点击了清除按钮
         * @param text      获取输入
         */
        void getInput(boolean isDoClear, String text);

        /**
         * 获取点击回车事件
         */
        void actionSearch();
    }


    public void initListener(TextChangeListener listener) {
        this.listener = listener;
    }

}