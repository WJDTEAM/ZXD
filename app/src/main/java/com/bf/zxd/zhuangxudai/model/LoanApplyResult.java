package com.bf.zxd.zhuangxudai.model;

/**
 * Created by Daniel on 2017/1/24.
 */

public class LoanApplyResult {
    int code;
    String msg;
    int apply_base_id;

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

    public int getApply_base_id() {
        return apply_base_id;
    }

    public void setApply_base_id(int apply_base_id) {
        this.apply_base_id = apply_base_id;
    }

}
