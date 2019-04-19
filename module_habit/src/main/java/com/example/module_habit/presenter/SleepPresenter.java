package com.example.module_habit.presenter;

import android.media.AudioFormat;
import android.util.Log;

import com.example.lib_common.audio.PcmToWavUtil;
import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.request.SleepData;
import com.example.lib_common.bean.request.TurnBean;
import com.example.lib_common.bean.response.SleepBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.http.MonkeyApiService;
import com.example.lib_common.util.CacheUtil;
import com.example.lib_common.util.DateUtil;
import com.example.module_habit.contract.SleepContract;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/17 15:48
 * @description:
 */
public class SleepPresenter<V extends SleepContract.View> extends BasePresenter<V> implements SleepContract.Presenter {
    @Override
    public void getAlarmFromDB() {
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.Alarm.ALARM_ID_FOUR, new DbOperateListener.OnQuerySingleListener() {
            @Override
            public void onQuerySingleListener(Object entry) {
                if (entry != null) {
                    view.get().setAlarm((Alarm) entry);
                }
            }
        });
//        DBManager.getInstance(context.get()).getAlarmDB().queryAllAlarm(new DbOperateListener.OnQueryAllListener() {
//            @Override
//            public void onQueryAllBatchListener(List list) {
//
//            }
//        });
    }

    @Override
    public void sendSleepData(final long start_time, final long end_time, int unLockTimes, List<TurnBean> turnBeans) {
        view.get().showDialog("数据报告生成中...");
        SleepData sleepData = new SleepData();
        sleepData.setStart_time(start_time);
        sleepData.setEnd_time(end_time);
        sleepData.setUnLockTimes(unLockTimes);
        sleepData.setTurns(turnBeans);
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi().sendSleepData(Constant.TOKEN, sleepData)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseWrapper<SleepBean>>() {
                    @Override
                    public void accept(ResponseWrapper<SleepBean> sleepBeanResponseWrapper) throws Exception {
                        pcmToWav(start_time, end_time);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<SleepBean>>(view.get()) {

                    @Override
                    public void onNext(ResponseWrapper<SleepBean> integerResponseWrapper) {
                        super.onNext(integerResponseWrapper);
                        view.get().dismissDialog();
                        view.get().analysisSuccess(new ReportBean(integerResponseWrapper.getData(), null));
                    }
                }));
    }

    @Override
    public void clearVolume(long start_time, long stop_time) {
        String pcmPath = CacheUtil.getRecordPcmDatePath(DateUtil.formatOne(start_time));
        File file = new File(pcmPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                long record_time = Long.valueOf(CacheUtil.getFileName(file1.getPath()));
                if (record_time >= start_time && record_time <= stop_time) {
                    file1.delete();
                }
            }
        }

        if (DateUtil.formatOne(start_time).equals(DateUtil.formatOne(stop_time))){
            return;
        }
        pcmPath = CacheUtil.getRecordPcmDatePath(DateUtil.formatOne(stop_time));
        file = new File(pcmPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                long record_time = Long.getLong(CacheUtil.getFileName(file1.getPath()));
                if (record_time >= start_time && record_time <= stop_time) {
                    file1.delete();
                }
            }
        }
    }

    @Override
    public void pcmToWav(long start_time, long stop_time) {
        String pcmPath = CacheUtil.getRecordPcmDatePath(DateUtil.formatOne(start_time));
        File file = new File(pcmPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                Log.e("SleepPresenter", "clearVolume: " + file1.getPath());
                long record_time = Long.valueOf(CacheUtil.getFileName(file1.getPath()));
                if (record_time >= start_time && record_time <= stop_time) {
                    PcmToWavUtil.pcmToWav(file1.getPath(), CacheUtil.getRecordSaveWAVFilePath(context.get(), file1.getPath()), Constant.SleepRecord.SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
                    file1.delete();
                }
            }
        }
        if (DateUtil.formatOne(start_time).equals(DateUtil.formatOne(stop_time))){
            return;
        }
        pcmPath = CacheUtil.getRecordPcmDatePath(DateUtil.formatOne(stop_time));
        file = new File(pcmPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                long record_time = Long.getLong(CacheUtil.getFileName(file1.getPath()));
                if (record_time >= start_time && record_time <= stop_time) {
                    PcmToWavUtil.pcmToWav(file1.getPath(), CacheUtil.getRecordSaveWAVFilePath(context.get(), file1.getPath()), Constant.SleepRecord.SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
                    file1.delete();
                }
            }
        }
    }

}
