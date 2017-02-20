package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/14.
 */

public class DecoCompanyYbjDetail {
    /**
     * caseId : 1
     * thumbnails : http://localhost:8080/zxd-web/upload/6/20170210124735771.jpg
     * companyId : 6
     * comments : 0
     * designInspiration :
     * housingSituation : 欧式 80㎡-100㎡ 四居
     * buildingAddr : 测试楼盘地址
     */

    private int caseId;
    private String thumbnails;
    private int companyId;
    private int comments;
    private String designInspiration="";
    private String housingSituation="";
    private String buildingAddr="";

    @Override
    public String toString() {
        return "DecoCompanyYbjDetail{" +
                "caseId=" + caseId +
                ", thumbnails='" + thumbnails + '\'' +
                ", companyId=" + companyId +
                ", comments=" + comments +
                ", designInspiration='" + designInspiration + '\'' +
                ", housingSituation='" + housingSituation + '\'' +
                ", buildingAddr='" + buildingAddr + '\'' +
                '}';
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
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

    public String getHousingSituation() {
        return housingSituation;
    }

    public void setHousingSituation(String housingSituation) {
        this.housingSituation = housingSituation;
    }

    public String getBuildingAddr() {
        return buildingAddr;
    }

    public void setBuildingAddr(String buildingAddr) {
        this.buildingAddr = buildingAddr;
    }
}
