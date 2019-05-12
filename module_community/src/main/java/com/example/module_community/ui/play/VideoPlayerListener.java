package com.example.module_community.ui.play;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 11:04
 * @email : 15869107730@163.com
 * @note :
 */
public abstract class VideoPlayerListener implements IMediaPlayer.OnBufferingUpdateListener
        , IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener
        , IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener
        , IMediaPlayer.OnSeekCompleteListener {
}
