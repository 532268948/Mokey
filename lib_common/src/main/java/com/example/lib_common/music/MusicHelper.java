package com.example.lib_common.music;

import android.content.Context;

import com.example.lib_common.base.BaseApplication;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 15:43
 * @email : 15869107730@163.com
 * @note :MusicPlayer帮助类
 */
public class MusicHelper {

    private MusicPlayer mPlayer;
    private static Context mContext=BaseApplication.mContext;
    private static MusicHelper mHelper;

    public static MusicHelper getInstance() {
        if (mHelper == null) {
            synchronized (MusicHelper.class) {
                if (mHelper == null) {
                    mHelper = new MusicHelper();
                }
            }
        }
        return mHelper;
    }

    private void bindPlayer(OnMusicPlayStateListener listener) {
        bindPlayer(listener, false);
    }

    private void bindPlayer(OnMusicPlayStateListener listener, boolean restore) {
        if (mPlayer == null) {
            mPlayer = new MusicPlayer(mContext);
//            mPlayer.setJumpSeekListener(BTEngine.singleton().getParentAstMgr());
        }
        if (restore) {
            mPlayer.reBindForLowMemoryKilled();
        }
        mPlayer.registerCallback(listener);
    }

//    private boolean isPlayed() {
//        return mPlayer != null && mPlayer.isPlayed();
//    }

    private void next() {
        if (mPlayer != null) {
            mPlayer.next(false);
        }
    }

    private void prev() {
        if (mPlayer != null) {
            mPlayer.prev();
        }
    }

    private void seekTo(int time) {
        if (mPlayer != null) {
            mPlayer.seekTo(time);
        }
    }

    private void play(long musicId) {
        if (mPlayer != null) {
            mPlayer.play(musicId);
        }
    }

    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.updateLastIds();
            mPlayer.stop(false);
        }
    }

    private void stop(boolean updateNoti) {
        if (mPlayer != null) {
            mPlayer.updateLastIds();
            mPlayer.stop(updateNoti);
        }
    }

    private void play() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }
}
