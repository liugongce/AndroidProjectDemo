package com.fivefivelike.androidprojectdemo.mvp.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.adapter.AddPicAdapter;
import com.fivefivelike.androidprojectdemo.mvp.delegate.ImageChooseDelegate;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.base.BigImageviewActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by liugongce on 2017/7/28.
 */

public class ImageChooseActivity extends BaseActivity<ImageChooseDelegate> {
    AddPicAdapter addPicAdapter;
    ArrayList<String> list;

    @Override
    protected Class<ImageChooseDelegate> getDelegateClass() {
        return ImageChooseDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("图片选择"));
        initRecycleView();
    }

    private void initRecycleView() {
        list = new ArrayList<>();
        addPicAdapter = new AddPicAdapter(this, list);
        GridLayoutManager manager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.recycleview.setLayoutManager(manager);
        final int dimension = getResources().getDimensionPixelSize(R.dimen.trans_8px);
        viewDelegate.recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position < 4) {
                    if (position == 3) {
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, 0, dimension, 0);
                    }
                } else {
                    if (position % 4 == 3) {
                        outRect.set(0, dimension, 0, 0);
                    } else {
                        outRect.set(0, dimension, dimension, 0);
                    }
                }
            }
        });
        viewDelegate.recycleview.setAdapter(addPicAdapter);
        addPicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int itemViewType = addPicAdapter.getItemViewType(position);
                switch (itemViewType) {
                    case AddPicAdapter.TYPE_ADD:
                        Album.image(ImageChooseActivity.this) // Image selection.
                                .multipleChoice()
                                .requestCode(100)
                                .camera(true)
                                .columnCount(3)
                                .selectCount(9 - list.size())
                                .onResult(new Action<ArrayList<AlbumFile>>() {
                                    @Override
                                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                        if (result != null) {
                                            for (AlbumFile item : result) {
                                                list.add(item.getPath());
                                            }
                                            addPicAdapter.notifyDataSetChanged();
                                        }
                                    }
                                })
                                .start();
                        break;
                    case AddPicAdapter.TYPE_PIC:
                        BigImageviewActivity.toBigImage(ImageChooseActivity.this)
                                .checkedList(list)
                                .currentPosition(position)
                                .start();
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
