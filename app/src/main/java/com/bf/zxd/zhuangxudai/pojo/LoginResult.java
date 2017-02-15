package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/23.
 */

public class LoginResult {
    int code;
    String msg;
    NewUser user;

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
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }
}
