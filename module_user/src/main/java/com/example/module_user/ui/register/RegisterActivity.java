package com.example.module_user.ui.register;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.SoftKeyboardUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_user.Presenter.RegisterPresenter;
import com.example.module_user.R;
import com.example.module_user.contract.RegisterContract;

/**
 * @author tianhuaye
 */
public class RegisterActivity extends BaseActivity<RegisterContract.View,RegisterPresenter<RegisterContract.View>> implements RegisterContract.View {

    private LinearLayout mParentLl;
    private LinearLayout mBackContainerLl;

    @Override
    protected RegisterPresenter<RegisterContract.View> createPresenter() {
        return new RegisterPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.transport), 255);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mParentLl=findViewById(R.id.ll_parent);
        mBackContainerLl=findViewById(R.id.ll_back_container);
    }

    @Override
    public void initListener() {
        mParentLl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mParentLl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //判断现在软键盘的开关状态
                        if (SoftKeyboardUtil.isSoftShowing(RegisterActivity.this)) {
                            ViewUtil.setViewGone(mBackContainerLl);
                        } else {
                            ViewUtil.setViewVisible(mBackContainerLl);
                        }
                    }
                }, 100L);
            }
        });
    }

    @Override
    public void initData() {

    }
}
