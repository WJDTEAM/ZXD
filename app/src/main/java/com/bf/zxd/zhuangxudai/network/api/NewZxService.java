package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.model.BankDetail;

import retrofit2.http.GET;
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

}
