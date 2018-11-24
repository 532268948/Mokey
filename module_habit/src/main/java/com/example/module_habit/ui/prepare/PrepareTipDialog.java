package com.example.module_habit.ui.prepare;

import android.view.View;
import android.widget.ImageView;

import com.example.lib_common.base.dialog.BaseDialogFragment;
import com.example.lib_common.base.dialog.DialogViewHolder;
import com.example.module_habit.R;

/**
 * author: tianhuaye
 * date:   2018/11/23 14:02
 * description:
 */
public class PrepareTipDialog extends BaseDialogFragment {
    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_prepare_tip;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        ((ImageView)holder.getView(R.id.iv_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
