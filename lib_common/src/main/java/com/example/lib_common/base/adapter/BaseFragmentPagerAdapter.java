package com.example.lib_common.base.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/13 12:24
 * description:
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected List<Fragment> fragments;
    protected List<String> titleList;
    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void setData(List<Fragment> fragments, List<String> titleList) {
        this.fragments = fragments;
        this.titleList=titleList;
        notifyDataSetChanged();
    }
}
