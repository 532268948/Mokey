package com.example.module_community.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.bean.BaseItem;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 15:46
 * @description:
 */
public class CommunityAdapter extends BaseRecyclerAdapter {

    public CommunityAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return super.onCreateViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
