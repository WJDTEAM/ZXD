package com.bf.zxd.zhuangxudai.pojo;

import java.math.BigDecimal;

/**
 * 贷款申请人资产信息
 * */
public class ApplyPersonAsset {


	private Integer personId;
	
	private Integer myHouse;
	
	private Integer houseValue;
	
	private Integer houseType;
	
	private Integer houseGuaranty;
	
	private Integer myCar;
	
	private BigDecimal carValue;
	
	private Integer carGuaranty;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getMyHouse() {
		return myHouse;
	}

	public void setMyHouse(Integer myHouse) {
		this.myHouse = myHouse;
	}

	public Integer getHouseValue() {
		return houseValue;
	}

	public void setHouseValue(Integer houseValue) {
		this.houseValue = houseValue;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public Integer getHouseGuaranty() {
		return houseGuaranty;
	}

	public void setHouseGuaranty(Integer houseGuaranty) {
		this.houseGuaranty = houseGuaranty;
	}

	public Integer getMyCar() {
		return myCar;
	}

	public void setMyCar(Integer myCar) {
		this.myCar = myCar;
	}

	public BigDecimal getCarValue() {
		return carValue;
	}

	public void setCarValue(BigDecimal carValue) {
		this.carValue = carValue;
	}

	public Integer getCarGuaranty() {
		return carGuaranty;
	}

	public void setCarGuaranty(Integer carGuaranty) {
		this.carGuaranty = carGuaranty;
	}
	
	
}
