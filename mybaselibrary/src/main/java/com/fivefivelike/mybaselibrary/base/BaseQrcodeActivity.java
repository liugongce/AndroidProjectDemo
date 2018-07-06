package com.fivefivelike.mybaselibrary.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.circledialog.CircleDialog;
import com.circledialog.callback.ConfigButton;
import com.circledialog.callback.ConfigDialog;
import com.circledialog.params.ButtonParams;
import com.circledialog.params.DialogParams;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.dialog.NetConnectDialog;
import com.qrcode.callback.OnQrcodeScanListener;
import com.qrcode.core.QRCodeView;
import com.qrcode.zxing.QRCodeDecoder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/12/5.
 */

public class BaseQrcodeActivity extends BaseActivity<BaseQrcodeDelegate> implements QRCodeView.Delegate {
    public static OnQrcodeScanListener onQrcodeScanListener;
    private AnalysePicTask analysePicTask;
    private NetConnectDialog netConnectDialog;

    @Override
    protected Class<BaseQrcodeDelegate> getDelegateClass() {
        return BaseQrcodeDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("二维码识别").setSubTitle("更多"));
        viewDelegate.zxingview.setDelegate(this);
        netConnectDialog = new NetConnectDialog(this);
    }

    private void analysePicture(String filePath) {
        if (analysePicTask == null) {
            analysePicTask = new AnalysePicTask(this, netConnectDialog);
        }
        netConnectDialog.show();
        analysePicTask.execute(filePath);

    }


    static class AnalysePicTask extends AsyncTask<String, Void, String> {
        Activity activity;
        Dialog dialog;

        public AnalysePicTask(Activity activity, Dialog dialog) {
            this.activity = activity;
            this.dialog = dialog;
        }

        @Override
        protected String doInBackground(String... path) {
            return QRCodeDecoder.syncDecodeQRCode(path[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (onQrcodeScanListener != null) {
                onQrcodeScanListener.onScanSuccess(result);
            }
            activity.onBackPressed();
        }
    }


    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        final String[] items = {"从相册选择", "开启闪关灯"};
        if (viewDelegate.zxingview.getFlashIsOpen()) {
            items[1] = "关闭闪光灯";
        } else {
            items[1] = "开启闪关灯";
        }

        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        //                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {
                        switch (position) {
                            case 0:
                                Album.image(BaseQrcodeActivity.this) // 选择图片。
                                        .singleChoice()
                                        .requestCode(9627)
                                        .camera(false)
                                        .columnCount(3)
                                        .onResult(new Action<ArrayList<AlbumFile>>() {
                                            @Override
                                            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                                String path = result.get(0).getPath();
                                                analysePicture(path);
                                            }
                                        })
                                        .start();
                                break;
                            case 1:
                                if (items[position].equals("关闭闪光灯")) {
                                    viewDelegate.zxingview.closeFlashlight();
                                } else if (items[position].equals("开启闪关灯")) {
                                    viewDelegate.zxingview.openFlashlight();
                                }
                                break;
                        }


                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (onQrcodeScanListener != null)
            onQrcodeScanListener.onScanSuccess(result);
        vibrate();
        onBackPressed();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        if (onQrcodeScanListener != null)
            onQrcodeScanListener.onScanFail();
        ToastUtil.show("开启相机发生错误");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AndPermission.with(this)
                .requestCode(1111)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        viewDelegate.zxingview.startCamera();
                        viewDelegate.zxingview.showScanRect();
                        viewDelegate.zxingview.startSpot();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.show("没有相机权限,请先打开权限");
                        onBackPressed();
                    }
                }).start();

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStop() {
        viewDelegate.zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        viewDelegate.zxingview.onDestroy();
        netConnectDialog = null;
        onQrcodeScanListener = null;
        if (analysePicTask != null) {
            analysePicTask.cancel(true);
            analysePicTask = null;
        }
        super.onDestroy();
    }


    /**
     * 开启扫描页面
     *
     * @param context
     */
    public static void startScan(Context context, OnQrcodeScanListener onQrcodeScanListener) {
        BaseQrcodeActivity.onQrcodeScanListener = onQrcodeScanListener;
        Intent intent = new Intent(context, BaseQrcodeActivity.class);
        context.startActivity(intent);
    }

}
