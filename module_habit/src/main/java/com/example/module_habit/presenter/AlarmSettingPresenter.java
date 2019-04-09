package com.example.module_habit.presenter;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.common.Constant;
import com.example.module_habit.contract.AlarmSettingContract;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:32
 * @description:
 */
public class AlarmSettingPresenter<V extends AlarmSettingContract.View> extends BasePresenter<V> implements AlarmSettingContract.Presenter {

    @Override
    public void saveAlarmToDB(int alarm_mode,long id,int type, int hour, int minute, String ringPath, String tip) {
        final Alarm alarm = new Alarm(id, hour, minute, alarm_mode, ringPath, tip, type, true);
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.Alarm.ALARM_ID_FOUR, new DbOperateListener.OnQuerySingleListener() {
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
