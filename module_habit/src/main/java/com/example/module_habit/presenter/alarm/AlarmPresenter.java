package com.example.module_habit.presenter.alarm;

import com.example.lib_common.base.BasePresenter;
import com.example.lib_common.common.Constant;
import com.example.lib_common.db.DBManager;
import com.example.lib_common.db.DbOperateListener;
import com.example.lib_common.db.entity.Alarm;
import com.example.module_habit.contract.AlarmContract;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/14 13:03
 * description:
 */
public class AlarmPresenter<V extends AlarmContract.View> extends BasePresenter<V> implements AlarmContract.Presenter {

    @Override
    public void getBaseAlarm() {
        view.get().showDialog("");
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereTypeAlarm(Constant.Alarm.ALARM_TYPE_ONE, new DbOperateListener.OnQueryAllListener<Alarm>() {
            @Override
            public void onQueryAllBatchListener(List<Alarm> list) {
                if (list != null) {
                    view.get().showBaseAlarm(list);
                }
                view.get().dismissDialog();
            }
        });
    }

    @Override
    public void changeAlarmOpenState(long id, final boolean open) {
        view.get().showDialog(null);
        DBManager.getInstance(context.get()).getAlarmDB().queryWhereAlarm(id, new DbOperateListener.OnQuerySingleListener<Alarm>() {
            @Override
            public void onQuerySingleListener(Alarm entry) {
                if (entry!=null){
                    if (entry.getOpen()!=open){
                        entry.setOpen(!entry.getOpen());
                        DBManager.getInstance(context.get()).getAlarmDB().updateSingleAlarm(entry, new DbOperateListener.OnUpdateListener() {
                            @Override
                            public void onUpdateListener(boolean type) {
                                view.get().dismissDialog();
                            }
                        });
                    }else {
                        view.get().dismissDialog();
                    }
                }
            }
        });
    }

}
