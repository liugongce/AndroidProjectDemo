package com.fivefivelike.mybaselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.R;


/**
 * package : com.fivefivelike.jinyingwu.widget<br>
 * description:<B>长宽指定比例的布局</B><br>
 * 通过whichIsFinal指定宽或高为固定的(不指定则为宽).<br>
 * 通过percentage指定比例,可变长度=percentage*固定长度<br>
 * data : 2017/11/9 下午4:52<br>
 *
 * @author XieGuoKang
 */

public class PercentageLinearLayout extends LinearLayout {
    private static final int WHICH_IS_FINAL_WIDTH = 0;
    private static final int WHICH_IS_FINAL_HEIGHT = 1;
    private int whichIsFinal;
    private int widWeight;
    private int heiWeight;

    public PercentageLinearLayout(Context context) {
        this(context, null);
    }

    public PercentageLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentageLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentageLinearLayout, 0, defStyle);
        whichIsFinal = ta.getInt(R.styleable.PercentageLinearLayout_whichIsFinal, WHICH_IS_FINAL_WIDTH);
        widWeight = ta.getInteger(R.styleable.PercentageLinearLayout_wid_weight, 1);
        heiWeight = ta.getInteger(R.styleable.PercentageLinearLayout_hei_weight, 1);
        ta.recycle();
    }

    public void setWhichIsFinal(int whichIsFinal) {
        this.whichIsFinal = whichIsFinal;
    }

    public void setWidWeight(int widWeight) {
        this.widWeight = widWeight;
    }

    public void setHeiWeight(int heiWeight) {
        this.heiWeight = heiWeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (whichIsFinal) {
            case WHICH_IS_FINAL_WIDTH: {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = width * heiWeight / widWeight;
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                break;
            }
            case WHICH_IS_FINAL_HEIGHT: {
                int height = MeasureSpec.getSize(heightMeasureSpec);
                int width = height * widWeight / heiWeight;
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                break;
            }
        }
    }
}
