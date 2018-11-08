package com.example.tianhuaye.monkey.base;

import android.content.Context;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * project: ModuleDemo
 * author : 叶天华
 * date   : 2018/10/14
 * time   : 13:02
 * email  : 15869107730@163.com
 * note   :
 */
public class BasePresenter<V> {


    protected WeakReference<V> view;
    protected WeakReference<Context> context;

    private CompositeDisposable compositeDisposable;

    /**
     * disposable管理
     *
     * @param disposable
     */
    protected void addDiaposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * view，context绑定
     * @param view
     * @param context
     */
    protected void attachView(V view, Context context) {
        this.view = new WeakReference<V>(view);
        this.context = new WeakReference<Context>(context);
    }

    /**
     * view,context,compositeDisposable解绑
     */
    public void detachView(){
        this.view.clear();
        this.context.clear();
        if (this.compositeDisposable!=null){
            this.compositeDisposable.clear();
        }
    }
}
