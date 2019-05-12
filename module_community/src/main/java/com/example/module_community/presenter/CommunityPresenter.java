package com.example.module_community.presenter;

import android.util.Log;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.FirstPageBean;
import com.example.lib_common.http.BiliBiliApiService;
import com.example.module_community.contract.CommunityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 15:30
 * @description:
 */
public class CommunityPresenter<V extends CommunityContract.View> extends BasePresenter<V> implements CommunityContract.Presenter {
    @Override
    public void getFirstPageMessage() {
        BiliBiliApiService.getLiveAPI().getLiveAppIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResponseWrapper<FirstPageBean>>(view.get()){
                    @Override
                    public void onNext(ResponseWrapper<FirstPageBean> firstPageBeanResponseWrapper) {
                        super.onNext(firstPageBeanResponseWrapper);
                        Log.e("CommunityPresenter", "onNext: ");
                        view.get().getDataSuccess(firstPageBeanResponseWrapper.getData());
                    }
                });

    }
}
