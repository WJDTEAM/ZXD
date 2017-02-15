package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/14.
 */

public class DecoCompanyDetail {
    /**
     * companyId : 6
     * companyName : 测试装修公司01
     * tel : 18615250685
     * addr : 测试地址看看
     * companyIcon : http://211.149.235.17:8080/zxd/upload/6/20170210092256144.jpg
     */

    private int companyId;
    private String companyName;
    private String tel;
    private String addr;
    private String companyIcon;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }
}
