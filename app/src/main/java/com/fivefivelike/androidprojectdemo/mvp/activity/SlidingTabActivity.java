package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.fivefivelike.androidprojectdemo.mvp.delegate.SlidingTabDelegate;
import com.fivefivelike.androidprojectdemo.mvp.fragment.SimpleCardFragment;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.tablayout.listener.OnTabSelectListener;
import com.tablayout.widget.MsgView;

import java.util.ArrayList;

public class SlidingTabActivity extends BaseActivity<SlidingTabDelegate> implements OnTabSelectListener {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("SlidingTabActivity"));
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewDelegate.viewHolder.vp.setAdapter(mAdapter);

        viewDelegate.viewHolder.tl_1.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_2.setOnTabSelectListener(this);
        viewDelegate.viewHolder.tl_3.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_4.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_5.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_6.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_7.setViewPager(viewDelegate.viewHolder.vp, mTitles);
        viewDelegate.viewHolder.tl_8.setViewPager(viewDelegate.viewHolder.vp, mTitles, this, mFragments);
        viewDelegate.viewHolder.tl_9.setViewPager(viewDelegate.viewHolder.vp);
        viewDelegate.viewHolder.tl_10.setViewPager(viewDelegate.viewHolder.vp);

        viewDelegate.viewHolder.vp.setCurrentItem(4);

        viewDelegate.viewHolder.tl_1.showDot(4);
        viewDelegate.viewHolder.tl_3.showDot(4);
        viewDelegate.viewHolder.tl_2.showDot(4);

        viewDelegate.viewHolder.tl_2.showMsg(3, 5);
        viewDelegate.viewHolder.tl_2.setMsgMargin(3, 0, 10);
        MsgView rtv_2_3 = viewDelegate.viewHolder.tl_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }

        viewDelegate.viewHolder.tl_2.showMsg(5, 5);
        viewDelegate.viewHolder.tl_2.setMsgMargin(5, 0, 10);

    }

    @Override
    protected Class<SlidingTabDelegate> getDelegateClass() {
        return SlidingTabDelegate.class;
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
