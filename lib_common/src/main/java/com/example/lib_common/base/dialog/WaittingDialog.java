package com.example.lib_common.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lib_common.R;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/22
 * @time : 16:56
 * @email : 15869107730@163.com
 * @note :
 */
public class WaittingDialog {

    private Dialog mDialog;
    private TextView mTitleTv;
    private ImageView mProgressIv;
    private Animation mLoadAnim;

    public WaittingDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        try {
            mLoadAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_waitting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_waitting, null);
        mTitleTv = view.findViewById(R.id.title_tv);
        mProgressIv = view.findViewById(R.id.progress);
        mDialog = new Dialog(context, R.style.waitting_dialog);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        Window window = mDialog.getWindow();
        if (window != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setContentView(view, params);
        }
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    public void showWaitingDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            displayAnim();
            mDialog.show();
        }
    }

    public void showWaitingDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            showWaitingDialog();
        }

        if (mDialog != null && !mDialog.isShowing()) {
            mTitleTv.setText(msg);
            displayAnim();
            mDialog.show();
        }
    }

    public void hideWaitingDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            try {
                stopAnim();
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayAnim() {
        if (mProgressIv == null) {
            return;
        }
        mProgressIv.clearAnimation();
        mProgressIv.startAnimation(mLoadAnim);
    }

    private void stopAnim() {
        if (mProgressIv != null) {
            mProgressIv.clearAnimation();
        }
    }
}
