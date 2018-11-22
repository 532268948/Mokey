package com.example.module_habit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_habit.R;

/**
 * author: tianhuaye
 * date:   2018/11/14 12:37
 * description:
 */
public class AlarmCardView extends LinearLayout {

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

    public int getLeftTopText() {
        return leftTopText;
    }

    public void setLeftTopText(int leftTopText) {
        this.leftTopText = leftTopText;
        if (this.leftTopText > 0) {
            this.mLeftTopTv.setText(this.leftTopText);
        }
    }

    public void setLeftTopText(String leftTopText) {
        if (!TextUtils.isEmpty(leftTopText)) {
            this.mLeftTopTv.setText(leftTopText);
        }
    }

    public int getLeftTopTextSize() {
        return leftTopTextSize;
    }

    public void setLeftTopTextSize(int leftTopTextSize) {
        this.leftTopTextSize = leftTopTextSize;
        if (this.leftTopTextSize > 0) {
            this.mLeftTopTv.setTextSize(this.leftTopTextSize);
        }
    }

    public int getLeftTopTextColor() {
        return leftTopTextColor;
    }

    public void setLeftTopTextColor(int leftTopTextColor) {
        this.leftTopTextColor = leftTopTextColor;
        this.mLeftTopTv.setTextColor(this.leftTopTextColor);
    }

    public int getLeftTopTextHint() {
        return leftTopTextHint;
    }

    public void setLeftTopTextHint(int leftTopTextHint) {
        this.leftTopTextHint = leftTopTextHint;
        if (this.leftTopTextHint > 0) {
            this.mLeftTopHintTv.setText(this.leftTopTextHint);
        }
    }

    public int getLeftBottomText() {
        return leftBottomText;
    }

    public void setLeftBottomText(int leftBottomText) {
        this.leftBottomText = leftBottomText;
        if (this.leftBottomText > 0) {
            this.mLeftBottomTv.setText(this.leftBottomText);
        }
    }

    public int getLeftBottomTextSize() {
        return leftBottomTextSize;
    }

    public void setLeftBottomTextSize(int leftBottomTextSize) {
        this.leftBottomTextSize = leftBottomTextSize;
        if (this.leftBottomTextSize > 0) {
            this.mLeftBottomTv.setTextSize(this.leftBottomTextSize);
        }
    }

    public int getLeftBottomTextColor() {
        return leftBottomTextColor;
    }

    public void setLeftBottomTextColor(int leftBottomTextColor) {
        this.leftBottomTextColor = leftBottomTextColor;
        this.mLeftBottomTv.setTextColor(this.leftBottomTextColor);
    }

    public int getLeftBottomImage() {
        return leftBottomImage;
    }

    public void setLeftBottomImage(int leftBottomImage) {
        this.leftBottomImage = leftBottomImage;
        if (this.leftBottomImage > 0) {
            this.mLeftBottomIv.setImageResource(this.leftBottomImage);
        }
    }

    public int getRightBottomText() {
        return rightBottomText;
    }

    public void setRightBottomText(int rightBottomText) {
        this.rightBottomText = rightBottomText;
        if (this.rightBottomText > 0) {
            this.mRightBottomTv.setText(this.rightBottomText);
        }
    }

    public int getRightBottomTextSize() {
        return rightBottomTextSize;
    }

    public void setRightBottomTextSize(int rightBottomTextSize) {
        this.rightBottomTextSize = rightBottomTextSize;
        if (this.rightBottomTextSize > 0) {
            this.mRightBottomTv.setTextSize(this.rightBottomTextSize);
        }
    }

    public int getRightBottomTextColor() {
        return rightBottomTextColor;
    }

    public void setRightBottomTextColor(int rightBottomTextColor) {
        this.rightBottomTextColor = rightBottomTextColor;
        this.mRightBottomTv.setTextColor(this.rightBottomTextColor);
    }

    public int getRightBottomImage() {
        return rightBottomImage;
    }

    public void setRightBottomImage(int rightBottomImage) {
        this.rightBottomImage = rightBottomImage;
        if (this.rightBottomImage > 0) {
            this.mRightBottomIv.setImageResource(this.rightBottomImage);
        }
    }
}
