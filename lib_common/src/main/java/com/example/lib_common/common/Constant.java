package com.example.lib_common.common;

import android.media.AudioFormat;
import android.media.AudioRecord;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/12
 * @time : 22:20
 * @email : 15869107730@163.com
 * @note :
 */
public final class Constant {

    public static final int NOTIFICATION_MUSIC_ID = 1000;

    public static final int SPLASH_DURING = 3000;

    public static final long MUSIC_TEMP_MAX_SIZE = 100 * 1024 * 1024L;

    public static final int HOUR_TO_MIMUTE = 60;
    public static final long MINUTE = 60 * 1000L;
    public static final long HOUR = 60 * 60 * 1000L;
    public static final long DAY = 24 * 60 * 60 * 1000L;
    public static final long WEEK = 7 * 24 * 60 * 60 * 1000L;

    public static final String BASE_URL = "http://192.168.43.72:8080/";
    public static final String MESSAGE_GET_DATA_FALSE = "获取数据失败";

    public static String TOKEN = "";
    public static Long USER_ID = 0L;

    public static class BiliBili {
        public static final String LIVE_BASE_URL = "http://live.bilibili.com/";
        public static final String LIVE_URL="AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";
//        public static final String BASE_URL = "http://192.168.43.72:8080/";

    }

    public static class Alarm {
        public static final String ALARM_ACTION = "com.example.alarm.clock";
        public static final int ALARM_MODE_ONCE = 0;
        public static final int ALARM_MODE_REPEAT = 1;

        /////////闹钟ID/////////////////////////////
        /**
         * 自定义闹钟
         */
        public static final long ALARM_ID_ONE = 1001;
        public static final long ALARM_ID_TWO = 1002;
        public static final long ALARM_ID_THREE = 1003;
        /**
         * 晨起闹钟
         */
        public static final long ALARM_ID_FOUR = 1004;
        /**
         * 入睡闹钟
         */
        public static final long ALARM_ID_FIVE = 1005;
        /**
         * 睡前准备起始ID （预留10个ID,1006-1016，当前睡前准备最多为三件事，支持日后扩展）
         */
        public static final long ALARM_ID_SIX = 1006;

        //////////闹钟类型////////////////////////
        /**
         * 自定义
         */
        public static final int ALARM_TYPE_ONE = 1;
        /**
         * 睡前准备
         */
        public static final int ALARM_TYPE_TWO = 2;
        /**
         * 入睡提醒
         */
        public static final int ALARM_TYPE_THREE = 3;
        /**
         * 晨起提醒
         */
        public static final int ALARM_TYPE_FOUR = 4;
    }

    public static class ListPageSize {
        public static final int MUSIC_SLEEP_BEFORE_PAGE_SIZE = 10;
    }

    public static class Activity {
        /**
         * module_user LoginActivity
         */
        public static final String ACTIVITY_LOGIN = "/user/login";
        /**
         * module_user RegisterActivity
         */
        public static final String ACTICITY_REGISTER = "/user/register";
        /**
         * module_report ReportDetailActivity
         */
        public static final String ACTIVITY_REPORT_DETAIL = "/report/detail";

        /**
         * module_habit AlarmNotifyActivity
         */
        public static final String ACTIVITY_ALARM_NOTIFY = "/habit/alarm_notify";

        /**
         * module_music MusicSelectActivity
         */
        public static final String ACTIVITY_MUSIC_SELECT = "/music/wake_music";
    }

    public static class Url {
        public static final String USER_LOGIN = "user/login";
        public static final String USER_INFORMATION = "user/information";
        public static final String MUSIC_BEFORE = "music/sleep_before";
        public static final String SLEEP_USER_DATA = "sleep/user_all_sleep_data";
        public static final String SLEEP_DATA_SEND = "sleep/send_sleep_data";
        public static final String COMMUNITY_FIRS_PAGE = "AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758";
    }

    public static class HttpCode {
        public static final int SUCCESS = 200;
        /**
         * 没有更多数据了
         */
        public static final int NO_MORE_DATA = 201;
        /**
         * 未登录
         */
        public static final int WITHOUT_LOGIN = 401;
        /**
         * 未注册
         */
        public static final int WITHOUT_REGISTER = 402;
    }

    public static class RequestAndResultCode {
        public static final int MAIN_REQUEST = 1;
        public static final int LOGIN_RESULT_OK = 2;
        public static final int SLEEP_REQUEST = 3;
        public static final int ALARM_RESULT_OK = 4;

