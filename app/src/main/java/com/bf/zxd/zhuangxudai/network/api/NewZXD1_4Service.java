package com.bf.zxd.zhuangxudai.network.api;

import android.text.Html;

import com.bf.zxd.zhuangxudai.pojo.ApplyPersonAsset;
import com.bf.zxd.zhuangxudai.pojo.ApplyPersonBase;
import com.bf.zxd.zhuangxudai.pojo.ApplyPersonWork;
import com.bf.zxd.zhuangxudai.pojo.ApplyStatusItem;
import com.bf.zxd.zhuangxudai.pojo.Comments;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyCase;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyYbjDetail;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyYbjItem;
import com.bf.zxd.zhuangxudai.pojo.DkhdItem;
import com.bf.zxd.zhuangxudai.pojo.HouseBaseInfo;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.bf.zxd.zhuangxudai.pojo.LoanCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.LoanCompanyItem;
import com.bf.zxd.zhuangxudai.pojo.LoanTypes;
import com.bf.zxd.zhuangxudai.pojo.PersonLoanItem;
import com.bf.zxd.zhuangxudai.pojo.PersonYyItem;
import com.bf.zxd.zhuangxudai.pojo.Recommends;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithCompanyFile;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithImg;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithLoanInfoId;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithUser;
import com.bf.zxd.zhuangxudai.pojo.ZxglItem;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by johe on 2017/2/13.
 */

public interface NewZXD1_4Service {
    /**
     * 用户登录
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<ResultCodeWithUser> login(@Field("phone") String phone, @Field("password") String  password);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Observable<ResultCodeWithUser> register(@Field("phone") String phone, @Field("password") String  password);

    /**
     * 用户头像上传
     * @return
     */
    @Multipart
    @POST("uploadAvatars")
    Observable<ResultCodeWithImg> uploadAvatars(@Part("userId") Integer userId,@Part MultipartBody.Part file);

    /**
     * 用户昵称编辑
     * @return
     */
    @FormUrlEncoded
    @POST("editNickname")
        Observable<ResultCode> editNickname(@Field("userId") Integer userId, @Field("nickname") String  nickname);

    /**
     * 获取装修攻略列表
     * @return
     */
    @GET("getZxglItem")
    Observable<List<ZxglItem>> getZxglItem();

    /**
     * 获取攻略内容详情
     * 获取活动内容详情
     * 获取互动内容详情
     * @return
     */
    @GET("getDetail/{articleId}")
    Observable<Html> getDetail(@Path("articleId") int articleId);

    /**
     * 获取贷款活动列表
     * @return
     */
    @GET("getDkhdItem")
    Observable<List<DkhdItem>> getDkhdItem();

    /**
     * 获取家装活动列表
     * @return
     */
    @GET("getJzhdItem")
    Observable<List<JzhdItem>> getJzhdItem();

    /**
     * 获取装修公司列表
     * @return
     */
    @GET("getDecoCompanyItem")
    Observable<List<DecoCompanyItem>> getDecoCompanyItem();

    /**
     * 获取装修公司案例
     * @returnhttp://ip/app/getDecoCompanyCase/{companyId}/01
     */
    @GET("getDecoCompanyCase/{companyId}/01")
    Observable<List<DecoCompanyCase>> getDecoCompanyCase(@Path("companyId") int companyId);

    /**
     * 获取某个案例下图片
     * 获取样板间图片
     * @return
     */
    @GET("getDecoCompanyCaseImages/{caseId}")
    Observable<List<String>> getDecoCompanyCaseImages(@Path("caseId") int caseId);

    /**
     * 获取贷款类型
     * @return
     */
    @GET("getLoanTypes ")
    Observable<List<LoanTypes>> getLoanTypes ();

    /**
     * 获取贷款机构列表
     * @return
     */
    @FormUrlEncoded
    @POST("getLoanCompanyItem")
    Observable<List<LoanCompanyItem>> getLoanCompanyItem(@Field("loanTypeId") Integer loanTypeId, @Field("minMoney") String  minMoney,
                                                        @Field("maxMoney") String maxMoney, @Field("rate") String rate, @Field("cycle") String cycle);

    /**
     * 获取贷款信息详情
     * @returnhttp://ip/app/ getLoanCompanyDetail/{companyId}
     */
    @GET("getLoanCompanyDetail/{companyId}")
    Observable<LoanCompanyDetail> getLoanCompanyDetail(@Path("companyId") int companyId);

    /**
     * 获取房屋风格、户型、面积信息
     * @return
     */
    @GET("getHouseBaseInfo")
    Observable<List<HouseBaseInfo>> getHouseBaseInfo();

    /**
     * 入驻申请文件上传
     * @return
     */
    @Multipart
    @POST("uploadCompanyFile")
    Observable<ResultCodeWithCompanyFile> uploadCompanyFile(@Part MultipartBody.Part file);

    /**
     * 入驻申请信息保存
     * @return
     */
    @FormUrlEncoded
    @POST("saveEnter")
    Observable<ResultCode> saveEnter(@Field("enter") String  enter );

    /**
     * 获取银行信息
     * @return
     */
    @GET("getRecommends")
    Observable<List<Recommends>> getRecommends ();



