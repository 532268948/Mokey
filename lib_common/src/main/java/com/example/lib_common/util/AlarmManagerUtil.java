package com.example.lib_common.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.lib_common.common.Constant;

import java.util.Calendar;


/**
 * @author: tianhuaye
 * @date: 2019/1/28 13:53
 * @description:
 */
public class AlarmManagerUtil {

    /**
     * 取消闹铃
     *
     * @param context
     * @param id      闹铃id
     */
    private static void cancelAlarm(Context context, int id) {
        Intent intent = new Intent(Constant.Alarm.ALARM_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

    /**
     * 设置一次性闹钟
     *
     * @param context
     * @param id       闹钟id
     * @param hour     时
     * @param minute   分
     * @param ringPath 唤醒音乐路径
     * @param tip      提示消息
     */
    public static void setOnceAlarm(Context context, int id, int hour, int minute, String ringPath, String tip) {
        //取消重复闹铃
        cancelAlarm(context, id);
        //设置一次性闹钟

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            AlarmManagerUtil.setAlarm(context, 0, hour, minute, id, 0, tip, ringPath);
        }else if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){

        }else{

        }

    }


    /**
     * 设置重复闹钟
     *
     * @param context
     * @param id       闹钟id
     * @param hour     时
     * @param minute   分
     * @param ringPath 唤醒音乐路径
     * @param tip      提示消息
     */
    public static void setRepeatAlarm(Context context, int id, int hour, int minute, String ringPath, String tip) {
        //取消仅一次闹钟
        cancelAlarm(context, id);
        //设置重复闹钟

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            AlarmManagerUtil.setAlarm(context, 1, hour, minute, id, 0, tip, ringPath);
        }
    }

    /**
     * @param flag     周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
     *                 表示按周每周提醒的闹钟（一周的周期性时间间隔）
     * @param hour     时
     * @param minute   分
     * @param id       闹钟的id
     * @param week     week=0表示一次性闹钟或者按天的周期性闹钟，非0 的情况下是几就代表以周为周期性的周几的闹钟
     * @param tips     闹钟提示信息
     * @param ringPath 唤醒音乐路径
     */
    private static void setAlarm(Context context, int flag, int hour, int minute, int id, int
            week, String tips, String ringPath) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long intervalMillis = 0;
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), hour, minute, 10);
        if (flag == 0) {
            intervalMillis = 0;
        } else if (flag == 1) {
            intervalMillis = 24 * 3600 * 1000;
        } else if (flag == 2) {
            intervalMillis = 24 * 3600 * 1000 * 7;
        }
        Intent intent = new Intent(Constant.Alarm.ALARM_ACTION);
        intent.putExtra("intervalMillis", intervalMillis);
        intent.putExtra("msg", tips);
        intent.putExtra("id", id);
        intent.putExtra("ringPath", ringPath);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis()),
                    intervalMillis, sender);
        } else {
            if (flag == 0) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis
                        ()), intervalMillis, sender);
            }
        }
    }

    /**
     * @param weekFlag 传入的是周几
     * @param dateTime 传入的是时间戳（设置当天的年月日+从选择框拿来的时分秒）
     * @return 返回起始闹钟时间的时间戳
     */
    private static long calMethod(int weekFlag, long dateTime) {
        long time = 0;
        //weekflag == 0表示是按天为周期性的时间间隔或者是一次行的，weekfalg非0时表示每周几的闹钟并以周为时间间隔
        if (weekFlag != 0) {
            Calendar c = Calendar.getInstance();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == 1) {
                week = 7;
            } else if (week == 2) {
                week = 1;
            } else if (week == 3) {
                week = 2;
            } else if (week == 4) {
                week = 3;
            } else if (week == 5) {
                week = 4;
            } else if (week == 6) {
                week = 5;
            } else if (week == 7) {
                week = 6;
            }

            if (week == weekFlag) {
                if (dateTime > System.currentTimeMillis()) {
                    time = dateTime;
                } else {
                    time = dateTime + 7 * 24 * 3600 * 1000;
                }
            } else if (weekFlag > week) {
                time = dateTime + (weekFlag - week) * 24 * 3600 * 1000;
            } else if (weekFlag < week) {
                time = dateTime + (weekFlag - week + 7) * 24 * 3600 * 1000;
            }
        } else {
            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }
        }
        return time;
    }
}
