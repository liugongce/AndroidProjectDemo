package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.adapter.MainAdapter;
import com.fivefivelike.androidprojectdemo.mvp.delegate.MainDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.decoration.RvDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2018/1/18.
 */

public class ToastActivity extends BaseActivity<MainDelegate> {
    private List<String> list;
    private MainAdapter adapter;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("吐司"));
        list = new ArrayList<>();
        list.add("错误吐司");
        list.add("成功吐司");
        list.add("信息展示");
        list.add("警告吐司");
        list.add("普通吐司");
        viewDelegate.recycleview.setLayoutManager(new LinearLayoutManager(this));
        viewDelegate.recycleview.addItemDecoration(new RvDivider().
                setVertical()
                .setDrawableRes(R.drawable.shape_div));
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
        if (content.equals("错误吐司")) {
            ToastUtil.error("这是一个错误");
        } else if (content.equals("成功吐司")) {
            ToastUtil.success("这是一个成功的事情");
        } else if (content.equals("信息展示")) {
            ToastUtil.info("这是一个信息而已");
        } else if (content.equals("警告吐司")) {
            ToastUtil.warning("这是一个严重的警告");
        } else if (content.equals("普通吐司")) {
            ToastUtil.show("这是一个普通的吐司");
        }
    }
}
