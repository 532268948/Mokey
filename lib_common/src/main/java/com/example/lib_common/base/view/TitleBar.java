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
    private int mRightText;
    private int mBottomMode;

    private ImageView mLeftIv;
    private ImageView mRightIv;
    private TextView mRightTv;
    private View mBottomView;
    private FrameLayout mCenterContainer;

    private TextView mTitleTv;

    private LeftIconClickListener leftIconClickListener;
    private RightIconClickListener rightIconClickListener;
    private RightTextClickListener rightTextClickListener;

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
            mBottomMode = typedArray.getInteger(R.styleable.TitleBar_bottom_line_visible, 0);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_text_size, 50);
            mRightText = typedArray.getResourceId(R.styleable.TitleBar_right_text, 0);
        } finally {
            typedArray.recycle();
        }

        mLeftIv = view.findViewById(R.id.left_icon);
        mRightIv = view.findViewById(R.id.right_icon);
        mRightTv = view.findViewById(R.id.right_text);
        mCenterContainer = view.findViewById(R.id.center_container);
        mBottomView = view.findViewById(R.id.bt_line);

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
        if (this.mRightText != 0) {
            mRightTv.setText(mRightText);
            mRightTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightTextClickListener != null) {
                        rightTextClickListener.rightTextClick();
                    }
                }
            });
        } else {
            ViewUtil.setViewGone(mRightTv);
        }
        if (mTitleMode == MODE_TITLE && mTitleText != 0) {
            setTitleText(mTitleText);
            if (mTextSize != 0) {
                mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
        }

        if (mBottomMode == 0) {
            ViewUtil.setViewGone(mBottomView);
        } else if (mBottomMode == 1) {
            ViewUtil.setViewVisible(mBottomView);
        }
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
            mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
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
        if (mLeftIv != null && this.leftIconClickListener != null) {
            mLeftIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TitleBar.this.leftIconClickListener.leftIconClick();
                }
            });
        }
    }

    /**
     * 右边按钮监听事件
     *
     * @param rightIconClickListener
     */
    public void setRightIconClickListener(RightIconClickListener rightIconClickListener) {
        this.rightIconClickListener = rightIconClickListener;
        if (mRightIv != null && this.rightIconClickListener != null) {
            mRightIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TitleBar.this.rightIconClickListener.rightIconClick();
                }
            });
        }
    }

    public void setRightTextClickListener(RightTextClickListener rightTextClickListener) {
        this.rightTextClickListener = rightTextClickListener;
        if (mRightTv != null && this.rightTextClickListener != null) {
            mRightTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TitleBar.this.rightTextClickListener.rightTextClick();
                }
            });
        }
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

    public interface RightTextClickListener {
        /**
         * 右边文字被点击
         */
        void rightTextClick();
    }

}
