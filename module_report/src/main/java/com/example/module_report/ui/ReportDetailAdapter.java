package com.example.module_report.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerAdapter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.DreamBean;
import com.example.lib_common.common.Constant;
import com.example.module_report.R;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 16:38
 * @description:
 */
public class ReportDetailAdapter extends BaseRecyclerAdapter {

    public ReportDetailAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view=null;
        BaseRecyclerHolder holder=null;
        if (viewType==Constant.ItemType.RECORD_DREAM){
            view = LayoutInflater.from(mContext).inflate(R.layout.report_detail_dream_item, viewGroup, false);
            holder=new ReportDetailHolder(view, mContext);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        if (mItems.get(position).getItemType()==Constant.ItemType.RECORD_DREAM){
            if (holder instanceof ReportDetailHolder) {
                ReportDetailHolder viewHolder = (ReportDetailHolder) holder;
                DreamBean dreamBean=(DreamBean)mItems.get(position);
                viewHolder.setInfo(dreamBean);
            }
        }

    }
}
