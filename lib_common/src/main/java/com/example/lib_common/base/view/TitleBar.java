package com.example.lib_common.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lib_common.R;
import com.example.lib_common.util.ViewUtil;

/**
 * @author: tianhuaye
 * @date: 2018/11/13 15:25
 * @description:自定义多功能标题栏
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

    private TextView mTitleTv;

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
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if (typedArray == null) {
            return;
        }
        try {
            mLeftIcon = typedArray.getResourceId(R.styleable.TitleBar_left_icon, 0);
            mRightIcon = typedArray.getResourceId(R.styleable.TitleBar_right_icon, 0);
            mTitleText = typedArray.getResourceId(R.styleable.TitleBar_title_text, 0);
            mTitleMode = typedArray.getInteger(R.styleable.TitleBar_title_mode, 0);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_text_size, 18);
        } finally {
            typedArray.recycle();
        }

        mLeftIv = view.findViewById(R.id.left_icon);
        mRightIv = view.findViewById(R.id.right_icon);
        mCenterContainer = view.findViewById(R.id.center_container);

        if (this.mLeftIcon != 0) {
            mLeftIv.setImageResource(this.mLeftIcon);
            mLeftIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leftIconClickListener != null) {
                        leftIconClickListener.leftIconClick();
                    }
                }
            });
        } else {
            ViewUtil.setViewGone(mLeftIv);
        }
        if (this.mRightIcon != 0) {
            mRightIv.setImageResource(this.mRightIcon);
            mRightIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightIconClickListener != null) {
                        rightIconClickListener.rightIconClick();
                    }
                }
            });
        } else {
            ViewUtil.setViewVisible(mRightIv);
        }
        if (mTitleMode == MODE_TITLE && mTitleText != 0) {
            setTitleText(mTitleText);
        }


        setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * 设置右边图标
     *
     * @param resourcesId
     */
    public void setLeftDrawable(int resourcesId) {
        this.mLeftIcon = resourcesId;
        if (this.mLeftIv != null) {
            this.mLeftIv.setImageResource(this.mLeftIcon);
        }
    }

    /**
     * 设置右边图标
     *
     * @param resourcesId
     */
    public void setRightDrawable(int resourcesId) {
        this.mRightIcon = resourcesId;
        if (this.mRightIv != null) {
            this.mRightIv.setImageResource(this.mRightIcon);
        }
    }

    /**
     * 设置title文字
     *
     * @param resourcesId
     */
    public void setTitleText(int resourcesId) {
        mTitleMode = MODE_TITLE;
        this.mTitleText = resourcesId;
        if (mTitleTv == null) {
            mCenterContainer.removeAllViews();
            mTitleTv = new TextView(mContext);
            mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
            mTitleTv.setTextColor(0xFF030303);
            mCenterContainer.addView(mTitleTv);
        }
        mTitleTv.setText(getResources().getString(this.mTitleText));
    }

    /**
     * 设置title颜色
     *
     * @param resourcesId
     */
    public void setTitleColor(int resourcesId) {
        if (mTitleTv != null) {
            mTitleTv.setTextColor(resourcesId);
        }
    }

    /**
     * title位置添加TabLayout控件
     *
     * @param view
     */
    public void addTabLayout(View view) {
        mTitleMode = MODE_TAB;
        mCenterContainer.removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        mCenterContainer.addView(view);
    }

    /**
     * 左边按钮监听事件
     *
     * @param leftIconClickListener
     */
    public void setLeftIconClickListener(LeftIconClickListener leftIconClickListener) {
        this.leftIconClickListener = leftIconClickListener;
    }

    /**
     * 右边按钮监听事件
     *
     * @param rightIconClickListener
     */
    public void setRightIconClickListener(RightIconClickListener rightIconClickListener) {
        this.rightIconClickListener = rightIconClickListener;
    }

    public interface LeftIconClickListener {
        /**
         * 左边按钮被点击
         */
        void leftIconClick();
    }

    public interface RightIconClickListener {
        /**
         * 右边按钮被点击
         */
        void rightIconClick();
    }


}
