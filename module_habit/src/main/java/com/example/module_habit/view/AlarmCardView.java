package com.example.module_habit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lib_common.base.view.SwitchButtonView;
import com.example.module_habit.R;

/**
 * @author: tianhuaye
 * date:   2018/11/14 12:37
 * description:
 */
public class AlarmCardView extends LinearLayout {

    private static final String TAG = "AlarmCardView";

    private Context mContext;
    private TextView mLeftTopTv;
    private TextView mLeftTopHintTv;
    private TextView mLeftBottomTv;
    private TextView mRightBottomTv;
    private ImageView mRightBottomIv;
    private LinearLayout mEditLl;
    private SwitchButtonView mSwitchButtonView;
    private LinearLayout mLeftBottomContainer;
    private OnToggleChangeListener onToggleChangeListener;

    private int leftTopText;
    private int leftTopTextSize;
    private int leftTopTextColor;
    private int leftTopTextHint;
    private int leftTopTextHintSize;
    private int leftTopTextHintColor;

    private int leftBottomText;
    private int leftBottomTextSize;
    private int leftBottomTextColor;
    private int leftBottomImage;

    private int rightBottomText;
    private int rightBottomTextSize;
    private int rightBottomTextColor;
    private int rightBottomImage;

    private OnEditClickListener onEditClickListener;

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
            leftTopTextHintSize = typedArray.getDimensionPixelSize(R.styleable.AlarmCardView_left_top_text_hint_size, 10);
            leftTopTextHintColor = typedArray.getResourceId(R.styleable.AlarmCardView_left_top_text_hint_color, 0XFF13227a);
            leftBottomText = typedArray.getResourceId(R.styleable.AlarmCardView_left_bottom_text, R.string.habit_alarm_text_empty);
            leftBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.AlarmCardView_left_bottom_text_size, 20);
            leftBottomTextColor = typedArray.getColor(R.styleable.AlarmCardView_left_bottom_text_color, 0XFF13227a);
        } finally {
            typedArray.recycle();
        }

        mLeftTopTv = view.findViewById(R.id.left_top_text);
        mLeftTopHintTv = view.findViewById(R.id.left_top_text_hint);
        mLeftBottomTv = view.findViewById(R.id.left_bottom_text);
        mEditLl = view.findViewById(R.id.alarm_edit);
        mSwitchButtonView = view.findViewById(R.id.switch_button);
        mLeftBottomContainer = view.findViewById(R.id.left_bottom);

        mLeftTopTv.setText(leftTopText);
        mLeftTopTv.setTextSize(leftTopTextSize);
        mLeftTopTv.setTextColor(leftTopTextColor);
        mLeftTopHintTv.setText(leftTopTextHint);
        mLeftBottomTv.setText(leftBottomText);
        mLeftBottomTv.setTextSize(leftBottomTextSize);
        mLeftBottomTv.setTextColor(leftBottomTextColor);


        mSwitchButtonView.setOnStateChangedListener(new SwitchButtonView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchButtonView view) {
                view.toggleSwitch(true);
                if (onToggleChangeListener != null) {
                    onToggleChangeListener.toggleChange(true);
                }
            }

            @Override
            public void toggleToOff(SwitchButtonView view) {
                view.toggleSwitch(false);
                if (onToggleChangeListener != null) {
                    onToggleChangeListener.toggleChange(false);
                }
            }
        });
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

    public void setLeftBottomText(String leftBottomText) {
        if (!TextUtils.isEmpty(leftBottomText)) {
            this.mLeftBottomTv.setText(leftBottomText);
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

//    public void setLeftBottomImage(int leftBottomImage) {
//        this.leftBottomImage = leftBottomImage;
//        if (this.leftBottomImage > 0) {
//            this.mLeftBottomIv.setImageResource(this.leftBottomImage);
//        }
//    }

    public void setLeftBottomImages(int[] src) {
        mLeftBottomContainer.removeAllViews();
        for (int i = 0; i < src.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(getContext().getResources().getDrawable(src[i]));
            mLeftBottomContainer.addView(imageView);
            if (i < src.length - 1) {
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.habit_alarm_card_add));
                mLeftBottomContainer.addView(imageView1);
            }
        }
        if (mLeftBottomContainer.getChildCount()==0){
            TextView textView=new TextView(getContext());
            textView.setText("还没有设置任何习惯");
            mLeftBottomContainer.addView(textView);
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

    public void setSwitchButtonOpen(boolean open) {
        mSwitchButtonView.toggleSwitch(open);
    }

    public void setSwitchButtonClickListener(OnToggleChangeListener onToggleChangeListener) {
        this.onToggleChangeListener = onToggleChangeListener;
    }


    public void addEditClickListener(final OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
        if (this.onEditClickListener != null && this.mEditLl != null) {
            mEditLl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditClickListener.onEditClick();
                }
            });
        }
    }

    public interface OnEditClickListener {
        void onEditClick();
    }

    public interface OnToggleChangeListener {
        void toggleChange(boolean open);
    }
}
