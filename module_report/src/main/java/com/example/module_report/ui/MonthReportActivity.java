package com.example.module_report.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.bean.ReportBean;
import com.example.module_report.R;

import java.util.List;

public class MonthReportActivity extends AppCompatActivity {

    private List<ReportBean> reportBeans;
    private RecyclerView mRecyclerView;
    private TitleBar mTitleBar;
    private MonthAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_report);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTitleBar=findViewById(R.id.title_bar);
        reportBeans = (List<ReportBean>) getIntent().getSerializableExtra("report");
        int month=getIntent().getIntExtra("month",0);
        mTitleBar.setTitleTextStr(month+"月睡眠报告");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new MonthAdapter(reportBeans);
        mRecyclerView.setAdapter(mAdapter);
        Log.e("MonthReportActivity", "onCreate: " + reportBeans.size());
    }
}
