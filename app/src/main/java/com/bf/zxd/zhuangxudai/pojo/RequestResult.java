package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/23.
 */

public class RequestResult<T> {
    int code;
    String msg;
    T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



}
