package com.example.module_report.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.util.DateUtil;
import com.example.module_report.R;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/23
 * @time : 17:22
 * @email : 15869107730@163.com
 * @note :
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    List<ReportBean> reportBeans;

    public MonthAdapter(List<ReportBean> list){
        reportBeans=list;
    }

    @NonNull
    @Override
    public MonthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_month, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mDateTv.setText(DateUtil.formatOne(reportBeans.get(i).getStartTime()));
        viewHolder.mGradeTv.setText(reportBeans.get(i).getGrade()+"");
    }

    @Override
    public int getItemCount() {
        return reportBeans == null ? 0 : reportBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDateTv;
        private TextView mGradeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateTv=itemView.findViewById(R.id.tv_date);
            mGradeTv=itemView.findViewById(R.id.tv_grade);
        }
    }
}
