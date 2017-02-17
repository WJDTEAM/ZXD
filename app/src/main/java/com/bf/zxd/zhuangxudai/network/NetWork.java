package com.bf.zxd.zhuangxudai.network;

import com.bf.zxd.zhuangxudai.network.api.JzztService;
import com.bf.zxd.zhuangxudai.network.api.NewZXD1_4Service;
import com.bf.zxd.zhuangxudai.network.api.NewZxService;
import com.bf.zxd.zhuangxudai.network.api.UpdateService;
import com.bf.zxd.zhuangxudai.network.api.UserService;
import com.bf.zxd.zhuangxudai.network.api.ZxService;

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
    private static ZxService zxService;
    private static NewZxService newZxService;
    private static NewZXD1_4Service newZXD1_4Service;
    private  static UpdateService updateService;

    public static UpdateService getUpdateService() {
        if (updateService == null) {
            Retrofit retrofit = getRetrofit();
            updateService = retrofit.create(UpdateService.class);
        }
        return updateService;
    }

    public static NewZXD1_4Service getNewZXD1_4Service() {
        if (newZXD1_4Service == null) {
            Retrofit retrofit = getRetrofit();
            newZXD1_4Service = retrofit.create(NewZXD1_4Service.class);
        }
        return newZXD1_4Service;
    }
    public static UserService getUserService() {
        if (userService == null) {
            Retrofit retrofit = getRetrofit();
            userService = retrofit.create(UserService.class);
        }
        return userService;
    }
    public static NewZxService getNewZxService() {
        if (newZxService == null) {
            Retrofit retrofit = getRetrofit();
            newZxService = retrofit.create(NewZxService.class);
        }
        return newZxService;
    }
    public static JzztService getJzztService() {
        if (jzztService == null) {
            Retrofit retrofit = getRetrofit();
            jzztService = retrofit.create(JzztService.class);
        }
        return jzztService;
    }
    public static ZxService getZxService() {
        if (zxService == null) {
            Retrofit retrofit = getRetrofit();
            zxService = retrofit.create(ZxService.class);
        }
        return zxService;
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
