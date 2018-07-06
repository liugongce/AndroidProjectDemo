package com.fivefivelike.androidprojectdemo.mvp.databinder;

import com.fivefivelike.androidprojectdemo.mvp.delegate.PageRequestDelegate;
import com.fivefivelike.androidprojectdemo.util.Httpurl;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;


/**
 * Created by liugongce on 2017/7/28.
 */

public class PageRequestDataBinder extends BaseDataBind<PageRequestDelegate> {

    public PageRequestDataBinder(PageRequestDelegate viewDelegate) {
        super(viewDelegate);
    }
    public Disposable getData(RequestCallback requestCallback, int page, int pagesize) {
        getBaseMap();
        baseMap.put("pagesize", pagesize);
        baseMap.put("page", page);
        baseMap.put("cid", 136);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(Httpurl.NEWS_INDEX)
                .setRequestName("测试api")
                .setRequestObj(baseMap)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}
