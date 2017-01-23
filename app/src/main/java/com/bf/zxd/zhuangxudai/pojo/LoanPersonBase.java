package com.bf.zxd.zhuangxudai.pojo;

public class LoanPersonBase {

    /**
     * credit_status : 2
     * marital_status : 2
     * full_name : ggg
     * mobile_phone : 1234567890
     * id_card : 123123123
     * addr : luoyang
     */

    private int credit_status;
    private int marital_status;
    private String full_name;
    private String mobile_phone;
    private String id_card;
    private String addr;

    public int getCredit_status() {
        return credit_status;
    }

    public void setCredit_status(int credit_status) {
        this.credit_status = credit_status;
    }

    public int getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(int marital_status) {
        this.marital_status = marital_status;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "LoanPersonBase{" +
                "credit_status=" + credit_status +
                ", marital_status=" + marital_status +
                ", full_name='" + full_name + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", id_card='" + id_card + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
