package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.androidprojectdemo.adapter.MainAdapter;
import com.fivefivelike.androidprojectdemo.mvp.delegate.MainDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liugongce on 2017/10/11.
 */

public class TablayoutActivity extends BaseActivity<MainDelegate> {
    private List<String> list;
    private MainAdapter adapter;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("Tablayout"));
        list = new ArrayList<>();
        list.add("slidingtablayout");
        list.add("commontablayout");
        list.add("segmenttablayout");
        viewDelegate.recycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this);
        adapter.setDatas(list);
        viewDelegate.recycleview.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                initListener(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initListener(int position) {
        String content = list.get(position);
        if (content.equals("slidingtablayout")) {

            startActivity(new Intent(this, SlidingTabActivity.class));
        } else if (content.equals("commontablayout")) {
            startActivity(new Intent(this, CommonTabActivity.class));

        } else if (content.equals("segmenttablayout")) {
            startActivity(new Intent(this, SegmentTabActivity.class));
        }
    }
}
