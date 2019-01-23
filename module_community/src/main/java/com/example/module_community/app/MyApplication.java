package com.example.module_community.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_common.BuildConfig;
import com.example.lib_common.base.BaseApplication;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 14:34
 * @description:
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
    }
}
