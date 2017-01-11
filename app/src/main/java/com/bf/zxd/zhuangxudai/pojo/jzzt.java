package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/9.
 * 家装专题列表item信息
 */

public class jzzt {

    /**
     * article_id : 5
     * company_id : 1
     * companyName : 超级管理员
     * description : 现代图书馆建筑设计较以往更加关注人、自然环境、科学管理及未来发展等因素...
     * title : 质地哥谭（上海高新区）现代风格
     * thumbnails : http://localhost:8080/upload/jzzt-20170106173445189.jpg
     */

    private int article_id;
    private int company_id;
    private String companyName;
    private String description;
    private String title;
    private String thumbnails;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "jzzt{" +
                "article_id=" + article_id +
                ", company_id=" + company_id +
                ", companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                '}';
    }
}
