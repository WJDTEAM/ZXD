package com.bf.zxd.zhuangxudai.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by johe on 2017/2/17.
 */

public class UserWithNameAndPassword extends RealmObject{

    @PrimaryKey
    int userId;

    String userName;
    String passWord;

    @Override
    public String toString() {
        return "UserWithNameAndPassword{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
