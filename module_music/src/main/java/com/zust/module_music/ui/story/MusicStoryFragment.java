package com.zust.module_music.ui.story;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.zust.module_music.R;
import com.zust.module_music.contract.story.MusicStoryContract;
import com.zust.module_music.presenter.story.MusicStoryPresenter;
import com.zust.module_music.ui.play.StoryPlayActivity;

import java.util.ArrayList;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/29
 * @time : 13:21
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicStoryFragment extends BaseListFragment<MusicStoryContract.View, MusicStoryPresenter<MusicStoryContract.View>> implements MusicStoryContract.View, OnItemClickListener {

    private MusicStoryAdapter mAdapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected MusicStoryPresenter createPresenter() {
        return new MusicStoryPresenter();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_music_story, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEmpty = view.findViewById(R.id.empty);
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
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
                mItems.add(musicItem);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MusicStoryAdapter(getContext());
        mAdapter.addItemClickListener(this);
        mAdapter.setData(mItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        startActivity(new Intent(getContext(), StoryPlayActivity.class));
    }
}
