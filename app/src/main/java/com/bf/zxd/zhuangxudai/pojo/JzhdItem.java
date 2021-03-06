package com.bf.zxd.zhuangxudai.pojo;

import java.io.Serializable;

/**
 * Created by johe on 2017/2/13.
 */

public class JzhdItem implements Serializable {

    /**
     * articleId : 29
     * title : 测试活动01
     * thumbnails : http://localhost:8080/zxd-web/upload/6/20170211142407995.jpg
     * hdrq : 2017/02/17~2017/02/28
     * addr : 测试活动地址
     * sponsor : 测试活动主办方
     */

    private int articleId;
    private String title;
    private String thumbnails;
    private String hdrq;
    private String addr;
    private String sponsor;
    private int companyId;

    @Override
    public String toString() {
        return "JzhdItem{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", hdrq='" + hdrq + '\'' +
                ", addr='" + addr + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", companyId=" + companyId +
                '}';
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getHdrq() {
        return hdrq;
    }

    public void setHdrq(String hdrq) {
        this.hdrq = hdrq;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
}
