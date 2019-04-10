package com.example.module_habit.contract;

import android.media.AudioRecord;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.bean.request.TurnBean;
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
        void analysisSuccess();
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
         * 存储录音
         *
         * @param audioRecord
         */
        void saveVolume(AudioRecord audioRecord);
    }
}
