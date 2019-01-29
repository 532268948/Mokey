package com.example.module_habit.ui.prepare;

import android.view.View;

import com.example.lib_common.base.dialog.BaseDialogFragment;
import com.example.lib_common.base.dialog.DialogViewHolder;
import com.example.module_habit.R;

/**
 * @author: tianhuaye
 * date:   2018/11/23 14:02
 * description:
 */
public class PrepareTipDialog extends BaseDialogFragment {

    private int imageResId;
    private String title;
    private String tip;

    /**
     * 设置弹框信息
     *
     * @param imageResId
     * @param title
     * @param tip
     */
    public void setMessage(int imageResId, String title, String tip) {
        this.imageResId = imageResId;
        this.title = title;
        this.tip = tip;
    }

    @Override
    protected void initData(DialogViewHolder holder) {
        holder.setImageResource(R.id.iv_prepare_tip_img, imageResId);
        holder.setText(R.id.tv_prepare_tip_name, title);
        holder.setText(R.id.tv_prepare_tip, tip);
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_prepare_tip;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        (holder.getView(R.id.iv_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
