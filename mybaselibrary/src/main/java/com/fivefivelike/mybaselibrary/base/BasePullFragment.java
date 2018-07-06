package com.fivefivelike.mybaselibrary.base;

import android.support.v7.widget.RecyclerView;

import com.fivefivelike.mybaselibrary.callback.AdapterUpdateIml;
import com.fivefivelike.mybaselibrary.mvp.databind.IDataBind;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by liugongce on 2017/2/15.
 */

public abstract class BasePullFragment<T extends BasePullDelegate, D extends IDataBind> extends BaseDataBindFragment<T, D>
        implements OnLoadmoreListener, OnRefreshListener {
    /**
     * 初始化使用RecycleView的上拉页面
     */
    protected void initRecycleViewPull(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        viewDelegate.initRecycleviewPull(adapter, layoutManager, this, this);
    }

    /**
     * 请求数据
     *
     * @param loadMode 类型
     */
    public void requestData(BasePullDelegate.LoadMode loadMode) {
        if (viewDelegate != null) {
            viewDelegate.requestData(loadMode);
            refreshData();
        }
    }

    /**
     * 请求数据返回更新
     *
     * @param backList 返回来的数据
     * @param adapter  {@link AdapterUpdateIml} 实现类
     */
    public void onGetData(List backList, AdapterUpdateIml adapter) {
        if (viewDelegate != null) {
            viewDelegate.requestBack(adapter.getDatas());
            if (backList != null && backList.size() > 0) {
                adapter.addDatas(backList);
            } else {
                viewDelegate.loadFinish();
            }
            adapter.notifyChanged();
        }
    }

    /**
     * 请求数据
     */
    protected abstract void refreshData();

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        requestData(BasePullDelegate.LoadMode.DOWN);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        requestData(BasePullDelegate.LoadMode.REFRESH);
    }

}
