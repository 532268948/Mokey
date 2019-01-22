package com.example.module_user.Presenter;

import android.util.Log;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.bean.ResponseWrapper;
import com.example.lib_common.base.bean.response.LoginItem;
import com.example.lib_common.base.db.DaoFactory;
import com.example.lib_common.base.db.entity.User;
import com.example.lib_common.common.Constant;
import com.example.lib_common.http.MonkeyApiService;
import com.example.module_user.contract.LoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/7 19:15
 * @description:
 */
public class LoginPresenter<V extends BaseView> extends BasePresenter<V> implements LoginContract.Presenter {
    @Override
    public void login(String name, String password) {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<LoginItem>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<LoginItem> loginItemResponseWrapper) {
                        Log.e("LoginPresenter", "onNext: 2  " + Thread.currentThread().getName());
                        if (loginItemResponseWrapper.getCode() == Constant.httpCode.SUCCESS) {
                            if (loginItemResponseWrapper.getData() != null && loginItemResponseWrapper.getData().getId() != null) {
                                User user = new User();
                                user.setId(loginItemResponseWrapper.getData().getId());
                                user.setName(loginItemResponseWrapper.getData().getName());
                                user.setHead(loginItemResponseWrapper.getData().getHead());
                                user.setNickname(loginItemResponseWrapper.getData().getNickname());
                                user.setPhone(loginItemResponseWrapper.getData().getPhone());
                                user.setLoginTime(System.currentTimeMillis());
                                DaoFactory.getInstance().getUserDB().updateSingle(User.class, user);
                            }
                        }
                    }
                }));
    }
}
