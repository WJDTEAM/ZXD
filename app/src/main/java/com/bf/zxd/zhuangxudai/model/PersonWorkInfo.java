package com.bf.zxd.zhuangxudai.model;

/**
 * Created by Daniel on 2017/1/23.
 */

public class PersonWorkInfo {

    /**
     * income_type : 1
     * monthly_income : 6888
     * local_cpf : 1
     * local_ss : 2
     */

    private String income_type;
    private String monthly_income;
    private String local_cpf;
    private String local_ss;

    public String getIncome_type() {
        return income_type;
    }

    public void setIncome_type(String income_type) {
        this.income_type = income_type;
    }

    public String getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(String monthly_income) {
        this.monthly_income = monthly_income;
    }

    public String getLocal_cpf() {
        return local_cpf;
    }

    public void setLocal_cpf(String local_cpf) {
        this.local_cpf = local_cpf;
    }

    public String getLocal_ss() {
        return local_ss;
    }

    public void setLocal_ss(String local_ss) {
        this.local_ss = local_ss;
    }
}
