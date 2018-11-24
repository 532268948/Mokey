package com.example.module_habit.ui.prepare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BaseItem;
import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.module_habit.R;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/23 14:46
 * description:
 */
public class PrepareAdapter extends BaseRecyclerAdapter {


    public PrepareAdapter(Context context) {
        super(context);
    }

    @Override
    protected List<BaseItem> getCacheData() {
        return null;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.habit_prepare_tip_item, viewGroup, false);
        return new PrepareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder baseRecyclerHolder, int i) {
    }

    private static class PrepareViewHolder extends BaseRecyclerHolder {

        public PrepareViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
