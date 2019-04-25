package com.example.module_report.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.bean.DreamBean;

import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/4 14:23
 * @description:
 */
public interface ReportDetailContract {

    interface View extends BaseView {
        void getDreamDataSuccess(List<DreamBean> dreamBeanList);
    }

    interface Presenter {
        void getDreamData(long startTime,long stopTime);
    }
}
