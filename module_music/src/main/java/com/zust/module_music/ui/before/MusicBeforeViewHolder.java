package com.zust.module_music.ui.before;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 13:03
 * @email : 15869107730@163.com
 * @note :
 */
class MusicBeforeViewHolder extends BaseRecyclerHolder {

    private TextView mNumberTv;
    private TextView mNameTv;
    private TextView mLengthTv;
    private TextView mListenNumberTv;
    private ImageView mPayIv;
    private ImageView mListenLevelIv;

    public MusicBeforeViewHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
        mNumberTv = itemView.findViewById(R.id.tv_music_number);
        mNameTv = itemView.findViewById(R.id.tv_music_name);
        mLengthTv = itemView.findViewById(R.id.tv_music_length);
        mListenNumberTv = itemView.findViewById(R.id.tv_listen_number);
        mPayIv = itemView.findViewById(R.id.iv_pay);
        mListenLevelIv = itemView.findViewById(R.id.iv_listening);
    }

    public void setInfo(MusicItem musicItem) {
        if (musicItem != null) {
            if (mNameTv != null) {
                mNameTv.setText(musicItem.getName());
            }
            if (mLengthTv != null) {
                mLengthTv.setText(musicItem.getDuration());
            }
            if (mListenNumberTv != null) {
                mListenNumberTv.setText(String.format(mContext.getResources().getString(R.string.music_before_item_number), musicItem.getPlayTimes()));
            }
            Drawable drawable;
            if (mListenLevelIv != null) {
                AnimationDrawable animationDrawable = (AnimationDrawable) mListenLevelIv.getDrawable();
                if (musicItem.isPlaying()) {
                    ViewUtil.setViewGone(mNumberTv);
                    ViewUtil.setViewVisible(mListenLevelIv);
                    mNameTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_name_select_color));
                    mLengthTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_length_select_color));
                    drawable = mContext.getResources().getDrawable(R.drawable.music_select_headphone);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mListenNumberTv.setCompoundDrawables(drawable, null, null, null);
                    mListenNumberTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_listen_select_number_color));
                    if (animationDrawable != null) {
                        animationDrawable.start();
                    }
                } else {
                    ViewUtil.setViewGone(mListenLevelIv);
                    ViewUtil.setViewVisible(mNumberTv);
                    mNameTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_name_normal_color));
                    mLengthTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_length_normal_color));
                    drawable = mContext.getResources().getDrawable(R.drawable.music_headphone);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mListenNumberTv.setCompoundDrawables(drawable, null, null, null);
                    mListenNumberTv.setTextColor(mContext.getResources().getColor(R.color.music_before_item_listen_normal_number_color));
                    if (animationDrawable != null) {
                        animationDrawable.stop();
                    }
                }
            }
        }
    }

    public void setNumber(int position) {
        if (mNumberTv != null) {
            mNumberTv.setText(String.format(mContext.getResources().getString(R.string.music_before_item_number), position+1));
        }
    }
}
