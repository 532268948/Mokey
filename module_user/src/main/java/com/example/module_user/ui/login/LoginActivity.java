package com.example.module_user.ui.login;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.util.StatusBarUtil;
import com.example.module_user.Presenter.LoginPresenter;
import com.example.module_user.R;
import com.example.module_user.contract.LoginContract;

/**
 * @author tianhuaye
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter<LoginContract.View>> implements LoginContract.View {


    @Override
    protected LoginPresenter<LoginContract.View> createPresenter() {
        return new LoginPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.transport),255);
    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
