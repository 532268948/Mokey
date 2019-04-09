package com.example.module_report.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.lib_common.util.DateUtil;
import com.example.module_report.R;
import com.example.module_report.bean.ReportBean;
import com.example.module_report.ui.ReportDetailActivity;

/**
 * @author: tianhuaye
 * @date: 2019/1/3 13:12
 * @description:
 */
public class ReportView extends CardView {

    private ReportBean reportBean;

    private SleepQualityView mSleepQualityView;
    private TextView mGradeTv;
    private TextView mDateTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mStartTv;
    private TextView mEndTv;

    public ReportView(@NonNull Context context) {
        this(context, null);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_report, this, true);
        mGradeTv = view.findViewById(R.id.tv_grade);
        mDateTv = view.findViewById(R.id.tv_date);
        mStartTimeTv = view.findViewById(R.id.tv_start_time);
        mEndTimeTv = view.findViewById(R.id.tv_end_time);
        mSleepQualityView = findViewById(R.id.sleep_quality_view);
        mStartTv = view.findViewById(R.id.tv_start);
        mEndTv = view.findViewById(R.id.tv_end);
        setRadius(20);
        setCardElevation(10);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ReportDetailActivity.class);
                intent.putExtra("report",reportBean);
                getContext().startActivity(intent);
            }
        });
    }

    public void setData(ReportBean reportBean) {
        this.reportBean = reportBean;
        if (mSleepQualityView != null) {
            mSleepQualityView.setData(this.reportBean.getQualityBeans());
        }
//        if (BuildConfig.DEBUG) {
//            Log.e("ReportView", "setData: "+reportBean.getStartTime()+" "+DateUtil.formatTwo(reportBean.getStartTime())+" "+reportBean.getEndTime()+" "+DateUtil.formatTwo(reportBean.getEndTime()));
//        }
        mGradeTv.setText(String.valueOf(reportBean.getGrade()));
        mDateTv.setText(DateUtil.formatOne(reportBean.getStartTime()));
        mStartTv.setText(DateUtil.formatTwo(reportBean.getStartTime()));
        mEndTv.setText(DateUtil.formatTwo(reportBean.getEndTime()));
        mStartTimeTv.setText(String.format(getResources().getString(R.string.report_view_sleep_start_time), DateUtil.formatTwo(reportBean.getStartTime())));
        mEndTimeTv.setText(String.format(getResources().getString(R.string.report_view_sleep_end_time), DateUtil.formatTwo(reportBean.getEndTime())));
    }
}
