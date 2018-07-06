package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.biv.view.BigImageView;
import com.fivefivelike.androidprojectdemo.adapter.MainAdapter;
import com.fivefivelike.androidprojectdemo.entity.PathEntity;
import com.fivefivelike.androidprojectdemo.mvp.delegate.MainDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.decoration.RvDivider;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.base.BaseQrcodeActivity;
import com.fivefivelike.mybaselibrary.base.BigImageviewActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.qrcode.callback.OnQrcodeScanListener;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainDelegate> {
    private List<String> list;
    private MainAdapter adapter;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("首页").setShowBack(false));
        list = new ArrayList<>();
        list.add("弹出框");
        list.add("tablayout");
        list.add("轮播");
        list.add("大图预览");
        list.add("wavebar字母索引");
        list.add("scrollview头部图片拉伸");
        list.add("数据请求");
        list.add("web页面");
        list.add("图片选择.裁剪");
        list.add("测试绘制视图");
        list.add("二维码");
        list.add("吐司");
        list.add("长度");

        viewDelegate.recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewDelegate.recycleview.addItemDecoration(
                new RvDivider().defaultVertical());
        adapter = new MainAdapter(this);
        adapter.setDatas(list);
        viewDelegate.recycleview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                initListener(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        AndPermission.with(this)
                .permission(Manifest.permission.SET_WALLPAPER)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        setWall();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

                    }
                }).start();


    }

    /**
     * 设置壁纸
     */
    private void setWall() {
        //        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        //        try {
        //            wallpaperManager.setResource(R.mipmap.ic_launcher);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
    }

    private void initListener(int position) {
        String content = list.get(position);
        if (content.equals("弹出框")) {
            startActivity(new Intent(this, DialogActivity.class));
        } else if (content.equals("tablayout")) {
            startActivity(new Intent(this, TablayoutActivity.class));
        } else if (content.equals("轮播")) {
            startActivity(new Intent(this, RollViewActivity.class));
        } else if (content.equals("wavebar字母索引")) {
            startActivity(new Intent(this, WaveBarActivity.class));
        } else if (content.equals("scrollview头部图片拉伸")) {
            startActivity(new Intent(this, HeadExpandActivity.class));
        } else if (content.equals("数据请求")) {
            startActivity(new Intent(this, RequestActivity.class));
        } else if (content.equals("web页面")) {
            NormalWebviewActivityActivity.startAct(this, "http://www.baidu.com", -1);
        } else if (content.equals("图片选择.裁剪")) {
            startActivity(new Intent(this, ImageChooseActivity.class));
        } else if (content.equals("跳转")) {//应用之间跳转
            //            String url="wangka://";
            //            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //            startActivity(intent);
            //            finish();
            //            Intent intent = new Intent(Intent.ACTION_MAIN);
            //            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            //            ComponentName cn = new ComponentName("com.fivefivelike.internetcafesheadlines", "com.fivefivelike.mvp.ui.activity.mainAct.WelcomeActivity");
            //            intent.setComponent(cn);
            //            startActivity(intent);
            doStartApplicationWithPackageName("com.Magic.HQM");
        } else if (content.equals("测试绘制视图")) {
            startActivity(new Intent(this, TestViewActivity.class));
        } else if (content.equals("二维码")) {
            BaseQrcodeActivity.startScan(this, new OnQrcodeScanListener() {
                @Override
                public void onScanSuccess(String result) {
                    ToastUtil.show(result);
                }

                @Override
                public void onScanFail() {

                }
            });
        } else if (content.equals("大图预览")) {
            List<PathEntity> pathEntityList = new ArrayList<>();
            pathEntityList.add(new PathEntity("http://img4.imgtn.bdimg.com/it/u=1962171707,2718340329&fm=27&gp=0.jpg",
                    "http://www.fuhaodq.com/d/file/201706/16/2uucyj1vlhyvjjr2779.jpg"));
            pathEntityList.add(new PathEntity("http://img4.imgtn.bdimg.com/it/u=1962171707,2718340329&fm=27&gp=0.jpg",
                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=240148720,3695524497&fm=11&gp=0.jpg"));
            pathEntityList.add(new PathEntity("http://img4.imgtn.bdimg.com/it/u=1962171707,2718340329&fm=27&gp=0.jpg",
                    "http://dynamic-image.yesky.com/740x-/uploadImages/2016/058/45/F5GG44HP48QH.jpg"));
            pathEntityList.add(new PathEntity("http://img4.imgtn.bdimg.com/it/u=1962171707,2718340329&fm=27&gp=0.jpg",
                    "http://picture.youth.cn/qtdb/201611/W020161116350101705223.jpg"));
            pathEntityList.add(new PathEntity("http://img4.imgtn.bdimg.com/it/u=1962171707,2718340329&fm=27&gp=0.jpg",
                    "http://pic.gansudaily.com.cn/0/10/41/83/10418360_999008.jpg"));
            BigImageviewActivity.toBigImage(this)
                    .checkedList(pathEntityList)
                    .setBigImageLoader(new BigImageviewActivity.BigImageLoader<PathEntity>() {
                        @Override
                        public void bigImageBind(BigImageView bigImageView, PathEntity pathEntity) {
                            bigImageView.showImage(pathEntity.getThumilpath(), pathEntity.getPath());
                        }
                    })
                    .onResult(new Action<List>() {
                        @Override
                        public void onAction(int requestCode, @NonNull List result) {
                            ToastUtil.show(result.size() + "");
                        }
                    }).start();
        } else if (content.equals("吐司")) {

            startActivity(new Intent(this, ToastActivity.class));
        }else if(content.equals("长度")){

        }

    }

    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }
}
