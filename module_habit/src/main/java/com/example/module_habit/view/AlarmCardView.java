package com.example.module_habit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_habit.R;

/**
 * author: tianhuaye
 * date:   2018/11/14 12:37
 * description:
 */
public class AlarmCardView extends CardView {

    private static final String TAG = "AlarmCardView";

    private Context mContext;
    private TextView mLeftTopTv;
    private TextView mLeftTopHintTv;
    private TextView mLeftBottomTv;
    private ImageView mLeftBottomIv;
    private TextView mRightBottomTv;
    private ImageView mRightBottomIv;

    private int leftTopText;
    private int leftTopTextSize;
    private int leftTopTextColor;
    private int leftTopTextHint;

    private int leftBottomText;
    private int leftBottomTextSize;
    private int leftBottomTextColor;
    private int leftBottomImage;

    private int rightBottomText;
    private int rightBottomTextSize;
    private int rightBottomTextColor;
    private int rightBottomImage;


    public AlarmCardView(@NonNull Context context) {
        this(context, null);
    }

    public AlarmCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlarmCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.habit_alarm_card_view, this, true);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AlarmCardView);
        if (typedArray == null) {
            return;
        }
        try {
            leftTopText = typedArray.getResourceId(R.styleable.AlarmCardView_left_top_text, R.string.habit_alarm_text_empty);
            leftTopTextSize = typedArray.getDimensionPixelSize(R.styleable.AlarmCardView_left_top_text_size, 20);
            leftTopTextColor = typedArray.getColor(R.styleable.AlarmCardView_left_top_text_color, 0XFF13227a);
            leftTopTextHint = typedArray.getResourceId(R.styleable.AlarmCardView_left_top_text_hint, R.string.habit_alarm_text_empty);
            leftBottomText = typedArray.getResourceId(R.styleable.AlarmCardView_left_bottom_text, R.string.habit_alarm_text_empty);
            leftBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.AlarmCardView_left_bottom_text_size, 20);
            leftBottomTextColor = typedArray.getColor(R.styleable.AlarmCardView_left_bottom_text_color, 0XFF13227a);
            leftBottomImage = typedArray.getResourceId(R.styleable.AlarmCardView_left_bottom_image_src, 0);
        } finally {
            typedArray.recycle();
        }
//
        mLeftTopTv = view.findViewById(R.id.left_top_text);
        mLeftTopHintTv = view.findViewById(R.id.left_top_text_hint);
        mLeftBottomTv = view.findViewById(R.id.left_bottom_text);
        mLeftBottomIv = view.findViewById(R.id.left_bottom_image);
//
        Log.e(TAG, "" + leftTopTextSize + " " + leftTopTextColor);
        mLeftTopTv.setText(leftTopText);
        mLeftTopTv.setTextSize(leftTopTextSize);
        mLeftTopTv.setTextColor(leftTopTextColor);
        mLeftTopHintTv.setText(leftTopTextHint);
        mLeftBottomTv.setText(leftBottomText);
        mLeftBottomTv.setTextSize(leftBottomTextSize);
        mLeftBottomTv.setTextColor(leftBottomTextColor);
        if (leftBottomImage != 0) {
            mLeftBottomIv.setImageResource(leftBottomImage);
        }
    }
}
