package com.zust.module_music.ui.before;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.bean.MusicItem;
import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.zust.module_music.R;
import com.zust.module_music.contract.before.MusicBeforeContract;
import com.zust.module_music.presenter.before.MusicBeforePresenter;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 11:13
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBeforeFragment extends BaseListFragment<MusicBeforeContract.View, MusicBeforePresenter<MusicBeforeContract.View>> implements MusicBeforeContract.View, OnItemClickListener {

    private MusicBeforeAdapter mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return new MusicBeforePresenter();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_music_before, container, false);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEmpty = view.findViewById(R.id.empty);
        return view;
    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        if (mItems == null) {
            mItems = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                MusicItem musicItem = new MusicItem();
                musicItem.setName("夜空中最亮的星" + i);
                Date date = new Date();
                musicItem.setDuration(date.getTime() - i * 1000);
                musicItem.setPlayTimes(i * 10000);
                mItems.add(musicItem);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MusicBeforeAdapter(getContext());
        mAdapter.addItemClickListener(this);
        mAdapter.setData(mItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (holder instanceof MusicBeforeViewHolder) {
            MusicItem musicItem;
            if (mItems!=null){
                for (int i = 0; i < mItems.size(); i++) {
                    musicItem=(MusicItem)mItems.get(i);
                    if (i==position){
                        musicItem.setPlaying(true);
                    }else {
                        musicItem.setPlaying(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }


}
