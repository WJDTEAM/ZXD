package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/16.
 */

public class PersonLoanItem {

    /**
     * applyId : 10
     * loanAmount : 6
     * loanCompany : 借贷公司01
     * decoCompany : 上海禧鑫装饰工程有限公司
     * referrer : fhjk
     * sqrq : 2017-2-16 16:01
     */

    private int applyId;
    private int loanAmount;
    private String loanCompany;
    private String decoCompany;
    private String referrer;
    private String sqrq;

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanCompany() {
        return loanCompany;
    }

    public void setLoanCompany(String loanCompany) {
        this.loanCompany = loanCompany;
    }

    public String getDecoCompany() {
        return decoCompany;
    }

    public void setDecoCompany(String decoCompany) {
        this.decoCompany = decoCompany;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSqrq() {
        return sqrq;
    }

    public void setSqrq(String sqrq) {
        this.sqrq = sqrq;
    }

    @Override
    public String toString() {
        return "PersonLoanItem{" +
                "applyId=" + applyId +
                ", loanAmount=" + loanAmount +
                ", loanCompany='" + loanCompany + '\'' +
                ", decoCompany='" + decoCompany + '\'' +
                ", referrer='" + referrer + '\'' +
                ", sqrq='" + sqrq + '\'' +
                '}';
    }
}
