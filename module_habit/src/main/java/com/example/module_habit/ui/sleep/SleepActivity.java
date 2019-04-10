package com.example.module_habit.ui.sleep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.DateUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_habit.BuildConfig;
import com.example.module_habit.R;
import com.example.lib_common.base.bean.request.TurnBean;
import com.example.module_habit.broadcast.UnLockReceiver;
import com.example.module_habit.contract.SleepContract;
import com.example.module_habit.listener.OnRecordListener;
import com.example.module_habit.presenter.SleepPresenter;
import com.example.module_habit.ui.alarm.AlarmActivity;
import com.example.module_habit.view.ProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhuaye
 */
public class SleepActivity extends BaseActivity<SleepContract.View, SleepPresenter<SleepContract.View>> implements SleepContract.View, SensorEventListener, OnRecordListener {


    private final String TAG = getClass().getSimpleName();

    private final int BOTTOM_SHRESHOLD_VALUE = -7;
    private final int TOP_SHRESHOLD_VALUE = 0;
    /**
     * 睡眠开始时间
     */
    private long startSleepTime = 0L;
    /**
     * 睡眠结束时间
     */
    private long stopSleepTime = 0L;
    /**
     * 长按结束进度值
     */
    private float percent = 0f;
    private boolean isVibrate = false;
    private boolean isFirst = true;
//    /**
//     * 长按结束按钮是否可点击
//     */
//    private boolean pressEnd = true;

    /**
     * 睡眠情况是否已记录
     */
    private boolean sleepRecord = false;

    private LinearLayout mScreenTopLl;
    private LinearLayout mScreenBottomLl;
    private TextView mAlarmSettingTv;
    private TextView mPressEndTv;
    private ProgressView mProgressView;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private Vibrator vibrator;
    private Handler handler = new Handler();
    private TimeCount mTimer;
    private TimeEnd mEndTimer;

    /**
     * 0 下 1过渡 2上
     */
    private int status = 2;
    /**
     * 睡觉翻身数据
     */
    private List<TurnBean> turnBeanList;

    private CheckMicophoneVolume volumeThread;

