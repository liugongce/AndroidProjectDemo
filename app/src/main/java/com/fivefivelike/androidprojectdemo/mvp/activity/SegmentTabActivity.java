package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.mvp.delegate.SegmentTabDelegate;
import com.fivefivelike.androidprojectdemo.mvp.fragment.SimpleCardFragment;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.tablayout.listener.OnTabSelectListener;
import com.tablayout.widget.MsgView;

import java.util.ArrayList;

public class SegmentTabActivity extends BaseActivity<SegmentTabDelegate> {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息"};
    private String[] mTitles_2 = {"首页", "消息", "联系人"};
    private String[] mTitles_3 = {"首页", "消息", "联系人", "更多"};

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("SegmentTabActivity"));
        for (String title : mTitles_3) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }

        for (String title : mTitles_2) {
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }

        viewDelegate.viewHolder.tl_1.setTabData(mTitles);
        viewDelegate.viewHolder.tl_2.setTabData(mTitles_2);
        tl_3();
        viewDelegate.viewHolder.tl_4.setTabData(mTitles_2, this, R.id.fl_change, mFragments2);
        viewDelegate.viewHolder.tl_5.setTabData(mTitles_3);

        //显示未读红点
        viewDelegate.viewHolder.tl_1.showDot(2);
        viewDelegate.viewHolder.tl_2.showDot(2);
        viewDelegate.viewHolder.tl_3.showDot(1);
        viewDelegate.viewHolder.tl_4.showDot(1);

        //设置未读消息红点
        viewDelegate.viewHolder.tl_3.showDot(2);
        MsgView rtv_3_2 = viewDelegate.viewHolder.tl_3.getMsgView(2);
        if (rtv_3_2 != null) {
            rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    @Override
    protected Class<SegmentTabDelegate> getDelegateClass() {
        return SegmentTabDelegate.class;
    }

    private void tl_3() {
        viewDelegate.viewHolder.vp_2.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        viewDelegate.viewHolder.tl_3.setTabData(mTitles_3);
        viewDelegate.viewHolder.tl_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.viewHolder.vp_2.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewDelegate.viewHolder.vp_2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewDelegate.viewHolder.tl_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewDelegate.viewHolder.vp_2.setCurrentItem(1);
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
            return mTitles_3[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
