package com.example.module_habit.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.db.entity.Alarm;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:31
 * @description:
 */
public interface AlarmSettingContract {

    interface View extends BaseView {
    }

    interface Presenter {

        void saveAlarmToDB(int alarm_mode, int hour, int minute, String ringPath, String tip);
    }
}
