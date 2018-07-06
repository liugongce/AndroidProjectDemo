package com.fivefivelike.androidprojectdemo.adapter;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fivefivelike.androidprojectdemo.R;
import com.fivefivelike.androidprojectdemo.util.GlideUtils;
import com.fivefivelike.mybaselibrary.adapter.recyclerview.MultiItemTypeAdapter.OnItemClickListener;
import com.fivefivelike.mybaselibrary.utils.DeviceUtils;

import java.util.List;


/**
 * Created by liugongce on 2017/3/14.
 */

public class AddPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List list;
    private Context mContext;
    private LayoutInflater inflater;
    public static final int TYPE_ADD = 0x6545;
    public static final int TYPE_PIC = 0x54236;
    private OnItemClickListener onItemClickListener;
    private int imageSize;
    @DrawableRes
    private int addPicRes = R.drawable.add_pic;
    @DimenRes
    private int cloumnPaddingSize = R.dimen.trans_100px;
    private int cloumnCount = 4;
    private boolean isShowAdd = true;
    private int totalWith;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AddPicAdapter(Context mContext, List<String> list) {
        this.list = list;
        this.mContext = mContext;
        totalWith = DeviceUtils.getScreenWidth();
        inflater = LayoutInflater.from(mContext);
        cacularImagsize();
    }

    public void setTotalWith(int totalWith) {
        this.totalWith = totalWith;
        cacularImagsize();
        notifyDataSetChanged();
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
        notifyDataSetChanged();
    }

    public void setAddPicRes(@DrawableRes int addPicRes) {
        this.addPicRes = addPicRes;
        cacularImagsize();
        notifyDataSetChanged();
    }

    public void setCloumnPaddingSize(@DimenRes int cloumnPaddingSize) {
        this.cloumnPaddingSize = cloumnPaddingSize;
        cacularImagsize();
        notifyDataSetChanged();
    }

    public void setCloumnCount(int cloumnCount) {
        this.cloumnCount = cloumnCount;
        cacularImagsize();
        notifyDataSetChanged();
    }

    private void cacularImagsize() {
        imageSize = cacularWidAndHei(cloumnPaddingSize, cloumnCount, 1, 1)[1];
    }

    private int[] cacularWidAndHei(@DimenRes int paddingRes, int viewNum, int relWith, int relHei) {
        int[] size = new int[2];
        int paddingValue = mContext.getResources().getDimensionPixelSize(paddingRes);
        int viewWidth = (totalWith - paddingValue) / viewNum;
        int viewHeight = viewWidth * relHei / relWith;
        size[0] = viewWidth;
        size[1] = viewHeight;
        return size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ADD:
                view = inflater.inflate(R.layout.item_pic, parent, false);
                ShowPicViewHolder viewHolder = new ShowPicViewHolder(view);
                setListener(parent, viewHolder, viewType);
                return viewHolder;
            case TYPE_PIC:
                view = inflater.inflate(R.layout.item_pic, parent, false);
                ShowPicViewHolder showPicViewHolder = new ShowPicViewHolder(view);
                setListener(parent, showPicViewHolder, viewType);
                return showPicViewHolder;
        }

        return null;
    }


    protected void setListener(final ViewGroup parent, final RecyclerView.ViewHolder viewHolder, int viewType) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return onItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int itemViewType = holder.getItemViewType();
        ShowPicViewHolder showPicViewHolder = (ShowPicViewHolder) holder;
        switch (itemViewType) {
            case TYPE_ADD:
                showPicViewHolder.iv_pic.setScaleType(ImageView.ScaleType.FIT_XY);
                showPicViewHolder.iv_pic.setImageResource(addPicRes);
                break;
            case TYPE_PIC:
                Object path = list.get(position);
                if (path instanceof String) {
                    GlideUtils.loadImage(path.toString(), showPicViewHolder.iv_pic);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public int getItemCount() {
        return isShowAdd ? list.size() + 1 : list.size();
    }

    class ShowPicViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_pic;

        public ShowPicViewHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            iv_pic.getLayoutParams().width = imageSize;
            iv_pic.getLayoutParams().height = imageSize;
            iv_pic.requestLayout();

        }
    }

}
