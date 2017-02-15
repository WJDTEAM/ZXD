package com.bf.zxd.zhuangxudai.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款申请人工作信息
 * */
public class ApplyPersonWork implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8232727334772771843L;

	private Integer personId;
	
	private Integer incomeType;
	
	private Integer localCpf;
	
	private Integer localSs;
	
	private BigDecimal monthlyIncome;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}

	public Integer getLocalCpf() {
		return localCpf;
	}

	public void setLocalCpf(Integer localCpf) {
		this.localCpf = localCpf;
	}

	public Integer getLocalSs() {
		return localSs;
	}

	public void setLocalSs(Integer localSs) {
		this.localSs = localSs;
	}

	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	
	
}
