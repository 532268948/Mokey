package com.example.module_habit.ui.prepare;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.view.TitleBar;
import com.example.module_habit.R;
import com.example.module_habit.ui.prepare.adapter.PrepareAdapter;

public class PrepareActivity extends BaseActivity<PrepareContract.View, PreparePresenter<PrepareContract.View>> implements OnItemClickListener, BaseView {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private PrepareAdapter mAdapter;
    private PrepareTipDialog mTipDialog;

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
    public void initListener() {
        mAdapter.addItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        Log.e("PrepareActivity", "onItemClick: " + position);
        showTipDialog();
    }

    private void showTipDialog() {
        if (mTipDialog == null) {
            mTipDialog = new PrepareTipDialog();
            WindowManager wm = (WindowManager) this
                    .getSystemService(Context.WINDOW_SERVICE);
            mTipDialog.setSize((int) (wm.getDefaultDisplay().getWidth() * 0.7), 0);
        }
        mTipDialog.show(getSupportFragmentManager());
    }
}
