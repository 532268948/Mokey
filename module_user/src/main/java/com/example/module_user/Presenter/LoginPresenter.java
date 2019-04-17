package com.example.module_user.Presenter;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.LoginItem;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.User;
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
public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter {
    @Override
    public void login(String name, String password) {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<LoginItem>>(view.get()) {
                    @Override
                    public void onNext(final ResponseWrapper<LoginItem> loginItemResponseWrapper) {
                        super.onNext(loginItemResponseWrapper);
                        if (loginItemResponseWrapper.getCode() != null && loginItemResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            if (loginItemResponseWrapper.getData() != null && loginItemResponseWrapper.getData().getId() != null) {
                                final User user = new User();
                                user.setId(loginItemResponseWrapper.getData().getId());
                                user.setName(loginItemResponseWrapper.getData().getName());
                                user.setHead(loginItemResponseWrapper.getData().getHead());
                                user.setGender(loginItemResponseWrapper.getData().getGender());
                                user.setNickname(loginItemResponseWrapper.getData().getNickname());
                                user.setPhone(loginItemResponseWrapper.getData().getPhone());
                                user.setLoginTime(System.currentTimeMillis());
                                DBManager.getInstance(context.get()).getUserDB().queryWhereUser(user.getId(), new DbOperateListener.OnQuerySingleListener() {
                                    @Override
                                    public void onQuerySingleListener(Object entry) {
                                        if (entry != null) {
                                            DBManager.getInstance(context.get()).getUserDB().updateSingleUser(user);
                                        } else {
                                            DBManager.getInstance(context.get()).getUserDB().insertUser(user);
                                        }
                                        view.get().loginSuccess(loginItemResponseWrapper.getData());
                                    }
                                });
                            }
                        } else {
                            view.get().showError(loginItemResponseWrapper.getMsg());
                        }
                    }
                }));
    }
}
