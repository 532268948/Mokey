package com.example.module_habit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.util.Log;

import com.example.lib_common.util.ToastUtil;

import java.io.IOException;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:48
 * @description:
 */
public class AlarmBroadcast extends BroadcastReceiver {

    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("AlarmBroadcast", "onReceive: ");
        String ring = intent.getStringExtra("ringPath");
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
        ToastUtil.showShortToastMessage(context,"hahah");
        startAlarm(context);
//        ToastUtil.showShortToastMessage(context, intent.getStringExtra("msg"));
    }

    private void startAlarm(Context context) {
        mMediaPlayer = MediaPlayer.create(context, RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE));
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }
}
