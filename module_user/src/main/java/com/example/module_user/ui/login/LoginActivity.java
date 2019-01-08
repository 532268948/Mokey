package com.example.module_user.ui.login;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.SoftKeyboardUtil;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_user.Presenter.LoginPresenter;
import com.example.module_user.R;
import com.example.module_user.contract.LoginContract;

/**
 * @author tianhuaye
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter<LoginContract.View>> implements LoginContract.View {

    private ScrollView mScrollView;
    private FrameLayout mHeadContainerFl;
    private LinearLayout mInputContainerLl;

    @Override
    protected LoginPresenter<LoginContract.View> createPresenter() {
        return new LoginPresenter<>();
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
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mScrollView = findViewById(R.id.scroll_view);
        mHeadContainerFl = findViewById(R.id.fl_head_container);
        mInputContainerLl = findViewById(R.id.ll_input_container);
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
                            mHeadContainerFl.setVisibility(View.GONE);
                        } else {
                            mHeadContainerFl.setVisibility(View.VISIBLE);
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
