package com.fivefivelike.mybaselibrary.mywebview;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.fivefivelike.mybaselibrary.utils.ToastUtil;

/**
 * Created by liugongce on 2017/11/30.
 */

public class ImageClickInterface {
    private Context context;

    public ImageClickInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void imageClick(String imgUrl,int index) {
        ToastUtil.show("----点击了图片"+index);
    }

    @JavascriptInterface
    public void textClick(String type, String item_pk) {
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(item_pk)) {
            ToastUtil.show("----点击了文字");
        }
    }

    @JavascriptInterface
    public void allPaths(String[] paths) {
                ToastUtil.show("----点击了图片"+paths.length);
    }
}
