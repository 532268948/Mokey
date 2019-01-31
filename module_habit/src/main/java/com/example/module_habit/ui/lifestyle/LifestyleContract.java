package com.example.module_habit.ui.lifestyle;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.db.entity.Alarm;

import java.util.List;

/**
 * @author: tianhuaye
 * date:   2018/11/23 11:10
 * description:
 */
public interface LifestyleContract {

    interface View extends BaseView {
        /**
         * 显示入睡闹钟信息
         *
         * @param alarm 数据
         */
        void setSleepAlarm(Alarm alarm);

        /**
         * 显示睡前准备闹钟信息
         *
         * @param alarmList 数据
         */
        void setPrepareAlarm(List<Alarm> alarmList);
    }

    interface Presenter {
        /**
         * 获取睡前准备闹钟信息
         */
        void getPrepareAlarm();

        /**
         * 显示睡前准备闹钟信息
         *
         * @param alarmList
         */
        void updateSleepAlarm(List<Alarm> alarmList);

        /**
         * 获取入睡闹钟
         */
        void getSleepAlarm();

        /**
         * 更新入睡闹钟信息
         *
         * @param alarm 闹钟信息
         */
        void updateSleepAlarm(Alarm alarm);
    }
}
