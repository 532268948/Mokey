package com.example.module_habit.ui.sleep;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.ViewUtil;
import com.example.module_habit.R;
import com.example.module_habit.contract.SleepContract;
import com.example.module_habit.presenter.SleepPresenter;
import com.example.module_habit.ui.alarm.AlarmActivity;

/**
 * @author tianhuaye
 */
public class SleepActivity extends BaseActivity<SleepContract.View, SleepPresenter<SleepContract.View>> implements SleepContract.View, SensorEventListener {

    private final String TAG = getClass().getSimpleName();

    private final int BOTTOM_SHRESHOLD_VALUE = -7;
    private final int TOP_SHRESHOLD_VALUE = 0;
    private boolean isVibrate = false;
    private boolean isFirst = true;

    private LinearLayout mScreenTopLl;
    private LinearLayout mScreenBottomLl;
    private TextView mAlarmSettingTv;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private Vibrator vibrator;
    private Handler handler = new Handler();

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
    public int generateIdLayout() {
        return R.layout.activity_sleep;
    }

    @Override
    public void initView() {
        mScreenTopLl = findViewById(R.id.ll_screen_top);
        mScreenBottomLl = findViewById(R.id.ll_screen_bottom);
        mAlarmSettingTv = findViewById(R.id.tv_alarm_setting);

        ViewUtil.setViewVisible(mScreenTopLl);
        ViewUtil.setViewGone(mScreenBottomLl);

    }

    @Override
    public void initListener() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mAlarmSettingTv.setOnClickListener(this);
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
        vibrator.cancel();
        mSensorManager.unregisterListener(this);
    }
}
