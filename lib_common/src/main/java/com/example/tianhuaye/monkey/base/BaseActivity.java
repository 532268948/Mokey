package com.example.tianhuaye.monkey.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lib_common.R;
import com.example.tianhuaye.monkey.base.inter.ILifeProcessor;
import com.example.tianhuaye.monkey.util.ActivityManager;
import com.example.tianhuaye.monkey.util.ToastUtil;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:02
 * email  : 15869107730@163.com
 * note   :
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends AppCompatActivity implements ILifeProcessor, BaseView {

    private T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityManager.getInstance().addActivity(this);
        initIntent(getIntent());
        initSaveInstanceState(savedInstanceState);
        if (generateIdLayout() > 0) {
            setContentView(generateIdLayout());
        } else if (generateViewLayout() != null) {
            setContentView(generateViewLayout());
        }
        mPresenter = createPresenter();
        mPresenter.attachView((V) this, this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCache();
        ActivityManager.getInstance().removeActivity(this);
        mPresenter.detachView();
    }

    protected abstract T createPresenter();

    @Override
    public void showError(String message) {
        ToastUtil.showShortToastMessage(message);
    }

    @Override
    public void initIntent(Intent intent) {

    }

    @Override
    public void initSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public int generateIdLayout() {
        return 0;
    }

    @Override
    public View generateViewLayout() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void releaseCache() {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String message, String code) {

    }
}
