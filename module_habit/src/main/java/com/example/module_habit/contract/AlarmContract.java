package com.example.module_habit.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.db.entity.Alarm;

import java.util.List;

/**
 * author: tianhuaye
 * date:   2018/11/14 13:02
 * description:
 */
public interface AlarmContract {

    interface View extends BaseView {
        /**
         * 显示自定义闹钟信息
         *
         * @param list
         */
        void showBaseAlarm(List<Alarm> list);
    }

    interface Presenter {
        /**
         * 获取自定义闹钟
         */
        void getBaseAlarm();

        /**
         * 改变自定义老总开关状态
         * @param id
         * @param open
         */
        void changeAlarmOpenState(long id,boolean open);
    }
}
