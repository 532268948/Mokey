package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lib_common.base.inter.OnItemClickListener;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/11/17
 * @time : 19:26
 * @email : 15869107730@163.com
 * @note :
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    protected Context mContext;

    public BaseRecyclerHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mContext = context;
    }

    public void setOnItemClickListener(final OnItemClickListener l) {
        if (l == null || itemView == null) {
            return;
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.onItemClick(BaseRecyclerHolder.this, getAdapterPosition());
            }
        });
    }

    public void removeItemClickListener() {
        if (itemView != null) {
            itemView.setOnClickListener(null);
        }
    }
}
