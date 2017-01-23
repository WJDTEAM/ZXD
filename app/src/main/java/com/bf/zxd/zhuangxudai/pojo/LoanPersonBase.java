package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/23.
 *  "full_name":String 姓名

 "mobile_phone":String 手机号

 "marital_status":String 婚姻情况：0-未婚；1-已婚'

 "credit_status":String '信用状况：1-无信用卡和贷款；2-信用良好；3-一年内预期少于3次且少于90天；4:-一年内预期三次或超过90天'

 "addr":String '所在城市'
 "id_card":String '身份证号'
 */

public class LoanPersonBase {
    String full_name;
    String mobile_phone;
    String marital_status;
    String credit_status;
    String addr;
    String id_card;

    public java.lang.String getId_card() {
        return id_card;
    }

    public void setId_card(java.lang.String id_card) {
        this.id_card = id_card;
    }

    public java.lang.String getFull_name() {
        return full_name;
    }

    public void setFull_name(java.lang.String full_name) {
        this.full_name = full_name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public java.lang.String getCredit_status() {
        return credit_status;
    }

    public void setCredit_status(java.lang.String credit_status) {
        this.credit_status = credit_status;
    }

    public java.lang.String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(java.lang.String marital_status) {
        this.marital_status = marital_status;
    }

    public java.lang.String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(java.lang.String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }
}
