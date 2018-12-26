package com.example.lib_common.music;

import com.example.lib_common.base.bean.MusicItem;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 15:45
 * @email : 15869107730@163.com
 * @note : 状态回调接口跟UI交互相关
 */
public interface OnMusicPlayStateListener {
    void onPlay(MusicItem item);

    void onStopped();

    void onPaused();

    void onPrepare();

    void onPosition(int pos);

    void onRemain(int count, long time);

    void onSeekToLast(int time);
}
