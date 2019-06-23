package com.example.lib_common.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.lib_common.BuildConfig;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.CacheUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/15
 * @time : 17:00
 * @email : 15869107730@163.com
 * @note :
 */
public class AudioRecordUtil {

    private final String TAG = "AudioRecordUtil";

    private volatile static AudioRecordUtil mInstance;
    //    private AudioRecord mAudioRecord;
    private CheckMicophoneVolume mCheckMicophoneVolume;
    private OnRecordListener onRecordListener;
    private Context mContext;

    private AudioRecordUtil(Context context) {
        mContext = context;
        onRecordListener = new OnRecordListener() {
            @Override
            public void startRecord(AudioRecord audioRecord) {

            }

            @Override
            public void resumeRecord(AudioRecord audioRecord) {

            }

            @Override
            public void pauseRecord(AudioRecord audioRecord) {

            }

            @Override
            public void stopRecord(AudioRecord audioRecord) {

            }

            @Override
            public void startSaveRecord(AudioRecord audioRecord) {

            }

            @Override
            public void stopSaveRecord(AudioRecord audioRecord) {

            }
        };
    }

    public static AudioRecordUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AudioRecordUtil.class) {
                if (mInstance == null) {
                    mInstance = new AudioRecordUtil(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void startRecord() {
        mCheckMicophoneVolume = new CheckMicophoneVolume(onRecordListener, mContext);
        mCheckMicophoneVolume.startRecord();
    }

    /**
     * 恢复录音
     */
    public void resumeRecord() {
        mCheckMicophoneVolume = new CheckMicophoneVolume(onRecordListener, mContext);
        mCheckMicophoneVolume.resumeRecord();
    }

    /**
     * 暂停录音
     */
    public void pauseRecord() {
        if (mCheckMicophoneVolume != null) {
            mCheckMicophoneVolume.exit();
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        if (mCheckMicophoneVolume != null) {
            mCheckMicophoneVolume.exit();
        }
    }

    /**
     * 存储录音
     */
    public void startSaveRecord() {

    }

    /**
     * 结束录音
     */
    public void stopSaveRecord() {

    }


    /**
     * 获取声音分贝线程
     */
    private static class CheckMicophoneVolume extends Thread {

        private static final String TAG = "AudioRecord";

        /**
         * 用于标记下次添加的录音的位置
         */
        private int pos = 0;

        private AudioRecord mAudioRecord;
        private AudioState mState = AudioState.IDLE;
        /**
         * 是否正在缓存声音
         */
        private boolean isRecord = false;
        private OnRecordListener onRecordListener;
        private Context context;

        public CheckMicophoneVolume(OnRecordListener onRecordListener, Context context) {
            this.onRecordListener = onRecordListener;
            this.context = context;
        }

        /**
         * 开始录音
         */
        public void startRecord() {
            if (mState == AudioState.Recording) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "还在录着呢");
                }
                return;
            }
            if (mAudioRecord == null) {
                mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                        Constant.SleepRecord.SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, Constant.SleepRecord.BUFFER_SIZE);
            }
            mState = AudioState.Recording;
            isRecord = false;
            start();
            if (onRecordListener != null) {
                onRecordListener.startRecord(mAudioRecord);
            }
        }

        /**
         * 恢复录音
         */
        public void resumeRecord() {
            startRecord();
            if (onRecordListener != null) {
                onRecordListener.resumeRecord(mAudioRecord);
            }
        }

        /**
         * 暂停录音
         */
        public void pauseRecord() {
            mState = AudioState.Pause;
            if (onRecordListener != null) {
                onRecordListener.pauseRecord(mAudioRecord);
            }
        }

        /**
         * 存储录音
         */
        public void startSaveRecord() {

        }

        /**
         * 结束录音
         */
        public void stopSaveRecord() {

        }


        public void exit() {
            mState = AudioState.Stop;
            if (onRecordListener != null) {
                onRecordListener.stopRecord(mAudioRecord);
            }
        }

        public boolean isRunning() {
            return mState == AudioState.Recording ? true : false;
        }

        @Override
        public void run() {

            if (mAudioRecord != null) {

                long highTime = 0L;
                FileOutputStream os = null;
                File file = null;
                byte[] buffer = new byte[Constant.SleepRecord.BUFFER_SIZE];
                mAudioRecord.startRecording();
                //正在检查分贝
                while (mState == AudioState.Recording) {
                    int readSize = mAudioRecord.read(buffer, 0, Constant.SleepRecord.BUFFER_SIZE);

                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) readSize;
                    double volume = 10 * Math.log10(mean);
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "分贝值:" + volume);
                    }
                    /**
                     * 开始记录
                     */
                    if (volume > Constant.SleepRecord.VOLUME_START_RECORD) {
                        highTime = System.currentTimeMillis();
                        //是否正在录音
                        if (isRecord) {
                            if (readSize != AudioRecord.ERROR_INVALID_OPERATION) {
                                try {
                                    Log.e("Checkrecord", "run: record");
                                    os.write(buffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                file = new File(CacheUtil.getRecordSavePCMFilePath(context, new Date()));
                                if (!file.exists()) {
                                    file.getParentFile().mkdirs();
                                    file.createNewFile();
                                }
                                os = new FileOutputStream(file);
                                Log.e("Checkfinish", "run: finish");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            isRecord = true;
                        }
                    } else {
                        if (System.currentTimeMillis() - highTime >= 5000) {
                            try {
                                if (os!=null){
                                    os.close();
                                }
                                isRecord=false;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
                mState = AudioState.IDLE;
                isRecord = false;
            }

        }

    }
}
