package com.fivefivelike.androidprojectdemo.mvp.delegate;

import android.widget.TextView;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;

/**
 * Created by liugongce on 2017/10/11.
 */

public class SimpleCardDelegate extends BaseDelegate {
    public TextView card_title_tv;

    @Override
    public void initView() {
        card_title_tv=getViewById(R.id.card_title_tv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_simple_card;
    }
}
