package com.example.lib_common.base;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

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

    public BaseObserver(BaseView view) {
        this.mView = view;
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = "未知错误";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() >= 500 && httpException.code() < 600) {
                errorMsg = "服务器处理请求出错";
            } else if (httpException.code() >= 400 && httpException.code() < 500) {
                errorMsg = "服务器无法处理请求";
            } else if (httpException.code() >= 300 && httpException.code() < 400) {
                errorMsg = "请求被重定向到其他页面";
            }
        } else if (e instanceof ParseException || e instanceof JSONException
                || e instanceof JSONException) {
            errorMsg = "数据解析错误";
        }
        mView.showError(errorMsg);
    }

    @Override
    public void onComplete() {

    }
}

