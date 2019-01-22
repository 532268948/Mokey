package com.example.lib_common.base.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.example.lib_common.R;
import com.example.lib_common.base.adapter.SelectedImageAdapter;
import com.example.lib_common.util.UriUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import top.zibin.luban.Luban;

/**
 * @author: tianhuaye
 * @date: 2019/1/21 17:29
 * @description: 图片选择控件
 */
public class ImageSelectView extends RecyclerView implements SelectedImageAdapter.AddButtonClick {

    private int SPAN_COUNT = 3;

    private int mDeleteIcon;
    private int mAddIcon;

    private List<File> mSelected;

    private AddImageClick addImageClick;
    private Context mContext;
    private SelectedImageAdapter mAdapter;

    public ImageSelectView(@NonNull Context context) {
        this(context, null);
    }

    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageSelectView);
        if (typedArray == null) {
            return;
        }
        try {
            mDeleteIcon = typedArray.getResourceId(R.styleable.ImageSelectView_delete_icon, 0);
            mAddIcon = typedArray.getResourceId(R.styleable.ImageSelectView_add_icon, 0);
            SPAN_COUNT = typedArray.getInteger(R.styleable.ImageSelectView_span_count, 3);
        } finally {
            typedArray.recycle();
        }

        mItemHelper.attachToRecyclerView(this);
        setLayoutManager(new GridLayoutManager(mContext, SPAN_COUNT));
        if (mSelected == null) {
            mSelected = new ArrayList<>();
        }
        mAdapter = new SelectedImageAdapter(mContext, mSelected, mItemHelper);
        mAdapter.setAddButtonClickListener(this);
        setAdapter(mAdapter);

    }

    /**
     * onActivityResult()方法中调用更新图片数据
     *
     * @param data
     * @param code
     */
    public void notifyDataChanged(Intent data, String code) {
        List<Uri> uriList = data.getParcelableArrayListExtra(code);
        List<String> strings = new ArrayList<>();
        for (Uri uri : uriList) {
            strings.add(UriUtil.getRealPathFromUri(mContext, uri));
        }
        try {
            if (mSelected == null) {
                mSelected = new ArrayList<>();
                mAdapter = new SelectedImageAdapter(mContext, mSelected, mItemHelper);
                mAdapter.setAddButtonClickListener(this);
                setAdapter(mAdapter);
            }
            mSelected.addAll(Luban.with(mContext).load(strings).setTargetDir(mContext.getExternalCacheDir().getAbsolutePath()).get());
            mAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置添加图片按钮的监听
     * @param addImageClick
     */
    public void setImageClickListener(AddImageClick addImageClick) {
        this.addImageClick = addImageClick;
    }

    @Override
    public void onAddButtonClick(int size) {
        if (addImageClick != null) {
            addImageClick.onAddImageClick(size);
        }
    }

    public interface AddImageClick {
        void onAddImageClick(int size);
    }

    ItemTouchHelper mItemHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mSelected, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mSelected, i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);

        }

        //重写拖拽不可用
        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }


    });
}
