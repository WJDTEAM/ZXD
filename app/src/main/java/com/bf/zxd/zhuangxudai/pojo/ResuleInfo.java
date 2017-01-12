package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/10.
 * "code":int 10001:成功;10000:失败

 "msg":String
 */

public class ResuleInfo {
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResuleInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
