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
import com.example.lib_common.util.ToastUtil;

/**
 * author: tianhuaye
 * date:   2018/11/12 16:54
 * description:
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements BaseView {

    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isLoad = false;
    public T presenter;


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
        presenter = createPresenter();
        presenter.attachView((V) this, mContext);
        setHasOptionsMenu(true);
    }

    protected abstract T createPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container);
        }
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

    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        releaseCache();
    }

    public void releaseCache() {

    }

    @Override
    public void showError(String message) {
        ToastUtil.showShortToastMessage(message);
    }
}
