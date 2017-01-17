package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/16.
 */

public class DksqItem {

    /**
     * full_name : 泡泡
     * apply_base_id : 50
     * apply_money : 100000
     * phone : 13701948749
     * sq_date : 2017-01-16 14:27
     * company_name : 上海柠檬树装饰工程有限公司
     * bank_name : 中国工商银行
     * status : 1
     */

    private String full_name;
    private int apply_base_id;
    private int apply_money;
    private String phone;
    private String sq_date;
    private String company_name;
    private String bank_name;
    private int status;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getApply_base_id() {
        return apply_base_id;
    }

    public void setApply_base_id(int apply_base_id) {
        this.apply_base_id = apply_base_id;
    }

    public int getApply_money() {
        return apply_money;
    }

    public void setApply_money(int apply_money) {
        this.apply_money = apply_money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSq_date() {
        return sq_date;
    }

    public void setSq_date(String sq_date) {
        this.sq_date = sq_date;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
