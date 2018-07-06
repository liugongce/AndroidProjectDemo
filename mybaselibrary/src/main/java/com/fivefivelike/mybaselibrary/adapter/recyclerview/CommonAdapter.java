package com.fivefivelike.mybaselibrary.adapter.recyclerview;

import android.content.Context;

import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ItemViewDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonAdapter(final Context context, final int layoutId)
    {
        super(context, new ArrayList<T>());
        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
