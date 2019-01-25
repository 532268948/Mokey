package com.example.lib_common.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.lib_common.R;

/**
 * @author: tianhuaye
 * @date: 2019/1/25 16:52
 * @description: 社区九宫格图片查看
 */
public class NinePictureView extends RecyclerView {

    private int SPAN_COUNT = 3;

    private Context mContext;

    public NinePictureView(@NonNull Context context) {
        this(context, null);
    }

    public NinePictureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePictureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NinePictureView);
        if (typedArray == null) {
            return;
        }
        try {
//            mDeleteIcon = typedArray.getResourceId(R.styleable.ImageSelectView_delete_icon, 0);
//            mAddIcon = typedArray.getResourceId(R.styleable.ImageSelectView_add_icon, 0);
            SPAN_COUNT = typedArray.getInteger(R.styleable.NinePictureView_span_count, 3);
        } finally {
            typedArray.recycle();
        }
    }
}
