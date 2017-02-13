package com.bf.zxd.zhuangxudai.network.api;

import android.text.Html;

import com.bf.zxd.zhuangxudai.pojo.DecoCompanyCase;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.pojo.DkhdItem;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.bf.zxd.zhuangxudai.pojo.LoanCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.LoanCompanyItem;
import com.bf.zxd.zhuangxudai.pojo.LoanTypes;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithImg;
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
    @POST("User/uploadAvatars")
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
    Observable<DecoCompanyCase> getDecoCompanyCase(@Path("companyId") int companyId);

    /**
     * 获取某个案例下图片
     * @return
     */
    @GET("getDecoCompanyCaseImages/{caseId}")
    Observable<String> getDecoCompanyCaseImages(@Path("caseId") int caseId);

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



}
