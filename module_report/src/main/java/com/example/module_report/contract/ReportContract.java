package com.example.module_report.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.bean.ReportBean;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 10:39
 * @description:
 */
public interface ReportContract {

    interface View extends BaseView {
        /**
         * 显示用户睡眠数据
         * @param list
         */
        void showUserSleepData(List<ReportBean> list);
    }

    interface Presenter {

        /**
         * 获取用户睡眠数据
         */
        void getUserSleepData();
    }
}
