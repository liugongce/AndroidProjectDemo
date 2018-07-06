package com.fivefivelike.mybaselibrary.mywebview;

import android.view.View;

/**
 * Created by liugongce on 2017/11/30.
 */

public interface OnWebviewLoadListener {

    // 隐藏进度条
    void webHindProgressBar();

    void webStartProgress();

    /**
     * 进度条变化时调用
     */
    void webProgressChanged(int newProgress);

    /**
     * 添加js监听
     */
    void webAddImageClickListener();

    /**
     * 播放网络视频全屏调用
     */
    void webAddVideoView(View view);

    void webRemoveVideoView(View view);


    void webStartTakePhoto();

    void webGetTitle(String title);

    void webSetRequestedOrientation(int screen_orientation);

    //加载错误
    void webLoadError();
}
