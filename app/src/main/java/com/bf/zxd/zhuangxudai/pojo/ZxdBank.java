package com.bf.zxd.zhuangxudai.pojo;

public class ZxdBank {


    /**
     * make_load_days : 1
     * max_money : 60000
     * rate : 0.050%
     * bank_id : 6
     * money_range : 3000.0000~60000.0000
     * bank_name : 借贷宝
     * application_num : 0
     * bank_logo : http://localhost:8080/zxd/upload/bank-20170123122228131.jpg
     * cycle : 6~48
     * loan_type : 2
     * load_unit : 日
     */

    private int make_load_days;
    private int max_money;
    private String rate;
    private int bank_id;
    private String money_range;
    private String bank_name;
    private int application_num;
    private String bank_logo;
    private String cycle;
    private int loan_type;
    private String load_unit;

    public int getMake_load_days() {
        return make_load_days;
    }

    public void setMake_load_days(int make_load_days) {
        this.make_load_days = make_load_days;
    }

    public int getMax_money() {
        return max_money;
    }

    public void setMax_money(int max_money) {
        this.max_money = max_money;
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

    public int getApplication_num() {
        return application_num;
    }

    public void setApplication_num(int application_num) {
        this.application_num = application_num;
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

    public int getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(int loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoad_unit() {
        return load_unit;
    }

    public void setLoad_unit(String load_unit) {
        this.load_unit = load_unit;
    }

    @Override
    public String toString() {
        return "ZxdBank{" +
                "make_load_days=" + make_load_days +
                ", max_money=" + max_money +
                ", rate='" + rate + '\'' +
                ", bank_id=" + bank_id +
                ", money_range='" + money_range + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", application_num=" + application_num +
                ", bank_logo='" + bank_logo + '\'' +
                ", cycle='" + cycle + '\'' +
                ", loan_type=" + loan_type +
                ", load_unit='" + load_unit + '\'' +
                '}';
    }
}
