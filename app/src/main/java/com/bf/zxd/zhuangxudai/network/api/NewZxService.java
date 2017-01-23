package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.model.BankDetail;
import com.bf.zxd.zhuangxudai.pojo.ZxdBank;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Daniel on 2017/1/23.
 */

public interface NewZxService {

    /**
     * 银行详情
     * @return
     */
    @GET("getBankDetail/{bank_id}")
    Observable<BankDetail> getBankDetail(@Path("bank_id") int bank_id);

    /**
     * 获取装修贷列表
     * http://ip/app/getZxdItem/
     *  "loan_type":String (非必须) 贷款类型1:房贷;2:装修

     "min_money":String (非必须) 最小金额

     "max_money":String (非必须) 最大金额

     "rate":String (非必须) 费率

     "cycle":String(非必须) 贷款期限
     */
    @FormUrlEncoded
    @POST("getZxdItem")
    Observable<List<ZxdBank>> getZxdItem
    (@Field("loan_type") String  loan_type, @Field("min_money") String min_money, @Field("max_money") String max_money,
     @Field("rate") String rate, @Field("cycle") String cycle);
}
