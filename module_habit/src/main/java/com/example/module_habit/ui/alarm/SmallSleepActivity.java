package com.example.module_habit.ui.alarm;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lib_common.base.view.CountDownView;
import com.example.lib_common.base.view.TitleBar;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_habit.R;

import java.io.IOException;

public class SmallSleepActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private CountDownView mMarqueeView;
    private TextView mFinishTv;
    private TitleBar mTitleBar;

    private int minutes = 0;
    private String title = "";
    private String msg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_sleep);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.habit_small_sleep_type_1_bg_color), 0);
        mMediaPlayer = MediaPlayer.create(this, RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE));
        mMarqueeView = findViewById(R.id.marqueen_view);
        mFinishTv = findViewById(R.id.tv_finish);
        mTitleBar = findViewById(R.id.title_bar);
        mMarqueeView.start(Constant.HOUR_TO_MIMUTE * 12);
        mMarqueeView.setSleepFinishListener(new CountDownView.OnSleepFinishListener() {
            @Override
            public void onSleepFinish() {
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
        });
        mTitleBar.setLeftIconClickListener(new TitleBar.LeftIconClickListener() {
            @Override
            public void leftIconClick() {
                onBackPressed();
            }
        });
        mFinishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

//
//    /**
//     * 自定义定时器
//     */
//    private class TimeCount extends CountDownTimer {
//
//        private long totalTime;
//
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//            totalTime = millisInFuture;
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            percent = 100f - (((float) millisUntilFinished) / ((float) totalTime)) * 100f;
////            if (BuildConfig.DEBUG){
////                Log.e("TimeCount", "onTick: "+percent);
////            }
//            if (mSmallSleepProgressView != null) {
//                mSmallSleepProgressView.setProgress((int) percent);
//            }
//            String text1 = String.valueOf(millisUntilFinished / (1000L * 10));
//            String text2 = String.valueOf((millisUntilFinished / 1000L) % 10);
//            if (BuildConfig.DEBUG) {
////                Log.e("TimeCount", "onTick: " + text1 + " " + text2);
//            }
//            mMarqueeView.updateData(text1, text2);
//        }
//
//        @Override
//        public void onFinish() {
//            percent = 100f;
//            if (mSmallSleepProgressView != null) {
//                mSmallSleepProgressView.setProgress(100);
//            }
//        }
//    }
}
