package com.example.tianhuaye.monkey.ui.activity;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.activity.BaseBottomTabActivity;
import com.example.lib_common.test.BlankFragment;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.ui.HabitFragment;
import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.contract.MainContract;
import com.example.tianhuaye.monkey.presenter.MainPresenter;
import com.zust.module_music.ui.MusicFragment;

import java.util.ArrayList;

/**
 * @author 53226
 */
public class MainActivity extends BaseBottomTabActivity<MainContract.View, MainPresenter<MainContract.View>> implements MainContract.View {


    @Override
    public int generateIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_status_bar_color),0);
        mViewPager = findViewById(R.id.view_pager);
        mTabView = findViewById(R.id.bottom_navigation_view);

        mViewPager.addOnPageChangeListener(this);
        mTabView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void addFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new BlankFragment());
        fragmentList.add(new MusicFragment());
        fragmentList.add(new HabitFragment());
        fragmentList.add(new BlankFragment());
        mAdapter = new PlayFragmentAdapter(getSupportFragmentManager(), fragmentList);
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

    @Override
    public void onClick(View v) {

    }
}
