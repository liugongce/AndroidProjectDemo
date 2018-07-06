package com.fivefivelike.mybaselibrary.http;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liugongce on 2017/7/6.
 */

public class HttpRequest {

    private String mRequestUrl;
    private RequestCallback mRequestCallback;
    private String mRequestName;
    private Object mRequestObj;
    private String mFirstCharForGetRequest = "?";
    private boolean mIsShowDialog;
    private String mEncoding;
    public int mConnectTimeOut;
    public int mReadTimeOut;
    private int mRequestCode;
    private Dialog mDialog;
    private CacheMode mCacheMode;
    private Map<String, Object> mFileMap;
    private Object mRequestTag;
    private RequestMode mRequestMode;
    private String REQUEST_TAG = "request";
    private String RESPONSE_TAG = "response";
    Request<String> mRequest = null;
    // -------------------------------------------构造函数--------------------------------------------------------


    private HttpRequest(Builder builder) {
        this.mRequestUrl = builder.requestUrl;
        this.mRequestCallback = builder.requestCallback;
        this.mRequestName = builder.requestName;
        this.mRequestObj = builder.requestObj;
        this.mConnectTimeOut = builder.connectTimeOut;
        this.mEncoding = builder.enCoding;
        this.mRequestCode = builder.requestCode;
        this.mDialog = builder.dialog;
        this.mIsShowDialog = builder.isShowDialog;
        this.mCacheMode = builder.cacheMode;
        this.mReadTimeOut = builder.readTimeOut;
        this.mFileMap = builder.fileMap;
        this.mRequestMode = builder.requestMode;
        this.mRequestTag = builder.requestTag == null ? "abctag" : mRequestTag;
    }
    // -------------------------------------------------公开调用方法------------------------------------------

    /**
     * 普通请求
     */
    public void sendRequest() {
        request();
    }

    /**
     * rx封装的请求
     *
     * @return Disposable
     */
    public Disposable RxSendRequest() {
        rxRequest();
        return sendRxRequest();
    }

    // ------------------------------------------------请求操作---------------------------------------------

    /**
     * 普通的请求操作
     */
    private void request() {
        if (TextUtils.isEmpty(mRequestUrl)) {
            KLog.i(REQUEST_TAG, mRequestName + "请求 Url为空");
            return;
        }
        requestSet();
        //使用单例请求
        SingleRequest.getInstance().addRequest(mRequestCode, mRequest, onResponseListener);
    }


    /**
     * 适配RxJava的请求
     *
     * @return
     */
    private void rxRequest() {
        if (TextUtils.isEmpty(mRequestUrl)) {
            KLog.i(REQUEST_TAG, mRequestName + "请求 Url为空");
            return;
        }
        requestSet();
    }

    /**
     * 请求设置
     */
    private void requestSet() {
        showDialog();
        if (mRequestMode == RequestMode.POST) {
            mRequest = NoHttp.createStringRequest(mRequestUrl, RequestMethod.POST);
        } else if (mRequestMode == RequestMode.GET) {
            mRequest = NoHttp.createStringRequest(mRequestUrl, RequestMethod.GET);
        }
        mRequest.setTag(mRequestTag);
        mRequest.setConnectTimeout(mConnectTimeOut);
        mRequest.setReadTimeout(mReadTimeOut);
        mRequest.setCacheMode(mCacheMode);
        mRequest.setParamsEncoding(mEncoding);
        if (mFileMap != null && mFileMap.size() > 0) {
            addFileMap();
        }
        if (mRequestObj != null) {
            logRequestUrlAndParams();
        }
    }

