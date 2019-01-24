package com.example.lib_common.base.view;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lib_common.R;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.util.ViewUtil;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/24
 * @time : 13:19
 * @email : 15869107730@163.com
 * @note : 加载更多
 */
public class MoreViewHolder extends BaseRecyclerHolder {
    public boolean isUploadMore = false;

    private TextView noMoreTv;
    private ProgressBar progressBar;
    private TextView loadingTv;

    public MoreViewHolder(View itemView, Context context) {
        super(itemView,context);
        noMoreTv =itemView.findViewById(R.id.no_more_data);
        progressBar = itemView.findViewById(R.id.more_item_progress);
        loadingTv = itemView.findViewById(R.id.more_item_loading);
    }

    public void setLoading(boolean loading) {
        //加载更多
        if (loading) {
            ViewUtil.setViewGone(noMoreTv);
            ViewUtil.setViewVisible(progressBar);
            ViewUtil.setViewVisible(loadingTv);
        } else { //没有更多数据
            ViewUtil.setViewVisible(noMoreTv);
            ViewUtil.setViewGone(progressBar);
            ViewUtil.setViewGone(loadingTv);
        }
    }
}
