package com.bf.zxd.zhuangxudai.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class user extends RealmObject {
	@PrimaryKey
    private Integer userId=0;

    private String userName;

    private String userPassword;

    
    public user(){}
    
    public user(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public user(Integer userId, String userName, String userPassword){
    	this.userId=userId;
    	this.userName=userName;
    	this.userPassword=userPassword;
    }
    

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

	@Override
	public String toString() {
		return "user{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userPassword='" + userPassword + '\'' +
				'}';
	}
}