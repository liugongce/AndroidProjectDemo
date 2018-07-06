package com.fivefivelike.mybaselibrary.view.popupWindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.CommonAdapter;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liugongce on 2017/8/17.
 */

public class ChoosePopupWindow extends BasePopupWindow {


    private RecyclerView recycler_view;
    private PopupAdapter adapter;

    public ChoosePopupWindow(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.popup_input;
    }

    @Override
    public void initView() {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        recycler_view = findViewById(R.id.recyclerView);
        adapter = new PopupAdapter(context);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public ChoosePopupWindow setSelectItem(String[] items) {
        adapter.getDatas().clear();
        List<String> itemList = Arrays.asList(items);
        if (itemList != null && itemList.size() > 0) {
            adapter.updateDatas(itemList);
        }
        adapter.notifyDataSetChanged();
        return this;
    }



    class PopupAdapter extends CommonAdapter<String> {

        public PopupAdapter(Context context) {
            super(context, 0);
        }

        @Override
        protected void convert(ViewHolder holder, String s, int position) {
//            holder.setText(R.id.tv_name, s);
//            holder.setVisible(R.id.view_line, position != mDatas.size() - 1);
        }
    }
    
}
