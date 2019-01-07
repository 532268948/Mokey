package com.example.module_report.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.util.ViewUtil;
import com.example.module_report.R;
import com.example.module_report.bean.DreamBean;
import com.example.module_report.bean.QualityBean;
import com.example.module_report.contract.ReportDetailContract;
import com.example.module_report.presenter.ReportDetailPresenter;
import com.example.module_report.view.SleepQualityView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhuaye
 */
public class ReportDetailActivity extends BaseActivity<ReportDetailContract.View, ReportDetailPresenter<ReportDetailContract.View>> implements ReportDetailContract.View {

    private SleepQualityView mSleepQualityView;
    private RecyclerView mRecyclerView;
    private ReportDetailAdapter mAdapter;
    private List<BaseItem> mItems;
    private LinearLayout mFoldTitleLl;

    @Override
    protected ReportDetailPresenter<ReportDetailContract.View> createPresenter() {
        return new ReportDetailPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_report_detail;
    }

    @Override
    public void initView() {
        mSleepQualityView = findViewById(R.id.sleep_quality_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFoldTitleLl = findViewById(R.id.ll_fold);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        List<QualityBean> qualityBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            QualityBean qualityBean = new QualityBean();
            qualityBean.setType(i % 4);
            qualityBean.setGrade(10 + (i % 5) * 20);
            qualityBeans.add(qualityBean);
        }
        mSleepQualityView.setData(qualityBeans);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReportDetailAdapter(this);
        if (mItems == null) {
            mItems = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                DreamBean dreamBean = new DreamBean();
                mItems.add(dreamBean);
            }
        }
        if (mItems != null && mItems.size() > 0) {
            ViewUtil.setViewVisible(mFoldTitleLl);
        } else {
            ViewUtil.setViewGone(mFoldTitleLl);
        }
        mAdapter.setData(mItems);
        mRecyclerView.setAdapter(mAdapter);
    }


}
