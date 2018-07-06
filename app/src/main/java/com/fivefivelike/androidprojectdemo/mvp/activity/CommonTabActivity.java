package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.entity.TabEntity;
import com.fivefivelike.androidprojectdemo.mvp.delegate.CommonTabDelegate;
import com.fivefivelike.androidprojectdemo.mvp.fragment.SimpleCardFragment;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.tablayout.utils.UnreadMsgUtils;
import com.tablayout.widget.MsgView;

import java.util.ArrayList;
import java.util.Random;

public class CommonTabActivity extends BaseActivity<CommonTabDelegate> {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect, R.drawable.tab_speech_unselect,
            R.drawable.tab_contact_unselect, R.drawable.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select, R.drawable.tab_speech_select,
            R.drawable.tab_contact_select, R.drawable.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("CommonTabActivity"));
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        viewDelegate.viewHolder.vp_2.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        viewDelegate.viewHolder.tl_1.setTabData(mTabEntities);
        tl_2();
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities, this, R.id.fl_change, mFragments2);
        viewDelegate.viewHolder.tl_3.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_4.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_5.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_6.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_7.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_8.setTabData(mTabEntities);

        viewDelegate.viewHolder.tl_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.viewHolder.tl_1.setCurrentTab(position);
                viewDelegate.viewHolder.tl_2.setCurrentTab(position);
                viewDelegate.viewHolder.tl_4.setCurrentTab(position);
                viewDelegate.viewHolder.tl_5.setCurrentTab(position);
                viewDelegate.viewHolder.tl_6.setCurrentTab(position);
                viewDelegate.viewHolder.tl_7.setCurrentTab(position);
                viewDelegate.viewHolder.tl_8.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewDelegate.viewHolder.tl_8.setCurrentTab(2);
        viewDelegate.viewHolder.tl_3.setCurrentTab(1);

        //显示未读红点
        viewDelegate.viewHolder.tl_1.showDot(2);
        viewDelegate.viewHolder.tl_3.showDot(1);
        viewDelegate.viewHolder.tl_4.showDot(1);

        //两位数
        viewDelegate.viewHolder.tl_2.showMsg(0, 55);
        viewDelegate.viewHolder.tl_2.setMsgMargin(0, -5, 5);

        //三位数
        viewDelegate.viewHolder.tl_2.showMsg(1, 100);
        viewDelegate.viewHolder.tl_2.setMsgMargin(1, -5, 5);

        //设置未读消息红点
        viewDelegate.viewHolder.tl_2.showDot(2);
        MsgView rtv_2_2 = viewDelegate.viewHolder.tl_2.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
        viewDelegate.viewHolder.tl_2.showMsg(3, 5);
        viewDelegate.viewHolder.tl_2.setMsgMargin(3, 0, 5);
        MsgView rtv_2_3 = viewDelegate.viewHolder.tl_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    @Override
    protected Class<CommonTabDelegate> getDelegateClass() {
        return CommonTabDelegate.class;
    }

    Random mRandom = new Random();

    private void tl_2() {
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
        viewDelegate.viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                 viewDelegate.viewHolder.vp_2.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    viewDelegate.viewHolder.tl_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(viewDelegate.viewHolder.tl_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

         viewDelegate.viewHolder.vp_2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewDelegate.viewHolder.tl_2.setCurrentTab(position);
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
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
