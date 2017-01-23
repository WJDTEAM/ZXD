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
	@PrimaryKey
    private Integer userId=0;
    private String userName;
    private String phone;
    private String password;
    private String tel;
    
    public User(){}
    

    

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}