package com.example.lib_common.service;

import android.app.AlarmManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import com.example.lib_common.base.ExecutorThreadService;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.util.AlarmManagerUtil;
import com.example.lib_common.util.DateUtil;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/8
 * @time : 16:30
 * @email : 15869107730@163.com
 * @note :
 */
public class MyJobService extends JobService {

    AlarmManager alarmManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("MyJobService", "onStartJob: ");
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ExecutorThreadService.execute(new Runnable() {
            @Override
            public void run() {
                checkAlarm();
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {


        return false;
    }

    /**
     * 闹钟检查（若失效则重启)
     */
    private void checkAlarm() {
        DBManager.getInstance(getApplicationContext()).getAlarmDB().querwhereAlarm(true, new DbOperateListener.OnQueryAllListener<Alarm>() {
            @Override
            public void onQueryAllBatchListener(List<Alarm> list) {
                if (alarmManager != null) {
                    AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();
                    String[] str = DateUtil.formatTwo(alarmClockInfo.getTriggerTime()).split(":");
                    Alarm nextAlarm=getNextOpenAlarm(list);
                    if (nextAlarm.getHour().intValue()!=Integer.valueOf(str[0])||nextAlarm.getMinute().intValue()!=Integer.valueOf(str[1])){
                        if (nextAlarm.getMode()==0){
                            AlarmManagerUtil.setOnceAlarm(getApplicationContext(),nextAlarm.getId().intValue(),nextAlarm.getHour(),nextAlarm.getMinute(),nextAlarm.getRingPath(),nextAlarm.getMsg());
                        }else if (nextAlarm.getMode()==1){
                            AlarmManagerUtil.setRepeatAlarm(getApplicationContext(),nextAlarm.getId().intValue(),nextAlarm.getHour(),nextAlarm.getMinute(),nextAlarm.getRingPath(),nextAlarm.getMsg());
                        }
                    }
                }
            }
        });
    }

    /**
     * 从数据库中获取下一个开启的闹钟
     *
     * @param list
     */
    private Alarm getNextOpenAlarm(List<Alarm> list) {
        Alarm returnAlarm=null;
        Alarm tempAlarm=null;
        for (Alarm alarm : list) {
            String[] str = DateUtil.formatTwo(System.currentTimeMillis()).split(":");
            int hour = Integer.valueOf(str[0]);
            int minute = Integer.valueOf(str[1]);
            if (hour==alarm.getHour()){
                if (minute<alarm.getMinute()){
                    tempAlarm=alarm;
                }
            }else if (hour<alarm.getHour()){
                tempAlarm=alarm;
            }
            if (returnAlarm==null){
                returnAlarm=tempAlarm;
            }else {
                if (returnAlarm.getHour().intValue()==tempAlarm.getHour().intValue()){
                    if (returnAlarm.getMinute()>tempAlarm.getMinute()){
                        returnAlarm=tempAlarm;
                    }
                }else if (returnAlarm.getHour().intValue()>tempAlarm.getHour().intValue()){
                    returnAlarm=tempAlarm;
                }
            }
        }
        return returnAlarm;
    }
}
