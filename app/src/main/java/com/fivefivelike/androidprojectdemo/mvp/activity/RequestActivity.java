package com.fivefivelike.androidprojectdemo.mvp.activity;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.entity.TabEntity;
import com.fivefivelike.androidprojectdemo.mvp.delegate.RequestDelegate;
import com.fivefivelike.androidprojectdemo.mvp.fragment.NormalRequestFragment;
import com.fivefivelike.androidprojectdemo.mvp.fragment.PageRequestFragment;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * Created by liugongce on 2017/10/11.
 */

public class RequestActivity extends BaseActivity<RequestDelegate> {
    @Override
    protected Class<RequestDelegate> getDelegateClass() {
        return RequestDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("数据请求"));
        ArrayList<CustomTabEntity>list=new ArrayList<>();
        list.add(new TabEntity("不带分页的列表",0,0));
        list.add(new TabEntity("带分页的列表",0,0));
        viewDelegate.tablayout.setTabData(list);
        viewDelegate.initAddFragment(R.id.fragmelayout,getSupportFragmentManager());
        viewDelegate.addFragment(new NormalRequestFragment());
        viewDelegate.addFragment(new PageRequestFragment());
        viewDelegate.tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.showFragment(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        viewDelegate.showFragment(0);
    }
}
