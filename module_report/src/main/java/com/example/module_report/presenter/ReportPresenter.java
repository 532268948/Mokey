package com.example.module_report.presenter;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.SleepBean;
import com.example.lib_common.bean.response.UserSleepBean;
import com.example.lib_common.common.Constant;
import com.example.lib_common.http.MonkeyApiService;
import com.example.lib_common.bean.ReportBean;
import com.example.module_report.contract.ReportContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 10:39
 * @description:
 */
public class ReportPresenter<V extends ReportContract.View> extends BasePresenter<V> implements ReportContract.Presenter {
    @Override
    public void getUserSleepData() {
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi()
                .getUserSleepData(Constant.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<UserSleepBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<UserSleepBean> userSleepBeanResponseWrapper) {
                        super.onNext(userSleepBeanResponseWrapper);
                        if (userSleepBeanResponseWrapper.getCode() == Constant.HttpCode.SUCCESS) {
                            UserSleepBean userSleepBean = userSleepBeanResponseWrapper.getData();
                            if (userSleepBean != null) {
                                if (userSleepBean.getSleepBeans() != null) {
                                    List<ReportBean> reportBeans = new ArrayList<>();
                                    for (SleepBean sleepBean : userSleepBean.getSleepBeans()) {
                                        reportBeans.add(new ReportBean(sleepBean, userSleepBean.getNickname()));
                                    }
                                    view.get().showUserSleepData(reportBeans);
                                }
                            } else {
                                view.get().showEmpty();
                            }
                        } else {
                            view.get().showEmpty();
                        }
                    }

                }));
    }
}
