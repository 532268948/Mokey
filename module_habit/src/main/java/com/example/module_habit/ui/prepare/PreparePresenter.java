package com.example.module_habit.ui.prepare;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.Alarm;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.AlarmManagerUtil;
import com.example.module_habit.bean.PrepareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * date:   2018/11/23 13:43
 * description:
 */
public class PreparePresenter<V extends PrepareContract.View> extends BasePresenter<V> implements PrepareContract.Presenter {
    @Override
    public void setSleepPrepareAlarm(final List<PrepareBean> list) {
        //获取入睡闹钟信息
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.Alarm.ALARM_ID_FIVE, new DbOperateListener.OnQuerySingleListener() {
            @Override
            public void onQuerySingleListener(Object entry) {
                if (entry != null) {
                    final Alarm alarm = (Alarm) entry;
                    if (alarm.getHour() != null && alarm.getMinute() != null) {
                        int hour = alarm.getHour();
                        int minute = alarm.getMinute();
                        int setHour = 0;
                        int setMinute = 0;
                        final List<Alarm> alarmList = new ArrayList<>();
                        for (int i = list.size() - 1; i >= 0; i--) {
                            //距离入睡的时间间隔
                            int leftTime = 0;
                            if (i == 0) {
                                leftTime = 30;
                            } else {
                                leftTime = (int) list.get(i - 1).getTime();
                            }
                            if ((minute - leftTime) >= 0) {
                                setHour = hour;
                                setMinute = (int) (minute - leftTime);
                            } else {
                                setHour = hour - 1;
                                setMinute = (int) (Constant.HOUR + (minute - leftTime));
                            }
                            Alarm alarm1 = new Alarm(Constant.Alarm.ALARM_ID_SIX + i, setHour, setMinute, alarm.getMode(), null, list.get(i).getSleepPrepareItem().getTitle(), Constant.Alarm.ALARM_TYPE_TWO, true);
                            alarmList.add(alarm1);
                        }
//                        List<Alarm> idList = new ArrayList<>();
//                        for (int i = 0; i < 3; i++) {
//                            Alarm alarm2 = new Alarm();
//                            alarm.setId(Constant.Alarm.ALARM_ID_SIX + i);
//                            idList.add(alarm2);
//                        }
                        //查询数据库原先闹钟信息
                        DBManager.getInstance(context.get()).getAlarmDB().queryWhereTypeAlarm(Constant.Alarm.ALARM_TYPE_TWO, new DbOperateListener.OnQueryAllListener() {
                            @Override
                            public void onQueryAllBatchListener(List list) {
                                //删除原先的睡前准备闹钟
                                DBManager.getInstance(context.get()).getAlarmDB().deleteBatchAlarm(list, new DbOperateListener.OnDeleteListener() {
                                    @Override
                                    public void onDeleteListener(boolean type) {
                                        //添加睡前准备闹钟到数据库
                                        DBManager.getInstance(context.get()).getAlarmDB().insertBatchAlarm(alarmList, new DbOperateListener.OnInsertListener() {
                                            @Override
                                            public void onInsertListener(boolean type) {
                                                //数据库添加闹钟成功
                                                if (type) {
                                                    if (alarm.getMode() != null) {
                                                        //一次性闹钟
                                                        if (alarm.getMode() == 0) {
                                                            for (Alarm alarm1 : alarmList) {
                                                                AlarmManagerUtil.setOnceAlarm(context.get(), alarm.getId().intValue(), alarm.getHour(), alarm.getMinute(), alarm.getRingPath(), alarm.getMsg());
                                                            }
                                                            //重复闹钟
                                                        } else if (alarm.getMode() == 1) {
                                                            for (Alarm alarm1 : alarmList) {
                                                                AlarmManagerUtil.setRepeatAlarm(context.get(), alarm.getId().intValue(), alarm.getHour(), alarm.getMinute(), alarm.getRingPath(), alarm.getMsg());
                                                            }
                                                        }
                                                        //入睡闹钟未开启
                                                        if (!alarm.getOpen()) {
                                                            //设置开启
                                                            alarm.setOpen(true);
                                                            //更新数据库
                                                            DBManager.getInstance(context.get()).getAlarmDB().updateSingleAlarm(alarm, new DbOperateListener.OnUpdateListener() {
                                                                @Override
                                                                public void onUpdateListener(boolean type) {
                                                                    view.get().setAlarmSuccess();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });

                    }
                }
            }
        });
    }
}
