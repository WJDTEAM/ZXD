package com.bf.zxd.zhuangxudai.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * "user_id": long 用户Id

 "user_name":String 用户账号

 "tel":String 用户电话
 "phone":String 手机号码

 "password":String 密码
 */
public class User extends RealmObject {
    /**
     * user_id : 33
     * user_name : 15632323232
     * tel : 15632323232
     */
    @PrimaryKey
    private int user_id;
    private String user_name;
    private String tel;
    private String phone;
    private String password;
    private String nickname;
    private String logoImg;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", tel='" + tel + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", logoImg='" + logoImg + '\'' +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}