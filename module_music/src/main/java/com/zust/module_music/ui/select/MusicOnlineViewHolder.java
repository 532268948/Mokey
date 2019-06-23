package com.zust.module_music.ui.select;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.MusicOnlineItem;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.BuildConfig;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/22
 * @time : 11:21
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicOnlineViewHolder extends BaseRecyclerHolder {

    private TextView mNumberTv;
    private TextView mNameTv;
    private TextView mLengthTv;
    private TextView mListenNumberTv;
    public ImageView mPlayIv;
    private ImageView mListenLevelIv;
    private ProgressBar mProgressBar;

    public MusicOnlineViewHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
        mNumberTv = itemView.findViewById(R.id.tv_music_number);
        mNameTv = itemView.findViewById(R.id.tv_music_name);
        mLengthTv = itemView.findViewById(R.id.tv_music_length);
        mListenNumberTv = itemView.findViewById(R.id.tv_listen_number);
        mPlayIv = itemView.findViewById(R.id.iv_pay);
        mListenLevelIv = itemView.findViewById(R.id.iv_listening);
        mProgressBar = itemView.findViewById(R.id.progress_bar);
    }

    public void setInfo(MusicOnlineItem musicItem) {
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
                    Glide.with(mContext).load(R.drawable.item_music_pause).into(mPlayIv);
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
                    Glide.with(mContext).load(R.drawable.item_music_play).into(mPlayIv);
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

    public void setProgress(int progress) {
        if (BuildConfig.DEBUG){
            Log.e("MusicOnlineViewHolder", "setProgress: "+progress);
        }
        if (progress <= 0) {
            ViewUtil.setViewGone(mProgressBar);
        } else if (progress < 100) {
            mProgressBar.setProgress(progress);
            ViewUtil.setViewVisible(mProgressBar);
        } else {
            ViewUtil.setViewGone(mProgressBar);
        }
    }
}
