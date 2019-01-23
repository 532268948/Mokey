package com.example.lib_common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * project: ModuleDemo
 *
 * @author : 叶天华
 * date   : 2018/10/14
 * time   : 13:18
 * email  : 15869107730@163.com
 * note   : Toast工具类
 */
public class ToastUtil {

    public static void showShortToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
