package com.zust.module_music.ui.select;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib_common.base.activity.BaseTopTabActivity;
import com.example.lib_common.base.fragment.BaseTopTabFragment;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.common.Constant;
import com.zust.module_music.R;
import com.zust.module_music.contract.MusicSelectContract;
import com.zust.module_music.presenter.select.MusicSelectPresenter;

import java.util.ArrayList;

@Route(path = Constant.Activity.ACTIVITY_MUSIC_SELECT)
public class MusicSelectActivity extends BaseTopTabActivity<MusicSelectContract.View, MusicSelectPresenter<MusicSelectContract.View>> implements MusicSelectContract.View {

    private TitleBar mTitleBar;

    @Override
    protected MusicSelectPresenter<MusicSelectContract.View> createPresenter() {
        return new MusicSelectPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_music_select;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>(3);
            fragmentList.add(new OnlineFragment());
            fragmentList.add(new Fragment());
            fragmentList.add(new SystemMusicFragment());
        }
        if (titleList == null) {
            titleList = new ArrayList<>();
        }
        titleList.add(getResources().getString(R.string.music_select_online));
        titleList.add(getResources().getString(R.string.music_select_local));
        titleList.add(getResources().getString(R.string.music_select_system));
        if (mAdapter == null) {
            mAdapter = new BaseTopTabFragment.TopTabFragmentPagerAdapter(getSupportFragmentManager());
        }
        mAdapter.setData(fragmentList, titleList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(Integer.MAX_VALUE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void startPlay(MusicItem musicItem){

    }

    @Override
    public void releaseCache() {
        super.releaseCache();
    }
}
