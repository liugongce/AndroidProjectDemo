package com.fivefivelike.mybaselibrary.base;

import android.content.Intent;
import android.os.Bundle;

import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.fivefivelike.mybaselibrary.mvp.databind.IDataBind;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * Created by liugongce on 2017/7/3.
 */

public abstract class BaseDataBindActivity<T extends BaseDelegate, D extends IDataBind> extends BaseActivity<T> implements RequestCallback {
    protected List<Disposable> disposableList;
    protected D binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binder = getDataBinder(viewDelegate);
        super.onCreate(savedInstanceState);
    }


    public abstract D getDataBinder(T viewDelegate);

    @Override
    protected void onDestroy() {
        cancelpost();
        super.onDestroy();
    }

    /**
     * 添加订阅
     *
     * @param disposable
     */
    protected void addRequest(Disposable disposable) {
        if (disposableList == null) {
            disposableList = new ArrayList<>();
        }
        disposableList.add(disposable);
    }

    /**
     * 取消订阅
     */
    private void cancelpost() {
        if (disposableList != null) {
            for (Disposable disposable : disposableList) {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }
    }

    @Override
    public void success(int requestCode, String jsonData) {
        String info;
        int status;
        String data;
        try {
            JSONObject object = new JSONObject(jsonData);
            info = object.getString("info");
            status = object.getInt("status");
            data = object.getString("data");
            if (status == 200) {
                onServiceSuccess(data, info, status, requestCode);
            } else {
                onServiceError(data, info, status, requestCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            error(requestCode, e);
        }
    }

    @Override
    public void error(int requestCode, Throwable exThrowable) {
        exThrowable.printStackTrace();
        // 提示异常信息。
        if (exThrowable instanceof NetworkError) {// 网络不好
            ToastUtil.show("网络不好");
        } else if (exThrowable instanceof TimeoutError) {// 请求超时
            ToastUtil.show("请求超时");
        } else if (exThrowable instanceof UnKnownHostError) {// 找不到服务器
            ToastUtil.show("找不到服务器");
        } else if (exThrowable instanceof URLError) {// 找不到服务器
            ToastUtil.show("找不到服务器");
        } else if (exThrowable instanceof NotFoundCacheError) {
            ToastUtil.show("没有缓存");
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
        } else if (exThrowable instanceof ProtocolException) {
            ToastUtil.show("系统不支持");
        } else if (exThrowable instanceof JSONException) {
            ToastUtil.show("数据格式错误");
        } else {
            ToastUtil.show("未知错误");
        }
    }

    protected void onServiceError(String data, String info, int status, int requestCode) {
        ToastUtil.show(info);
        if (status==502){
            Intent intent=new Intent(this,loginCls);
            ActUtil.getInstance().killAllActivity(this);
            startActivity(intent);
            finish();
        }
    }

    protected abstract void onServiceSuccess(String data, String info, int status, int requestCode);


}

