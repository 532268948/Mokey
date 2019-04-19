package com.example.lib_common.broadcast;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_common.common.Constant;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/18
 * @time : 17:24
 * @email : 15869107730@163.com
 * @note :
 */
public class TestAlarmBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String ring = intent.getStringExtra("ringPath");
        wakeUpAndUnlock(context);
        ARouter.getInstance().build(Constant.Activity.ACTIVITY_ALARM_NOTIFY).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).withString("ring", ring).navigation();
    }


    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "test");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

}
