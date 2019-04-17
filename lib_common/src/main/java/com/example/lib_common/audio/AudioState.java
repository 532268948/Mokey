package com.example.lib_common.audio;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/16
 * @time : 13:21
 * @email : 15869107730@163.com
 * @note :
 */
public enum  AudioState {
    /**
     * 闲置状态
     */
    IDLE,
    /**
     * 开始录音
     */
    Recording,
    /**
     * 暂停录音
     */
    Pause,
    /**
     * 继续录音
     */
    Resume,
    /**
     * 结束录音
     */
    Stop

}
