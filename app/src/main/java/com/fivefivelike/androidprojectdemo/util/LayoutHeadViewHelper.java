package com.fivefivelike.androidprojectdemo.util;

import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.utils.logger.KLog;

import java.util.ArrayList;

/**
 * Created by liugongce on 2017/12/13.
 */

public class LayoutHeadViewHelper {

    public static String HEAD_VIEW_TAG = "sticky";
    private ViewGroup mViewGroup;
    private ArrayList<View> mHeadViewList = new ArrayList<>();

    public LayoutHeadViewHelper(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
        addHeadView(viewGroup);
        addHeadLayoutChangeListener();
    }

    public void addHeadLayoutChangeListener(){
        int size = mHeadViewList.size();
        for(int i=0;i<size;i++){
            View view = mHeadViewList.get(size);
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    KLog.i("left"+left+"");
                }
            });
        }
    }

    //添加悬浮控件
    private void addHeadView(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(childCount);
            if (getViewTag(child).equals(HEAD_VIEW_TAG)) {//有标签的view
                mHeadViewList.add(child);
            } else {
                if (child instanceof ViewGroup) {
                    addHeadView((ViewGroup) child);
                }
            }
        }
    }

    private String getViewTag(View view) {
        return view==null?"":String.valueOf(view.getTag());
    }



}
