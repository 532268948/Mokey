package com.example.module_habit.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.bean.ReportBean;
import com.example.lib_common.bean.request.TurnBean;
import com.example.lib_common.db.entity.Alarm;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/17 15:48
 * @description:
 */
public interface SleepContract {

    interface View extends BaseView {
        void setAlarm(Alarm alarm);

        /**
         * 数据分析成功
         */
        void analysisSuccess(ReportBean reportBean);
    }

    interface Presenter {
        void getAlarmFromDB();

        /**
         * 发送睡眠数据
         *
         * @param start_time  睡眠时间
         * @param end_time    结束时间
         * @param unLockTimes 解锁次数
         * @param turnBeans   翻身数据
         */
        void sendSleepData(long start_time, long end_time, int unLockTimes, List<TurnBean> turnBeans);

        /**
         * 时间太短，清除录音
         * @param start_time
         * @param stop_time
         */
        void clearVolume(long start_time,long stop_time);

        /**
         * pcm文件转化为wav文件
         * @param start_time
         * @param stop_time
         */
        void pcmToWav(long start_time,long stop_time);
    }

}
