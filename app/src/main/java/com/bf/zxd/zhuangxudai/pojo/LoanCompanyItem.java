package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/13.
 */

public class LoanCompanyItem {
    /**
     * companyId : 8
     * companyIcon : http://localhost:8080/zxd-web/upload/8/20170211102007911.png
     * companyName : 借贷公司01
     * makeLoadDays : 30
     * loadUnit : 小时
     * rate : 0.453%
     * cycle : 6~48
     * maxMoney : 500
     * loanTypeName : 抵押贷款
     */

    private int companyId;
    private String companyIcon;
    private String companyName;
    private int makeLoadDays;
    private String loadUnit;
    private String rate;
    private String cycle;
    private int maxMoney;
    private String loanTypeName;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getMakeLoadDays() {
        return makeLoadDays;
    }

    public void setMakeLoadDays(int makeLoadDays) {
        this.makeLoadDays = makeLoadDays;
    }

    public String getLoadUnit() {
        return loadUnit;
    }

    public void setLoadUnit(String loadUnit) {
        this.loadUnit = loadUnit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }
}
