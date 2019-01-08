package com.example.module_user.ui.login;

import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.SoftKeyboardUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.lib_common.util.ViewUtil;
import com.example.module_user.Presenter.LoginPresenter;
import com.example.module_user.R;
import com.example.module_user.contract.LoginContract;
import com.example.module_user.ui.register.RegisterActivity;

/**
 * @author tianhuaye
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter<LoginContract.View>> implements LoginContract.View {

    private ScrollView mScrollView;
    private FrameLayout mHeadContainerFl;
    private LinearLayout mInputContainerLl;
    private TextView mRegisterTv;

    @Override
    protected LoginPresenter<LoginContract.View> createPresenter() {
        return new LoginPresenter<>();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    @Override
    public void initUIParams() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.transport), 255);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mScrollView = findViewById(R.id.scroll_view);
        mHeadContainerFl = findViewById(R.id.fl_head_container);
        mInputContainerLl = findViewById(R.id.ll_input_container);
        mRegisterTv = findViewById(R.id.tv_register);
    }

    @Override
    public void initListener() {
        mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //判断现在软键盘的开关状态
                        if (SoftKeyboardUtil.isSoftShowing(LoginActivity.this)) {
                            ViewUtil.setViewGone(mHeadContainerFl);
                        } else {
                            ViewUtil.setViewVisible(mHeadContainerFl);
                        }
                    }
                }, 100L);
            }
        });
        mRegisterTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
}
