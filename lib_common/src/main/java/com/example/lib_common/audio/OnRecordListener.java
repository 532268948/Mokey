package com.example.lib_common.audio;

import android.media.AudioRecord;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/15
 * @time : 17:06
 * @email : 15869107730@163.com
 * @note :
 */
public interface OnRecordListener {

    /**
     * 开启分贝监听
     * @param audioRecord
     */
    void startRecord(AudioRecord audioRecord);

    /**
     * 暂停分贝监听
     * @param audioRecord
     */
    void resumeRecord(AudioRecord audioRecord);

    /**
     * 继续分贝监听
     * @param audioRecord
     */
    void pauseRecord(AudioRecord audioRecord);

    /**
     * 结束分贝监听
     * @param audioRecord
     */
    void stopRecord(AudioRecord audioRecord);

    /**
     * 开始缓存
     */
    void startSaveRecord(AudioRecord audioRecord);

    /**
     * 停止缓存
     */
    void stopSaveRecord(AudioRecord audioRecord);
}
