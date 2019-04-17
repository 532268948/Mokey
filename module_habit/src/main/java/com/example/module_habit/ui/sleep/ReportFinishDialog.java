package com.example.module_habit.ui.sleep;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_common.base.dialog.BaseDialogFragment;
import com.example.lib_common.base.dialog.DialogViewHolder;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.common.Constant;
import com.example.module_habit.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/11
 * @time : 15:50
 * @email : 15869107730@163.com
 * @note :
 */
public class ReportFinishDialog extends BaseDialogFragment {

    private ReportBean reportBean;

    @Override
    protected void initData(DialogViewHolder holder) {

    }

    public void setReportBean(ReportBean reportBean) {
        this.reportBean = reportBean;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_report_finish;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        holder.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getActivity().finish();
            }
        });

        holder.getView(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ARouter.getInstance().build(Constant.Activity.ACTIVITY_REPORT_DETAIL).withSerializable("report",reportBean).navigation();
                getActivity().finish();
            }
        });
    }
}
