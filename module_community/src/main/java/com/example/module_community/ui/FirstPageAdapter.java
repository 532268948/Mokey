package com.example.module_community.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.bean.response.FirstPageBean;
import com.example.lib_common.bean.response.LivesBean;
import com.example.lib_common.bean.response.PartitionBean;
import com.example.lib_common.common.Constant;
import com.example.module_community.BuildConfig;
import com.example.module_community.R;
import com.example.module_community.ui.publish.LivePartitionViewHolder;
import com.example.module_community.ui.publish.LiveViewHolder;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 14:53
 * @email : 15869107730@163.com
 * @note :
 */
public class FirstPageAdapter extends Adapter {
    private FirstPageBean firstPageBean;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (i) {
            case Constant.ItemType.LIVE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live, null);
                holder = new LiveViewHolder(view, viewGroup.getContext());
                break;
            case Constant.ItemType.LIVE_ITEM_PARTITION:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live_partition_title, null);
                holder = new LivePartitionViewHolder(view, viewGroup.getContext());
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof LiveViewHolder) {
            LiveViewHolder holder = (LiveViewHolder) viewHolder;
            holder.setInfo(getLivesBean(i));
        } else if (viewHolder instanceof LivePartitionViewHolder) {
            LivePartitionViewHolder holder = (LivePartitionViewHolder) viewHolder;
            holder.setInfo(getPartitionBean(i));
        }
    }

    private PartitionBean getPartitionBean(int position) {
        return firstPageBean.getPartitions().get(position / 11).getPartition();
    }

    private LivesBean getLivesBean(int position) {
        int num = position / 11;
        int left = position % 11 - 1;
        return firstPageBean.getPartitions().get(num).getLives().get(left);
//        int lastCount = 0;
//        int currentCount = 0;
//        for (int i = 0; i < firstPageBean.getPartitions().size(); i++) {
//            currentCount = currentCount + firstPageBean.getPartitions().get(i).getLives().size()+1;
//            if (currentCount - 1 >= position) {
//                return firstPageBean.getPartitions().get(i).getLives().get(position - lastCount);
//            } else {
//                lastCount = currentCount;
//            }
//        }
//        return null;
    }

    public void setData(FirstPageBean firstPageBean) {
        this.firstPageBean = firstPageBean;
        if (BuildConfig.DEBUG) {
            Log.e("FirstPageAdapter", "setData: " + this.firstPageBean);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 11 == 0) {
            return Constant.ItemType.LIVE_ITEM_PARTITION;
        } else {
            return Constant.ItemType.LIVE_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (firstPageBean != null) {
            if (firstPageBean.getPartitions() == null) {
                count = 0;
            } else {
                for (int i = 0; i < firstPageBean.getPartitions().size(); i++) {
                    count = count + 1;
                    for (int j = 0; j < firstPageBean.getPartitions().get(i).getLives().size(); j++) {
                        count = count + 1;
                    }
                }
            }
        } else {
            count = 0;
        }
        if (BuildConfig.DEBUG) {
            Log.e("FirstPageAdapter", "getItemCount: " + count);
        }
        return count;
    }

    public int getSpanSize(int position) {
        switch (getItemViewType(position)) {
            case Constant.ItemType.LIVE_ITEM_PARTITION:
                return 2;
            case Constant.ItemType.LIVE_ITEM:
                return 1;
            default:
                return 2;
        }
    }
}
