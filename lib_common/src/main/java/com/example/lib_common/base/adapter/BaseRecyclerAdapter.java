package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.lib_common.base.BaseItem;
import com.example.lib_common.base.inter.OnItemClickListener;

import java.util.List;

/**
 * project: Monkey
 * author : 叶天华
 * date   : 2018/11/17
 * time   : 19:34
 * email  : 15869107730@163.com
 * note   :
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected List<BaseItem> mItems;
    private Context mContext;
    protected OnItemClickListener onItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        mItems = getCacheData();
    }

    @Override
    public int getItemCount() {
//        return mItems == null ? 0 : mItems.size();
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        BaseItem item = (BaseItem) getItem(position);
        if (item != null) {
            return item.itemType;
        }
        return 0;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (onItemClickListener != null) {
            holder.setOnItemClickListener(onItemClickListener);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseRecyclerHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (onItemClickListener != null) {
            holder.removeItemClickListener();
        }
    }

    public void addItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setData(List<BaseItem> mItems) {
        if (this.mItems != null) {
            this.mItems.clear();
            this.mItems.addAll(mItems);
        }
    }

    public void notifyDataChanged(List<BaseItem> mItems) {
        if (this.mItems != null) {
            this.mItems.clear();
            this.mItems.addAll(mItems);
            notifyDataSetChanged();
        }
    }

    public void notifyItemChanged(BaseItem baseItem, int position) {
        if (this.mItems != null) {
            this.mItems.set(position, baseItem);
        }
    }

    private List<BaseItem> getData() {
        return this.mItems;
    }

    private Object getItem(int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            return mItems.get(position);
        }
        return null;
    }

    protected abstract List<BaseItem> getCacheData();
}
