package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.adapter.WavebarAdapter;
import com.fivefivelike.androidprojectdemo.entity.WavebarEntity;
import com.fivefivelike.androidprojectdemo.mvp.delegate.WavebarDelegate;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.WaveSideBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/7/26.
 */

public class WaveBarActivity extends BaseActivity<WavebarDelegate> {
    WavebarAdapter adapter;
    List list = new ArrayList();
    private WavebarEntity wavebarEntity;

    @Override
    protected Class<WavebarDelegate> getDelegateClass() {
        return WavebarDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("wavebar字母索引"));
        viewDelegate.setOnClickListener(this, R.id.layout_buy, R.id.layout_sale, R.id.layout_sale_order, R.id.layout_search);
        initSideBar();
        initRv();
        getData();
    }

    private void getData() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("wavebar_page_data.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String data = new String(buffer, "UTF-8");
            wavebarEntity = GsonUtil.getInstance().toObj(data, WavebarEntity.class);
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initSideBar() {
        viewDelegate.viewHolder.side_bar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                scrollLayout(index);

            }
        });
    }

    private void scrollLayout(String index) {
        if (list.contains(index)) {
            int i = list.indexOf(index);//第几个
            View viewByPosition = viewDelegate.viewHolder.recycler_view.getLayoutManager().findViewByPosition(i);
            int top = viewDelegate.viewHolder.recycler_view.getTop();
            int top1 = viewByPosition.getTop();
            viewDelegate.viewHolder.scrollView.scrollTo(0, top + top1);
        }
    }


    private void initRv() {
        adapter = new WavebarAdapter(this, list);
        viewDelegate.viewHolder.recycler_view.setNestedScrollingEnabled(false);
        viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        viewDelegate.viewHolder.recycler_view.setAdapter(adapter);
    }

    private void initData() {
        list.clear();
        List<WavebarEntity.ListBean> keylist = wavebarEntity.getKeylist();
        List<WavebarEntity.DatalistBean> datalist = wavebarEntity.getDatalist();
        if (keylist != null && keylist.size() > 0) {
            list.add("重点客户");
            list.addAll(keylist);
        }
        if (datalist != null && datalist.size() > 0) {
            for (WavebarEntity.DatalistBean item : datalist) {
                list.add(item.getName());
                list.addAll(item.getList());
            }
        }
        adapter.notifyDataSetChanged();
    }

}
