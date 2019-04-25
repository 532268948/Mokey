package com.zust.module_music.presenter.select;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.MusicOnlineItem;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.MusicBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.MusicBefore;
import com.example.lib_common.http.MonkeyApiService;
import com.zust.module_music.contract.OnlineContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/21
 * @time : 14:05
 * @email : 15869107730@163.com
 * @note :
 */
public class OnlinePresenter<V extends OnlineContract.View> extends BasePresenter<OnlineContract.View> implements OnlineContract.Presenter {
    @Override
    public void getOnlineMusicList(final int currentPage) {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .getMusicSleepBefore(Constant.TOKEN, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<MusicBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<MusicBean>> musicBeanResponseWrapper) {
                        super.onNext(musicBeanResponseWrapper);
                        if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            final List<MusicOnlineItem> musicList = new ArrayList<>();
                            if (musicBeanResponseWrapper.getData() != null) {
                                for (MusicBean musicBean : musicBeanResponseWrapper.getData()) {
                                    MusicOnlineItem musicItem = new MusicOnlineItem(musicBean);
                                    musicItem.setDownloadWhenPlaying(true);
                                    musicList.add(musicItem);
                                }
                            }
                            DBManager.getInstance(context.get()).getMusicBeforeDB().queryAllMusic(new DbOperateListener.OnQueryAllListener<MusicBefore>() {
                                @Override
                                public void onQueryAllBatchListener(List<MusicBefore> list) {
                                    for (int i = 0; i < musicList.size(); i++) {
                                        for (int j = 0; j < list.size(); j++) {
                                            if (musicList.get(i).getMusicId() == list.get(j).getId()) {
                                                musicList.get(i).setLocalFile(list.get(j).getLocalFile());
                                                break;
                                            }
                                        }
                                    }
                                    view.get().addMoreData(musicList);
                                    view.get().updateCurrentPage(currentPage + 1);
                                }
                            });


                        } else if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.NO_MORE_DATA) {
                            view.get().addMoreData(null);
                        }
                    }
                }));
    }

    @Override
    public void refreshOnlineMusicList() {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .getMusicSleepBefore(Constant.TOKEN, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<MusicBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<MusicBean>> musicBeanResponseWrapper) {
                        super.onNext(musicBeanResponseWrapper);
                        if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            final List<MusicOnlineItem> musicList = new ArrayList<>();
                            if (musicBeanResponseWrapper.getData() != null) {
                                for (MusicBean musicBean : musicBeanResponseWrapper.getData()) {
                                    MusicOnlineItem musicItem = new MusicOnlineItem(musicBean);
                                    musicItem.setDownloadWhenPlaying(true);
                                    musicList.add(musicItem);
                                }
                            }
                            DBManager.getInstance(context.get()).getMusicBeforeDB().queryAllMusic(new DbOperateListener.OnQueryAllListener<MusicBefore>() {
                                @Override
                                public void onQueryAllBatchListener(List<MusicBefore> list) {
                                    for (int i = 0; i < musicList.size(); i++) {
                                        for (int j = 0; j < list.size(); j++) {
                                            if (musicList.get(i).getMusicId() == list.get(j).getId()) {
                                                musicList.get(i).setLocalFile(list.get(j).getLocalFile());
                                                break;
                                            }
                                        }
                                    }
                                    view.get().refreshData(musicList);
                                    view.get().updateCurrentPage(1);
                                }
                            });


                        } else if (musicBeanResponseWrapper.getCode() == Constant.HttpCode.NO_MORE_DATA) {
                            view.get().refreshData(null);
                        }
                    }
                }));
    }
}
