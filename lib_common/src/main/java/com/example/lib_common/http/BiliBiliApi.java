package com.example.lib_common.http;

import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.response.FirstPageBean;
import com.example.lib_common.common.Constant;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 14:05
 * @email : 15869107730@163.com
 * @note :
 */
public interface BiliBiliApi {
    /**
     * 首页直播
     */
    @GET(Constant.BiliBili.LIVE_URL)
    Observable<ResponseWrapper<FirstPageBean>> getLiveAppIndex();
}
