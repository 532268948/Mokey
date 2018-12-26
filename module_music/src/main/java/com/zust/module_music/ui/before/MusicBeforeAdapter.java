package com.zust.module_music.ui.before;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.base.bean.MusicItem;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_before_item, viewGroup, false);
        return new MusicBeforeViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            BaseItem baseItem = mItems.get(position);
            if (holder instanceof MusicBeforeViewHolder) {
                MusicBeforeViewHolder viewHolder = (MusicBeforeViewHolder) holder;
                MusicItem musicItem = (MusicItem) baseItem;
                viewHolder.setInfo(musicItem);
                viewHolder.setNumber(position);
            }
        }
        super.onBindViewHolder(holder, position);
    }

}
