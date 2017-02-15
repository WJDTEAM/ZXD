package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/14.
 */

public class DecoCompanyYbjItem {

    /**
     * caseId : 4
     * caseName : 样板间测试01
     * thumbnails : http://localhost:8080/zxd-web/upload/6/ybj-20170210164717391.jpg
     * companyId : 6
     * comments : 0
     * designInspiration :
     */

    private int caseId;
    private String caseName;
    private String thumbnails;
    private int companyId;
    private int comments;
    private String designInspiration;

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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getDesignInspiration() {
        return designInspiration;
    }

    public void setDesignInspiration(String designInspiration) {
        this.designInspiration = designInspiration;
    }
}
