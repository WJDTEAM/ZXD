package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/14.
 */

public class ApplyForInformatin {
    private String proposer;
    private String locationArea;
    private String detailedAddr;
    private String companyName;
    private String businessLicense;
    private String idCard;
    private String applyType;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ApplyForInformatin{" +
                "proposer='" + proposer + '\'' +
                ", locationArea='" + locationArea + '\'' +
                ", detailedAddr='" + detailedAddr + '\'' +
                ", companyName='" + companyName + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", idCard='" + idCard + '\'' +
                ", applyType='" + applyType + '\'' +
                '}';
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getDetailedAddr() {
        return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
        this.detailedAddr = detailedAddr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
}
