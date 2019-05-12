package com.example.lib_common.base.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lib_common.R;
import com.example.lib_common.common.Constant;


/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/5
 * @time : 16:11
 * @email : 15869107730@163.com
 * @note :
 */
public class CountDownView extends FrameLayout {

    private long totalSeconds = 0L;
    private long leftSeconds = 0L;
    private SmallSleepProgressView mProgressView;
    private TextSwitcher mTextSwitcher1;
    private TextSwitcher mTextSwitcher2;

    private int animDuration = 500;

    @AnimRes
    private int inAnimResId = R.anim.anim_bottom_in;
    @AnimRes
    private int outAnimResId = R.anim.anim_top_out;

    private OnSleepFinishListener mOnSleepFinishListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftSeconds--;
                    updateData(leftSeconds);
                    if (leftSeconds > 0) {
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.count_down_view, this, true);

        mProgressView = findViewById(R.id.progress_view);
        mTextSwitcher1 = findViewById(R.id.text_switcher_1);
        mTextSwitcher2 = findViewById(R.id.text_switcher_2);

        mTextSwitcher1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(40);
                textView.setBackgroundColor(getResources().getColor(R.color.transport));
                return textView;
            }
        });

        mTextSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(40);
                textView.setBackgroundColor(getResources().getColor(R.color.transport));
                return textView;
            }
        });
        setInAndOutAnimation(inAnimResId, outAnimResId);
    }

    public void start(long seconds) {
        totalSeconds = seconds+Constant.HOUR_TO_MIMUTE;
        leftSeconds = totalSeconds;
        int minute1 = (int) seconds / (60 * 10);
        int minute2 = ((int) seconds / 60) % 10;
        mTextSwitcher1.setText(String.valueOf(minute1));
        mTextSwitcher2.setText(String.valueOf(minute2));
        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message, 1000);
    }

    private void updateData(long seconds) {
        int minute1 = (int) seconds / (60 * 10);
        int minute2 = ((int) seconds / 60) % 10;
        int percent=100 - (int) (leftSeconds * 100 / totalSeconds);
//        Log.e("CountDownView", "updateData: "+percent);
        mProgressView.setProgress(percent);
        if (percent>=100&&mOnSleepFinishListener!=null){
            this.mOnSleepFinishListener.onSleepFinish();
            percent=0;
        }
        updateTextView1(String.valueOf(minute1));
        updateTextView2(String.valueOf(minute2));
    }

    private void updateTextView1(String text1) {
        if (!((TextView) mTextSwitcher1.getCurrentView()).getText().equals(text1)) {
            mTextSwitcher1.setText(text1);
        }
    }

    private void updateTextView2(String text2) {
        if (!((TextView) mTextSwitcher2.getCurrentView()).getText().equals(text2)) {
            mTextSwitcher2.setText(text2);
        }
    }

    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        mTextSwitcher1.setInAnimation(inAnim);
        mTextSwitcher2.setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        mTextSwitcher1.setOutAnimation(outAnim);
        mTextSwitcher2.setOutAnimation(outAnim);
    }

    public void setSleepFinishListener(OnSleepFinishListener onSleepFinishListener){
        this.mOnSleepFinishListener=onSleepFinishListener;
    }

    public interface OnSleepFinishListener{
        void onSleepFinish();
    }
}
