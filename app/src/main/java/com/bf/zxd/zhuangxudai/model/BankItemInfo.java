package com.bf.zxd.zhuangxudai.model;

/**
 * Created by Daniel on 2017/1/24.
 */

public class BankItemInfo {

    /**
     * max_money : 10
     * rate : 0.450%
     * bank_id : 10
     * bank_name : 借贷公司04
     * application_num : 0
     * bank_logo : http://211.149.235.17:8080/zxd/upload/bank-20170124125348651.png
     */

    private int max_money;
    private String rate;
    private int bank_id;
    private String bank_name;
    private int application_num;
    private String bank_logo;

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
}
