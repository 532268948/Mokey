package com.example.module_habit.listener;

import android.media.AudioRecord;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/8
 * @time : 16:00
 * @email : 15869107730@163.com
 * @note : 声音分贝监听器
 */
public interface OnRecordListener {
    /**
     * 开始缓存
     */
    void startRecord(AudioRecord audioRecord);

    /**
     * 停止缓存
     */
    void stopRecord(AudioRecord audioRecord);
}
