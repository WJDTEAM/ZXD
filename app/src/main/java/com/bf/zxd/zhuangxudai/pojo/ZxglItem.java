package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/13.
 */

public class ZxglItem {

    /**
     * articleId : 30
     * title : 测试文章
     * description : 444
     * thumbnails : http://localhost:8080/zxd-web/upload/6/20170211144245482.jpg
     */

    private int articleId;
    private String title;
    private String description;
    private String thumbnails;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}
