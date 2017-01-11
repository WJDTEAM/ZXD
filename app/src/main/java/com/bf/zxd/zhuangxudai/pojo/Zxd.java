package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/10.
 * 装修贷银行信息
 *  "article_id":int 文章Id

 "title":String 文章标题

 "thumbnails":String 缩略图

 "bank_id":int 银行Id

 "bank_name":String 银行名称

 "bank_img":String 银行图片
 */

public class Zxd {
    int article_id;
    String title;
    String thumbnails;
    int bank_id;
    String bank_name;
    String bank_img;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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

    public String getBank_img() {
        return bank_img;
    }

    public void setBank_img(String bank_img) {
        this.bank_img = bank_img;
    }

    @Override
    public String toString() {
        return "Zxd{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", bank_id=" + bank_id +
                ", bank_name='" + bank_name + '\'' +
                ", bank_img='" + bank_img + '\'' +
                '}';
    }
}
