package com.example.module_user.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_common.base.BaseApplication;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 18:05
 * @description:
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
