package com.bf.zxd.zhuangxudai.pojo;

import java.math.BigDecimal;

/**
 * Created by johe on 2017/2/13.
 */

public class LoanCompanyDetail {
    /**
     * companyId : 8
     * companyIcon : http://localhost:8080/zxd-web/upload/8/20170211102007911.png
     * companyName : 借贷公司01
     * makeLoadDays : 30
     * loadUnit : 小时
     * rate : 0.453%
     * minCycle : 6
     * maxCycle : 48
     * minMoney : 6
     * maxMoney : 500
     * productDesc : null
     * application : null
     * required : null
     */

    private int companyId;
    private String companyIcon;
    private String companyName;
    private int makeLoadDays;
    private String loadUnit;
    private String rate;
    private Integer minCycle;
    private Integer maxCycle;
    private BigDecimal minMoney;
    private BigDecimal maxMoney;
    private String  productDesc;
    private String  application;
    private String  required;

    @Override
    public String toString() {
        return "LoanCompanyDetail{" +
                "companyId=" + companyId +
                ", companyIcon='" + companyIcon + '\'' +
                ", companyName='" + companyName + '\'' +
                ", makeLoadDays=" + makeLoadDays +
                ", loadUnit='" + loadUnit + '\'' +
                ", rate='" + rate + '\'' +
                ", minCycle=" + minCycle +
                ", maxCycle=" + maxCycle +
                ", minMoney=" + minMoney +
                ", maxMoney=" + maxMoney +
                ", productDesc='" + productDesc + '\'' +
                ", application='" + application + '\'' +
                ", required='" + required + '\'' +
                '}';
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public Integer getMaxCycle() {
        return maxCycle;
    }

    public void setMaxCycle(Integer maxCycle) {
        this.maxCycle = maxCycle;
    }

    public Integer getMinCycle() {
        return minCycle;
    }

    public void setMinCycle(Integer minCycle) {
        this.minCycle = minCycle;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLoadUnit() {
        return loadUnit;
    }

    public void setLoadUnit(String loadUnit) {
        this.loadUnit = loadUnit;
    }

    public int getMakeLoadDays() {
        return makeLoadDays;
    }

    public void setMakeLoadDays(int makeLoadDays) {
        this.makeLoadDays = makeLoadDays;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }
}
