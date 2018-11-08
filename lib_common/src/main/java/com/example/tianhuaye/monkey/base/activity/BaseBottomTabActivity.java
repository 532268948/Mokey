package com.example.tianhuaye.monkey.base.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.tianhuaye.monkey.base.BaseActivity;
import com.example.tianhuaye.monkey.base.BasePresenter;
import com.example.tianhuaye.monkey.base.BaseView;

import java.util.List;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/15
 * time   : 13:59
 * email  : 15869107730@163.com
 * note   :
 */
public abstract class BaseBottomTabActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private BottomNavigationView mBottomNavigationView;
    private List<Fragment> fragmentList;
    private ViewPager mViewPager;

    protected abstract void addFragment();

    static class PlayFragmentAdapter extends FragmentPagerAdapter {

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
