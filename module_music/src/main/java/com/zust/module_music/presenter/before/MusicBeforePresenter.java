package com.zust.module_music.presenter.before;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.bean.MusicItem;
import com.example.lib_common.base.bean.ResponseWrapper;
import com.example.lib_common.base.bean.response.MusicBean;
import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.common.Constant;
import com.example.lib_common.http.MonkeyApiService;
import com.zust.module_music.contract.before.MusicBeforeContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 11:22
 * @email : 15869107730@163.com
 * @note :
 */
public class MusicBeforePresenter<V extends MusicBeforeContract.View> extends BasePresenter<V> implements MusicBeforeContract.Presenter {

    @Override
    public void getDataFromDB(final int currentPage) {
        DBManager.getInstance(context.get()).getMusicBeforeDB().queryWhereMusic(currentPage, null, new DbOperateListener.OnQueryAllListener() {
            @Override
            public void onQueryAllBatchListener(List list) {
                if (list == null || list.size() == 0) {
                    if (currentPage == 0) {
                        view.get().showEmpty();
                    } else {
                        view.get().noMoreData();
                    }
                }
            }
        });
    }

    @Override
    public void getSleepBeforeMusicList(final int currentPage) {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .getMusicSleepBefore(Constant.TOKEN, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<MusicBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<MusicBean>> musicBeanResponseWrapper) {
                        super.onNext(musicBeanResponseWrapper);
                        if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            List<MusicItem> musicItemList = new ArrayList<>();
                            if (musicBeanResponseWrapper.getData() != null) {
                                for (MusicBean musicBean : musicBeanResponseWrapper.getData()) {
                                    MusicItem musicItem = new MusicItem(musicBean);
                                    musicItemList.add(musicItem);
                                }
                            }
                            view.get().addMoreData(musicItemList);
                            view.get().updateCurrentPage(currentPage + 1);
                        } else if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.NO_MORE_DATA) {
                            view.get().addMoreData(null);
                        }
                    }
                }));
    }

    @Override
    public void refreshSleepBeforeMusicList() {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .getMusicSleepBefore(Constant.TOKEN, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<MusicBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<MusicBean>> musicBeanResponseWrapper) {
                        super.onNext(musicBeanResponseWrapper);
                        if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            List<MusicItem> musicItemList = new ArrayList<>();
                            if (musicBeanResponseWrapper.getData() != null) {
                                for (MusicBean musicBean : musicBeanResponseWrapper.getData()) {
                                    MusicItem musicItem = new MusicItem(musicBean);
                                    musicItemList.add(musicItem);
                                }
                            }
                            view.get().refreshData(musicItemList);
                            view.get().updateCurrentPage(1);
                        } else if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.NO_MORE_DATA) {
                            view.get().refreshData(null);
                        }
                    }
                }));
    }


}
