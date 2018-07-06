package com.fivefivelike.androidprojectdemo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.entity.NewsEntity;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.CommonAdapter;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ViewHolder;


/**
 * Created by liugongce on 2017/7/28.
 */

public class NewsAdapter extends CommonAdapter<NewsEntity.ListBean> {
    private TextView tv_name;

    public NewsAdapter(Context context) {
        super(context, R.layout.adapter_main);
    }

    @Override
    protected void convert(ViewHolder holder, NewsEntity.ListBean listBean, int position) {
        tv_name=holder.getView(R.id.tv_name);
        tv_name.setText(listBean.getName());
    }
}
