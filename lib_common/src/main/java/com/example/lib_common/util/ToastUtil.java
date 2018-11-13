package com.example.lib_common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:18
 * email  : 15869107730@163.com
 * note   :
 */
public class ToastUtil {
    private static Context context;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public static void showShortToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG);
    }
}
