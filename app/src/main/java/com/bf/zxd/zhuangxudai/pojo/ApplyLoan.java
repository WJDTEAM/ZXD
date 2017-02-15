package com.bf.zxd.zhuangxudai.pojo;

import java.math.BigDecimal;

/**
 * 贷款基本信息
 * */
public class ApplyLoan {

	private Integer applyId;
	
	private Integer toLoanCompanyId;
	
	private Integer toDecoCompanyId;
	
	private Integer fromUserId;
	
	private String loanPurpose = "";
	
	private Integer loanTerm;
	
	private BigDecimal loanAmount;
	
	private String referrer = "";
	
	private String status = "01";

	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Integer getToLoanCompanyId() {
		return toLoanCompanyId;
	}

	public void setToLoanCompanyId(Integer toLoanCompanyId) {
		this.toLoanCompanyId = toLoanCompanyId;
	}

	public Integer getToDecoCompanyId() {
		return toDecoCompanyId;
	}

	public void setToDecoCompanyId(Integer toDecoCompanyId) {
		this.toDecoCompanyId = toDecoCompanyId;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
