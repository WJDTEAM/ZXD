package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/17.
 * "cycle_unit": "月",
 "make_load_days": 30,
 "max_money": 500000,
 "rate": "0.040%",
 "bank_img": "http://211.149.235.17:8080/zxd/static/upload/zsyh.gif",
 "bank_id": 1,
 "money_range": "20000.00~500000.00",
 "dkfw": "大额贷",
 "bank_name": "招商银行",
 "rate_unit": "月",
 "cycle": "6~24",
 "load_unit": "分钟"
 */

public class ZxdItem {

   String cycle_unit;
    int        make_load_days;
    int      max_money;
    String        rate;
    String       bank_img;
    int     bank_id;
    String        money_range;
    String       dkfw;
    String       bank_name;
    String       rate_unit;
    String       cycle;
    String        load_unit;

    public String getCycle_unit() {
        return cycle_unit;
    }

    public void setCycle_unit(String cycle_unit) {
        this.cycle_unit = cycle_unit;
    }

    public String getLoad_unit() {
        return load_unit;
    }

    public void setLoad_unit(String load_unit) {
        this.load_unit = load_unit;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getRate_unit() {
        return rate_unit;
    }

    public void setRate_unit(String rate_unit) {
        this.rate_unit = rate_unit;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getDkfw() {
        return dkfw;
    }

    public void setDkfw(String dkfw) {
        this.dkfw = dkfw;
    }

    public String getMoney_range() {
        return money_range;
    }

    public void setMoney_range(String money_range) {
        this.money_range = money_range;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_img() {
        return bank_img;
    }

    public void setBank_img(String bank_img) {
        this.bank_img = bank_img;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getMax_money() {
        return max_money;
    }

    public void setMax_money(int max_money) {
        this.max_money = max_money;
    }

    public int getMake_load_days() {
        return make_load_days;
    }

    public void setMake_load_days(int make_load_days) {
        this.make_load_days = make_load_days;
    }

    @Override
    public String toString() {
        return "ZxdItem{" +
                "cycle_unit='" + cycle_unit + '\'' +
                ", make_load_days=" + make_load_days +
                ", max_money=" + max_money +
                ", rate='" + rate + '\'' +
                ", bank_img='" + bank_img + '\'' +
                ", bank_id=" + bank_id +
                ", money_range='" + money_range + '\'' +
                ", dkfw='" + dkfw + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", rate_unit='" + rate_unit + '\'' +
                ", cycle='" + cycle + '\'' +
                ", load_unit='" + load_unit + '\'' +
                '}';
    }
}
