package com.qrcode.callback;

/**
 * Created by liugongce on 2017/12/5.
 */

public interface OnQrcodeScanListener {
    void onScanSuccess(String result);

    void onScanFail();
}
