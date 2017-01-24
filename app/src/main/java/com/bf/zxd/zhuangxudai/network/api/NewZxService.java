package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.model.BankDetail;
import com.bf.zxd.zhuangxudai.pojo.ZxdBank;
import com.bf.zxd.zhuangxudai.model.PersonAssetInfo;
import com.bf.zxd.zhuangxudai.model.PersonWorkInfo;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
     * 获取申请贷款个人工作信息
     * @return
     */
    @GET("getLoanPersonWork/{user_id}")
    Observable<PersonWorkInfo> getLoanPersonWork(@Path("user_id") int user_id);

    /**
     * 获取申请贷款个人工作信息
     * @return
     */
    @GET("getLoanPersonAsset/{user_id}")
    Observable<PersonAssetInfo> getLoanPersonAsset(@Path("user_id") int user_id);


    /**
     * 申请贷款个人工作信息保存或更新
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonWork")
    Observable<ResuleInfo> saveOrUpdatePersonWork(@Field("user_id") int user_id, @Field("income_type") String  income_type, @Field("monthly_income") String monthly_income, @Field("local_cpf") String local_cpf, @Field("local_ss") String local_ss);

    /**
     * 申请贷款个人资产信息保存或更新
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonAsset")
    Observable<ResuleInfo> saveOrUpdatePersonAsset(@Field("user_id") int user_id,@Field("my_house") String  my_house,@Field("house_value") String  house_value,@Field("house_type") String  house_type,@Field("house_guaranty") String  house_guaranty,@Field("my_car") String  my_car,@Field("car_value") String  car_value, @Field("car_guaranty") String  car_guaranty  );
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
