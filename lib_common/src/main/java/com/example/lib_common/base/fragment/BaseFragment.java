package com.example.lib_common.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.BaseView;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.MessageLooper;
import com.example.lib_common.util.MessageLooperMgr;
import com.example.lib_common.util.SharedPreferencesUtil;
import com.example.lib_common.util.ToastUtil;

/**
 * author: tianhuaye
 * date:   2018/11/12 16:54
 * description:
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements View.OnClickListener, BaseView {

    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isLoad = false;
    public T mPresenter;
    public SharedPreferencesUtil sharedPreferencesUtil;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenter = createPresenter();
        mPresenter.attachView((V) this, mContext);
        setHasOptionsMenu(true);
    }

    protected abstract T createPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container);
        }
        initListener();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isPrepared = true;
        lazyLoad();
    }


    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        if (!isLoad) {
            //loadAnimation();
            initData();
            isLoad = true;
        }
    }

    protected void onInvisible() {

    }

    /**
     * 布局导入及控件初始化
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化控件监听事件
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        onUnregisterMessageReceiver();
        releaseCache();
    }

    public void releaseCache() {

    }

    @Override
    public void showError(String message) {
        ToastUtil.showShortToastMessage(getContext(), message);
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

    @Override
    public void clearLoginInformation() {
        if (sharedPreferencesUtil == null) {
            sharedPreferencesUtil = new SharedPreferencesUtil(getContext(), "monkey");
        }
        sharedPreferencesUtil.put("user_id", "");
        sharedPreferencesUtil.put("token", "");
        Constant.USER_ID = 0L;
        Constant.TOKEN = "";

    }

    public void onRegisterMessageReceiver() {

    }

    public void onUnregisterMessageReceiver() {
        MessageLooperMgr.onUnregisterMessageReceiver(this);
    }

    /**
     * 注册监听器用此方法
     *
     * @param cmd
     * @param l
     */
    public final void registerMessageReceiver(String cmd, MessageLooper.OnMessageListener l) {
        MessageLooperMgr.registerMessageReceiver(this, cmd, l);
    }
}
