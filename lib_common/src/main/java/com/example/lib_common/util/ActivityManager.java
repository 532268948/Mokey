package com.example.lib_common.util;

import android.app.Activity;

import java.util.ArrayList;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:14
 * email  : 15869107730@163.com
 * note   :
 */
public class ActivityManager {
    private static ActivityManager activityManager;
    private ArrayList<Activity> allActivities;

    public synchronized static ActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }


    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public Activity getTopActivity() {
        if (allActivities != null) {
            for (int i = allActivities.size() - 1; i >= 0; i--) {
                if (allActivities.get(i) != null) {
                    return allActivities.get(i);
                }
            }
        }
        return null;
    }
}
