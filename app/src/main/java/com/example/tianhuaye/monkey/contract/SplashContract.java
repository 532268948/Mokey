package com.example.tianhuaye.monkey.contract;

import com.example.lib_common.base.BaseView;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 13:56
 * @description:
 */
public interface SplashContract {

    interface View extends BaseView {
        /**
         * 登录失败清空用户的Id和token，以判断用户未登录
         */
        void clearUserInformation();
    }

    interface Presenter {
        /**
         * 每次重新进入APP时重新获取用户信息
         * @param token
         */
        void getUserInformation(String token);
    }
}
