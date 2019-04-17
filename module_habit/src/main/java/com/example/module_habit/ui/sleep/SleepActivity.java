package com.example.module_habit.ui.sleep;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lib_common.audio.AudioRecordUtil;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.bean.request.TurnBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.DateUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_habit.BuildConfig;
import com.example.module_habit.R;
import com.example.module_habit.broadcast.UnLockReceiver;
import com.example.module_habit.contract.SleepContract;
import com.example.module_habit.presenter.SleepPresenter;
import com.example.module_habit.ui.alarm.AlarmActivity;
import com.example.module_habit.view.ProgressView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhuaye
 */
public class SleepActivity extends BaseActivity<SleepContract.View, SleepPresenter<SleepContract.View>> implements SleepContract.View, SensorEventListener {


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
    private LinearLayout mRecordLl;

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

    /**
     * 是否暂停记录数据
     */
    private boolean pause = false;

    private ReportFinishDialog reportFinishDialog;

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
        mRecordLl = findViewById(R.id.ll_record);

        ViewUtil.setViewVisible(mScreenTopLl);
        ViewUtil.setViewGone(mScreenBottomLl);
        checkPermission();
    }

    private void checkPermission() {

        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Permission.RECORD_AUDIO, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE})
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
//                        try {
//
////                            String path=getCacheDir().getAbsolutePath()+File.separator  + (new Date()).getTime()+File.separator ;
//                            String path = CacheUtil.getRecordSaveFilePath(SleepActivity.this, new Date());
//                            File file = new File(path);
//                            if (!file.exists()) {
//                                file.getParentFile().mkdirs();
//                                file.createNewFile();
//                                Log.e("SleepActivity", "onAction:" + path);
//                            } else {
//                                Log.e("SleepActivity", "onAction:exists");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.e("SleepActivity", "onAction: " + e.toString());
//                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        finish();
                    }
                }).start();
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
//                    if (BuildConfig.DEBUG) {
//                        Log.e("SleepActivity", "onSensorChanged: " + event.values[0]);
//                    }
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
//                    if (BuildConfig.DEBUG) {
//                        Log.e(TAG, "onSensorChanged: 1" + z);
//                    }
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
                                            pauseSleep();
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
    public void analysisSuccess(ReportBean id) {
        showReportFinishDialog(id);
    }

    private void showReportFinishDialog(ReportBean id) {
        if (reportFinishDialog == null) {
            reportFinishDialog = new ReportFinishDialog();
            reportFinishDialog.setReportBean(id);
        }
        reportFinishDialog.show(getSupportFragmentManager());
    }

    /**
     * @param status 0第一次进入 1屏幕朝下 2记录过程屏幕朝上
     */
    private void showScreen(int status) {
        if (status == 0) {
            ViewUtil.setViewVisible(mScreenTopLl);
            ViewUtil.setViewGone(mScreenBottomLl);
            ViewUtil.setViewGone(mRecordLl);
        } else if (status == 1) {
            ViewUtil.setViewVisible(mRecordLl);
            ViewUtil.setViewGone(mScreenTopLl);
            ViewUtil.setViewGone(mScreenBottomLl);
        } else if (status == 2) {
            ViewUtil.setViewVisible(mScreenBottomLl);
            ViewUtil.setViewGone(mRecordLl);
            ViewUtil.setViewGone(mScreenTopLl);
        }
    }

    /**
     * 开始睡眠
     */
    private void startSleep() {
        showScreen(1);
        if (turnBeanList == null) {
            turnBeanList = new ArrayList<>();
        }
        if (!pause) {
            turnBeanList.clear();
        }
        pause = false;
        startSleepTime = System.currentTimeMillis();
        startRecord();
    }

    private void pauseSleep() {
        pause = true;
        showScreen(2);
        stopRecord();
    }

    private void stopSleep() {

        pause = false;
        stopSleepTime = System.currentTimeMillis();
        if (stopSleepTime - startSleepTime < Constant.MINUTE * 2) {
            showError(getResources().getString(R.string.sleep_tip_5));
            return;
        } else {
            mPresenter.sendSleepData(startSleepTime, stopSleepTime, UnLockReceiver.getNum2(), turnBeanList);
        }
    }

    public void startRecord() {
        AudioRecordUtil.getInstance(this).startRecord();
    }

    public void stopRecord() {
        AudioRecordUtil.getInstance(this).stopRecord();
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
            stopSleep();
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
