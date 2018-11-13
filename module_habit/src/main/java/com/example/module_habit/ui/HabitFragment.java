package com.example.module_habit.ui;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.adapter.BaseFragmentPagerAdapter;
import com.example.lib_common.base.fragment.BaseTopTabFragment;
import com.example.lib_common.test.BlankFragment;
import com.example.module_habit.R;
import com.example.module_habit.contract.HabitContract;
import com.example.module_habit.presenter.HabitPresenter;

import java.util.ArrayList;

/**
 * author: tianhuaye
 * date:   2018/11/13 12:51
 * description:
 */
public class HabitFragment extends BaseTopTabFragment<HabitContract.View, HabitPresenter<HabitContract.View>> {
    @Override
    public void addFragmentAndTitle() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new BlankFragment());
        fragmentList.add(new BlankFragment());
        if (titleList == null) {
            titleList = new ArrayList<>();
        }
        titleList.add(mContext.getResources().getString(R.string.habit_top_tab_habit));
        titleList.add(mContext.getResources().getString(R.string.habit_top_tab_alarm));
        if (mAdapter == null) {
            mAdapter = new TopTabFragmentPagerAdapter(getChildFragmentManager());
        }
        mAdapter.setData(fragmentList,titleList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected HabitPresenter createPresenter() {
        return new HabitPresenter();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_habit, container, false);
        mViewPager = view.findViewById(R.id.view_pager);
        mTabLayout = view.findViewById(R.id.tab_layout);
        addFragmentAndTitle();
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String message, String code) {

    }
}
