package com.zust.module_music.presenter.before;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.bean.MusicItem;
import com.example.lib_common.base.bean.ResponseWrapper;
import com.example.lib_common.base.bean.response.MusicBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.MusicBefore;
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
//        view.get().showLoading();
        DBManager.getInstance(context.get()).getMusicBeforeDB().queryWhereMusic(currentPage, null, new DbOperateListener.OnQueryAllListener() {
            @Override
            public void onQueryAllBatchListener(List list) {
                if (list == null || list.size() == 0) {
//                    if (currentPage == 0) {
//                        view.get().showEmpty();
//                    } else {
//                        view.get().noMoreData();
//                    }
                } else {
                    List<MusicItem> musicItemList = new ArrayList<>();
                    for (MusicBefore musicBefore : (List<MusicBefore>) list) {
                        MusicItem musicItem = new MusicItem(musicBefore);
                        musicItemList.add(musicItem);
                    }
                    view.get().setDBData(musicItemList);
                }
//                view.get().dismissDialog();
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
                            final List<MusicItem> musicItemList = new ArrayList<>();
                            if (musicBeanResponseWrapper.getData() != null) {
                                for (MusicBean musicBean : musicBeanResponseWrapper.getData()) {
                                    MusicItem musicItem = new MusicItem(musicBean);
                                    musicItem.setDownloadWhenPlaying(true);
                                    musicItemList.add(musicItem);
                                }
                            }
                            DBManager.getInstance(context.get()).getMusicBeforeDB().queryAllMusic(new DbOperateListener.OnQueryAllListener<MusicBefore>() {
                                @Override
                                public void onQueryAllBatchListener(List<MusicBefore> list) {
                                    for (int i = 0; i < musicItemList.size(); i++) {
                                        for (int j = 0; j < list.size(); j++) {
                                            if (musicItemList.get(i).getMusicId() == list.get(j).getId()) {
                                                musicItemList.get(i).setLocalFile(list.get(j).getLocalFile());
                                                break;
                                            }
                                        }
                                    }
                                    view.get().addMoreData(musicItemList);
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

    @Override
    public void updateMusicDb(final String localFile, final MusicItem musicItem) {
        if (musicItem == null) {
            return;
        }
        DBManager.getInstance(context.get()).getMusicBeforeDB().queryMusicById(musicItem.getMusicId(), new DbOperateListener.OnQuerySingleListener<MusicBefore>() {
            @Override
            public void onQuerySingleListener(MusicBefore entry) {
                if (entry == null) {
                    musicItem.setLocalFile(localFile);
                    DBManager.getInstance(context.get()).getMusicBeforeDB().insertSingle(new MusicBefore(musicItem));
                } else {
                    musicItem.setLocalFile(localFile);
                    DBManager.getInstance(context.get()).getMusicBeforeDB().updateSingleMusic(new MusicBefore(musicItem));
                }
            }
        });
    }


}
