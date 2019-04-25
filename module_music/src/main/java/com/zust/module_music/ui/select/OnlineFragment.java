package com.zust.module_music.ui.select;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.inter.OnLoadMoreListener;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.bean.MusicOnlineItem;
import com.example.lib_common.common.Constant;
import com.example.lib_common.music.CacheableMediaPlayer;
import com.example.lib_common.music.MusicHelper;
import com.example.lib_common.music.OnMusicPlayStateListener;
import com.example.lib_common.util.FileUtil;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.BuildConfig;
import com.zust.module_music.R;
import com.zust.module_music.contract.OnlineContract;
import com.zust.module_music.presenter.select.OnlinePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/21
 * @time : 14:04
 * @email : 15869107730@163.com
 * @note :
 */
public class OnlineFragment extends BaseListFragment<OnlineContract.View, OnlinePresenter<OnlineContract.View>> implements
        OnlineContract.View,
        OnLoadMoreListener,
        OnItemClickListener,
        OnMusicPlayStateListener,
        CacheableMediaPlayer.OnCachedProgressUpdateListener,
        OnlineAdapter.OnPlayBtnClickListener {

    private int currentPage = 0;
    private OnlineAdapter mAdapter;
    private DownloadTipDialog mDownloadTipDialog;

    @Override
    protected OnlinePresenter<OnlineContract.View> createPresenter() {
        return new OnlinePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_online, container, false);
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
                refreshData();
            }
        });
        MusicHelper.getInstance().setMusicCacheProgressListener(OnlineFragment.this);
    }

    private void refreshData() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
        currentPage = 0;
        mPresenter.refreshOnlineMusicList();
    }

    @Override
    public void initData() {
        if (mAdapter == null) {
            mAdapter = new OnlineAdapter(getContext());
        }
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mAdapter.setData(mItems);
        mAdapter.addLoadMoreListener(this);
        mAdapter.addItemClickListener(this);
        mAdapter.addPlayBtnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getOnlineMusicList(currentPage);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void addMoreData(List<MusicOnlineItem> musicItemList) {
        if (mAdapter != null) {
            mAdapter.insertAll(musicItemList);
            if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
                showEmpty();
            } else {
                showNormal();
            }
        }
    }

    @Override
    public void updateCurrentPage(int page) {
        currentPage = page;
    }

    @Override
    public void refreshData(List<MusicOnlineItem> musicItemList) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
        if (musicItemList == null||musicItemList.size()==0) {
            mAdapter.setEmpty();
            showEmpty();
        } else {
            showNormal();
            mAdapter.refreshData(musicItemList);
        }
    }

    @Override
    public void showEmpty() {
        ViewUtil.setEmptyViewVisible(mEmpty, getContext(), true, false);
        ViewUtil.setViewGone(mRecyclerView);
    }

    @Override
    public void showNormal() {
        super.showNormal();
        ViewUtil.setViewGone(mEmpty);
        ViewUtil.setViewVisible(mRecyclerView);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getOnlineMusicList(currentPage);
    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (holder instanceof MusicOnlineViewHolder) {
            if (mAdapter != null) {
                if (mAdapter.getData() != null) {
                    if (position >= 0 && position < mAdapter.getData().size()) {
                        BaseItem baseItem = mAdapter.getData().get(position);
                        if (baseItem != null) {
                            if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                                MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                                if (BuildConfig.DEBUG) {
                                    Log.e("OnlineFragment", "onItemClick: " + musicOnlineItem.getLocalFile());
                                }
                                if (!FileUtil.checkFileExist(musicOnlineItem.getLocalFile())) {
                                    MusicItem musicItem = new MusicItem(musicOnlineItem);
                                    showDownloadTipDialog(musicItem);
                                } else {
                                    Intent intent = new Intent();
                                    intent.putExtra("name", musicOnlineItem.getName());
                                    intent.putExtra("ring", musicOnlineItem.getLocalFile());
                                    ((MusicSelectActivity) getActivity()).setResult(Activity.RESULT_OK, intent);
                                    ((MusicSelectActivity) getActivity()).finish();
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public void onPlay(MusicItem item) {
        if (mAdapter != null) {
            if (mAdapter.getData() != null) {
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    BaseItem baseItem = mAdapter.getData().get(i);
                    if (baseItem != null) {
                        if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                            MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                            if (musicOnlineItem.getMusicId() == item.getMusicId()) {
                                musicOnlineItem.setPlaying(true);
                                mAdapter.notifyItemPlay(i);
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onStopped(MusicItem item) {
        if (mAdapter != null) {
            if (mAdapter.getData() != null) {
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    BaseItem baseItem = mAdapter.getData().get(i);
                    if (baseItem != null) {
                        if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                            MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                            if (musicOnlineItem.getMusicId() == item.getMusicId()) {
                                musicOnlineItem.setPlaying(false);
                                mAdapter.notifyItemChanged(i);
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onPaused(MusicItem item) {
        if (mAdapter != null) {
            if (mAdapter.getData() != null) {
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    BaseItem baseItem = mAdapter.getData().get(i);
                    if (baseItem != null) {
                        if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                            MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                            if (musicOnlineItem.getMusicId() == item.getMusicId()) {
                                musicOnlineItem.setPlaying(false);
                                mAdapter.notifyItemChanged(i);
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onPrepare(MusicItem item) {

    }

    @Override
    public void onPosition(MusicItem item, int pos) {

    }

    @Override
    public void onComplete(MusicItem item) {

    }

    @Override
    public void onRemain(int count, long time) {

    }

    @Override
    public void onSeekToLast(int time) {

    }

    @Override
    public void updateCachedProgress(final int progress) {
//        Log.e("OnlineFragment", "updateCachedProgress1: "+progress);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAdapter != null) {
                    if (mAdapter.getData() != null) {
                        for (int i = 0; i < mAdapter.getData().size(); i++) {
                            BaseItem baseItem = mAdapter.getData().get(i);
                            if (baseItem != null) {
                                if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                                    MusicOnlineItem musicOnlineItem = (MusicOnlineItem) baseItem;
                                    if (musicOnlineItem.getMusicId() == MusicHelper.getInstance().getMusicPlayer().getCurMusicItem().getMusicId()) {
                                        musicOnlineItem.setProgress(progress);
//                                        Log.e("OnlineFragment", "updateCachedProgress2: "+progress);
                                        mAdapter.notifyItemChanged(i);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });

    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        MusicHelper.getInstance().unBingPlayer(this);
    }

    @Override
    public void onPlayBtnClick(int position) {
        if (mAdapter != null) {
            List<BaseItem> baseItems = mAdapter.getData();
            if (baseItems != null && position >= 0 && position < baseItems.size() && baseItems.get(position) != null) {
                BaseItem baseItem = baseItems.get(position);
                if (baseItem.getItemType() == Constant.ItemType.MUSIC_ONLINE) {
                    MusicItem musicItem = new MusicItem((MusicOnlineItem) baseItem);
                    MusicHelper.getInstance().initMusicItem(musicItem, musicItem.getMusicId(), true, this);
                }
            }
        }
    }

    private void showDownloadTipDialog(final MusicItem item) {
        if (mDownloadTipDialog == null) {
            mDownloadTipDialog = new DownloadTipDialog();
        }
        mDownloadTipDialog.addSureBtnClickListener(new DownloadTipDialog.OnSureBtnClickListener() {
            @Override
            public void onSureClick() {
                mDownloadTipDialog.dismiss();
                MusicHelper.getInstance().initMusicItem(item, item.getMusicId(), true, OnlineFragment.this);
            }
        });
        mDownloadTipDialog.show(getFragmentManager(), "music_download");
    }
}
