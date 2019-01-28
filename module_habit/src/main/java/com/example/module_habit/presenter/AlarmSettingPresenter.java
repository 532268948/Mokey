package com.example.module_habit.presenter;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.Alarm;
import com.example.lib_common.common.Constant;
import com.example.module_habit.contract.AlarmSettingContract;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:32
 * @description:
 */
public class AlarmSettingPresenter<V extends AlarmSettingContract.View> extends BasePresenter<V> implements AlarmSettingContract.Presenter {

    @Override
    public void saveAlarmToDB(int alarm_mode, int hour, int minute, String ringPath, String tip) {
        final Alarm alarm = new Alarm(Constant.ALARM_ID_FOUR, hour, minute, alarm_mode, ringPath, tip);
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.ALARM_ID_FOUR, new DbOperateListener.OnQuerySingleListener() {
            @Override
            public void onQuerySingleListener(Object entry) {
                if (entry != null) {
                    DBManager.getInstance(context.get()).getAlarmDB().updateSingleAlarm(alarm);
                } else {
                    DBManager.getInstance(context.get()).getAlarmDB().insertAlarm(alarm);
                }
            }
        });
    }
}
