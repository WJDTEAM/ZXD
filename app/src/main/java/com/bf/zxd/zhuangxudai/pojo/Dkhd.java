package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/10.
 * 贷款活动
 *  "article_id":int 文章Id

 "title":String 文章标题

 'description':String 文章摘要

 "thumbnails":String 缩略图
 */

public class Dkhd {

    int article_id;
    String title;
    String thumbnails;

    String  create_time;


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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Dkhd{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
