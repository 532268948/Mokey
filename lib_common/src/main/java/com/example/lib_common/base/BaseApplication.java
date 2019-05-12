package com.example.lib_common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.example.lib_common.db.DBManager;
import com.example.lib_common.swipelayout.SwipeBackHelper;
import com.mob.MobSDK;

/**
 * @author : 叶天华
 * @project: ModuleDemo
 * @date : 2018/10/14
 * @time : 13:46
 * @email : 15869107730@163.com
 * @note :
 */
public class BaseApplication extends Application {

    private int mActivityCount=0;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SwipeBackHelper.init(this, null);
        DBManager.getInstance(this);
        MobSDK.init(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                Log.d(TAG,"onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
//                Log.d(TAG,"onActivityStarted");
                mActivityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
//                Log.d(TAG,"onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
//                Log.d(TAG,"onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
//                Log.d(TAG,"onActivityStopped");
                mActivityCount--;
//                if (mActivityCount==0){
//                    MusicHelper.getInstance().unBingPlayer(null);
//                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//                Log.d(TAG,"onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                Log.d(TAG,"onActivityDestroyed");
            }
        });

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.getInstance(this).closeConnection();
    }
}
