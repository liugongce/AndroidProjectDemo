package com.fivefivelike.androidprojectdemo.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.mybaselibrary.utils.FileUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;


/**
 * Created by liugongce on 2017/7/17.
 */

public class UIHelper {


    /**
     * 判断提交必填参数是否为空
     *
     * @param content
     * @return
     */
    public static boolean judgeRequestContentIsNull(String content, String toastString) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show(toastString);
            return true;
        }
        return false;
    }

    private static void cropImage(Activity context,String[]pathList) {
        Durban.with(context)
                // Che title of the UI.
                .title("裁剪")
                .toolBarColor(ContextCompat.getColor(context, R.color.blue)) // Toolbar color.
                .statusBarColor(ContextCompat.getColor(context, R.color.blue)) // StatusBar color.
                // Image path list/array.
                .inputImagePaths(pathList)
                // Image output directory.
                .outputDirectory(FileUtil.getCropPath(context))
                // Image size limit.
                .maxWidthHeight(500, 500)
                // Aspect ratio.
                .aspectRatio(1, 1)
                // Output format: JPEG, PNG.
                .compressFormat(Durban.COMPRESS_JPEG)
                // Compress quality, see Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // Gesture: ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(
                        Controller.newBuilder() // Create Builder of Controller.
                                .enable(false) // Enable the control panel.
                                .rotation(true) // Rotation button.
                                .rotationTitle(true) // Rotation button title.
                                .scale(true) // Scale button.
                                .scaleTitle(true) // Scale button title.
                                .build()) // Create Controller Config.
                .requestCode(200)
                .start();
    }

}
