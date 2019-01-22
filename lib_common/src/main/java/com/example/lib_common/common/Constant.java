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

    public static final String BASE_URL = "http://192.168.1.107:8080/";

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
    }

    public static class httpCode {
        public static final String SUCCESS = "200";
        public static final String WITHOUT_LOGIN = "401";
    }
}
