package com.example.module_community.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.bean.response.FirstPageBean;
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

    private FirstPageBean firstPageBean;
    private FirstPageAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected CommunityPresenter<CommunityContract.View> createPresenter() {
        return new CommunityPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        firstPageBean=new FirstPageBean();
        mAdapter = new FirstPageAdapter();
        mAdapter.setData(firstPageBean);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return mAdapter.getSpanSize(i);
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getFirstPageMessage();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showNormal() {
        super.showNormal();
        setEmptyViewVisible(false, false, null);
        ViewUtil.setViewVisible(mRecyclerView);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        setEmptyViewVisible(false, false, null);
        ViewUtil.setViewVisible(mRecyclerView);
    }

    @Override
    public void getDataSuccess(FirstPageBean firstPageBean) {
        mAdapter.setData(firstPageBean);
    }
}
