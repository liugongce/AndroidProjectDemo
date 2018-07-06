package com.fivefivelike.mybaselibrary.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.mvp.presenter.ActivityPresenter;
import com.fivefivelike.mybaselibrary.utils.ActUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/7/7.
 */

public abstract class BaseActivity<T extends BaseDelegate> extends ActivityPresenter<T> implements View.OnClickListener {
    public static Class loginCls;
    public static List<String> doubleClickActList = new ArrayList<>();
    public Activity mContext;
    private long exitTime = 0;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.toolbar_img) {
                clickRightIv();
            }
            if (id == R.id.toolbar_img1) {
                clickRightIv1();
            } else if (id == R.id.toolbar_img2) {
                clickRightIv2();
            } else if (id == R.id.toolbar_subtitle) {
                clickRightTv();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActUtil.getInstance().addActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            addNoStatusBarFlag();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActUtil.getInstance().removeActivitiyFromStack(this);//将页面从栈中移除
    }

    @Override
    public void onBackPressed() {
        if (doubleClickActList.contains(getClass().getName())) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActUtil.getInstance().AppExit(this);
            }
            return;
        }
        super.onBackPressed();
    }

    /**
     * 去掉状态栏
     */
    protected void addNoStatusBarFlag() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 显示状态栏
     */
    protected void clearNoStatusBarFlag() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    protected void initToolbar(ToolbarBuilder toolbarBuilder) {
        viewDelegate.initToolBar(this, onClickListener, toolbarBuilder);
    }

    @Override
    public void onClick(View v) {

    }

    protected void clickRightIv() {
    }

    protected void clickRightIv1() {
    }

    protected void clickRightIv2() {
    }

    protected void clickRightTv() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
