package com.bf.zxd.zhuangxudai.network.api;

import com.bf.zxd.zhuangxudai.pojo.DictData;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by johe on 2017/1/9.
 */

public interface JzztService {
    /**
     * 风格、户型、面积字典数据
     */
    @GET("getDictData")
    Observable<List<DictData>> getDictData();
}
