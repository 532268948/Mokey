package com.example.module_report.ui;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.DreamBean;
import com.example.lib_common.util.DateUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_report.R;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 17:20
 * @description:
 */
public class ReportDetailHolder extends BaseRecyclerHolder {

    private TextView mRecordTimeTv;
    private ImageView mPlayIv;
    private TextView mDuringTv;

    public ReportDetailHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
        mRecordTimeTv = itemView.findViewById(R.id.tv_dream_time);
        mPlayIv = itemView.findViewById(R.id.iv_play);
        mDuringTv = itemView.findViewById(R.id.tv_during);
    }

    public void setInfo(DreamBean dreamBean) {
        mRecordTimeTv.setText(DateUtil.formatTwo(dreamBean.getDream_time()));
        mDuringTv.setText(DateUtil.getDistanceTime(dreamBean.getDuring()));
        Drawable drawable;
        if (mPlayIv != null) {
            AnimationDrawable animationDrawable = (AnimationDrawable) mPlayIv.getDrawable();

            if (dreamBean.isPlaying()) {
                ViewUtil.setViewVisible(mPlayIv);
                if (animationDrawable != null) {
                    animationDrawable.start();
                }
            } else {
                ViewUtil.setViewInVisible(mPlayIv);
                if (animationDrawable != null) {
                    animationDrawable.stop();
                    animationDrawable.setLevel(0);
                }
            }

        }
    }
}
