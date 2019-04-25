package com.zust.module_music.ui.select;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.fragment.BaseListFragment;
import com.example.lib_common.bean.RingtoneBean;
import com.example.lib_common.util.UriUtil;
import com.example.lib_common.util.ViewUtil;
import com.zust.module_music.R;
import com.zust.module_music.contract.SystemMusicContract;
import com.zust.module_music.presenter.SystemMusicPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/24
 * @time : 10:44
 * @email : 15869107730@163.com
 * @note :
 */
public class SystemMusicFragment extends BaseListFragment<SystemMusicContract.View, SystemMusicPresenter<SystemMusicContract.View>> implements SystemMusicContract.View, SystemMusicAdapter.OnItemClickListener {

    private SystemMusicAdapter mAdapter;

    @Override
    protected SystemMusicPresenter<SystemMusicContract.View> createPresenter() {
        return new SystemMusicPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_music_system, container, false);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEmpty = view.findViewById(R.id.empty);
        return view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        showDialog(null);
        if (mAdapter == null) {
            mAdapter = new SystemMusicAdapter(getContext());
        }
        mAdapter.addOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        Observable.create(new ObservableOnSubscribe<List<RingtoneBean>>() {

            @Override
            public void subscribe(ObservableEmitter<List<RingtoneBean>> emitter) throws Exception {
                try {
                    emitter.onNext(getRingtoneList(RingtoneManager.TYPE_RINGTONE));
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribe(new Observer<List<RingtoneBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<RingtoneBean> ringtoneBeans) {
                if (ringtoneBeans == null || ringtoneBeans.size() == 0) {
                    Log.e("SystemMusicFragment", "onNext: empty");
                    showEmpty();
                } else {
                    mAdapter.setData(ringtoneBeans);
                    Log.e("SystemMusicFragment", "onNext: normal");
                    showNormal();
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("SystemMusicFragment", "onError: ");
                showEmpty();
                dismissDialog();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {
        if (mAdapter != null && mAdapter.getData() != null) {
            if (position >= 0 && position < mAdapter.getData().size()) {
                Intent intent = new Intent();
                intent.putExtra("name", mAdapter.getData().get(position).getRingtone().getTitle(getContext()));
                intent.putExtra("ring", mAdapter.getData().get(position).getPath());
                ((MusicSelectActivity) getActivity()).setResult(Activity.RESULT_OK, intent);
                ((MusicSelectActivity) getActivity()).finish();
            }
        }

//        if ()
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.stopPlay();
        }
    }

    @Override
    public void showNormal() {
        super.showNormal();
        ViewUtil.setViewGone(mEmpty);
        ViewUtil.setViewVisible(mRecyclerView);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        ViewUtil.setViewGone(mRecyclerView);
        ViewUtil.setViewVisible(mEmpty);
    }

    public List<RingtoneBean> getRingtoneList(int type) {

        List<RingtoneBean> resArr = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(mContext);
        manager.setType(type);
        Cursor cursor = manager.getCursor();
        int count = cursor.getCount();
        cursor.moveToFirst();
        for (int i = 0; i < count; i++) {
            Ringtone ringtone = manager.getRingtone(i);
            Uri uri = ContentUris.withAppendedId(Uri.parse(cursor.getString(RingtoneManager.URI_COLUMN_INDEX)), cursor
                    .getLong(RingtoneManager.ID_COLUMN_INDEX));
            resArr.add(new RingtoneBean(ringtone, UriUtil.getRealPathFromUri(getContext(), uri)));
            cursor.moveToNext();
        }
        return resArr;

    }
}
