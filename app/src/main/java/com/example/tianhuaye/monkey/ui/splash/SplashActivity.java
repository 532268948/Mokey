package com.example.tianhuaye.monkey.ui.splash;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lib_common.base.ExecutorThreadService;
import com.example.lib_common.base.activity.BaseActivity;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.SharedPreferencesUtil;
import com.example.tianhuaye.monkey.R;
import com.example.tianhuaye.monkey.contract.SplashContract;
import com.example.tianhuaye.monkey.presenter.SplashPresenter;
import com.example.tianhuaye.monkey.ui.activity.MainActivity;


/**
 * @author tianhuaye
 */
public class SplashActivity extends BaseActivity<SplashContract.View, SplashPresenter<SplashContract.View>> implements SplashContract.View {

    /**
     * 是否第一次打开app
     */
    private boolean first=false;

    @Override
    protected SplashPresenter<SplashContract.View> createPresenter() {
        return new SplashPresenter<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initUIParams() {
        super.initUIParams();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        ExecutorThreadService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constant.SPLASH_DURING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gotoMainActivity();
            }
        });
        sharedPreferencesUtil = new SharedPreferencesUtil(this, "monkey");
        Constant.USER_ID = (Long) sharedPreferencesUtil.getSharedPreference("user_id", 0L);
        Constant.TOKEN = (String) sharedPreferencesUtil.getSharedPreference("token", "");
        //获取用户信息
        mPresenter.getUserInformation(Constant.TOKEN);
        first=(Boolean)sharedPreferencesUtil.getSharedPreference("first",true);
        if (first){
            mPresenter.initFirst();
            updateFirst();
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateFirst() {
        sharedPreferencesUtil.put("first",false);
    }
}
