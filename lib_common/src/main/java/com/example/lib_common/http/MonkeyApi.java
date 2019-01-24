package com.example.lib_common.http;


import com.example.lib_common.base.bean.ResponseWrapper;
import com.example.lib_common.base.bean.response.LoginItem;
import com.example.lib_common.base.bean.response.MusicBean;
import com.example.lib_common.common.Constant;

import java.util.List;

import io.reactivex.Observable;
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
}
