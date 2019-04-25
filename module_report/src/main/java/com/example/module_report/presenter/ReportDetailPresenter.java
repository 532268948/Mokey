package com.example.module_report.presenter;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.DreamBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.CacheUtil;
import com.example.lib_common.util.DateUtil;
import com.example.module_report.BuildConfig;
import com.example.module_report.contract.ReportDetailContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 14:23
 * @description:
 */
public class ReportDetailPresenter<V extends ReportDetailContract.View> extends BasePresenter<V> implements ReportDetailContract.Presenter {
    @Override
    public void getDreamData(final long startTime, final long stopTime) {
        Observable.create(new ObservableOnSubscribe<List<DreamBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DreamBean>> emitter) throws Exception {
                List<DreamBean> dreamBeanList = new ArrayList<>();
                File file = new File(CacheUtil.getRecordWavDatePath(DateUtil.formatOne(startTime)));
                if (file.exists() && file.isDirectory()) {
                    List<File> fileList = Arrays.asList(file.listFiles());
                    if (fileList != null) {
                        for (File file1 : fileList) {
                            if (file1.exists()) {
                                long record_time = Long.valueOf(CacheUtil.getFileName(file1.getAbsolutePath()));
                                if (record_time >= startTime && record_time < stopTime) {
                                    DreamBean dreamBean = new DreamBean();
                                    dreamBean.setItemType(Constant.ItemType.RECORD_DREAM);
                                    dreamBean.setPath(file1.getAbsolutePath());
                                    dreamBean.setDream_time(record_time);
                                    MediaPlayer mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(file1.getAbsolutePath());
                                    mediaPlayer.prepare();
                                    int during=mediaPlayer.getDuration();
                                    mediaPlayer.release();
                                    mediaPlayer=null;
                                    dreamBeanList.add(dreamBean);
                                    dreamBean.setDuring(during);
                                }
                            }
                        }
                    }
                }
                if (DateUtil.formatOne(startTime).equals(DateUtil.formatOne(stopTime))) {
                    emitter.onNext(dreamBeanList);
                    return;
                }

                file = new File(CacheUtil.getRecordWavDatePath(DateUtil.formatOne(stopTime)));
                if (file.exists() && file.isDirectory()) {
                    List<File> fileList = Arrays.asList(file.listFiles());
                    if (fileList != null) {
                        for (File file1 : fileList) {
                            if (file1.exists()) {
                                long record_time = Long.valueOf(CacheUtil.getFileName(file1.getAbsolutePath()));
                                if (record_time >= startTime && record_time < stopTime) {
                                    DreamBean dreamBean = new DreamBean();
                                    dreamBean.setItemType(Constant.ItemType.RECORD_DREAM);
                                    dreamBean.setPath(file1.getAbsolutePath());
                                    dreamBean.setDream_time(record_time);
                                    MediaPlayer mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(file1.getAbsolutePath());
                                    mediaPlayer.prepare();
                                    int during=mediaPlayer.getDuration();
                                    mediaPlayer.release();
                                    mediaPlayer=null;
                                    dreamBeanList.add(dreamBean);
                                    dreamBean.setDuring(during);
                                }
                            }
                        }
                    }
                }
                emitter.onNext(dreamBeanList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DreamBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DreamBean> dreamBeans) {
                        if (BuildConfig.DEBUG) {
                            Log.e("ReportDetailPresenter", "onNext: ");
                        }
                        view.get().getDreamDataSuccess(dreamBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) {
                            Log.e("ReportDetailPresenter", "onError: ");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 根据本地文件地址获取wav音频时长
     */
    public long getWavLength(File file) {
        byte[] wavdata = getBytes(file);
        if (wavdata != null && wavdata.length > 44) {
            int byteRate = byteArrayToInt(wavdata, 28, 31);
            int waveSize = byteArrayToInt(wavdata, 40, 43);
            return waveSize * 1000 / byteRate;
        }
        return 0;
    }

    /**
     * file 2 byte数组
     */
    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 将byte[]转化为int
     */
    private int byteArrayToInt(byte[] b, int start, int end) {
        return ByteBuffer.wrap(b, start, end).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}
