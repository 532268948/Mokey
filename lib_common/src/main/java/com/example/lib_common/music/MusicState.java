package com.example.lib_common.music;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/22
 * @time : 22:31
 * @email : 15869107730@163.com
 * @note : 音乐播放状态
 */
public enum MusicState {
    /**
     * media player is stopped and not prepared to play
     */
    Stopped,
    /**
     * media player is preparing...
     */
    Preparing,
    /**
     * playback active (media player ready!). (but the media player
     */
    Playing,
    /**
     * playback paused (media player ready!)
     */
    Paused,
    Completed,
}
