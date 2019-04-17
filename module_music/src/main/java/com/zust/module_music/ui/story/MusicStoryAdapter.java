package com.zust.module_music.ui.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.BaseItem;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/29
 * @time : 13:45
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicStoryAdapter extends BaseRecyclerAdapter {

    public MusicStoryAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_story_item, viewGroup, false);
        return new MusicStoryViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            BaseItem baseItem = mItems.get(position);
            if (holder instanceof MusicStoryViewHolder && baseItem != null) {

            }
        }
    }
}
