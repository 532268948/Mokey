package com.example.module_habit.ui.prepare;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.view.TitleBar;
import com.example.module_habit.R;
import com.example.module_habit.ui.prepare.adapter.PrepareAdapter;

public class PrepareActivity extends BaseActivity<PrepareContract.View, PreparePresenter<PrepareContract.View>> implements BaseView {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private PrepareAdapter mAdapter;

    @Override
    protected PreparePresenter<PrepareContract.View> createPresenter() {
        return new PreparePresenter<>();
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_prepare;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new PrepareAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }
}
