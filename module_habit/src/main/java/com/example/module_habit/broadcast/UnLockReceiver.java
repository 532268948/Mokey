package com.example.module_habit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/10
 * @time : 11:00
 * @email : 15869107730@163.com
 * @note :
 */
public class UnLockReceiver extends BroadcastReceiver {
    public static int num2 =  0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 解锁
        if (intent != null
                && Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            Toast.makeText(context, "屏幕已解锁", Toast.LENGTH_SHORT).show();
            num2++;
        }
    }

    public static int getNum2() {
        return num2;
    }
}
