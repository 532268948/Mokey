package com.example.module_report.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.module_report.R;
import com.example.module_report.bean.QualityBean;
import com.example.module_report.ui.ReportDetailActivity;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/3 13:12
 * @description:
 */
public class ReportView extends CardView {

    private List<QualityBean> qualityBeans;

    private SleepQualityView mSleepQualityView;

    public ReportView(@NonNull Context context) {
        this(context, null);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_report, this, true);
        setRadius(20);
        setCardElevation(10);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), ReportDetailActivity.class));
            }
        });

        mSleepQualityView = findViewById(R.id.sleep_quality_view);
    }

    public void setData(List<QualityBean> qualityBeans) {
        this.qualityBeans = qualityBeans;
        if (mSleepQualityView != null) {
            mSleepQualityView.setData(this.qualityBeans);
        }
    }
}
