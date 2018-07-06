package com.fivefivelike.androidprojectdemo;

import android.app.Application;
import android.content.Context;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.fivefivelike.androidprojectdemo.util.GlideAlbumLoader;
import com.fivefivelike.mybaselibrary.utils.AreaUtils;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.nohttp.NoHttp;

import java.util.Locale;

/**
 * Created by liugongce on 2017/7/6.
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        GlobleContext.init(this);
        AreaUtils.getInstance().initData("https://app.joysuny.com/app/v4/user/getarea/");
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(new GlideAlbumLoader()) // This is not necessary.
                        .setLocale(Locale.getDefault())
                        .build());

    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setEnableHeaderTranslationContent(false);
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context);
            }
        });
    }
}
