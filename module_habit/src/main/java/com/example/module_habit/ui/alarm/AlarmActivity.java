package com.example.module_habit.ui.alarm;

import android.app.AlarmManager;
import android.app.Service;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.R;
import com.example.module_habit.contract.AlarmSettingContract;
import com.example.module_habit.presenter.AlarmSettingPresenter;

/**
 * @author tianhuaye
 */
public class AlarmActivity extends BaseActivity<AlarmSettingContract.View, AlarmSettingPresenter<AlarmSettingContract.View>> implements AlarmSettingContract.View {

    private LinearLayout mParentLl;
    private TitleBar mTitleBar;
    private TimePicker mTimePicker;

    private AlarmManager mAlarmManager;

    @Override
    protected AlarmSettingPresenter<AlarmSettingContract.View> createPresenter() {
        return new AlarmSettingPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
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
        mTimePicker.setIs24HourView(true);
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
                finish();
            }
        });
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });
        mAlarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
    }

    @Override
    public void initData() {

    }
}
