package com.example.tianhuaye.monkey.presenter;

import android.text.TextUtils;

import com.example.lib_common.base.BaseObserver;
import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.LoginItem;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.lib_common.db.entity.User;
import com.example.lib_common.common.Constant;
import com.example.lib_common.http.MonkeyApiService;
import com.example.tianhuaye.monkey.contract.SplashContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 13:57
 * @description:
 */
public class SplashPresenter<V extends SplashContract.View> extends BasePresenter<V> implements SplashContract.Presenter {
    @Override
    public void getUserInformation(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        addDisposable(MonkeyApiService.getInstance(context.get()).getMonkeyApi().getUserInformation(token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<LoginItem>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<LoginItem> loginItemResponseWrapper) {
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
                                    }
                                });
                            }
                        }
                    }
                }));
    }

    @Override
    public void initFirst() {
        //初始化闹钟信息
        List<Alarm> alarmList = new ArrayList<>();
        //三个基础闹钟
        Alarm alarm1 = new Alarm(Constant.Alarm.ALARM_ID_ONE, 7, 30, Constant.Alarm.ALARM_MODE_ONCE, null, null, Constant.Alarm.ALARM_TYPE_ONE, false);
        Alarm alarm2 = new Alarm(Constant.Alarm.ALARM_ID_TWO, 7, 30, Constant.Alarm.ALARM_MODE_ONCE, null, null, Constant.Alarm.ALARM_TYPE_ONE, false);
        Alarm alarm3 = new Alarm(Constant.Alarm.ALARM_ID_THREE, 7, 30, Constant.Alarm.ALARM_MODE_ONCE, null, null, Constant.Alarm.ALARM_TYPE_ONE, false);
        alarmList.add(alarm1);
        alarmList.add(alarm2);
        alarmList.add(alarm3);
        //入睡提醒闹钟
        Alarm alarm4 = new Alarm(Constant.Alarm.ALARM_ID_FIVE, 22, 30, Constant.Alarm.ALARM_MODE_ONCE, null, null, Constant.Alarm.ALARM_TYPE_THREE, false);
        alarmList.add(alarm4);
        //晨起闹钟
        Alarm alarm5 = new Alarm(Constant.Alarm.ALARM_ID_FOUR, 7, 30, Constant.Alarm.ALARM_MODE_ONCE, null, null, Constant.Alarm.ALARM_TYPE_FOUR, false);
        alarmList.add(alarm5);
        DBManager.getInstance(context.get()).getAlarmDB().insertBatchAlarm(alarmList);
    }
}
