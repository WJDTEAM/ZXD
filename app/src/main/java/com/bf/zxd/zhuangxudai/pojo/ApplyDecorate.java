package com.bf.zxd.zhuangxudai.pojo;

/**
 * 装修预约申请
 * */
public class ApplyDecorate {

	/**
	 * 
	 */
	private Integer applyId;
	
	private Integer fromUserId;
	
	private Integer toCompanyId;
	
	private String proposer = "";
	
	private String tel = "";
	
	private String addr = "";
	
	private String houseArea = "";
	
	private String houseType = "";
	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToCompanyId() {
		return toCompanyId;
	}

	public void setToCompanyId(Integer toCompanyId) {
		this.toCompanyId = toCompanyId;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}


	@Override
	public String toString() {
		return "ApplyDecorate{" +
				"applyId=" + applyId +
				", fromUserId=" + fromUserId +
				", toCompanyId=" + toCompanyId +
				", proposer='" + proposer + '\'' +
				", tel='" + tel + '\'' +
				", addr='" + addr + '\'' +
				", houseArea='" + houseArea + '\'' +
				", houseType='" + houseType + '\'' +
				'}';
	}
}