    @Override
    protected SleepPresenter<SleepContract.View> createPresenter() {
        return new SleepPresenter<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestAndResultCode.SLEEP_REQUEST) {
            switch (resultCode) {
                case Constant.RequestAndResultCode.ALARM_RESULT_OK:
                    if (mAlarmSettingTv != null && data != null) {
                        mAlarmSettingTv.setText(DateUtil.timeToStr(data.getIntExtra("hour", 0), data.getIntExtra("minute", 0)));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_alarm_setting) {
            startActivityForResult(new Intent(this, AlarmActivity.class), Constant.RequestAndResultCode.SLEEP_REQUEST);
        }
    }

    @Override
    public void initUIParams() {
        super.initUIParams();
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_sleep;
    }

    @Override
    public void initView() {
        mScreenTopLl = findViewById(R.id.ll_screen_top);
        mScreenBottomLl = findViewById(R.id.ll_screen_bottom);
        mAlarmSettingTv = findViewById(R.id.tv_alarm_setting);
        mPressEndTv = findViewById(R.id.tv_press_end);
        mProgressView = findViewById(R.id.process_view);

        ViewUtil.setViewVisible(mScreenTopLl);
        ViewUtil.setViewGone(mScreenBottomLl);
        checkRecordPermission();
    }

    private void checkRecordPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }
    }

    @Override
    public void initListener() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mAlarmSettingTv.setOnClickListener(this);
        mPressEndTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                if (!pressEnd) {
//                    return false;
//                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!sleepRecord) {
                            if (mTimer == null) {
                                mTimer = new TimeCount(4000, 40);
                            }
//                        pressEnd = false;
                            mTimer.start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!sleepRecord) {
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                            mEndTimer = new TimeEnd(((long) percent) * 20, 40);
//                        pressEnd = false;
                            mEndTimer.start();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (!sleepRecord) {
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                            mEndTimer = new TimeEnd(((long) percent) * 20, 40);
//                        pressEnd = false;
                            mEndTimer.start();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getAlarmFromDB();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //不是震动状态
        if (!isVibrate) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //  使用加速度传感器可以实现了检测手机的摇一摇功能，通过摇一摇，弹出是否退出应用的对话框，选择是则退出应用
                if (status == 0) {
                    double value = 0.6f;
                    double max = 3;
                    if (BuildConfig.DEBUG) {
                        Log.e("SleepActivity", "onSensorChanged: " + event.values[0]);
                    }
                    if (Math.abs(event.values[0]) > value || Math.abs(event.values[1]) > value) {
                        if (Math.abs(event.values[0]) < max || Math.abs(event.values[1]) < max) {
                            if (turnBeanList != null) {
                                if (BuildConfig.DEBUG) {
                                    Log.e("SleepActivity", "onSensorChanged: turn");
                                }
                                turnBeanList.add(new TurnBean(System.currentTimeMillis(), 1));
                            }
                        }
                    }
                }
                int z = (int) event.values[2];

                //屏幕在上下翻转过程中
                if (z > BOTTOM_SHRESHOLD_VALUE && z < TOP_SHRESHOLD_VALUE) {
                    //设置当前状态为翻转状态
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "onSensorChanged: 1" + z);
                    }
                    status = 1;
                } else //屏幕朝下
                    if (z <= BOTTOM_SHRESHOLD_VALUE) {
                        //当前状态为翻转状态
                        if (status == 1) {
                            status = 0;
                            //设置为震动状态
                            isVibrate = true;
                            vibrator.vibrate(2000);
                            //2秒后设置为非震动状态
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    isVibrate = false;
                                    startSleep();
//                                    startSleepRecord();
                                }
                            }, 2000);

                        }
                    } else //屏幕朝上
                        if (z >= TOP_SHRESHOLD_VALUE) {
                            //当前状态为翻转状态
                            if (!isFirst) {
                                if (status == 1) {
                                    status = 2;
                                    //设置为震动状态
                                    isVibrate = true;
                                    vibrator.vibrate(1000);
                                    //1秒后设置为非震动状态
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            isVibrate = false;
                                            stopSleep();
//                                            endSleepRecord();
                                        }
                                    }, 1000);

                                }
                            } else {
                                isFirst = false;
                            }
                        }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mEndTimer != null) {
            mEndTimer.cancel();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void setAlarm(Alarm alarm) {
        if (alarm != null) {
            mAlarmSettingTv.setText(DateUtil.timeToStr(alarm.getHour(), alarm.getMinute()));
        }
    }

    @Override
    public void analysisSuccess() {
        showError("！！！！！！！！！！");
    }

    private void showTopScreen(boolean show){
        if (show){
            ViewUtil.setViewVisible(mScreenTopLl);
            ViewUtil.setViewGone(mScreenBottomLl);
        }else {
            ViewUtil.setViewGone(mScreenTopLl);
            ViewUtil.setViewVisible(mScreenBottomLl);
        }
    }

    /**
     * 开始睡眠
     */
    private void startSleep() {
        showTopScreen(false);
        if (turnBeanList == null) {
            turnBeanList = new ArrayList<>();
        }
        turnBeanList.clear();
        startSleepTime=System.currentTimeMillis();
    }

    private void stopSleep() {
        showTopScreen(true);
        stopSleepTime=System.currentTimeMillis();
        if (stopSleepTime-startSleepTime<Constant.MINUTE*2){
            showError(getResources().getString(R.string.sleep_tip_5));
            return;
        }else {
            mPresenter.sendSleepData(startSleepTime,stopSleepTime,UnLockReceiver.getNum2(),turnBeanList);
        }
    }

    /**
     * 开始睡眠记录
     */
    private void startSleepRecord() {
        if (volumeThread == null) {
            volumeThread = new CheckMicophoneVolume(this);
        }
        volumeThread.startRecord();

    }

    /**
     * 结束睡眠记录
     */
    private void endSleepRecord() {
        if (volumeThread != null) {
            if (volumeThread.isRuning()) {
                volumeThread.exit();
            }
        }
    }

    @Override
    public void startRecord(final AudioRecord audioRecord) {
        SleepActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.saveVolume(audioRecord);
            }
        });
    }

    @Override
    public void stopRecord(AudioRecord audioRecord) {
        if (audioRecord != null) {
            //释放资源
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    /**
     * 这里将数据写入文件，但是并不能播放，因为AudioRecord获得的音频是原始的裸音频，
     * 如果需要播放就必须加入一些格式或者编码的头信息。但是这样的好处就是你可以对音频的 裸数据进行处理，比如你要做一个爱说话的TOM
     * 猫在这里就进行音频的处理，然后重新封装 所以说这样得到的音频比较容易做一些音频的处理。
     */


    /**
     * 获取声音分贝线程
     */
    private static class CheckMicophoneVolume extends Thread {
        /**
         * 用于在录音时记录最近5次的录音分贝
         */
        private double[] record = {70f, 70f, 70f, 70f, 70f};
        /**
         * 用于标记下次添加的录音的位置
         */
        private int pos = 0;
        private static final String TAG = "AudioRecord";
        /**
         * 获取分贝间隔时间
         */
        private static final long LOCK_TIME = 1000L;
        /**
         * 缓存大小
         */
        private static final int BUFFER_SIZE = Constant.SleepRecord.BUFFER_SIZE;

        private AudioRecord mAudioRecord;
        /**
         * 用于控制子线程是否继续获取声音分贝
         */
        private boolean isGetVoiceRun;
        /**
         * 是否正在缓存声音
         */
        private boolean isRecord = false;
        private static volatile Object mLock;
        private OnRecordListener onRecordListener;

        public CheckMicophoneVolume(OnRecordListener onRecordListener) {
            this.onRecordListener = onRecordListener;
        }

        public void startRecord() {
            mLock = new Object();
            if (isGetVoiceRun) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "还在录着呢");
                }
                return;
            }
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    Constant.SleepRecord.SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                    AudioFormat.ENCODING_PCM_16BIT, Constant.SleepRecord.BUFFER_SIZE);
            if (mAudioRecord == null) {
                Log.e(TAG, "mAudioRecord初始化失败");
            }
            isGetVoiceRun = true;
            isRecord = false;
            start();
        }

        public void exit() {
            isGetVoiceRun = false;
            isRecord = false;
        }

        public boolean isRuning() {
            return isGetVoiceRun;
        }

        @Override
        public void run() {

            if (mAudioRecord != null) {
                mAudioRecord.startRecording();

                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "分贝值:" + volume);
                    }
                    //

                    if (!isRecord && volume >= Constant.SleepRecord.VOLUME_START_RECORD) {
                        if (onRecordListener != null) {
                            onRecordListener.startRecord(mAudioRecord);
                            isRecord = true;
                        }
                    }
                    if (isRecord && AverageRecordVolume(volume) > Constant.SleepRecord.VOLUME_STOP_RECORD) {
                        if (onRecordListener != null) {
                            onRecordListener.stopRecord(mAudioRecord);
                            isRecord = false;
                        }
                    }
                    synchronized (mLock) {
                        try {
                            mLock.wait(LOCK_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }

        public double AverageRecordVolume(double volume) {
            record[pos] = volume;
            pos = pos + 1;
            if (pos >= 5) {
                pos = 0;
            }
            return (record[0] + record[1] + record[2] + record[3] + record[4]) / 5f;
        }
    }

    /**
     * 自定义定时器
     */
    private class TimeCount extends CountDownTimer {

        private long totalTime;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            totalTime = millisInFuture;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            percent = 100f - (((float) millisUntilFinished) / ((float) totalTime)) * 100f;
            Log.e(TAG, "onTick: " + percent);
            if (mProgressView != null) {
                mProgressView.setProgress((int) percent);
            }
        }

        @Override
        public void onFinish() {
            percent = 100f;
            if (mProgressView != null) {
                mProgressView.setProgress(100);
            }
//            pressEnd = true;
            sleepRecord = true;
        }
    }

    /**
     * 自定义定时器
     */
    private class TimeEnd extends CountDownTimer {

        private long totalTime;

        public TimeEnd(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            totalTime = millisInFuture;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int percent = (int) ((((float) millisUntilFinished) / ((float) totalTime)) * SleepActivity.this.percent);
            Log.e(TAG, "onTick: " + percent);
            if (mProgressView != null) {
                mProgressView.setProgress(percent);
            }
        }

        @Override
        public void onFinish() {
            percent = 0f;
            if (mProgressView != null) {
                mProgressView.setProgress(0);
            }
//            pressEnd = true;
        }
    }
}
