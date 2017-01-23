package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/23.
 */

public class LoginResult {
    int code;
    String msg;
    User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
