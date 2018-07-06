package com.fivefivelike.androidprojectdemo.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.androidprojectdemo.adapter.NewsAdapter;
import com.fivefivelike.androidprojectdemo.entity.NewsEntity;
import com.fivefivelike.androidprojectdemo.mvp.databinder.NormalRequestDataBinder;
import com.fivefivelike.androidprojectdemo.mvp.delegate.NormalRequestDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/7/28.
 */

public class NormalRequestFragment extends BaseDataBindFragment<NormalRequestDelegate, NormalRequestDataBinder> {
    private List<NewsEntity.ListBean> list;
    private NewsAdapter adapter;

    @Override
    public NormalRequestDataBinder getDataBinder(NormalRequestDelegate viewDelegate) {
        return new NormalRequestDataBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initRv();
        addRequest(binder.getData(this, "1", "20"));
    }

    private void initRv() {
        list = new ArrayList<>();
        adapter = new NewsAdapter(getActivity());
        adapter.setDatas(list);
        viewDelegate.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewDelegate.recycleview.setAdapter(adapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                NewsEntity newsEntity = GsonUtil.getInstance().toObj(data, NewsEntity.class);
                List<NewsEntity.ListBean> list = newsEntity.getList();
                if (list != null && list.size() > 0) {
                    this.list.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected Class<NormalRequestDelegate> getDelegateClass() {
        return NormalRequestDelegate.class;
    }
}
