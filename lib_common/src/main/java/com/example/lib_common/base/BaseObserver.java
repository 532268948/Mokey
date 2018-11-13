package com.example.lib_common.base;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.ResourceObserver;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:03
 * email  : 15869107730@163.com
 * note   :
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {
    private BaseView mView;
    public BaseObserver(BaseView view){
        this.mView=view;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof TimeoutException){
            mView.showError("请求超时");
        }else if (e instanceof ConnectException){
            mView.showError("连接异常");
        }else {
            e.printStackTrace();
        }
    }
    @Override
    public void onComplete() {

    }
}
