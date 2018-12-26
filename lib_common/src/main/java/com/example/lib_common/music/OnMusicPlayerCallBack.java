package com.example.lib_common.music;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/22
 * @time : 22:31
 * @email : 15869107730@163.com
 * @note :
 */
public interface OnMusicPlayerCallBack {
    void onStateChanged(MusicState state);//播放状态回调

//    void onLogicAction(BBLogicAction action);//回调业务逻辑

    void onControlAction(String action);//回调控制播放

    void onSeekToLast();//seek 到上次播放位置

    void onSeekCompleted();//完成seek
}
