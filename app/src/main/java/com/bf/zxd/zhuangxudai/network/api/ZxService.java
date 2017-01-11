package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.pojo.Dkhd;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.Zxd;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dict;
import com.bf.zxd.zhuangxudai.pojo.jzzt;
import com.bf.zxd.zhuangxudai.pojo.zxgl;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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


    /**
     * 获取贷款活动
     */
    @GET("getDkhdItem")
    Observable<List<Dkhd>> getDkhdItem();

    /**
     * 入驻申请
     *   "unit_name":String 单位名称
          "unit_addr":String 单位地址
         "full_name":String 申请人
        "phone":String 联系电话
     */
    @FormUrlEncoded
    @POST("saveRzsq")
    Observable<ResuleInfo> saveRzsq(@Field("unit_name") String unit_name, @Field("unit_addr") String unit_addr, @Field("full_name") String full_name, @Field("phone") String phone);


    /**
     * 装修贷银行信息
     */
    @GET("getZxdItem")
    Observable<List<Zxd>> getZxdItem();


    /**
     * http://ip/app/getZxgs/{company_id}
     * 获取装修公司
     */
    @GET("getZxgs/{company_id}")
    Observable<Zxgs> getZxgs(@Path("company_id")int company_id);


}