        /**
         * LifeStyleFragment
         */
        public static final int FRAGMENT_LIFESTYLE_REQUES = 5;

        public static final int ACTIVITY_PREAPRE_RESULT_OK = 6;

        public static final int ALARM_REQUEST = 7;
    }

    public static class ItemType {
        /**
         * 加载更多
         */
        public static final int LOAD_MORE_DATA = 1;
        /**
         * 睡前小曲
         */
        public static final int MUSIC_BEFORE = 2;
        /**
         * 睡前准备
         */
        public static final int HABIT_SLEEP_PREPARE = 3;

        /**
         * 睡眠质量报告
         */
        public static final int SLEEP_REPORT = 4;

        /**
         * 在线音乐
         */
        public static final int MUSIC_ONLINE = 5;

        /**
         * 梦话
         */
        public static final int RECORD_DREAM = 6;

        public static final int LIVE_ITEM = 7;
        public static final int LIVE_ITEM_PARTITION = 8;
    }

    /**
     * 服务端类型
     */
    public static class ServerItemType {
        /**
         * 睡前小曲
         */
        public static final int MUSIC_BEFORE = 0;

        /**
         * 睡前小曲
         */
        public static final int MUSIC_ONLINE = 1;
    }

    public static class SleepQuality {
        /**
         * 0error 1deep 2shallow 3dream
         */
        public static final int SLEEP_ERROR = 0;
        public static final int SLEEP_DEEP = 1;
        public static final int SLEEP_SHALLOW = 2;
        public static final int SLEEP_DREAM = 3;


    }

    public static class SleepStatus {
        public static final int SLEEP_START_STATUS_EARLY = 1;
        public static final int SLEEP_START_STATUS_NORMAL = 2;
        public static final int SLEEP_START_STATUS_LATE = 3;
        public static final int SLEEP_START_STATUS_ERROR = 4;
        public static final int SLEEP_END_STATUS_EARLY = 5;
        public static final int SLEEP_END_STATUS_NORMAL = 6;
        public static final int SLEEP_END_STATUS_LATE = 7;
        public static final int SLEEP_END_STATUS_ERROR = 8;
        public static final int SLEEP_TIME_STATUS_LOW = 9;
        public static final int SLEEP_TIME_STATUS_NORMAL = 10;
        public static final int SLEEP_TIME_STATUS_HIGH = 11;
        public static final int SLEEP_TIME_STATUS_ERROR = 12;
        public static final int SLEEP_GRADE_STATUS_LOW = 13;
        public static final int SLEEP_GRADE_STATUS_NORMAL = 14;
        public static final int SLEEP_GRADE_STATUS_HIGH = 15;
        public static final int SLEEP_GRADE_STATUS_ERROR = 16;
    }

    public static class Hour {
        public static final int HOUR_ZERO = 0;
        public static final int HOUR_ONE = 1;
        public static final int HOUR_TWO = 2;
        public static final int HOUR_THREE = 3;
        public static final int HOUR_FOUR = 4;
        public static final int HOUR_FIVE = 5;
        public static final int HOUR_SIX = 6;
        public static final int HOUR_SEVEN = 7;
        public static final int HOUR_EIGHT = 8;
        public static final int HOUR_NINE = 9;
        public static final int HOUR_TEN = 10;
        public static final int HOUR_ELEVEN = 11;
        public static final int HOUR_TWELVE = 12;
        public static final int HOUR_THIRTEEN = 13;
        public static final int HOUR_FORTEEH = 14;
        public static final int HOUR_FIFTEEN = 15;
        public static final int HOUR_SIXTEEN = 16;
        public static final int HOUR_SEVENTEEN = 17;
        public static final int HOUR_EIGHTEEN = 18;
        public static final int HOUR_NINETEEN = 19;
        public static final int HOUR_TWENTY = 20;
        public static final int HOUR_TWENTY_ONE = 21;
        public static final int HOUR_TWENTY_TWO = 22;
        public static final int HOUR_TWENTY_THREE = 23;
        public static final int HOUR_TWENTY_FOUR = 24;
    }

    public static class SleepRecord {
        public static final int SAMPLE_RATE_IN_HZ = 8000;
        public static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
        public static final double VOLUME_START_RECORD = 40f;
        public static final double VOLUME_STOP_RECORD = 20f;
    }
}
