package com.zust.module_music.contract.before;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.base.bean.MusicItem;

import java.util.List;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/24
 * @time : 11:23
 * @email : 15869107730@163.com
 * @note :
 */
public interface MusicBeforeContract {

    interface View extends BaseView {
        void updateMusic(List<MusicItem> musicItemList,int position);
    }

    interface Presenter {
    }

    ;
}
