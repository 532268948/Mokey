package com.example.lib_common.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/29
 * @time : 11:19
 * @email : 15869107730@163.com
 * @note :
 */
public class SquareLinearLayout extends LinearLayout {
    public SquareLinearLayout(Context context) {
        this(context, null);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
