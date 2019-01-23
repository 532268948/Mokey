package com.example.module_user.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.bean.response.LoginItem;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 19:15
 * @description:
 */
public interface LoginContract {

    interface View extends BaseView {
        /**
         * 登录成功后的回调方法
         *
         * @param loginInfo
         */
        void loginSuccess(LoginItem loginInfo);
    }

    interface Presenter {
        /**
         * 登录
         *
         * @param name
         * @param password
         */
        void login(String name, String password);
    }
}
