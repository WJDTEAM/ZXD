package com.bf.zxd.zhuangxudai.pojo;

import java.io.Serializable;

/**
 * 贷款申请人的基本信息
 * */
public class ApplyPersonBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4909125515613633604L;

	private Integer personId;
	
	private String fullName = "";
	
	private String mobilePhone = "";
	
	private Integer maritalStatus;
	
	private Integer creditStatus;
	
	private String addr = "";
	
	private String idCard = "";

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
}
