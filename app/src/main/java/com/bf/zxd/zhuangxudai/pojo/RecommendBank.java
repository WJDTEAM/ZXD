package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/17.
 */

public class RecommendBank {


    /**
     * cycle_unit : 月
     * make_load_days : 30
     * max_money : 500000
     * rate : 0.040%
     * bank_img : http://211.149.235.17:8080/zxd/static/upload/zsyh.gif
     * bank_id : 1
     * money_range : 20000.00~500000.00
     * dkfw : 大额贷
     * bank_name : 招商银行
     * rate_unit : 月
     * cycle : 6~24
     * load_unit : 分钟
     */



    /**
     * "max_money": 50000, 最高可贷金额

     "money_range": "1000.00~50000.00",可贷金额范围

     "rate": "0.060%",费率

     "make_load_days": 10,放款时长

     "load_unit": "小时",放款时长单位

     "cycle": "6~48",贷款期限

     "bank_name": "中国建设银行",  银行名称

     "bank_logo": "http://211.149.235.17:8080/zxd/static/upload/jsyh.gif",银行图标
     "bank_id": 3,银行Id

     "application_num":已申请人数
     */
    private String cycle_unit;
    private int make_load_days;
    private int max_money;
    private String rate;
    private String bank_img;
    private int bank_id;
    private String money_range;
    private String dkfw;
    private String bank_name;
    private String rate_unit;
    private String cycle;
    private String load_unit;

    public String getCycle_unit() {
        return cycle_unit;
    }

    public void setCycle_unit(String cycle_unit) {
        this.cycle_unit = cycle_unit;
    }

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

    public String getBank_img() {
        return bank_img;
    }

    public void setBank_img(String bank_img) {
        this.bank_img = bank_img;
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

    public String getDkfw() {
        return dkfw;
    }

    public void setDkfw(String dkfw) {
        this.dkfw = dkfw;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getRate_unit() {
        return rate_unit;
    }

    public void setRate_unit(String rate_unit) {
        this.rate_unit = rate_unit;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getLoad_unit() {
        return load_unit;
    }

    public void setLoad_unit(String load_unit) {
        this.load_unit = load_unit;
    }
}
