package com.example.module_habit.presenter;

import android.util.Log;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.AlarmManagerUtil;
import com.example.module_habit.BuildConfig;
import com.example.module_habit.contract.AlarmSettingContract;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:32
 * @description:
 */
public class AlarmSettingPresenter<V extends AlarmSettingContract.View> extends BasePresenter<V> implements AlarmSettingContract.Presenter {

    @Override
    public void saveAlarmToDB(int alarm_mode, long id, int type, int hour, int minute, String ringPath, String tip) {
        view.get().showDialog(null);
        final Alarm alarm = new Alarm(id, hour, minute, alarm_mode, ringPath, tip, type, true);
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.Alarm.ALARM_ID_FOUR, new DbOperateListener.OnQuerySingleListener<Alarm>() {
            @Override
            public void onQuerySingleListener(Alarm entry) {
                if (entry != null) {
                    DBManager.getInstance(context.get()).getAlarmDB().updateSingleAlarm(alarm, new DbOperateListener.OnUpdateListener() {
                        @Override
                        public void onUpdateListener(boolean type) {
                            if (type) {
                                //获取睡前准备闹钟
                                DBManager.getInstance(context.get()).getAlarmDB().queryWhereTypeAlarm(Constant.Alarm.ALARM_TYPE_TWO, new DbOperateListener.OnQueryAllListener<Alarm>() {

                                    @Override
                                    public void onQueryAllBatchListener(final List<Alarm> list) {
                                        if (list != null && list.size() != 0) {
                                            if (BuildConfig.DEBUG) {
                                                Log.e("AlarmSettingPresenter", "onQueryAllBatchListener: " + list);
                                            }
                                            //计算上次睡前闹钟时间
                                            int lastHour = 0;
                                            int lastMinute = 0;
                                            if (list.get(0).getMinute() + 30 >= 60) {
                                                lastHour = list.get(0).getHour() + 1;
                                                lastMinute = list.get(0).getMinute() + 30 - 60;
                                            }
                                            //尖酸上次睡前闹钟与这次的时间差
                                            int leftHour = 0;
                                            int leftMinute = 0;
                                            if (alarm.getMinute() - lastMinute < 0) {
                                                leftHour = alarm.getHour() - lastHour - 1;
                                                leftMinute = Constant.HOUR_TO_MIMUTE + alarm.getMinute() - lastMinute;
                                            } else {
                                                leftHour = alarm.getHour() - lastHour;
                                                leftMinute = alarm.getMinute() - lastMinute;
                                            }
                                            for (int i = 0; i < list.size(); i++) {
                                                Alarm alarm1 = list.get(i);
                                                if (alarm1.getMinute() + leftMinute >= Constant.HOUR_TO_MIMUTE) {
                                                    alarm1.setHour(alarm1.getHour() + leftHour + 1);
                                                    alarm1.setMinute(alarm1.getMinute() + leftMinute - Constant.HOUR_TO_MIMUTE);
                                                } else {
                                                    alarm1.setHour(alarm1.getHour() + leftHour);
                                                    alarm1.setMinute(alarm1.getMinute() + leftMinute);
                                                }
                                            }

                                            DBManager.getInstance(context.get()).getAlarmDB().updateBatchAlarm(list, new DbOperateListener.OnUpdateListener<List<Alarm>>() {
                                                @Override
                                                public void onUpdateListener(boolean type) {
                                                    if (type) {
                                                        if (alarm.getMode() != null) {
                                                            //一次性闹钟
                                                            if (alarm.getMode() == 0) {
                                                                for (Alarm alarm1 : list) {
                                                                    if (BuildConfig.DEBUG) {
                                                                        Log.e("PreparePresenter", "onInsertListener: " + alarm1.getMsg());
                                                                    }
                                                                    AlarmManagerUtil.setOnceAlarm(context.get(), alarm1.getId().intValue(), alarm1.getHour(), alarm1.getMinute(), alarm1.getRingPath(), alarm1.getMsg());
                                                                }
                                                                AlarmManagerUtil.setOnceAlarm(context.get(), alarm.getId().intValue(), alarm.getHour(), alarm.getMinute(), alarm.getRingPath(), alarm.getMsg());
                                                                //重复闹钟
                                                            } else if (alarm.getMode() == 1) {
                                                                for (Alarm alarm1 : list) {
                                                                    AlarmManagerUtil.setRepeatAlarm(context.get(), alarm1.getId().intValue(), alarm1.getHour(), alarm1.getMinute(), alarm1.getRingPath(), alarm1.getMsg());
                                                                }
                                                                AlarmManagerUtil.setRepeatAlarm(context.get(), alarm.getId().intValue(), alarm.getHour(), alarm.getMinute(), alarm.getRingPath(), alarm.getMsg());
                                                            }
                                                            if (BuildConfig.DEBUG){
                                                                Log.e("AlarmSettingPresenter", "onUpdateListener: "+list);
                                                            }
                                                            view.get().dismissDialog();
                                                            view.get().setSleepAlarmSuccess();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    DBManager.getInstance(context.get()).getAlarmDB().insertAlarm(alarm);
                }
            }
        });
    }
}
