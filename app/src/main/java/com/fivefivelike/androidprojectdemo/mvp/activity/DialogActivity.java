package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.circledialog.CircleDialog;
import com.circledialog.callback.ConfigButton;
import com.circledialog.callback.ConfigDialog;
import com.circledialog.callback.ConfigText;
import com.circledialog.params.ButtonParams;
import com.circledialog.params.DialogParams;
import com.circledialog.params.ProgressParams;
import com.circledialog.params.TextParams;
import com.fivefivelike.androidprojectdemo.adapter.MainAdapter;
import com.fivefivelike.androidprojectdemo.mvp.delegate.MainDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.AreaObj;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.dialog.BirthdayDialog;
import com.fivefivelike.mybaselibrary.view.dialog.CityChooseDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by liugongce on 2017/10/11.
 */

public class DialogActivity extends BaseActivity<MainDelegate> {
    private List<String> list;
    private MainAdapter adapter;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("弹出框"));
        list = new ArrayList<>();
        list.add("提示弹出框");
        list.add("中间弹出框");
        list.add("下面弹出框");
        list.add("加载框");
        list.add("加载进度框");
        list.add("时间选择");
        list.add("地址选择");
        list.add("测试");
        viewDelegate.recycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this);
        adapter.setDatas(list);
        viewDelegate.recycleview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                initListener(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initListener(int position) {
        String content = list.get(position);
        if (content.equals("提示弹出框")) {
            new CircleDialog.Builder(this)
                    .setTitle("标题")
                    .setText("提示框")
                    .configText(new ConfigText() {
                        @Override
                        public void onConfig(TextParams params) {
                            params.gravity = Gravity.LEFT;
                            params.height = 0;
                            params.padding = new int[]{50, 0, 50, 50};
                        }
                    })
                    .setPositive("确定", null)
                    .show();
        } else if (content.equals("中间弹出框")) {
            new CircleDialog.Builder(this)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .setText("确定框")
                    .setNegative("取消", null)
                    .setPositive("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();

        } else if (content.equals("下面弹出框")) {
            final String[] items = {"拍照", "从相册选择", "小视频"};
            new CircleDialog.Builder(this)
                    .configDialog(new ConfigDialog() {
                        @Override
                        public void onConfig(DialogParams params) {
                            //增加弹出动画
                            //                        params.animStyle = R.style.dialogWindowAnim;
                        }
                    })
                    .setTitle("标题")
                    .setTitleColor(Color.BLUE)
                    .setItems(items, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int
                                position, long id) {
                            ToastUtil.show(items[position]);

                        }
                    })
                    .setNegative("取消", null)
                    .configNegative(new ConfigButton() {
                        @Override
                        public void onConfig(ButtonParams params) {
                            //取消按钮字体颜色
                            params.textColor = Color.RED;
                        }
                    })
                    .show();

        } else if (content.equals("加载框")) {
            final DialogFragment dialogFragment = new CircleDialog.Builder(this)
                    .setProgressText("登录中...")
                    .setProgressStyle(ProgressParams.STYLE_SPINNER)
                    //                        .setProgressDrawable(R.drawable.bg_progress_s)
                    .show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogFragment.dismiss();
                }
            }, 3000);

        } else if (content.equals("加载进度框")) {
            final Timer timer = new Timer();
            final CircleDialog.Builder builder = new CircleDialog.Builder(this);
            builder.setCancelable(false).setCanceledOnTouchOutside(false)
                    .setTitle("下载")
                    .setProgressText("已经下载")
                    //                        .setProgressText("已经下载%s了")
                    //                        .setProgressDrawable(R.drawable.bg_progress_h)
                    .setNegative("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timer.cancel();
                        }
                    })
                    .show();
            TimerTask timerTask = new TimerTask() {
                final int max = 222;
                int progress = 0;

                @Override
                public void run() {
                    progress++;
                    if (progress >= max) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder.setProgressText("下载完成").create();
                                timer.cancel();
                            }
                        });
                    } else {
                        builder.setProgress(max, progress).create();
                    }
                }
            };
            timer.schedule(timerTask, 0, 50);

        } else if (content.equals("时间选择")) {
            new BirthdayDialog(this, new BirthdayDialog.OnTimeChooseListener() {
                @Override
                public void setOnTimeChooseListener(String time) {
                    ToastUtil.show(time);
                }
            }).show();
        } else if (content.equals("地址选择")) {
            new CityChooseDialog(this, new CityChooseDialog.OnChooseCityListener() {
                @Override
                public void chooseBack(AreaObj province, AreaObj city, AreaObj area) {
                    ToastUtil.show(province.getName() + city.getName() + area.getName());
                }
            }).show();
        } else if (content.equals("测试")) {
            startActivity(new Intent(this, TestActivity.class));
        }
    }
}
