package com.example.tianhuaye.monkey.ui.activity;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.base.BasePresenter;
import com.example.tianhuaye.monkey.base.activity.BaseBottomTabActivity;
import com.example.tianhuaye.monkey.contract.MainContract;
import com.example.tianhuaye.monkey.presenter.MainPresenter;
import com.example.tianhuaye.monkey.test.TestFragment;

import java.util.ArrayList;

public class MainActivity extends BaseBottomTabActivity<MainContract.View, MainPresenter<MainContract.View>> {


    @Override
    public int generateIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.view_pager);
        mTabView = findViewById(R.id.bottom_navigation_view);

        mViewPager.addOnPageChangeListener(this);
        mTabView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void addFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        mAdapter = new PlayFragmentAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(mAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.menuItem = menuItem;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_habit:
                mViewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_person:
                mViewPager.setCurrentItem(3);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            mTabView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = mTabView.getMenu().getItem(i);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter();
    }
}
