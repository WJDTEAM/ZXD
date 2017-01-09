package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/1/9.
 */

public class zxgl {

    /**
     * article_id : 4
     * description : 现代图书馆建筑设计较以往更加关注人、自然环境、科学管理及未来发展等因素...
     * title : 质地哥谭（上海高新区）现代风格
     * thumbnails : http://localhost:8080/upload/zxgl-20170106173145687.jpg
     */

    private int article_id;
    private String description;
    private String title;
    private String thumbnails;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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
}
