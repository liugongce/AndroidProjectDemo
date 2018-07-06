package com.fivefivelike.mybaselibrary.base;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by liugongce on 2017/7/10.
 */

public abstract class BasePullDelegate extends BaseDelegate {
    // 分页页数
    public int page;
    //分页长度
    public int pagesize = 20;
    private LoadMode mMode = LoadMode.REFRESH;
    /**
     * 下拉刷新控件
     */
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView pullRecyclerView;

    /**
     * 用来判断{@link #pullRecyclerView}中的是不是已经加载了所有的数据
     */
    private RelativeLayout noData;
    private TextView tvNoData;
    private ImageView ivNoData;

    /**
     * 初始化使用RecyclerView的上拉页面
     *
     * @param adapter RecyclerView 的adapter
     * @param manager RecyclerView的显示方式
     */
    public void initRecycleviewPull(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager, final OnLoadmoreListener callback, OnRefreshListener onRefreshListener) {
        smartRefreshLayout = getViewById(R.id.smartRefreshLayout);
        pullRecyclerView = getViewById(R.id.pull_recycleview);
        pullRecyclerView.setLayoutManager(manager);
        pullRecyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshListener(onRefreshListener);
        smartRefreshLayout.setOnLoadmoreListener(callback);
    }


    /**
     * 数据请求回来调用
     *
     * @param list
     */
    protected void requestBack(List list) {
        hideNoData();
        switch (mMode) {
            case REFRESH://下拉
                if (list != null) {
                    list.clear();
                }
                break;
            case DOWN://上拉
                break;
        }
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadmore();
            smartRefreshLayout.finishRefresh();
        }
    }

    public void loadFinish() {
        switch (mMode) {
            case REFRESH://下拉
                showNoData();
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishRefresh(true);
                }
                break;
            case DOWN://上拉
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setLoadmoreFinished(true);
                }
                break;
        }
    }


    /**
     * 隐藏没有数据
     */
    public void hideNoData() {
        initNodata();
        if (noData != null)
            noData.setVisibility(View.GONE);
    }

    /**
     * 显示没有数据
     */
    public void showNoData() {
        initNodata();
        if (noData != null)
            noData.setVisibility(View.VISIBLE);
    }


    private void initNodata() {
        if (noData == null) {
            noData = getViewById(R.id.no_data);
            tvNoData = getViewById(R.id.tv_nodata);
            ivNoData = getViewById(R.id.iv_nodata);
        }
    }

    /**
     * 设置是否上拉加载
     *
     * @param isLoadMore
     */
    public void setIsLoadMore(boolean isLoadMore) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableLoadmore(isLoadMore);
        }
    }

    /**
     * 设置是否下拉刷新
     *
     * @param isPullDown
     */
    public void setIsPullDown(boolean isPullDown) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableRefresh(isPullDown);
        }
    }

    /**
     * 请求数据
     *
     * @param loadMode 类型
     */
    public void requestData(LoadMode loadMode) {
        switch (loadMode) {
            case REFRESH://下拉刷新
                mMode = LoadMode.REFRESH;
                page = 1;
                break;
            case DOWN://上拉加载
                mMode = LoadMode.DOWN;
                page++;
                break;
        }
    }

    public enum LoadMode {
        /**
         * 下拉刷新
         */
        REFRESH,
        /**
         * 上拉加载
         */
        DOWN
    }


    /**
     * 加载失败
     */
    public void loadError(@DrawableRes int errorRes, String errorMsg) {
        switch (mMode) {
            case REFRESH://下拉
                if (errorRes != 0) {
                    setNoDataRes(errorRes);
                } else {
                    setNoDataRes(R.drawable.icon_no_data);
                }
                if (!TextUtils.isEmpty(errorMsg)) {
                    setNoDataText(errorMsg);
                } else {
                    setNoDataText("暂无数据");
                }
                showNoData();
                break;
            case DOWN://上拉
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishLoadmore(false);
                }
                break;
        }
    }

    private void setNoDataText(String errorMsg) {
        if (tvNoData != null)
            tvNoData.setText(errorMsg);
    }

    private void setNoDataRes(@DrawableRes int errorRes) {
        if (ivNoData != null)
            ivNoData.setImageResource(errorRes);
    }


    public LoadMode getmMode() {
        return mMode;
    }


    public RecyclerView getPullRecyclerView() {
        return pullRecyclerView;
    }

    public RelativeLayout getNoData() {
        return noData;
    }

    public TextView getTvNoData() {
        return tvNoData;
    }

    public ImageView getIvNoData() {
        return ivNoData;
    }

    public void setSPAN_SIZE(int size) {

    }

    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }
}
