package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.update.UpdateMsg;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by johe on 2016/12/29.
 */

public interface UpdateService {

    @GET("getAppVersion")
    Observable<UpdateMsg> getAppVersion();

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
