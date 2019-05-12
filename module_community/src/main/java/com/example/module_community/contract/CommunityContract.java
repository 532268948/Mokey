package com.example.module_community.contract;

import com.example.lib_common.base.BaseView;
import com.example.lib_common.bean.response.FirstPageBean;

/**
 * @author: tianhuaye
 * @date: 2019/1/8 15:30
 * @description:
 */
public interface CommunityContract {

    interface View extends BaseView {
        void getDataSuccess(FirstPageBean firstPageBean);
    }

    interface Presenter {
        void getFirstPageMessage();
    }
}
