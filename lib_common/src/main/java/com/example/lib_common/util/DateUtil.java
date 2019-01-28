package com.example.lib_common.util;

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
}
