package com.example.module_habit.presenter;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.base.db.DBManager;
import com.example.lib_common.base.db.DbOperate;
import com.example.lib_common.base.db.DbOperateListener;
import com.example.lib_common.base.db.entity.Alarm;
import com.example.lib_common.common.Constant;
import com.example.module_habit.contract.SleepContract;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/17 15:48
 * @description:
 */
public class SleepPresenter<V extends SleepContract.View> extends BasePresenter<V> implements SleepContract.Presenter {
    @Override
    public void getAlarmFromDB() {
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(Constant.ALARM_ID_FOUR, new DbOperateListener.OnQuerySingleListener() {
            @Override
            public void onQuerySingleListener(Object entry) {
                if (entry != null) {
                    view.get().setAlarm((Alarm) entry);
                }
            }
        });
//        DBManager.getInstance(context.get()).getAlarmDB().queryAllAlarm(new DbOperateListener.OnQueryAllListener() {
//            @Override
//            public void onQueryAllBatchListener(List list) {
//
//            }
//        });
    }
}
