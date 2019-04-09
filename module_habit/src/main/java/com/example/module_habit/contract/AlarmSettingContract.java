package com.example.module_habit.contract;

import com.example.lib_common.base.BaseView;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:31
 * @description:
 */
public interface AlarmSettingContract {

    interface View extends BaseView {
    }

    interface Presenter {

        void saveAlarmToDB(int alarm_mode,long id,int type, int hour, int minute, String ringPath, String tip);
    }
}
