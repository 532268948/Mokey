package com.example.module_habit.ui.lifestyle;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.common.Constant;

import java.util.List;

/**
 * @author: tianhuaye
 * date:   2018/11/23 11:11
 * description:
 */
public class LifestylePresenter<V extends LifestyleContract.View> extends BasePresenter<V> implements LifestyleContract.Presenter {
    @Override
    public void getPrepareAlarm() {
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereTypeAlarm(Constant.Alarm.ALARM_TYPE_TWO, new DbOperateListener.OnQueryAllListener() {
            @Override
            public void onQueryAllBatchListener(List list) {
                if (list != null) {
                    view.get().setPrepareAlarm(list);
                }
            }
        });
    }

    @Override
    public void updateSleepAlarm(List<Alarm> alarmList) {

    }

    @Override
    public void getSleepAlarm() {
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.Alarm.ALARM_ID_FIVE, new DbOperateListener.OnQuerySingleListener() {
            @Override
            public void onQuerySingleListener(Object entry) {
                if (entry != null) {
                    Alarm alarm = (Alarm) entry;
                    view.get().setSleepAlarm(alarm);
                }
            }
        });
    }

    @Override
    public void updateSleepAlarm(Alarm alarm) {
        DBManager.getInstance(context.get()).getAlarmDB().updateSingleAlarm(alarm, new DbOperateListener.OnUpdateListener() {
            @Override
            public void onUpdateListener(boolean type) {

            }
        });
    }
}
