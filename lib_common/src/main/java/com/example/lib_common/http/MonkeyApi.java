package com.example.lib_common.http;


import com.example.lib_common.bean.ResponseWrapper;
import com.example.lib_common.bean.request.SleepData;
import com.example.lib_common.bean.response.LoginItem;
import com.example.lib_common.bean.response.MusicBean;
import com.example.lib_common.bean.response.SleepBean;
import com.example.lib_common.bean.response.UserSleepBean;
import com.example.lib_common.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/12
 * @time : 22:43
 * @email : 15869107730@163.com
 * @note :
 */
public interface MonkeyApi {
    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return
     */
    @POST(Constant.Url.USER_LOGIN)
    Observable<ResponseWrapper<LoginItem>> login(@Query("name") String name, @Query("password") String password);

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @POST(Constant.Url.USER_INFORMATION)
    Observable<ResponseWrapper<LoginItem>> getUserInformation(@Query("token") String token);

    /**
     * 获取睡前小曲
     *
     * @param token
     * @param currentPage
     * @return
     */
    @POST(Constant.Url.MUSIC_BEFORE)
    Observable<ResponseWrapper<List<MusicBean>>> getMusicSleepBefore(@Query("token") String token, @Query("currentPage") int currentPage);

    /**
     * 获取用户睡眠质量报告数据
     *
     * @param token
     * @return
     */
    @POST(Constant.Url.SLEEP_USER_DATA)
    Observable<ResponseWrapper<UserSleepBean>> getUserSleepData(@Query("token") String token);

    /**
     * 发送睡眠数据到服务器
     *
     * @param token
     * @param sleepData
     * @return
     */
    @POST(Constant.Url.SLEEP_DATA_SEND)
    Observable<ResponseWrapper<SleepBean>> sendSleepData(@Query("token") String token, @Body SleepData sleepData);
}
