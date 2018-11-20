package com.example.lib_common.base.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.BaseView;

import java.util.List;

/**
 * project: Monkey
 * author : 叶天华
 * date   : 2018/10/15
 * time   : 13:59
 * email  : 15869107730@163.com
 * note   : 封装有BottomNavigationView的BaseActivity
 */
public abstract class BaseBottomTabActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    protected BottomNavigationView mTabView;
    protected List<Fragment> fragmentList;
    protected ViewPager mViewPager;
    protected MenuItem menuItem;
    protected PlayFragmentAdapter mAdapter;

    protected abstract void addFragment();

    @Override
    public void initData() {
        super.initData();
        addFragment();
    }

    protected static class PlayFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public PlayFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return this.fragmentList.size();
        }
    }
}
