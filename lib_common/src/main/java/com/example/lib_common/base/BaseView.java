package com.example.lib_common.base;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:11
 * email  : 15869107730@163.com
 * note   :
 */
public interface BaseView {
    /**
     * 显示dialog
     *
     * @param message 消息内容
     */
    void showDialog(String message);

    /**
     * 隐藏dialog
     */
    void dismissDialog();

    /**
     * loading页面
     */
    void showLoading();

    /**
     * 正常展示页面
     */
    void showNormal();

    /**
     * 空页面
     */
    void showEmpty();

    /**
     * 错误信息dialog提示
     *
     * @param message
     * @param code
     */
    void showError(String message, String code);

    /**
     * 信息toast提示
     *
     * @param message
     */
    void showError(String message);

    /**
     * 清除登录信息
     */
    void clearLoginInformation();
}
