package com.fivefivelike.androidprojectdemo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.entity.WavebarEntity;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ItemViewDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by liugongce on 2017/10/9.
 */

public class WavebarAdapter extends MultiItemTypeAdapter {

    public WavebarAdapter(Context context, List datas) {
        super(context, datas);
        addItemViewDelegate(new CrmHeadAdapter());
        addItemViewDelegate(new CrmItemAdapter());
    }

    class CrmHeadAdapter implements ItemViewDelegate<Object> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.adapter_crm_head;
        }

        @Override
        public boolean isForViewType(Object item, int position) {
            return item instanceof String;
        }

        @Override
        public void convert(ViewHolder holder, Object o, int position) {
            String item = (String) o;
            holder.setText(R.id.tv_name, item);
        }
    }

    class CrmItemAdapter implements ItemViewDelegate<Object> {
        private TextView tv_name;
        private TextView tv_company_name;

        @Override
        public int getItemViewLayoutId() {
            return R.layout.adapter_crm_item;
        }

        @Override
        public boolean isForViewType(Object item, int position) {
            return item instanceof WavebarEntity.ListBean;
        }

        @Override
        public void convert(ViewHolder holder, Object o, int position) {
            tv_name = holder.getView(R.id.tv_name);
            tv_company_name = holder.getView(R.id.tv_company_name);
            final WavebarEntity.ListBean item = (WavebarEntity.ListBean) o;
            tv_name.setText(item.getName());
            tv_company_name.setText(item.getCompanyname());
        }
    }
}
