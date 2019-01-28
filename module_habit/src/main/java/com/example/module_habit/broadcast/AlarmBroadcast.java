package com.example.module_habit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lib_common.util.ToastUtil;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:48
 * @description:
 */
public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.showShortToastMessage(context, intent.getStringExtra("msg"));
    }
}
