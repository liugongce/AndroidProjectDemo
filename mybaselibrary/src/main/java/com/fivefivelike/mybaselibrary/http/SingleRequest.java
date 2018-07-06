package com.fivefivelike.mybaselibrary.http;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by liugongce on 2017/7/6.
 */

public class SingleRequest {
    private static SingleRequest singleRequest;
    private static RequestQueue queen;

    private SingleRequest() {
        queen = NoHttp.newRequestQueue();
    }

    public static SingleRequest getInstance() {
        if (singleRequest == null) {
            synchronized (SingleRequest.class) {
                if (singleRequest == null) {
                    singleRequest = new SingleRequest();
                }
            }
        }
        return singleRequest;
    }

    public void addRequest(int requestCode, Request<String> request, OnResponseListener<String> onResponseListener) {
        queen.add(requestCode, request, onResponseListener);
    }

    public void cancelBySign(Object sign) {
        queen.cancelBySign(sign);
    }

    public void cancelAll() {
        queen.cancelAll();
    }
}
