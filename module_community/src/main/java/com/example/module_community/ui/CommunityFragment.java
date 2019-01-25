package com.example.module_community.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.util.ViewUtil;
import com.example.module_community.R;
import com.example.module_community.contract.CommunityContract;
import com.example.module_community.presenter.CommunityPresenter;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 15:28
 * @description:
 */
public class CommunityFragment extends BaseListFragment<CommunityContract.View, CommunityPresenter<CommunityContract.View>> implements CommunityContract.View {

    @Override
    protected CommunityPresenter<CommunityContract.View> createPresenter() {
        return new CommunityPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        return view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showNormal() {
        super.showNormal();
        setEmptyViewVisible(false,false,null);
        ViewUtil.setViewVisible(mRecyclerView);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        setEmptyViewVisible(false,false,null);
        ViewUtil.setViewVisible(mRecyclerView);
    }
}
