package com.example.module_habit.presenter;

import android.media.AudioRecord;
import android.util.Log;

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
import com.example.module_habit.BuildConfig;
import com.example.module_habit.contract.SleepContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
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
    public void sendSleepData(long start_time, long end_time, int unLockTimes, List<TurnBean> turnBeans) {
        view.get().showDialog("数据报告生成中...");
        SleepData sleepData=new SleepData();
        sleepData.setStart_time(start_time);
        sleepData.setEnd_time(end_time);
        sleepData.setUnLockTimes(unLockTimes);
        sleepData.setTurns(turnBeans);
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi().sendSleepData(Constant.TOKEN,sleepData)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<SleepBean> >(view.get()){

            @Override
            public void onNext(ResponseWrapper<SleepBean> integerResponseWrapper) {
                super.onNext(integerResponseWrapper);
                view.get().dismissDialog();
                view.get().analysisSuccess(new ReportBean(integerResponseWrapper.getData(), null));
            }
        }));
    }

    @Override
    public void saveVolume(final AudioRecord audioRecord) {
        final String inName = getTempFileName();
        final String outName = getWAVFileName();
        addDisposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                try {
                    writeDateTOFile(audioRecord,inName);
                    copyWaveFile(inName,outName);
                    if (BuildConfig.DEBUG){
                        Log.e("SleepPresenter", "subscribe: "+Thread.currentThread());
                    }
                    emitter.onNext(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new ResourceObserver<Integer>(){
            @Override
            public void onNext(Integer integer) {
                if (integer!=null&&integer==0){
                    if (BuildConfig.DEBUG){
                        Log.e("SleepPresenter", "onNext: "+Thread.currentThread());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    // 这里得到可播放的音频文件
    private void copyWaveFile(String inFilename, String outFilename) {
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = Constant.SleepRecord.SAMPLE_RATE_IN_HZ;
        int channels = 2;
        long byteRate = 16 * Constant.SleepRecord.SAMPLE_RATE_IN_HZ * channels / 8;
        byte[] data = new byte[Constant.SleepRecord.BUFFER_SIZE];
        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                     long totalDataLen, long longSampleRate, int channels, long byteRate)
            throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8); // block align
        header[33] = 0;
        header[34] = 16; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }

    private String getTempFileName() {
        return context.get().getCacheDir() + "/record/temp/temp.raw";
    }

    private String getWAVFileName() {
        return context.get().getCacheDir() + "/record/" + (new Date()).getTime() + ".wav";
    }

    private void writeDateTOFile(AudioRecord audioRecord, String fileName) {
        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] audiodata = new byte[Constant.SleepRecord.BUFFER_SIZE];
        FileOutputStream fos = null;
        int readsize = 0;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (Exception e) {
            e.printStackTrace();
        }

        readsize = audioRecord.read(audiodata, 0, Constant.SleepRecord.BUFFER_SIZE);
        if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
            try {
                fos.write(audiodata);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            fos.close();// 关闭写入流
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
