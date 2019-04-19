package com.example.lib_common.base;

import android.app.Application;
import android.content.Context;

import com.example.lib_common.db.DBManager;
import com.example.lib_common.swipelayout.SwipeBackHelper;

/**
 * @author : 叶天华
 * @project: ModuleDemo
 * @date : 2018/10/14
 * @time : 13:46
 * @email : 15869107730@163.com
 * @note :
 */
public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SwipeBackHelper.init(this, null);
        DBManager.getInstance(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.getInstance(this).closeConnection();
    }
}
