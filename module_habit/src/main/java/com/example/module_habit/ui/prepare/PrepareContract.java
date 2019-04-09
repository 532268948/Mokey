package com.example.module_habit.ui.prepare;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.db.entity.Alarm;
import com.example.module_habit.bean.PrepareBean;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/23 13:42
 * description:
 */
public interface PrepareContract {

    interface View extends BaseView {
        void setPrepareAlarmList(List<Alarm> alarmList);
        void setAlarmSuccess();
    }

    interface Presenter {

        void getSleepPrepareAlarm();

        void setSleepPrepareAlarm(List<PrepareBean> list);
    }
}
