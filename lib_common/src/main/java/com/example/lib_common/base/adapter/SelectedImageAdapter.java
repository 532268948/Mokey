package com.example.lib_common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lib_common.R;

import java.io.File;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/21 17:41
 * @description:
 */
public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder> {

    private List<File> mSelected;
    private Context mContext;
    private ItemTouchHelper mItemTouchHelper;
    private int IMAGE_SIZE = 9;
    private Boolean isMaxSize = false;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 9;
//        return mSelected == null ? 0 : mSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAddIv;
        private ImageView mDeleteIv;
        private ImageView mPhtotIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddIv = itemView.findViewById(R.id.iv_add);
            mPhtotIv = itemView.findViewById(R.id.iv_photo);
            mDeleteIv = itemView.findViewById(R.id.iv_delete);
        }
    }
}
