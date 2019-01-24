package com.example.tianhuaye.monkey.contract;

import com.example.lib_common.base.BaseView;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 13:56
 * @description:
 */
public interface SplashContract {

    interface View extends BaseView {
    }

    interface Presenter {
        /**
         * 每次重新进入APP时重新获取用户信息
         * @param token
         */
        void getUserInformation(String token);
    }
}
