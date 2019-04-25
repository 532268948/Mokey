package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.inter.OnLoadMoreListener;
import com.example.lib_common.base.view.MoreViewHolder;
import com.example.lib_common.bean.BaseItem;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/11/17
 * @time : 19:34
 * @email : 15869107730@163.com
 * @note :
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected List<BaseItem> mItems;
    protected Context mContext;
    protected OnItemClickListener onItemClickListener;
    protected OnLoadMoreListener onLoadMoreListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItem item = (BaseItem) getItem(position);
        if (item != null) {
            return item.getItemType();
        }
        return 0;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof MoreViewHolder) {
            MoreViewHolder moreHolder = (MoreViewHolder) holder;
            if (moreHolder.isUploadMore) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
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

    public void addLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setData(List<BaseItem> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

//    public void notifyDataChanged(List<BaseItem> mItems) {
//        if (this.mItems != null) {
//            this.mItems.clear();
//            this.mItems.addAll(mItems);
//            notifyDataSetChanged();
//        }
//    }


    public void insertAll(List<BaseItem> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        if (mItems == null) {
            mItems = items;
            notifyDataSetChanged();
            return;
        }

        mItems.addAll(items);
        notifyDataSetChanged();

    }

    public void notifyItemChanged(BaseItem baseItem, int position) {
        if (this.mItems != null) {
            this.mItems.set(position, baseItem);
        }
        notifyItemChanged(position);
    }

    public List<BaseItem> getData() {
        return this.mItems;
    }

    private Object getItem(int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            return mItems.get(position);
        }
        return null;
    }
}
