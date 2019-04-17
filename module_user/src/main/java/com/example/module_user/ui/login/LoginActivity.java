package com.example.module_user.ui.login;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.bean.response.LoginItem;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.SharedPreferencesUtil;
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
@Route(path = Constant.Activity.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter<LoginContract.View>> implements LoginContract.View {

    private SharedPreferencesUtil sharedPreferencesUtil;

    private ScrollView mScrollView;
    private FrameLayout mHeadContainerFl;
    private LinearLayout mInputContainerLl;
    private TextView mLoginTv;
    private TextView mRegisterTv;
    private TextView mAccountTv;
    private TextView mPasswordTv;

    @Override
    protected LoginPresenter<LoginContract.View> createPresenter() {
        return new LoginPresenter<>();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login) {
            mPresenter.login(mAccountTv.getText().toString(), mPasswordTv.getText().toString());
            loseFocus();
            showDialog(null);
        } else if (v.getId() == R.id.tv_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
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
        mAccountTv = findViewById(R.id.et_account);
        mPasswordTv = findViewById(R.id.et_password);
        mLoginTv = findViewById(R.id.tv_login);
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
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loseFocus();
                return false;
            }
        });
        mLoginTv.setOnClickListener(this);
        mRegisterTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        sharedPreferencesUtil = new SharedPreferencesUtil(this, "monkey");
    }

    @Override
    public void loginSuccess(LoginItem loginInfo) {
        sharedPreferencesUtil.put("user_id", loginInfo.getId());
        sharedPreferencesUtil.put("token", loginInfo.getToken());
        Constant.USER_ID = loginInfo.getId();
        Constant.TOKEN = loginInfo.getToken();
        dismissDialog();
        Intent intent = new Intent();
        intent.putExtra("user", loginInfo);
        setResult(Constant.RequestAndResultCode.LOGIN_RESULT_OK, intent);
        finish();
    }

    /**
     * 输入框失去焦点
     */
    private void loseFocus() {
        mScrollView.setFocusable(true);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mScrollView.getWindowToken(), 0);
    }
}
