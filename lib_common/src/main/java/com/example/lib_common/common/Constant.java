package com.example.lib_common.common;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/12
 * @time : 22:20
 * @email : 15869107730@163.com
 * @note :
 */
public final class Constant {

    public static final int SPLASH_DURING = 3000;

    public static final String MESSAGE_GET_DATA_FALSE = "获取数据失败";

    public static final long MUSIC_TEMP_MAX_SIZE = 100 * 1024 * 1024L;
    public static final long DAY = 24 * 60 * 60 * 1000L;
    public static final long WEEK = 7 * 24 * 60 * 60 * 1000L;
    public static final int HOUR = 60;

    public static final String BASE_URL = "http://192.168.2.119:8080/";


    public static String TOKEN = "";
    public static Long USER_ID = 0L;

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
    }

    public static class Url {
        public static final String USER_LOGIN = "user/login";
        public static final String USER_INFORMATION = "user/information";
        public static final String MUSIC_BEFORE = "music/sleep_before";
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
    }

    /**
     * 服务端类型
     */
    public static class ServerItemType {
        /**
         * 睡前小曲
         */
        public static final int MUSIC_BEFORE = 0;
    }
}
