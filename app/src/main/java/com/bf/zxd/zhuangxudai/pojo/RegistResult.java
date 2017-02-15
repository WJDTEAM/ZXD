package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/23.
 */

public class RegistResult {
    int code;
    String msg;
    NewUser User;

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

    public NewUser getUser() {
        return User;
    }

    public void setUser(NewUser user) {
        User = user;
    }
}