    /**
     *  获取样板间列表
     * @return
     */
    @FormUrlEncoded
    @POST("getDecoCompanyYbjItem")
    Observable<List<DecoCompanyYbjItem>> getDecoCompanyYbjItem(@Field("houseStyle") Integer houseStyle, @Field("houseType") Integer houseType, @Field("houseArea") Integer houseArea);

    /**
     *  获取样板间详情
     * @return
     */
    @GET("getDecoCompanyYbjDetail/{caseId}")
    Observable<DecoCompanyYbjDetail> getDecoCompanyYbjDetail(@Path("caseId") int caseId);

    /**
     * 获取公司信息
     * @return
     */
    @GET("getDecoCompanyDetail/{companyId}")
    Observable<DecoCompanyDetail> getDecoCompanyDetail(@Path("companyId") int companyId);

    /**
     *  贷款申请人资产信息保存
     *  "personId": Integer 用户ID
     "myHouse":Integer名下房产：0-无房；1-有房，已抵押；2-有房，无抵押
     "houseValue":Bidecimal房产估值
     "houseType":Interger房产类型：1-商品住宅；2-商铺；3-厂房；4-办公楼；5-危改房；6-经济适用房；7-其他
     "houseGuaranty":Integer房产抵押：1-能接受；2-不能接受
     "myCar":Integer名下车产：1-有车，无抵押；2-有车，已抵押；3-无车，准备购买；4-无车
     "carValue":Bigdecimal车辆估值
     "carGuaranty":Integer车辆抵押：1-能接受；2-不能接受
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonAsset")
    Observable<ResultCode> saveOrUpdatePersonAsset
    (@Field("personAsset") String personAsset);

    /**
     * 获取贷款申请人资产信息
     * @return
     */
    @GET("getPersonAsset/{personId}")
    Observable<ApplyPersonAsset> getPersonAsset(@Path("personId") int personId);

    /**
     *   贷款申请人工作信息保存
     *  "personId": Integer 用户ID
     " incomeType":Integer收入形势：1-银行代发；2-转账工资；3-现金发放
     " localCpf":Integer本地公积金：0-无；1-有
     " localSs": Interger本地社保：0-无；1-有
     " monthlyIncome": String每月收入
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonWork")
    Observable<ResultCode> saveOrUpdatePersonWork
            (@Field("personWork") String personWork);

    /**
     * 获取贷款申请人工作信息
     * @return
     */
    @GET("getPersonWork/{personId}")
    Observable<ApplyPersonWork> getPersonWork(@Path("personId") int personId);

    /**
     *   贷款申请人基本信息保存
     *  "personId": Integer 用户ID
     "fullName": String 申请人姓名
     " mobilePhone":String 联系方式
     " maritalStatus": Interger婚姻情况：0-未婚；1-已婚
     "creditStatus": Integer信用状况：1-无信用卡和贷款；2-信用良好；3-一年内预期少于3次且少于90天；4:-一年内预期三次或超过90天
     "addr": String 地址
     "idCard": String 身份证号
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdatePersonBase")
    Observable<ResultCode> saveOrUpdatePersonBase
            (@Field("personBase") String personBase);

    /**
     * 获取贷款申请人基本信息
     * @return
     */
    @GET("getPersonBase/{personId}")
    Observable<ApplyPersonBase> getPersonBase(@Path("personId") int personId);

    /**
     *   贷款基本信息保存
     *  "fromUserId":Integer 用户ID
     "toLoanCompanyId":Integer 借贷公司ID
     "toDecoCompanyId":Integer 装修公司ID（若无，则默认为0）
     "loanPurpose":String 贷款用途
     "loanTerm":Integer 贷款周期
     "loanAmount":String 贷款金额
     "referrer":String 推荐人
     * @return
     */
    @FormUrlEncoded
    @POST("saveLoanInfo")
    Observable<ResultCodeWithLoanInfoId> saveLoanInfo(@Field("loan") String loan);

    /**
     *   评论信息保存
     * @return
     */
    @FormUrlEncoded
    @POST("saveComments")
    Observable<ResultCode> saveComments(@Field("userId") Integer userId,@Field("objectId") Integer objectId,@Field("content") String content);

    /**
     * 获取评论信息
     * @return
     */
    @GET("getComments/{objectId}")
    Observable<List<Comments>> getComments(@Path("objectId") int objectId);

    /**
     * 预约保存

     * @return
     */
    @FormUrlEncoded
    @POST("saveZxyy")
    Observable<ResuleInfo> saveZxyy(@Field("fromUserId ") Integer  fromUserId, @Field("toCompanyId") Integer toCompanyId, @Field("proposer") String proposer, @Field("tel") String tel, @Field("addr") String addr, @Field("area") int area, @Field("houseArea") int houseArea, @Field("houseType") int houseType);

    /**
     * 获取我的贷款
     * @return
     */
    @GET("getPersonLoanItem/{userId}")
    Observable<List<PersonLoanItem>> getPersonLoanItem(@Path("userId") int userId);

    /**
     * 获取我的预约
     * @return
     */
    @GET("getPersonYyItem/{userId}")
    Observable<List<PersonYyItem>> getPersonYyItem(@Path("userId") int userId);


    /**
     * 申请进度
     * @return
     */
    @GET("getApplyStatusItem/{applyType}/{applyId}")
    Observable<List<ApplyStatusItem>> getApplyStatusItem(@Path("applyType") int applyType, @Path("applyId") int applyId);


}
