package com.zust.module_music.ui.select;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecycler2Adapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.view.MoreViewHolder;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.HasMoreItem;
import com.example.lib_common.bean.MusicOnlineItem;
import com.example.lib_common.common.Constant;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/22
 * @time : 11:13
 * @email : 15869107730@163.com
 * @note :
 */
public class OnlineAdapter extends BaseRecycler2Adapter {

    private int lastPlay = -1;
    private OnPlayBtnClickListener onPlayBtnClickListener;

    public OnlineAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        BaseRecyclerHolder holder = null;
        if (viewType == Constant.ItemType.MUSIC_ONLINE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_music_online, viewGroup, false);
            holder = new MusicOnlineViewHolder(view, mContext);
        } else if (viewType == Constant.ItemType.LOAD_MORE_DATA) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_more, viewGroup, false);
            holder = new MoreViewHolder(view, mContext);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size() && mItems.get(position) != null) {
            BaseItem baseItem = mItems.get(position);
            int itemViewType = getItemViewType(position);
            if (itemViewType == Constant.ItemType.MUSIC_ONLINE) {
                final MusicOnlineViewHolder viewHolder = (MusicOnlineViewHolder) holder;
                MusicOnlineItem musicItem = (MusicOnlineItem) baseItem;
                viewHolder.setInfo(musicItem);
                viewHolder.setNumber(position);
                viewHolder.setProgress(musicItem.getProgress());
                viewHolder.mPlayIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPlayBtnClickListener != null) {
                            onPlayBtnClickListener.onPlayBtnClick(viewHolder.getLayoutPosition());
                        }
                    }
                });
            } else if (itemViewType == Constant.ItemType.LOAD_MORE_DATA) {
                MoreViewHolder viewHolder = (MoreViewHolder) holder;
                HasMoreItem hasMoreItem = (HasMoreItem) baseItem;
                if (hasMoreItem.getHasMore()) {
                    viewHolder.isUploadMore = true;
                    viewHolder.setLoading(true);
                } else {
                    viewHolder.isUploadMore = false;
                    viewHolder.setLoading(false);
                }
            }
        }
    }

    public void notifyItemPlay(int position) {
        for (int i = 0; i < mItems.size(); i++) {
            BaseItem baseItem = mItems.get(i);
            if (baseItem != null) {
                if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE && i == lastPlay) {
                    MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                    musicOnlineItem.setPlaying(false);
                    notifyItemChanged(i);
                    Log.e("OnlineAdapter", "notifyItemPlay: i " + i);
                }
            }
        }
        notifyItemChanged(position);
        Log.e("OnlineAdapter", "notifyItemPlay: position  " + position);
        lastPlay = position;

    }

    public void addPlayBtnClickListener(OnPlayBtnClickListener onPlayBtnClickListener) {
        this.onPlayBtnClickListener = onPlayBtnClickListener;
    }

    interface OnPlayBtnClickListener {
        void onPlayBtnClick(int position);
    }
}
