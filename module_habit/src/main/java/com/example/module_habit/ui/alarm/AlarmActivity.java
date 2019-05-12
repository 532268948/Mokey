package com.example.module_habit.ui.alarm;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.view.SwitchButtonView;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.FileUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.R;
import com.example.module_habit.contract.AlarmSettingContract;
import com.example.module_habit.presenter.AlarmSettingPresenter;

/**
 * @author tianhuaye
 */
public class AlarmActivity extends BaseActivity<AlarmSettingContract.View, AlarmSettingPresenter<AlarmSettingContract.View>> implements AlarmSettingContract.View {

    public static final String INTENT_SOURCE_PAGE = "sourcePage";
    public static final String INTENT_CUSTOM_ID = "customAlarmId";
    public static final String INTENT_RING_PATH = "ringPath";
    public static final String INTENT_NAME = "alarm_name";
    public static final String INTENT_HOUR = "hour";
    public static final String INTENT_MINUTE = "minute";

    private LinearLayout mParentLl;
    private TitleBar mTitleBar;
    private TimePicker mTimePicker;
    private SwitchButtonView mRepeatBtn;
    private LinearLayout mWakeMusicLl;
    private EditText mNameEt;
    private TextView mMusicTv;
    private int hour = 7;
    private int minute = 0;
    private String name = "";
    /**
     * 0一次 1重复
     */
    private int alarm_mode = 0;
    private String ringPath;
    /**
     * 1 lifestyleFragment 2 SleepActivity 3AlarmFragment
     */
    private int sourcePage = 0;
    private int customAlarmId = 0;

    @Override
    protected AlarmSettingPresenter<AlarmSettingContract.View> createPresenter() {
        return new AlarmSettingPresenter<>();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_alarm_wake_music) {
            ARouter.getInstance().build(Constant.Activity.ACTIVITY_MUSIC_SELECT).navigation(this, 11);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("AlarmActivity", "onActivityResult: ");
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                ringPath = data.getExtras().getString("ring");
                String musicName = data.getExtras().getString("name");
                mMusicTv.setText(musicName);
                Log.e("AlarmActivity", "onActivityResult: " + ringPath);
            }
        }
    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
    }

    @Override
    public void initIntent(Intent intent) {
        sourcePage = intent.getIntExtra(INTENT_SOURCE_PAGE, 0);
        customAlarmId = (int) intent.getLongExtra(INTENT_CUSTOM_ID, 0);
        hour = intent.getIntExtra(INTENT_HOUR, -1);
        minute = intent.getIntExtra(INTENT_MINUTE, -1);
        ringPath = intent.getStringExtra(INTENT_RING_PATH);
        name = intent.getStringExtra(INTENT_NAME);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_alarm;
    }

    @Override
    public void initView() {
        mParentLl = findViewById(R.id.ll_parent);
        mTimePicker = findViewById(R.id.time_picker);
        mTitleBar = findViewById(R.id.title_bar);
        mRepeatBtn = findViewById(R.id.switch_button_view);
        mWakeMusicLl = findViewById(R.id.ll_alarm_wake_music);
        mNameEt = findViewById(R.id.et_alarm_name);
        mMusicTv = findViewById(R.id.tv_music);
        mTimePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hour >= 0) {
                mTimePicker.setHour(hour);
            }
            if (minute >= 0) {
                mTimePicker.setMinute(minute);
            }
        }

    }

    @Override
    public void initListener() {
        mParentLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mParentLl.setFocusable(true);
                mParentLl.setFocusableInTouchMode(true);
                mParentLl.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mParentLl.getWindowToken(), 0);
                return false;
            }
        });
        mTitleBar.setLeftIconClickListener(new TitleBar.LeftIconClickListener() {
            @Override
            public void leftIconClick() {
                finish();
            }
        });
        mTitleBar.setRightTextClickListener(new TitleBar.RightTextClickListener() {
            @Override
            public void rightTextClick() {
                if (sourcePage == 1) {
                    setSleepAlarm();
                } else if (sourcePage == 2) {
                    setMorningAlarm();
                } else if (sourcePage == 3) {
                    setCustomAlarm();
                }
            }
        });
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                AlarmActivity.this.minute = minute;
            }
        });
        mRepeatBtn.setOnStateChangedListener(new SwitchButtonView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchButtonView view) {
                view.toggleSwitch(true);
                alarm_mode = 1;
            }

            @Override
            public void toggleToOff(SwitchButtonView view) {
                view.toggleSwitch(false);
                alarm_mode = 0;
            }
        });
        mWakeMusicLl.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(ringPath)) {
            mMusicTv.setText(FileUtil.getFileName(ringPath));
        }

        if (!TextUtils.isEmpty(name)) {
            mNameEt.setText(name);
        }

    }

    private void setCustomAlarm() {
        mPresenter.saveCustomAlarmToDB(alarm_mode, customAlarmId, Constant.Alarm.ALARM_TYPE_ONE, hour, minute, ringPath, mNameEt.getText().toString());
    }

    /**
     * 设置入睡闹钟
     */
    private void setSleepAlarm() {
//        if (alarm_mode == 0) {
//            AlarmManagerUtil.setOnceAlarm(this, (int) Constant.Alarm.ALARM_ID_FIVE, hour, minute, ringPath, mNameEt.getText().toString());
//        } else if (alarm_mode == 1) {
//            AlarmManagerUtil.setRepeatAlarm(this, (int) Constant.Alarm.ALARM_ID_FIVE, hour, minute, ringPath, mNameEt.getText().toString());
//        }
        mPresenter.saveAlarmToDB(alarm_mode, Constant.Alarm.ALARM_ID_FIVE, Constant.Alarm.ALARM_TYPE_THREE, hour, minute, ringPath, mNameEt.getText().toString());

    }

    /**
     * 设置晨起闹钟
     */
    private void setMorningAlarm() {
//        if (alarm_mode == 0) {
//            AlarmManagerUtil.setOnceAlarm(this, (int) Constant.Alarm.ALARM_ID_FOUR, hour, minute, ringPath, mNameEt.getText().toString());
//        } else if (alarm_mode == 1) {
//            AlarmManagerUtil.setRepeatAlarm(this, (int) Constant.Alarm.ALARM_ID_FOUR, hour, minute, ringPath, mNameEt.getText().toString());
//        }
//        mPresenter.saveAlarmToDB(alarm_mode, Constant.Alarm.ALARM_ID_FOUR, Constant.Alarm.ALARM_TYPE_FOUR, hour, minute, ringPath, mNameEt.getText().toString());
//        Intent intent = new Intent();
//        intent.putExtra("hour", hour);
//        intent.putExtra("minute", minute);
//        setResult(Constant.RequestAndResultCode.ALARM_RESULT_OK, intent);
//        finish();
    }

    @Override
    public void setSleepAlarmSuccess() {
        Intent intent = new Intent();
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        setResult(Constant.RequestAndResultCode.ALARM_RESULT_OK, intent);
        finish();
    }

    @Override
    public void setCustomAlarmSuccess() {
        Intent intent = new Intent();
        intent.putExtra("id", customAlarmId);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("msg", mNameEt.getText().toString());
        intent.putExtra("ringPath", ringPath);
        setResult(Constant.RequestAndResultCode.ALARM_RESULT_OK, intent);
        finish();
    }

    @Override
    public void setMorningAlarmSuccess() {
        Intent intent = new Intent();
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        setResult(Constant.RequestAndResultCode.ALARM_RESULT_OK, intent);
        finish();
    }
}
