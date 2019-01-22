package com.example.lib_common.http;

import android.content.Context;

import com.example.lib_common.BuildConfig;
import com.example.lib_common.common.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/1/13
 * @time : 20:21
 * @email : 15869107730@163.com
 * @note :
 */
public class MonkeyApiService {
    private volatile static MonkeyApiService instance = null;
    private MonkeyApi monkeyApi;
    private Retrofit retrofit;
    private Context context;

    private MonkeyApiService(Context context) {
        this.context = context;
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //DEBUG模式下 添加日志拦截器
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(interceptor);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        monkeyApi = retrofit.create(MonkeyApi.class);
    }

    public static MonkeyApiService getInstance(Context context) {
        if (instance == null) {
            synchronized (MonkeyApiService.class) {
                if (instance == null) {
                    instance = new MonkeyApiService(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public MonkeyApi getMonkeyApi() {
        return monkeyApi;
    }
}
