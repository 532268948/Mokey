package com.example.lib_common.base.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.adapter.BaseFragmentPagerAdapter;

import java.util.List;

/**
 * @author: tianhuaye
 * @date:   2018/11/12 16:53
 * @description:
 */
public abstract class BaseTopTabFragment<V,T extends BasePresenter<V>> extends BaseFragment {

    protected List<Fragment> fragmentList;
    protected List<String> titleList;
    protected TopTabFragmentPagerAdapter mAdapter;

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;

    /**
     * fragment和title集合添加
     */
    public abstract void addFragmentAndTitle();

    protected static class TopTabFragmentPagerAdapter extends BaseFragmentPagerAdapter{

        public TopTabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    }


}
