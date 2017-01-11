package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/11.
 * "company_id":int 公司Id

 "company_name":String 公司名称

 "logo_img":String 公司logo

 "tel":String 公司电话

 "addr":String 公司地址
 */

public class Zxgs {
    int company_id;
    String company_name;
    String logo_img;
    String tel;
    String addr;

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "Zxgs{" +
                "company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", logo_img='" + logo_img + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
