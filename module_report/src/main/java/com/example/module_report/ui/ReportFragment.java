package com.example.module_report.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.bean.BaseItem;
import com.example.lib_common.base.fragment.BaseFragment;
import com.example.lib_common.base.view.WrapContentHeightViewPager;
import com.example.module_report.R;
import com.example.module_report.bean.ReportBean;
import com.example.module_report.contract.ReportContract;
import com.example.module_report.presenter.ReportPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 10:38
 * @description:
 */
public class ReportFragment extends BaseFragment<ReportContract.View, ReportPresenter<ReportContract.View>> implements ReportContract.View {

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
        mViewPager = view.findViewById(R.id.view_pager);
        if (mItems == null) {
            mItems = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ReportBean reportBean = new ReportBean();
                mItems.add(reportBean);
            }
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

    }

    @Override
    public void initData() {

//        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