    /**
     * 打印文件
     */
    private void addFileMap() {
        for (Map.Entry<String, Object> entry : mFileMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof File) {
                mRequest.add(key, new FileBinary((File) value));//以文件的形式上传
                KLog.i(REQUEST_TAG, "上传文件" + key + "     " + ((File) value).getPath());
            } else if (value instanceof Bitmap) {//以bitmap的形式上传
                mRequest.add(key, new BitmapBinary((Bitmap) value, key));
            }
        }
    }

    /**
     * 打印参数和链接  添加参数
     */
    private void logRequestUrlAndParams() {
        Map<String, Object> map = GsonUtil.getInstance().objToMap(mRequestObj, new TypeReference<Map<String, Object>>() {
        });
        StringBuilder sb = new StringBuilder(mRequestUrl);
        if (mRequestUrl.contains("?")) {
            mFirstCharForGetRequest = "&";
        }
        sb.append(mFirstCharForGetRequest);
        KLog.i(REQUEST_TAG, "请求名称: " + mRequestName + "请求Url: " + mRequestUrl.toString());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey() == null ? "" : entry.getKey().toString().trim();
            String value = entry.getValue() == null ? "" : entry.getValue().toString().trim();
            KLog.i(REQUEST_TAG, "提交参数: " + key + " = " + value);
            sb.append(key + "=" + value);
            sb.append("&");
            mRequest.add(key, value);
        }
        sb.deleteCharAt(sb.length() - 1);
        KLog.i(REQUEST_TAG, "全地址: " + mRequestName + "请求全Url: " + sb.toString());
    }


    /**
     * 获得Disposable对象
     *
     * @return
     */
    private Disposable sendRxRequest() {
        return Observable.create(new ObservableOnSubscribe<Response<String>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<String>> e) throws Exception {
                //同步请求
                Response<String> response = NoHttp.startRequestSync(mRequest);
                if (response.isSucceed())
                    e.onNext(response);
                else
                    e.onError(response.getException());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<String>>() {
                    @Override
                    public void accept(@NonNull Response<String> stringResponse) throws Exception {
                        success(stringResponse.get());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissDialog();
                        throwable.printStackTrace();
                        if (mRequestCallback != null) {
                            mRequestCallback.error(mRequestCode, throwable);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                    }
                });
    }

    /**
     * 请求成功后的操作
     *
     * @param json
     */
    private void success(String json) {
        KLog.i(RESPONSE_TAG, mRequestName);
        KLog.json(RESPONSE_TAG, json);
        if (mRequestCallback != null) {
            mRequestCallback.success(mRequestCode, json);
        }
    }
    // --------------------------------------------回调操作------------------------------------------------

    /**
     * 回调监听类 onStart:开始请求回调 onFailure:请求失败回调 onSuccess:请求成功回调 onLoading:请求中回调
     */
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            success(response.get());
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            Exception exception = response.getException();
            if (mRequestCallback != null) {
                mRequestCallback.error(what, exception);
            }
            KLog.i("错误：" + exception.getMessage());
        }

        @Override
        public void onFinish(int what) {
            dismissDialog();
        }
    };

    private void showDialog() {
        try {
            if (mIsShowDialog && mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissDialog() {
        try {
            if (mIsShowDialog && mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    public static class Builder {
        private String requestUrl = "";
        private RequestCallback requestCallback;// 回调接口
        private String requestName = "http请求描述";// 请求描述
        private Object requestObj = null;
        private boolean isShowDialog;
        private String enCoding = "UTF-8";
        private Object requestTag;
        private CacheMode cacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE;
        private int requestCode = -1;
        private Dialog dialog;
        public int connectTimeOut = 10 * 1000;
        public int readTimeOut = 20 * 1000;
        private Map<String, Object> fileMap;
        public RequestMode requestMode = RequestMode.POST;

        public Builder() {
        }

        /**
         * @param requestUrl 请求地址
         * @return
         */
        public Builder setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        /**
         * @param requestCallback 请求回调
         * @return
         */
        public Builder setRequestCallback(RequestCallback requestCallback) {
            this.requestCallback = requestCallback;
            return this;
        }

        /**
         * @param requestName 请求名字,在日志中显示
         * @return
         */
        public Builder setRequestName(String requestName) {
            this.requestName = requestName;
            return this;
        }

        /**
         * @param requestObj 请求参数,可以使一个类,也可以是map集合
         * @return
         */
        public Builder setRequestObj(Object requestObj) {
            this.requestObj = requestObj;
            return this;
        }

        /**
         * @param showDialog 是否显示弹出框
         * @return
         */
        public Builder setShowDialog(boolean showDialog) {
            isShowDialog = showDialog;
            return this;
        }

        /**
         * @param enCoding 编码
         * @return
         */
        public Builder setEnCoding(String enCoding) {
            this.enCoding = enCoding;
            return this;
        }

        /**
         * @param requestTag 请求标识
         * @return
         */
        public Builder setRequestTag(Object requestTag) {
            this.requestTag = requestTag;
            return this;
        }

        /**
         * @param cacheMode 缓存模式
         * @return
         */
        public Builder setCacheMode(CacheMode cacheMode) {
            this.cacheMode = cacheMode;
            return this;
        }

        /**
         * @param requestCode 请求id
         * @return
         */
        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * @param dialog 请求弹出框
         * @return
         */
        public Builder setDialog(Dialog dialog) {
            this.dialog = dialog;
            return this;
        }

        /**
         * @param connectTimeOut 请求超时时间
         * @return
         */
        public Builder setConnectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        /**
         * @param readTimeOut 响应超时时间
         * @return
         */
        public Builder setReadTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        /**
         * @param fileMap 上传的文件集合
         * @return
         */
        public Builder setFileMap(Map<String, Object> fileMap) {
            this.fileMap = fileMap;
            return this;
        }

        public Builder setRequestMode(RequestMode requestMode) {
            this.requestMode = requestMode;
            return this;
        }

        /**
         * @return 返回一个请求对象
         */
        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

    public enum RequestMode {
        /**
         * post请求
         */
        POST,
        /**
         * get请求
         */
        GET
    }

}
