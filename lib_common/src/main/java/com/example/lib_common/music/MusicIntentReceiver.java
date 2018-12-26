package com.example.lib_common.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/22
 * @time : 22:55
 * @email : 15869107730@163.com
 * @note :Receives broadcasted intents. In particular, we are interested in the
 * android.media.AUDIO_BECOMING_NOISY and android.intent.action.MEDIA_BUTTON intents, which is
 * broadcast, for example, when the user disconnects the headphones. This class works because we are
 * declaring it in a &lt;receiver&gt; tag in AndroidManifest.xml.
 */
public class MusicIntentReceiver extends BroadcastReceiver {
    final static String TAG = "MusicIntentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            // send an intent to our MusicService to telling it to pause the audio
            Intent intent1 = new Intent(ControlAction.ACTION_PAUSE);
            intent1.setPackage(context.getPackageName());
            try {
                context.startService(intent1);
            } catch (Exception e) {
            }

        } else if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            if (intent.getExtras() == null)
                return;
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent == null || keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    Intent intent1 = new Intent(ControlAction.ACTION_TOGGLE_PLAYBACK);
                    intent1.setPackage(context.getPackageName());
                    context.startService(intent1);
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    Intent intent2 = new Intent(ControlAction.ACTION_PLAY);
                    intent2.setPackage(context.getPackageName());
                    context.startService(intent2);
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    Intent intent3 = new Intent(ControlAction.ACTION_PAUSE);
                    intent3.setPackage(context.getPackageName());
                    context.startService(intent3);
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    Intent intent4 = new Intent(ControlAction.ACTION_STOP);
                    intent4.setPackage(context.getPackageName());
                    context.startService(intent4);
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    Intent intent5 = new Intent(ControlAction.ACTION_NEXT);
                    intent5.setPackage(context.getPackageName());
                    context.startService(intent5);
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    Intent intent6 = new Intent(ControlAction.ACTION_PREV);
                    intent6.setPackage(context.getPackageName());
                    context.startService(intent6);
                    break;
            }
        } else if (ControlAction.ACTION_NEXT.equals(intent.getAction())) {
            Intent intent1 = new Intent(ControlAction.ACTION_NEXT);
            intent1.setPackage(context.getPackageName());
            context.startService(intent1);
        } else if (ControlAction.ACTION_PAUSE.equals(intent.getAction())) {
            Intent intent1 = new Intent(ControlAction.ACTION_PAUSE);
            intent1.setPackage(context.getPackageName());
            context.startService(intent1);
        } else if (ControlAction.ACTION_PLAY.equals(intent.getAction())) {
            Intent intent1 = new Intent(ControlAction.ACTION_PLAY);
            intent1.setPackage(context.getPackageName());
            context.startService(intent1);
        } else if (ControlAction.ACTION_PREV.equals(intent.getAction())) {
            Intent intent1 = new Intent(ControlAction.ACTION_PREV);
            intent1.setPackage(context.getPackageName());
            context.startService(intent1);
        } else if (ControlAction.ACTION_STOP.equals(intent.getAction())) {
            Intent intent1 = new Intent(ControlAction.ACTION_STOP);
            intent1.setPackage(context.getPackageName());
            context.startService(intent1);
        }
    }
}
