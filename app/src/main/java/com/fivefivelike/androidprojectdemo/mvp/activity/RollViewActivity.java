package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.fivefivelike.androidprojectdemo.mvp.delegate.RollViewDelegate;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.base.BigImageviewActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.bannerview.Mode;
import com.fivefivelike.mybaselibrary.view.bannerview.RollViewPage;
import com.fivefivelike.mybaselibrary.view.bannerview.callback.OnItemViewClickListener;
import com.fivefivelike.mybaselibrary.view.bannerview.config.BannerBuilder;
import com.fivefivelike.mybaselibrary.view.bannerview.config.CardModeOptions;
import com.fivefivelike.mybaselibrary.view.bannerview.config.CirculationModeOptions;
import com.fivefivelike.mybaselibrary.view.bannerview.config.DotOptions;
import com.yanzhenjie.album.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugongce on 2017/7/28.
 */

public class RollViewActivity extends BaseActivity<RollViewDelegate> implements OnItemViewClickListener {
    final ArrayList<String> list = new ArrayList<>();
    @Override
    protected Class<RollViewDelegate> getDelegateClass() {
        return RollViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("美女"));
        //                viewDelegate.iv_pic.setProgressIndicator(new ProgressPieIndicator());
        //                  viewDelegate.iv_pic.showImage( "http://www.fuhaodq.com/d/file/201706/16/2uucyj1vlhyvjjr2779.jpg");
        //        viewDelegate.iv_pic.showImage("http://test.ttyong.com/upload/markettask/464/1507601142.jpg","http://dynamic-image.yesky.com/740x-/uploadImages/2016/058/45/F5GG44HP48QH.jpg");

        list.add("http://www.fuhaodq.com/d/file/201706/16/2uucyj1vlhyvjjr2779.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=240148720,3695524497&fm=11&gp=0.jpg");
        list.add("http://dynamic-image.yesky.com/740x-/uploadImages/2016/058/45/F5GG44HP48QH.jpg");
        list.add("http://picture.youth.cn/qtdb/201611/W020161116350101705223.jpg");
        list.add("http://pic.gansudaily.com.cn/0/10/41/83/10418360_999008.jpg");
        initPage(list);
    }

    private void initPage(final ArrayList<String> list) {

        RollViewPage.build(new BannerBuilder(this)
                .setBanList(list)
                .setOnItemViewClickListener(this)
                .setNotInterceptTouch(true)
                .setRelativeLayout(viewDelegate.rl_banner)
                .setDotOptions(new DotOptions().setShowDot(true).setMode(Mode.middle)
                        .setDotSize(CommonUtils.dip2px(this, 10)))
                .setCirculationModeOptions(new CirculationModeOptions()
                        .setAutoCirculation(true)
                        .setRepeat(false)));

        RollViewPage.build(new BannerBuilder(this)
                .setBanList(list)
                .setOnItemViewClickListener(this)
                .setNotInterceptTouch(true)
                .setRelativeLayout(viewDelegate.rl_banner1)
                .setCardViewOptions(new CardModeOptions().setCardMode(true))
                .setDotOptions(new DotOptions().setShowDot(true).setMode(Mode.middle)
                        .setDotSize(CommonUtils.dip2px(this, 10)))
                .setCirculationModeOptions(new CirculationModeOptions()
                        .setAutoCirculation(true)
                        .setRepeat(true)));
    }


    @Override
    public void onItemClick(View view, int position) {
        BigImageviewActivity.toBigImage(RollViewActivity.this)
                .checkedList(list)
                .currentPosition(position)
                .onResult(new Action<List>() {
                    @Override
                    public void onAction(int requestCode, @NonNull List result) {
                        ToastUtil.show(result.size() + "");
                    }
                }).start();
    }
}
