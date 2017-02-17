package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/16.
 */

public class PersonYyItem {
    Integer applyId;
    String tel;
    String decoCompany;
    String sqrq;
    String proposer;

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDecoCompany() {
        return decoCompany;
    }

    public void setDecoCompany(String decoCompany) {
        this.decoCompany = decoCompany;
    }

    public String getSqrq() {
        return sqrq;
    }

    public void setSqrq(String sqrq) {
        this.sqrq = sqrq;
    }

    @Override
    public String toString() {
        return "PersonYyItem{" +
                "applyId=" + applyId +
                ", tel='" + tel + '\'' +
                ", decoCompany='" + decoCompany + '\'' +
                ", sqrq='" + sqrq + '\'' +
                '}';
    }
}
