package com.example.lib_common.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lib_common.R;

/**
 * author: tianhuaye
 * date:   2018/11/13 15:25
 * description:自定义多功能标题栏
 */
public class TitleBar extends RelativeLayout {

    private static final String TAG = "TitleBar";
    public static final int MODE_TITLE = 0;
    public static final int MODE_TAB = 1;
    private Context mContext;
    private int mLeftIcon;
    private int mRightIcon;
    private int mTitleText;
    private int mTextSize;
    private int mTitleMode;
    private ImageView mLeftIv;
    private ImageView mRightIv;
    private FrameLayout mCenterContainer;

    private LeftIconClickListener leftIconClickListener;
    private RightIconClickListener rightIconClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.title_bar, this, true);
//        setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if (typedArray == null) {
            return;
        }
        try {
            mLeftIcon = typedArray.getResourceId(R.styleable.TitleBar_left_icon, 0);
            mRightIcon = typedArray.getResourceId(R.styleable.TitleBar_right_icon, 0);
            mTitleText = typedArray.getResourceId(R.styleable.TitleBar_title_text, 0);
            mTitleMode = typedArray.getInteger(R.styleable.TitleBar_title_mode, 0);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_text_size, mContext.getResources().getDimensionPixelSize(R.dimen.dp_16));
        } finally {
            typedArray.recycle();
        }

        mLeftIv = view.findViewById(R.id.left_icon);
        mRightIv = view.findViewById(R.id.right_icon);
        mCenterContainer = view.findViewById(R.id.center_container);

        if (this.mLeftIcon!=0){
            mLeftIv.setImageResource(this.mLeftIcon);
            mLeftIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leftIconClickListener!=null){
                        leftIconClickListener.leftIconClick();
                    }
                }
            });
        }
        if (this.mRightIcon!=0){
            mRightIv.setImageResource(this.mRightIcon);
            mRightIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightIconClickListener!=null){
                        rightIconClickListener.rightIconClick();
                    }
                }
            });
        }
        if (mTitleMode == MODE_TITLE && mTitleText != 0) {
            setTitleText(mTitleText);
        }
    }

    public void setLeftDrawable(int resourcesId) {
        this.mLeftIcon = resourcesId;
        if (this.mLeftIv != null) {
            this.mLeftIv.setImageResource(this.mLeftIcon);
        }
    }

    public void setRightDrawable(int resourcesId) {
        this.mRightIcon = resourcesId;
        if (this.mRightIv != null) {
            this.mRightIv.setImageResource(this.mRightIcon);
        }
    }

    public void setTitleText(int resourcesId) {
        mTitleMode = MODE_TITLE;
        this.mTitleText = resourcesId;
        mCenterContainer.removeAllViews();
        TextView textView = new TextView(mContext);
        textView.setText(this.mTitleText);
        textView.setTextSize(mTextSize);
        mCenterContainer.addView(textView);
    }

    public void addTabLayout(View view) {
        mTitleMode = MODE_TAB;
        mCenterContainer.removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        mCenterContainer.addView(view);
    }

    public void setLeftIconClickListener(LeftIconClickListener leftIconClickListener) {
        this.leftIconClickListener = leftIconClickListener;
    }

    public void setRightIconClickListener(RightIconClickListener rightIconClickListener) {
        this.rightIconClickListener = rightIconClickListener;
    }

    public interface LeftIconClickListener {
        void leftIconClick();
    }

    public interface RightIconClickListener {
        void rightIconClick();
    }
}
