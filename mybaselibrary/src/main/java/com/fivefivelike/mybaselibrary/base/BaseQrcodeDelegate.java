package com.fivefivelike.mybaselibrary.base;

import com.fivefivelike.mybaselibrary.R;
import com.qrcode.zxing.ZXingView;

/**
 * Created by liugongce on 2017/12/5.
 */

public class BaseQrcodeDelegate extends BaseDelegate {
    public ZXingView zxingview;

    @Override
    public void initView() {
        zxingview=getViewById(R.id.zxingview);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_qrcode;
    }
}
