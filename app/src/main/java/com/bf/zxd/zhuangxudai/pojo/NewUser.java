package com.bf.zxd.zhuangxudai.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by johe on 2017/2/13.
 */

public class NewUser extends RealmObject {
    /**
     * userId : 3
     * userName : 15670702651
     * password : 40bd001563085fc35165329ea1ff5c5ecbdbbeef
     * nickname :
     * logoImg :
     */
    @PrimaryKey
    private int userId;
    private String userName;
    private String password;
    private String nickname;
    private String logoImg;

    @Override
    public String toString() {
        return "NewUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", logoImg='" + logoImg + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
