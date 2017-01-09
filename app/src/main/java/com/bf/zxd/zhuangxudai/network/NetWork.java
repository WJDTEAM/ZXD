package com.bf.zxd.zhuangxudai.network;
import com.bf.zxd.zhuangxudai.network.api.JzztService;
import com.bf.zxd.zhuangxudai.network.api.UserService;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wjy on 16/9/1.
 */
public class NetWork {
    private static UserService userService;
    private static JzztService jzztService;

    public static UserService getUserService() {
        if (userService == null) {
            Retrofit retrofit = getRetrofit();
            userService = retrofit.create(UserService.class);
        }
        return userService;
    }
    public static JzztService getJzztService() {
        if (jzztService == null) {
            Retrofit retrofit = getRetrofit();
            jzztService = retrofit.create(JzztService.class);
        }
        return jzztService;
    }
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://211.149.235.17:8080/zxd/app/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();

    }

}
