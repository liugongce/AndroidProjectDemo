package com.fivefivelike.androidprojectdemo.mvp.fragment;

import com.fivefivelike.androidprojectdemo.mvp.delegate.SimpleCardDelegate;
import com.fivefivelike.mybaselibrary.base.BaseFragment;

public class SimpleCardFragment extends BaseFragment<SimpleCardDelegate> {
    private String mTitle;

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.card_title_tv.setText(mTitle);
    }

    @Override
    protected Class<SimpleCardDelegate> getDelegateClass() {
        return SimpleCardDelegate.class;
    }
}