package com.example.lib_common.util;

import com.example.lib_common.common.Constant;

import java.text.SimpleDateFormat;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 13:53
 * @email : 15869107730@163.com
 * @note : 日期转换工具
 */
public class DateUtil {

    /**
     * 时间戳转化为实践格式 mm:ss
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr1(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(timeStamp);
    }

    public static String timeToStr(int hour, int minute) {
        String hourStr = null;
        String minuteStr = null;
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = String.valueOf(hour);
        }

        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = String.valueOf(minute);
        }

        return hourStr + ":" + minuteStr;
    }


    public static String formatOne(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timeStamp);
    }

    public static String formatTwo(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(timeStamp);
    }

    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
//        long sec = 0;
        long diff;

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / Constant.DAY;
        hour = (diff / (Constant.HOUR));
        min = ((diff / Constant.MINUTE) - day * 24 * 60 - hour * 60);
        if (hour >= 0 && hour < 10) {
            if (min >= 0 && min < 10) {
                return "0" + hour + ":" + "0" + min;
            } else {
                return "0" + hour + ":" + min;
            }
        } else if (hour >= 10) {
            if (min >= 0 && min < 10) {
                return hour + ":" + "0" + min;
            } else {
                return hour + ":" + min;
            }
        }
        return "00:00";
//        day = diff / (24 * 60 * 60 * 1000);
//        hour = (diff / (60 * 60 * 1000) - day * 24);
//        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        if (day != 0) return day + "天"+hour + "小时"+min + "分钟" + sec + "秒";
//        if (hour != 0) return hour + "小时"+ min + "分钟" + sec + "秒";
//        if (min != 0) return min + "分钟" + sec + "秒";
//        if (sec != 0) return sec + "秒" ;
//        return "0秒";
    }

    public static String getDistanceTime(long diff) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        day = diff / Constant.DAY;
        hour = (diff / (Constant.HOUR));
        min = ((diff / Constant.MINUTE) - day * 24 * 60 - hour * 60);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            return day + "天" + hour + "时" + min + "分" + sec + "秒";
        }
        if (hour != 0) {
            return hour + "时" + min + "分" + sec + "秒";
        }
        if (min != 0) {
            return min + "分" + sec + "秒";
        }
        if (sec != 0) {
            return sec + "秒";
        }
        return "0秒";
    }

    public static int getMinuteLeft(int hour1, int minute1, int hour2, int minute2) {
        if (hour1 == hour2) {
            return minute2 - minute1;
        } else if (hour1 < hour2) {
            return minute2 - minute1 + Constant.HOUR_TO_MIMUTE * (hour2 - hour1);
        }
        return 0;
    }
}
