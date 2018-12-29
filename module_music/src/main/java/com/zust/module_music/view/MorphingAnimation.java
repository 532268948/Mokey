package com.zust.module_music.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/27
 * @time : 17:34
 * @email : 15869107730@163.com
 * @note : 悬音乐浮窗变化动画
 */
public class MorphingAnimation {
    public static final int DURATION_NORMAL = 600;
    public static final int DURATION_INSTANT = 1;

    private OnAnimationEndListener mListener;

    private int mDuration;

    private int mFromWidth;
    private int mToWidth;

    private int mFromColor;
    private int mToColor;

    private float mFromCornerRadius;
    private float mToCornerRadius;

    private float mPadding;
    private FloatingMusicView mView;
    private GradientDrawable mDrawable;

    public MorphingAnimation(FloatingMusicView view, GradientDrawable drawable) {
        this.mView = view;
        mDrawable = drawable;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setListener(OnAnimationEndListener listener) {
        mListener = listener;
    }

    public void setFromWidth(int fromWidth) {
        mFromWidth = fromWidth;
    }

    public void setToWidth(int toWidth) {
        mToWidth = toWidth;
    }

    public void setFromColor(int fromColor) {
        mFromColor = fromColor;
    }

    public void setToColor(int toColor) {
        mToColor = toColor;
    }

    public void setFromCornerRadius(float fromCornerRadius) {
        mFromCornerRadius = fromCornerRadius;
    }

    public void setToCornerRadius(float toCornerRadius) {
        mToCornerRadius = toCornerRadius;
    }

    public void setPadding(float padding) {
        mPadding = padding;
    }

    public void start() {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(mFromWidth, mToWidth);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                int leftOffset;
                int rightOffset;
                int padding;

                if (mFromWidth > mToWidth) {
//                    leftOffset = (mFromWidth - value);
                    rightOffset = mFromWidth;
//                    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
                    leftOffset = (mFromWidth - value);
//                    rightOffset = mFromWidth - leftOffset;
                    padding = (int) (mPadding * animation.getAnimatedFraction());
                } else {
//                    leftOffset = (mToWidth - value);
                    rightOffset = mToWidth;
//                    padding = (int) (mPadding * animation.getAnimatedFraction());
                    leftOffset = (mToWidth - value);
//                    rightOffset = mToWidth - leftOffset;
                    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
                }

                Log.e("MorphingAnimation", "onAnimationUpdate: leftOffset"+leftOffset+"padding:"+padding+"rightOffset"+rightOffset);
                mDrawable.setBounds(leftOffset + padding, padding, rightOffset - padding, mView.getHeight() - padding);
            }
        });

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(mDrawable, "color", mFromColor, mToColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());

        ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(mDrawable, "cornerRadius", mFromCornerRadius, mToCornerRadius);

//        ObjectAnimator viewWidthAnimation=ObjectAnimator.ofFloat(mView,"width",mFromCornerRadius,mToWidth);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(widthAnimation, bgColorAnimation, cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mListener != null) {
                    mListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    public interface OnAnimationEndListener {
        /**
         * 动画结束监听回调
         */
        void onAnimationEnd();
    }
}
