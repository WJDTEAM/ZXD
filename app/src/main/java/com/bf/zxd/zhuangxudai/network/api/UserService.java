package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.pojo.LoginResult;
import com.bf.zxd.zhuangxudai.pojo.RegistResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by johe on 2017/1/5.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResult> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Observable<RegistResult> register(@Field("phone") String phone, @Field("password") String password);


}
