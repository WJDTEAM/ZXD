package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.model.BankItemInfo;
import com.bf.zxd.zhuangxudai.model.LoanApplyResult;
import com.bf.zxd.zhuangxudai.pojo.Dkhd;
import com.bf.zxd.zhuangxudai.pojo.DksqItem;
import com.bf.zxd.zhuangxudai.pojo.LoanPersonBase;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.VerificationInfo;
import com.bf.zxd.zhuangxudai.pojo.YysqItem;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dict;
import com.bf.zxd.zhuangxudai.pojo.jzzt;
import com.bf.zxd.zhuangxudai.pojo.zxgl;
import com.bf.zxd.zhuangxudai.pojo.zxgs_wjd;

import java.math.BigDecimal;
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
     * 获取装修公司列表接口
     * @return
     */
    @GET("getZxgsItem")
    Observable<List<zxgs_wjd>> getZxgsItem();

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
    @POST("saveRzsq")
    @FormUrlEncoded
    Observable<ResuleInfo> saveRzsq(@Field("unit_name") String unit_name, @Field("unit_addr") String unit_addr, @Field("full_name") String full_name, @Field("phone") String phone);

    /**
     * 贷款申请保存
     * "bank_id":int 银行Id

     "user_id":int 用户Id

     "company_id":int 公司ID

     "apply_money"begdecimal 申请金额

     "loan_purpose":String 贷款用途

     "loan_term":String 贷款期限
     */
    @FormUrlEncoded
    @POST("saveDksq")
    Observable<LoanApplyResult> saveDksq(@Field("bank_id") int bank_id, @Field("user_id") int user_id, @Field("company_id") int company_id, @Field("apply_money") BigDecimal apply_money, @Field("loan_purpose") String loan_purpose, @Field("loan_term") String loan_term);

    /**
     * 预约保存

     * @return
     */
    @FormUrlEncoded
    @POST("saveZxyy")
        Observable<ResuleInfo> saveZxyy(@Field("fromUserId ") Integer  fromUserId, @Field("toCompanyId") Integer toCompanyId, @Field("proposer") String proposer, @Field("tel") String tel, @Field("addr") String addr, @Field("houseArea") String  houseArea, @Field("houseType") String  houseType);


//    /**
//     * 装修贷银行信息
//     */
//    @GET("getZxdItem")
//    Observable<List<Zxd>> getZxdItem();


    /**
     * http://ip/app/getZxgs/{company_id}
     * 获取装修公司
     */
    @GET("getZxgs/{company_id}")
    Observable<Zxgs> getZxgs(@Path("company_id")int company_id);

    /**
     * 我的贷款列表
     * @return
     */
    @GET("getDksqItem/{user_id}")
    Observable<List<DksqItem>> getDksqItem(@Path("user_id") int user_id);

    /**
     * 我的预约列表
     * @return
     */
    @GET("getYysqItem")
    Observable<List<YysqItem>> getYysqItem();

    /**
     * 推荐银行列表
     * @return
     */
    @GET("getBankItem")
    Observable<List<BankItemInfo>> getBankItem();


    /**
     * http://211.149.235.17:8080/zxd/app/getZxdItem/
     * 获取装修贷
     */
    @GET("getZxdItem/{dkfw}")
    Observable<List<RecommendBank>> getZxdItem(@Path("dkfw")String dkfw);


    /**
     * 获取用户装修贷申请信息通过结果
     * http://ip/app/getVerificationInfo/{user_id}
     */
    @GET("getVerificationInfo/{user_id}")
    Observable<VerificationInfo> getVerificationInfo(@Path("user_id")int user_id);

    /**
     * 获取申请贷款个人基本信息
     * http://ip/app/getLoanPersonBase/{user_id}
     */
    @GET("getLoanPersonBase/{user_id}")
    Observable<LoanPersonBase> getLoanPersonBase(@Path("user_id")int user_id);

    /**
     * 申请贷款个人基本信息保存或更新
     * http://ip/app/saveOrUpdatePersonBase
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonBase")
    Observable<ResuleInfo> saveOrUpdatePersonBase
    (@Field("user_id") int user_id, @Field("full_name") String full_name, @Field("mobile_phone") String mobile_phone,
     @Field("marital_status") String marital_status, @Field("credit_status") String credit_status, @Field("addr") String addr,
     @Field("id_card") String id_card);





}
