package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.R;
import com.example.lib_common.util.ViewUtil;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/25 17:18
 * @description:
 */
public class NinePictureAdapter extends RecyclerView.Adapter<NinePictureAdapter.ViewHolder> {

    private int IMAGE_SIZE = 9;
    private Boolean isMaxSize = false;
    private Context mContext;
    private List<String> mImageList;
    private MoreButtonClickListener moreButtonClickListener;
    private OnPictureClickListener onPictureClickListener;

    public NinePictureAdapter(List<String> imageList, Context context) {
        this.mImageList = imageList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nine_picture_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mImageList != null) {
            //超出最大图片数量
            if (isMaxSize) {
                //最后一项
                if (i + 1 == getItemCount()) {
                    ViewUtil.setViewVisible(viewHolder.mMoreTv);
                    viewHolder.mMoreTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (moreButtonClickListener != null) {
                                moreButtonClickListener.onClick();
                            }
                        }
                    });
                } else {
                    ViewUtil.setViewGone(viewHolder.mMoreTv);
                    viewHolder.mPictureIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onPictureClickListener != null) {
                                onPictureClickListener.onClick();
                            }
                        }
                    });
                }
            } else {

            }
        }
    }

    @Override
    public int getItemCount() {
        if (mImageList == null) {
            isMaxSize = false;
            return 0;
        }
        if (mImageList.size() <= IMAGE_SIZE) {
            isMaxSize = false;
            return mImageList.size();
        }
        if (mImageList.size() > IMAGE_SIZE) {
            isMaxSize = true;
            return IMAGE_SIZE;
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPictureIv;
        private TextView mMoreTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPictureIv = itemView.findViewById(R.id.iv_picture);
            mMoreTv = itemView.findViewById(R.id.tv_more);
        }

    }

    public void setMoreClickListener(MoreButtonClickListener moreClickListener) {
        this.moreButtonClickListener = moreClickListener;
    }

    public void setPictureClickListener(OnPictureClickListener onPictureClickListener) {
        this.onPictureClickListener = onPictureClickListener;
    }

    public interface MoreButtonClickListener {
        void onClick();
    }

    public interface OnPictureClickListener {
        void onClick();
    }
}
