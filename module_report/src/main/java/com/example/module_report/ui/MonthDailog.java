package com.example.module_report.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.module_report.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/23
 * @time : 16:41
 * @email : 15869107730@163.com
 * @note :
 */
public class MonthDailog extends DialogFragment {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mTextView7;
    private TextView mTextView8;
    private TextView mTextView9;
    private TextView mTextView10;
    private TextView mTextView11;
    private TextView mTextView12;

    private MonthOnClick monthOnClick;

    private Context mContext;
    private int mWidth;
    private int mHeight;
    /**
     * 背景昏暗度
     */
    private float mDimAmount = 0.5f;
    /**
     * 是否底部显示
     */
    private boolean mShowBottomEnable;
    /**
     * 左右边距
     */
    private int mMargin = 0;
    /**
     * 进入退出动画
     */
    private int mAnimStyle = 0;
    /**
     * 点击外部取消
     */
    private boolean mOutCancel = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_month_select, container, false);
        mTextView1=view.findViewById(R.id.month_1);
        mTextView2=view.findViewById(R.id.month_2);
        mTextView3=view.findViewById(R.id.month_3);
        mTextView4=view.findViewById(R.id.month_4);
        mTextView5=view.findViewById(R.id.month_5);
        mTextView6=view.findViewById(R.id.month_6);
        mTextView7=view.findViewById(R.id.month_7);
        mTextView8=view.findViewById(R.id.month_8);
        mTextView9=view.findViewById(R.id.month_9);
        mTextView10=view.findViewById(R.id.month_10);
        mTextView11=view.findViewById(R.id.month_11);
        mTextView12=view.findViewById(R.id.month_12);

        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(1);
                }
            }
        });
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(2);
                }
            }
        });
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(3);
                }
            }
        });
        mTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(4);
                }
            }
        });
        mTextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(5);
                }
            }
        });
        mTextView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(6);
                }
            }
        });
        mTextView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(7);
                }
            }
        });
        mTextView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(8);
                }
            }
        });
        mTextView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(9);
                }
            }
        });
        mTextView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(10);
                }
            }
        });
        mTextView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(11);
                }
            }
        });
        mTextView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthOnClick!=null){
                    monthOnClick.onMonthClick(12);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmount;

            //设置dialog显示位置
            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM;
            }

            //设置dialog宽度
            if (mWidth == 0) {
                params.width = (int)(getContext().getResources().getDisplayMetrics().widthPixels*0.6);
            } else {
                params.width = mWidth;
            }

            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = mHeight;
            }

//            params.height = (int)(WindowManager.LayoutParams.MATCH_PARENT*0.6);

            //设置dialog动画
            if (mAnimStyle == 0) {
                window.setWindowAnimations(com.example.lib_common.R.style.dialogAnim);
            } else {
                window.setWindowAnimations(mAnimStyle);
            }
            window.setBackgroundDrawableResource(android.R.color.white);
            window.setAttributes(params);
        }
        setCancelable(mOutCancel);
    }

    public void setMonthClickListener(MonthOnClick monthOnClick){
        this.monthOnClick=monthOnClick;
    }

    public interface MonthOnClick{
        void onMonthClick(int month);
    }
}
