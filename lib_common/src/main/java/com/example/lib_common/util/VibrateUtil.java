package com.example.lib_common.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 9:46
 * @description:
 */
public class VibrateUtil {
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
