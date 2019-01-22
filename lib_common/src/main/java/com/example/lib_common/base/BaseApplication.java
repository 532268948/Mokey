package com.example.lib_common.base;

import android.app.Application;
import android.content.Context;

import com.example.lib_common.base.db.DBManager;

/**
 * @project: ModuleDemo
 * @author : 叶天华
 * @date   : 2018/10/14
 * @time   : 13:46
 * @email  : 15869107730@163.com
 * @note   :
 */
public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        DBManager.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.getInstance().closeConnection();
    }
}
