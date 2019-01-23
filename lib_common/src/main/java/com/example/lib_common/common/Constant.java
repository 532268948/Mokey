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

    public static final String BASE_URL = "http://192.168.2.119:8080/";
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
    }
}
