package com.example.lib_common.http;

import com.example.lib_common.base.BaseApplication;
import com.example.lib_common.common.Constant;
import com.example.lib_common.util.CommonUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/5/10
 * @time : 13:59
 * @email : 15869107730@163.com
 * @note :
 */
public class BiliBiliApiService {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static BiliBiliApi getLiveAPI() {
        return createApi(BiliBiliApi.class, Constant.BiliBili.LIVE_BASE_URL);
    }

//    public static BiliBiliApi getBiliAppAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.APP_BASE_URL);
//    }
//
//    public static BiliBiliApi getBiliAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.API_BASE_URL);
//    }
//
//    public static BiliBiliApi getBiliGoAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.BILI_GO_BASE_URL);
//    }
//
//    public static BiliBiliApi getRankAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.RANK_BASE_URL);
//    }
//
//    public static BiliBiliApi getUserAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.USER_BASE_URL);
//    }
//
//    public static BiliBiliApi getVipAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.VIP_BASE_URL);
//    }
//
//    public static BiliBiliApi getBangumiAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.BANGUMI_BASE_URL);
//    }
//
//    public static BiliBiliApi getSearchAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.SEARCH_BASE_URL);
//    }
//
//    public static BiliBiliApi getAccountAPI() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.ACCOUNT_BASE_URL);
//    }
//
//    public static BiliBiliApi getIm9API() {
//        return createApi(BiliBiliApi.class, Constant.BiliBili.IM9_BASE_URL);
//    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (BiliBiliApiService.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(BaseApplication.mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "OhMyBiliBili Android Client/2.1 (100332338@qq.com)")
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(BaseApplication.mContext)) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(BaseApplication.mContext)) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
