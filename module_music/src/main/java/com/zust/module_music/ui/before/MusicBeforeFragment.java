package com.zust.module_music.ui.before;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.adapter.BaseRecyclerHolder;
import com.example.lib_common.bean.BaseItem;
import com.example.lib_common.bean.HasMoreItem;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.base.inter.OnItemClickListener;
import com.example.lib_common.base.inter.OnLoadMoreListener;
import com.example.lib_common.common.Constant;
import com.example.lib_common.music.CacheableMediaPlayer;
import com.example.lib_common.music.MusicHelper;
import com.example.lib_common.music.MusicState;
import com.example.lib_common.music.OnMusicPlayStateListener;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.R;
import com.zust.module_music.contract.before.MusicBeforeContract;
import com.zust.module_music.presenter.before.MusicBeforePresenter;
import com.zust.module_music.ui.MusicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 11:13
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBeforeFragment extends BaseListFragment<MusicBeforeContract.View, MusicBeforePresenter<MusicBeforeContract.View>> implements
        MusicBeforeContract.View,
        OnItemClickListener,
        OnLoadMoreListener,
        OnMusicPlayStateListener,
        CacheableMediaPlayer.OnCachedProgressUpdateListener {


    private int currentPage = 0;
    private MusicBeforeAdapter mAdapter;
    /**
     * 数据是否有更新
     */
    private boolean update = true;


    @Override
    protected MusicBeforePresenter<MusicBeforeContract.View> createPresenter() {
        return new MusicBeforePresenter<>();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
                refreshData();
            }
        });
        MusicHelper.getInstance().setMusicCacheProgressListener(this);
        MusicHelper.getInstance().setMusicCacheSuccessListener(new CacheableMediaPlayer.MusicControlInterface() {
            @Override
            public void updateBufferFinishMusicPath(String localPath, long id) {
                if (mItems!=null){
                    for (int i = 0; i < mItems.size(); i++) {
                        if (mItems.get(i).getItemType() == Constant.ItemType.MUSIC_BEFORE) {
                            MusicItem musicItem = (MusicItem) mItems.get(i);
                            musicItem.setLocalFile(localPath);
                            if (musicItem.getMusicId() == id) {
                                mPresenter.updateMusicDb(localPath, musicItem);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MusicBeforeAdapter(getContext());
        mAdapter.addItemClickListener(this);
        mAdapter.addLoadMoreListener(this);
        mAdapter.setData(mItems);
        mRecyclerView.setAdapter(mAdapter);
//        mPresenter.getDataFromDB(0);
        mPresenter.getSleepBeforeMusicList(currentPage);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(BaseRecyclerHolder holder, int position) {
        if (holder instanceof MusicBeforeViewHolder) {
            MusicItem musicItem;
            if (mItems != null && position < mAdapter.getItemCount() - 1) {
                List<MusicItem> musicItemList = new ArrayList<>();
                for (int i = 0; i < mItems.size() - 1; i++) {
                    musicItem = (MusicItem) mItems.get(i);
                    musicItemList.add(musicItem);
//                    if (i == position) {
//                        musicItem.setPlaying(true);
//                    } else {
//                        musicItem.setPlaying(false);
//                    }
                }
                if (update) {
                    MusicHelper.getInstance().initMusicItem(musicItemList, musicItemList.get(position).getMusicId(), true, this);
//                    MusicHelper.getInstance().
                    update = false;
                } else {
                    MusicHelper.getInstance().play(musicItemList.get(position).getMusicId());
                }
//                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void setDBData(List<MusicItem> musicItemList) {
        if (mItems != null && mItems.size() == 0) {
            mItems.addAll(musicItemList);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateMusic(List<MusicItem> musicItemList, int position) {

    }

    @Override
    public void addMoreData(List<MusicItem> musicItemList) {
        if (mItems == null) {
            mItems = new ArrayList<>();
            if (mAdapter != null) {
                mAdapter.setData(mItems);
            }
        }
        if (currentPage == 0) {
            mItems.clear();
        }
        if (mItems.size() == 0) {
            if (musicItemList != null && musicItemList.size() != 0) {
                showNormal();
                mItems.addAll(musicItemList);
                mItems.add(new HasMoreItem(true, Constant.ItemType.LOAD_MORE_DATA));
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                showEmpty();
            }
        } else {
            if (musicItemList != null && musicItemList.size() != 0) {
                if (mItems.get(mItems.size() - 1).getItemType() == Constant.ItemType.LOAD_MORE_DATA) {
                    mItems.addAll(mItems.size() - 2, musicItemList);
                }
                if (mAdapter != null) {
                    mAdapter.notifyItemRangeChanged(mItems.size() - musicItemList.size() - 1, musicItemList.size() + 1);
                }
            } else {
                if (mItems.get(mItems.size() - 1).getItemType() == Constant.ItemType.LOAD_MORE_DATA) {
                    ((HasMoreItem) mItems.get(mItems.size() - 1)).setHasMore(false);
                }
                mAdapter.notifyItemChanged(mItems.size() - 1);
            }
        }
    }

    @Override
    public void refreshData(List<MusicItem> musicItemList) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
        if (mItems == null) {
            mItems = new ArrayList<>();
            if (mAdapter != null) {
                mAdapter.setData(mItems);
            }
        }
        mItems.clear();
        if (musicItemList != null && musicItemList.size() != 0) {
            showNormal();
            mItems.addAll(musicItemList);
            mItems.add(new HasMoreItem(true, Constant.ItemType.LOAD_MORE_DATA));
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        } else {
            showEmpty();
        }
    }

    @Override
    public void updateCurrentPage(int page) {
        currentPage = page;
    }

    @Override
    public void noMoreData() {
        if (mItems != null) {
            if (mItems.get(mItems.size() - 1).getItemType() == Constant.ItemType.LOAD_MORE_DATA) {
                HasMoreItem item = (HasMoreItem) mItems.get(mItems.size() - 1);
                if (item.getHasMore()) {
                    item.setHasMore(false);
                    if (mAdapter != null) {
                        mAdapter.notifyItemChanged(mItems.size() - 1);
                    }
                }
            }

        }
    }

    @Override
    public void showNormal() {
        super.showNormal();
        ViewUtil.setViewVisible(mRecyclerView);
        ViewUtil.setEmptyViewVisible(mEmpty, getContext(), false, false);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        ViewUtil.setEmptyViewVisible(mEmpty, getContext(), true, false);
        ViewUtil.setViewGone(mRecyclerView);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getSleepBeforeMusicList(currentPage);
    }

    private void refreshData() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
        currentPage = 0;
        mPresenter.refreshSleepBeforeMusicList();
    }

    @Override
    public void onPlay(MusicItem item) {
        MusicFragment parentFragment = (MusicFragment) getParentFragment();
        if (parentFragment != null) {
            Log.e("MusicBeforeFragment", "onPlay: ");
            parentFragment.showBigMusicView(MusicState.Playing);
            for (BaseItem baseItem : mItems) {
                if (baseItem.getItemType() == Constant.ItemType.MUSIC_BEFORE) {
                    if (((MusicItem) baseItem).getMusicId() == item.getMusicId()) {
                        ((MusicItem) baseItem).setPlaying(true);
                    } else {
                        ((MusicItem) baseItem).setPlaying(false);
                    }
                }
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onStopped(MusicItem item) {
        MusicFragment parentFragment = (MusicFragment) getParentFragment();
        if (parentFragment != null) {
            parentFragment.showBigMusicView(MusicState.Stopped);
            if (mItems != null) {
                for (BaseItem baseItem : mItems) {
                    if (baseItem.getItemType() == Constant.ItemType.MUSIC_BEFORE) {
                        if (((MusicItem) baseItem).getMusicId() == item.getMusicId()) {
                            ((MusicItem) baseItem).setPlaying(false);
                        }
                    }
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onPaused(MusicItem item) {
        MusicFragment parentFragment = (MusicFragment) getParentFragment();
        if (parentFragment != null) {
            parentFragment.showBigMusicView(MusicState.Paused);
            if (mItems != null) {
                for (BaseItem baseItem : mItems) {
                    if (baseItem.getItemType() == Constant.ItemType.MUSIC_BEFORE) {
                        if (((MusicItem) baseItem).getMusicId() == item.getMusicId()) {
                            ((MusicItem) baseItem).setPlaying(false);
                        }
                    }
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onPrepare(MusicItem item) {
        MusicFragment parentFragment = (MusicFragment) getParentFragment();
        if (parentFragment != null) {
            parentFragment.showBigMusicView(MusicState.Preparing);
        }
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
    public void updateCachedProgress(int progress) {
        Log.e("MusicBeforeFragment", "updateCachedProgress: " + progress);
    }
}
