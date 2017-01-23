package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/17.
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


 "make_load_days": 1,
 "max_money": 60000,
 "rate": "0.050%",
 "bank_id": 6,
 "money_range": "3000.0000~60000.0000",
 "bank_name": "借贷宝",
 "application_num": null,
 "bank_logo": "http://localhost:8080/zxd/upload/bank-20170123122228131.jpg",
 "cycle": "6~48",
 "loan_type": 2,
 "load_unit": "日"
 */

public class ZxdBank {



    String rate;

    String money_range;
    String bank_name;

    String bank_logo;
    String cycle;

    String load_unit;

    @Override
    public String toString() {
        return "ZxdBank{" +
                "rate='" + rate + '\'' +
                ", money_range='" + money_range + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_logo='" + bank_logo + '\'' +
                ", cycle='" + cycle + '\'' +
                ", load_unit='" + load_unit + '\'' +
                '}';
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(String bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getMoney_range() {
        return money_range;
    }

    public void setMoney_range(String money_range) {
        this.money_range = money_range;
    }
}
