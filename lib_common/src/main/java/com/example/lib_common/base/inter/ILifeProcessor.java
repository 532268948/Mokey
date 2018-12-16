package com.example.lib_common.base.inter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/15
 * time   : 14:09
 * email  : 15869107730@163.com
 * note   : activity生命周期定制化流程，规定该方法在何时调用
 */
public interface ILifeProcessor {
    /**
     * 初始化获取Intent中的数据
     */
    void initIntent(Intent intent);

    /**
     * 数据恢复
     */
    void initSaveInstanceState(Bundle savedInstanceState);

    /**
     * @return layout id
     */
    int generateIdLayout();

    /**
     * @return layout view
     */
    View generateViewLayout();

    /**
     * 初始化Views
     */
    void initView();

    /**
     * 初始化Listener
     */
    void initListener();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 释放资源
     */
    void releaseCache();

    /**
     * 注册监听器
     */
    void onRegisterMessageReceiver();

    /**
     * 注销监听器
     */
    void onUnregisterMessageReceiver();
}
