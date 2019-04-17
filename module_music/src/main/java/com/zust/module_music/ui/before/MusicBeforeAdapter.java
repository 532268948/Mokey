package com.zust.module_music.ui.before;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.HasMoreItem;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.base.view.MoreViewHolder;
import com.example.lib_common.common.Constant;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 12:57
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBeforeAdapter extends BaseRecyclerAdapter {


    public MusicBeforeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        BaseRecyclerHolder holder = null;
        if (viewType == Constant.ItemType.MUSIC_BEFORE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.music_before_item, viewGroup, false);
            holder = new MusicBeforeViewHolder(view, mContext);
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
            if (itemViewType == Constant.ItemType.MUSIC_BEFORE) {
                MusicBeforeViewHolder viewHolder = (MusicBeforeViewHolder) holder;
                MusicItem musicItem = (MusicItem) baseItem;
                viewHolder.setInfo(musicItem);
                viewHolder.setNumber(position);
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

}
