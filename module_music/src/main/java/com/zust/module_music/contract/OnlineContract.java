package com.zust.module_music.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.bean.MusicOnlineItem;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/21
 * @time : 14:06
 * @email : 15869107730@163.com
 * @note :
 */
public interface OnlineContract {
    interface View extends BaseView {
        void addMoreData(List<MusicOnlineItem> musicItemList);

        void updateCurrentPage(int page);

        void refreshData(List<MusicOnlineItem> musicItemList);
    }

    interface Presenter {
        void getOnlineMusicList(int currentPage);
        void refreshOnlineMusicList();

        /**
         * 更新音乐本地缓存路径
         * @param localFile
         * @param id
         */
        void updateMusicDb(String localFile,MusicItem musicItem);
    }
}
