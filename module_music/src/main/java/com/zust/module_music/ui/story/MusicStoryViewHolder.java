package com.zust.module_music.ui.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.MusicItem;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/29
 * @time : 14:03
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicStoryViewHolder extends BaseRecyclerHolder {

    private ImageView mCoverIv;
    private TextView mTitleTv;
    private TextView mTimeTv;

    public MusicStoryViewHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
        mCoverIv = itemView.findViewById(R.id.iv_story_cover);
        mTitleTv = itemView.findViewById(R.id.tv_story_title);
        mTimeTv = itemView.findViewById(R.id.tv_story_time);
    }

    public void setInfo(MusicItem musicItem){

    }
}
