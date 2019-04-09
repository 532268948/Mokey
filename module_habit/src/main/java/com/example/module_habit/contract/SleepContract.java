package com.example.module_habit.contract;

import android.media.AudioRecord;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.db.entity.Alarm;

/**
 * @author: tianhuaye
 * @date: 2019/1/17 15:48
 * @description:
 */
public interface SleepContract {

    interface View extends BaseView {
        void setAlarm(Alarm alarm);
    }

    interface Presenter {
        void getAlarmFromDB();

        /**
         * 存储录音
         * @param audioRecord
         */
        void saveVolume(AudioRecord audioRecord);
    }
}
