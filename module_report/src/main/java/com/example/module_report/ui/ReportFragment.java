package com.example.module_report.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.fragment.BaseFragment;
import com.example.lib_common.base.view.RefreshLayout;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.base.view.WrapContentHeightViewPager;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.DateUtil;
import com.example.module_report.R;
import com.example.module_report.contract.ReportContract;
import com.example.module_report.presenter.ReportPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 10:38
 * @description:
 */
public class ReportFragment extends BaseFragment<ReportContract.View, ReportPresenter<ReportContract.View>> implements ReportContract.View {

    private RefreshLayout mSwipeRefreshLayout;
    private TitleBar mTitleBar;

    private WrapContentHeightViewPager mViewPager;
    private ReportPagerAdapter mPagerAdapter;
    private List<BaseItem> mItems;

    @Override
    protected ReportPresenter<ReportContract.View> createPresenter() {
        return new ReportPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        mTitleBar = view.findViewById(R.id.title_bar);
        mViewPager = view.findViewById(R.id.view_pager);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mPagerAdapter = new ReportPagerAdapter(getContext(), mItems);
        mViewPager.setPageMargin(50);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true, new GalleryPageTransformer());
        mViewPager.setAdapter(mPagerAdapter);
        return view;
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getUserSleepData();
            }
        });
        mTitleBar.setRightIconClickListener(new TitleBar.RightIconClickListener() {
            @Override
            public void rightIconClick() {
//                showError("tt");
                final MonthDailog monthDailog = new MonthDailog();
                monthDailog.setMonthClickListener(new MonthDailog.MonthOnClick() {
                    @Override
                    public void onMonthClick(int month) {
                        monthDailog.dismiss();
                        getMmonthData(month);

                    }
                });
                monthDailog.show(getFragmentManager(), "month");
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getUserSleepData();

//        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showUserSleepData(List<ReportBean> list) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mItems.clear();
        mItems.addAll(list);
        if (mPagerAdapter != null) {
            mPagerAdapter.notifyDataSetChanged();
        }
        mViewPager.setCurrentItem(mItems.size() - 1);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void getMmonthData(int month) {
        List<ReportBean> list = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getItemType() == Constant.ItemType.SLEEP_REPORT) {
                ReportBean reportBean = (ReportBean) mItems.get(i);
                String monthStr = DateUtil.formatThree(reportBean.getStartTime());
                if (Integer.valueOf(monthStr) == month) {
                    list.add(reportBean);
                }
            }
        }
        Intent intent = new Intent(getContext(), MonthReportActivity.class);
        intent.putExtra("report", (Serializable) list);
        intent.putExtra("month",month);
        startActivity(intent);

    }
}
