package com.zust.module_music.ui.select;

import android.view.View;

import com.example.lib_common.base.dialog.BaseDialogFragment;
import com.example.lib_common.base.dialog.DialogViewHolder;
import com.zust.module_music.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/23
 * @time : 16:45
 * @email : 15869107730@163.com
 * @note :
 */
public class DownloadTipDialog extends BaseDialogFragment {
    private OnSureBtnClickListener onSureBtnClickListener;
    @Override
    protected void initData(DialogViewHolder holder) {

    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_download_tip;
    }

    @Override
    public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {
        (holder.getView(R.id.tv_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSureBtnClickListener!=null){
                    onSureBtnClickListener.onSureClick();
                }
            }
        });
        (holder.getView(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void addSureBtnClickListener(OnSureBtnClickListener onSureBtnClickListener){
        this.onSureBtnClickListener=onSureBtnClickListener;
    }

    interface OnSureBtnClickListener{
        void onSureClick();
    }
}
