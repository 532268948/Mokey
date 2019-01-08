package com.example.tianhuaye.monkey.ui.splash;

import android.view.View;

import com.example.lib_common.base.activity.BaseActivity;
import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.contract.SplashContract;
import com.example.tianhuaye.monkey.presenter.SplashPresenter;


/**
 * @author tianhuaye
 */
public class SplashActivity extends BaseActivity<SplashContract.View, SplashPresenter<SplashContract.View>> implements SplashContract.View {


    @Override
    protected SplashPresenter<SplashContract.View> createPresenter() {
        return new SplashPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int generateIdLayout() {
        return R.layout.activity_splash;
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
