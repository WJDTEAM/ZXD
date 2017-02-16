package com.bf.zxd.zhuangxudai.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by johe on 2017/2/16.
 *  "articleId": Integer 文章Id
 "title": String 文章标题
 "thumbnails":String 缩略图
 "hdrq": String 活动日期
 "description": String 文章摘要
 "type": String 类型 01-装修;02-贷款

 */

public class MyCollection extends RealmObject{

    @PrimaryKey
    Integer articleId;
    String title="";
    String thumbnails="";
    String hdrq="";
    String description="";
    String type="";

    @Override
    public String toString() {
        return "MyCollection{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", hdrq='" + hdrq + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHdrq() {
        return hdrq;
    }

    public void setHdrq(String hdrq) {
        this.hdrq = hdrq;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
