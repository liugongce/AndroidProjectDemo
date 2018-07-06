package com.fivefivelike.androidprojectdemo.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.androidprojectdemo.adapter.NewsAdapter;
import com.fivefivelike.androidprojectdemo.entity.NewsEntity;
import com.fivefivelike.androidprojectdemo.mvp.databinder.PageRequestDataBinder;
import com.fivefivelike.androidprojectdemo.mvp.delegate.PageRequestDelegate;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;

/**
 * Created by liugongce on 2017/7/28.
 */

public class PageRequestFragment extends BasePullFragment<PageRequestDelegate, PageRequestDataBinder> {
    private NewsAdapter adapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initRv();
        refreshData();
    }

    @Override
    protected Class<PageRequestDelegate> getDelegateClass() {
        return PageRequestDelegate.class;
    }

    private void initRv() {
        adapter = new NewsAdapter(getActivity());
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
    }

    @Override
    public PageRequestDataBinder getDataBinder(PageRequestDelegate viewDelegate) {
        return new PageRequestDataBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                NewsEntity newsEntity = GsonUtil.getInstance().toObj(data, NewsEntity.class);
                onGetData(newsEntity.getList(), adapter);//调用封装好的
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getData(this, viewDelegate.page, viewDelegate.pagesize));
    }
}
