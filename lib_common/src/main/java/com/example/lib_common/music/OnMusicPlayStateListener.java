package com.example.lib_common.music;

import com.example.lib_common.bean.MusicItem;

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

    void onStopped(MusicItem item);

    void onPaused(MusicItem item);

    void onPrepare(MusicItem item);

    void onPosition(MusicItem item,int pos);

    void onComplete(MusicItem item);

    void onRemain(int count, long time);

    void onSeekToLast(int time);
}
