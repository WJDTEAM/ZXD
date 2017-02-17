package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/16.
 */

public class ApplyStatusItem {
    String status;
    String comment;
    String statusRq;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatusRq() {
        return statusRq;
    }

    public void setStatusRq(String statusRq) {
        this.statusRq = statusRq;
    }

    @Override
    public String toString() {
        return "ApplyStatusItem{" +
                "status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", statusRq='" + statusRq + '\'' +
                '}';
    }
}
