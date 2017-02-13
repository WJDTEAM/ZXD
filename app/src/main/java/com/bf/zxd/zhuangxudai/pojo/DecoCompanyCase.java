package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/13.
 */

public class DecoCompanyCase {
    /**
     * caseId : 1
     * caseName : 测试案例
     * thumbnails : http://localhost:8080/zxd-web/upload/6/20170210124735771.jpg
     */

    private int caseId;
    private String caseName;
    private String thumbnails;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}
