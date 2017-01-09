package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.pojo.dict;
import com.bf.zxd.zhuangxudai.pojo.jzzt;
import com.bf.zxd.zhuangxudai.pojo.zxgl;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by johe on 2017/1/5.
 */

public interface ZxService {
    /**
     * 风格，户型，面积字典数据接口
     * @return
     */
    @GET("getDictData")
    Observable<List<dict>> getDictData();

    /**
     * 获取装修攻略列表接口
     * @return
     */
    @GET("getZxglItem")
    Observable<List<zxgl>> getZxglItem();

    /**
     * 家装专题列表接口
     * @param houseStyle
     * @param houseType
     * @param houseArea
     * @return
     */
    @FormUrlEncoded
    @POST("getJzztItem")
    Observable<List<jzzt>> getJzztItem(@Field("houseStyle") String houseStyle, @Field("houseType") String houseType, @Field("houseArea") String houseArea);




}
