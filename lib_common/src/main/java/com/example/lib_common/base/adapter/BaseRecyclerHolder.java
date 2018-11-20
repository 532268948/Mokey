package com.example.lib_common.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lib_common.base.inter.OnItemClickListener;

/**
 * project: Monkey
 * author : 叶天华
 * date   : 2018/11/17
 * time   : 19:26
 * email  : 15869107730@163.com
 * note   :
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerHolder(@NonNull View itemView) {
        super(itemView);
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
