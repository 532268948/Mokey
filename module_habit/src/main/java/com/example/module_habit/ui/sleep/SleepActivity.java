package com.example.module_habit.ui.sleep;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.ViewUtil;
import com.example.module_habit.R;
import com.example.module_habit.contract.SleepContract;
import com.example.module_habit.presenter.SleepPresenter;
import com.example.module_habit.ui.alarm.AlarmActivity;
import com.example.module_habit.view.ProgressView;

/**
 * @author tianhuaye
 */
public class SleepActivity extends BaseActivity<SleepContract.View, SleepPresenter<SleepContract.View>> implements SleepContract.View, SensorEventListener {

    private final String TAG = getClass().getSimpleName();

    private final int BOTTOM_SHRESHOLD_VALUE = -7;
    private final int TOP_SHRESHOLD_VALUE = 0;
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

    @Override
    protected SleepPresenter<SleepContract.View> createPresenter() {
        return new SleepPresenter<>();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_alarm_setting) {
            startActivity(new Intent(this, AlarmActivity.class));
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

        ViewUtil.setViewGone(mScreenTopLl);
        ViewUtil.setViewVisible(mScreenBottomLl);

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

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //不是震动状态
        if (!isVibrate) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                int z = (int) event.values[2];

                //屏幕在上下翻转过程中
                if (z > BOTTOM_SHRESHOLD_VALUE && z < TOP_SHRESHOLD_VALUE) {
                    //设置当前状态为翻转状态
                    Log.e(TAG, "onSensorChanged: 1" + z);
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
