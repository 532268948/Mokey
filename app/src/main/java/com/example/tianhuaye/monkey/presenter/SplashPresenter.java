package com.example.tianhuaye.monkey.presenter;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.bean.ResponseWrapper;
import com.example.lib_common.base.bean.response.LoginItem;
import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.User;
import com.example.lib_common.common.Constant;
import com.example.lib_common.http.MonkeyApiService;
import com.example.tianhuaye.monkey.contract.SplashContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 13:57
 * @description:
 */
public class SplashPresenter<V extends BaseView> extends BasePresenter<V> implements SplashContract.Presenter {
    @Override
    public void getUserInformation(String token) {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi().getUserInformation(token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<LoginItem>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<LoginItem> loginItemResponseWrapper) {
                        if (loginItemResponseWrapper.getCode() != null && loginItemResponseWrapper.getCode() == Constant.httpCode.SUCCESS) {
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
                                    }
                                });
                            } else {

                            }
                        }
                    }
                }));
    }
}
