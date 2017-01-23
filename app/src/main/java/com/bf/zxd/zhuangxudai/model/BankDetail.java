package com.bf.zxd.zhuangxudai.model;

/**
 * Created by Daniel on 2017/1/23.
 */

public class BankDetail {


    /**
     * product_desc : null
     * application : null
     * rate : 0.060%
     * bank_id : 2
     * money_range : 1000.0000~800000.0000
     * bank_name : 2
     * bank_logo : null
     * cycle : 6~48
     * required : null
     */

    private String product_desc;
    private String application;
    private String rate;
    private int bank_id;
    private String money_range;
    private String bank_name;
    private String bank_logo;
    private String cycle;
    private String required;

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getMoney_range() {
        return money_range;
    }

    public void setMoney_range(String money_range) {
        this.money_range = money_range;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(String bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
