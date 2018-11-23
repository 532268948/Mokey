package com.example.module_habit.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.adapter.BaseFragmentPagerAdapter;
import com.example.lib_common.base.fragment.BaseTopTabFragment;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.test.BlankFragment;
import com.example.module_habit.R;
import com.example.module_habit.contract.HabitContract;
import com.example.module_habit.presenter.HabitPresenter;
import com.example.module_habit.ui.alarm.AlarmFragment;
import com.example.module_habit.ui.lifestyle.LifestyleFragment;

import java.util.ArrayList;

/**
 * author: tianhuaye
 * date:   2018/11/13 12:51
 * description:
 */
public class HabitFragment extends BaseTopTabFragment<HabitContract.View, HabitPresenter<HabitContract.View>> implements TitleBar.LeftIconClickListener {

    private TitleBar mTitleBar;

    @Override
    public void addFragmentAndTitle() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new LifestyleFragment());
        fragmentList.add(new AlarmFragment());
        if (titleList == null) {
            titleList = new ArrayList<>();
        }
        titleList.add(mContext.getResources().getString(R.string.habit_top_tab_habit));
        titleList.add(mContext.getResources().getString(R.string.habit_top_tab_alarm));
        if (mAdapter == null) {
            mAdapter = new TopTabFragmentPagerAdapter(getChildFragmentManager());
        }
        mAdapter.setData(fragmentList, titleList);
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
        mTitleBar = view.findViewById(R.id.title_bar);
        mTabLayout = new TabLayout(getContext());
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.theme_color));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(getContext().getResources().getColor(R.color.habit_tab_top_normal_color), getContext().getResources().getColor(R.color.habit_tab_top_pressed_color));
        mTitleBar.addTabLayout(mTabLayout);
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

    @Override
    public void leftIconClick() {

    }

    @Override
    public void onClick(View view) {

    }
}
