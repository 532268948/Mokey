package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.inter.OnLoadMoreListener;
import com.example.lib_common.base.view.MoreViewHolder;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.HasMoreItem;
import com.example.lib_common.common.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/22
 * @time : 11:07
 * @email : 15869107730@163.com
 * @note :
 */
public abstract class BaseRecycler2Adapter extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected List<BaseItem> mItems;
    protected Context mContext;
    protected OnItemClickListener onItemClickListener;
    protected OnLoadMoreListener onLoadMoreListener;

    public BaseRecycler2Adapter(Context context) {
        this.mContext = context;
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

    public void setData(List<? extends BaseItem> mItems) {
        this.mItems = (List<BaseItem>) mItems;
        notifyDataSetChanged();
    }

    public void refreshData(List<? extends BaseItem> mItems){
        if (this.mItems!=null){
            this.mItems.clear();
            this.mItems.addAll(mItems);
        }else {
            this.mItems= (List<BaseItem>) mItems;
        }
        this.mItems.add(new HasMoreItem(true, Constant.ItemType.LOAD_MORE_DATA));
        notifyDataSetChanged();
    }

    public void insertAll(List<? extends BaseItem> items) {
        if (items == null || items.size() == 0) {
            setNoMoreData();
            return;
        }
        if (mItems == null) {
            mItems = (List<BaseItem>) items;
            notifyDataSetChanged();
            return;
        }
        if (mItems.size() > 0) {
            if (mItems.get(mItems.size() - 1).getItemType() == Constant.ItemType.LOAD_MORE_DATA) {
                mItems.remove(mItems.size() - 1);
            }
        }
        mItems.addAll(items);
        mItems.add(new HasMoreItem(true, Constant.ItemType.LOAD_MORE_DATA));
        notifyDataSetChanged();

    }

    public void setNoMoreData() {
        if (mItems == null) {
            mItems = new ArrayList<>();
            mItems.add(new HasMoreItem(false, Constant.ItemType.LOAD_MORE_DATA));
            notifyDataSetChanged();
            return;
        }
        if (mItems.size() > 0) {
            if (mItems.get(mItems.size() - 1).getItemType() == Constant.ItemType.LOAD_MORE_DATA) {
                ((HasMoreItem) mItems.get(mItems.size() - 1)).setHasMore(false);
                notifyItemChanged(mItems.size() - 1);
                return;
            }
        }
        mItems.add(new HasMoreItem(false, Constant.ItemType.LOAD_MORE_DATA));
        notifyDataSetChanged();
    }

    public void setEmpty(){
        mItems.clear();
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
