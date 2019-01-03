package com.example.module_report.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * @author: tianhuaye
 * @date: 2019/1/3 13:12
 * @description:
 */
public class ReportView extends CardView {

    public ReportView(@NonNull Context context) {
        this(context,null);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReportView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
