package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/13.
 * "msg": String 返回结果信息
 "code": Integer 返回结果码
 "logoImg": String 用户头像

 */

public class ResultCodeWithImg  extends ResultCode{
    String logoImg;

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    @Override
    public String toString() {
        return "ResultCodeWithImg{" +
                "logoImg='" + logoImg + '\'' +
                '}';
    }
}
