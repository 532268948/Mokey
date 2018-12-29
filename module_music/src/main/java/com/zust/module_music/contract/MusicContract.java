package com.zust.module_music.contract;

import com.example.lib_common.base.BaseView;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/16
 * @time : 11:38
 * @email : 15869107730@163.com
 * @note :
 */
public interface MusicContract {

    interface View extends BaseView {
        /**
         * 显示音乐播放浮窗
         */
        void showBigMusicView();

        /**
         * 音乐浮窗缩至右下角
         */
        void changeSmallMusicView();

        /**
         * 音乐浮窗变大
         */
        void changeBigMusicView();

        /**
         * 隐藏音乐浮窗
         */
        void hideMusicView();
    }

    interface Presenter {
    }
}
