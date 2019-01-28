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

    public static final long ALARM_ID_ONE = 1001;
    public static final long ALARM_ID_TWO = 1002;
    public static final long ALARM_ID_THREE = 1003;
    public static final long ALARM_ID_FOUR = 1004;


//    public static final int ALARM_ONE_ID_ONCE = 1001;
//    public static final int ALARM_ONE_ID_REPEAT = 1002;
//    public static final int ALARM_TWO_ID_ONCE = 2001;
//    public static final int ALARM_TWO_ID_REPEAT = 2002;
//    public static final int ALARM_THREE_ID_ONCE = 3001;
//    public static final int ALARM_THREE_ID_REPEAT = 3002;
//    public static final int ALARM_FOUR_ID_ONCE = 4001;
//    public static final int ALARM_FOUR_ID_REPEAT = 4002;

    public static final int SPLASH_DURING = 3000;

    public static final long MUSIC_TEMP_MAX_SIZE = 100 * 1024 * 1024L;
    public static final long DAY = 24 * 60 * 60 * 1000L;
    public static final long WEEK = 7 * 24 * 60 * 60 * 1000L;

    public static final String BASE_URL = "http://192.168.2.119:8080/";
    public static final String ALARM_ACTION = "com.example.alarm.clock";
    public static String TOKEN = "";
    public static Long USER_ID = 0L;

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
    }

    public static class httpCode {
        public static final int SUCCESS = 200;
        public static final int WITHOUT_LOGIN = 401;
    }

    public static class RequestAndResultCode {
        public static final int MAIN_REQUEST = 1;
        public static final int LOGIN_RESULT_OK = 2;
        public static final int SLEEP_REQUEST = 3;
        public static final int ALARM_RESULT_OK = 4;
    }
}
