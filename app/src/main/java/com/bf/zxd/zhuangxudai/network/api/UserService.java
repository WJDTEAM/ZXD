package com.bf.zxd.zhuangxudai.network.api;
import com.bf.zxd.zhuangxudai.pojo.user;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by johe on 2017/1/5.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("User/loginUsers")
    Observable<user> loginUsers(@Field("userName") String user_name, @Field("userPassword") String user_password);

}
