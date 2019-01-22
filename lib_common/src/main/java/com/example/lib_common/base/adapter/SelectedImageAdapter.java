package com.example.lib_common.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lib_common.R;
import com.example.lib_common.util.ViewUtil;

import java.io.File;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/21 17:41
 * @description:
 */
public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder> {

    private int IMAGE_SIZE = 9;
    private Boolean isMaxSize = false;
    private Context mContext;
    private List<File> mSelected;
    private ItemTouchHelper mItemTouchHelper;
    private AddButtonClick addButtonClick;

    public SelectedImageAdapter(Context context, List<File> mSelected, ItemTouchHelper itemTouchHelper) {
        this.mContext = context;
        this.mSelected = mSelected;
        this.mItemTouchHelper = itemTouchHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_add, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        if (mSelected != null) {
            //未到达最大数量
            if (mSelected.size() != IMAGE_SIZE) {
                //当前为最后一张，则显示添加图片
                if (i + 1 == getItemCount()) {
                    ViewUtil.setViewVisible(viewHolder.mAddIv);
                    ViewUtil.setViewGone(viewHolder.mDeleteIv);
                    ViewUtil.setViewGone(viewHolder.mPhotoIv);
                    viewHolder.mAddIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (addButtonClick != null) {
                                addButtonClick.onAddButtonClick(IMAGE_SIZE - mSelected.size());
                            }
                        }
                    });
                } else {
                    ViewUtil.setViewGone(viewHolder.mAddIv);
                    ViewUtil.setViewVisible(viewHolder.mDeleteIv);
                    ViewUtil.setViewVisible(viewHolder.mPhotoIv);
                    if (mSelected.get(i) != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(mSelected.get(i).getPath());
                        viewHolder.mPhotoIv.setImageBitmap(bitmap);
                        viewHolder.mPhotoIv.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                if (mItemTouchHelper != null) {
                                    mItemTouchHelper.startDrag(viewHolder);
                                }
                                return false;
                            }
                        });
                        viewHolder.mDeleteIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteImage(viewHolder.getPosition());
                            }
                        });
                    }
                }
            }else {
                ViewUtil.setViewGone(viewHolder.mAddIv);
                ViewUtil.setViewVisible(viewHolder.mDeleteIv);
                ViewUtil.setViewVisible(viewHolder.mPhotoIv);
                if (mSelected.get(i) != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(mSelected.get(i).getPath());
                    viewHolder.mPhotoIv.setImageBitmap(bitmap);
                    viewHolder.mPhotoIv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (mItemTouchHelper != null) {
                                mItemTouchHelper.startDrag(viewHolder);
                            }
                            return false;
                        }
                    });
                    viewHolder.mDeleteIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteImage(viewHolder.getPosition());
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mSelected == null || mSelected.size() == 0) {
            isMaxSize = false;
            return 1;
        }
        if (mSelected.size() > 0 && mSelected.size() < IMAGE_SIZE) {
            isMaxSize = false;
            return mSelected.size() + 1;
        }
        if (mSelected.size() == IMAGE_SIZE) {
            isMaxSize = true;
            return IMAGE_SIZE;
        }
        if (mSelected.size() > IMAGE_SIZE) {
            Toast.makeText(mContext, "图片数量不能大于" + IMAGE_SIZE + "张", Toast.LENGTH_SHORT).show();
            mSelected.clear();
            isMaxSize = false;
            return 0;
        }
        isMaxSize = false;
        return 0;
    }

    /**
     * 添加按钮监听
     *
     * @param addButtonClick
     */
    public void setAddButtonClickListener(AddButtonClick addButtonClick) {
        this.addButtonClick = addButtonClick;
    }

    public interface AddButtonClick {
        /**
         * @param size 剩余应添加的图片数量
         */
        void onAddButtonClick(int size);
    }

    /**
     * 删除照片
     *
     * @param position
     */
    private void deleteImage(int position) {
        if (mSelected != null) {
            if (mSelected.size() >= position + 1) {
                if (mSelected.get(position) != null) {
                    mSelected.remove(position);
                    this.notifyItemRemoved(position);
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAddIv;
        private ImageView mDeleteIv;
        private ImageView mPhotoIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddIv = itemView.findViewById(R.id.iv_add);
            mPhotoIv = itemView.findViewById(R.id.iv_photo);
            mDeleteIv = itemView.findViewById(R.id.iv_delete);
        }
    }
}
