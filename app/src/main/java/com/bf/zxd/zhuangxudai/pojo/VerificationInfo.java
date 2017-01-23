package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/23.
 */

public class VerificationInfo {
    Boolean base;
    Boolean work;
    Boolean asset;

    public Boolean getBase() {
        return base;
    }

    public void setBase(Boolean base) {
        this.base = base;
    }

    public Boolean getWork() {
        return work;
    }

    public void setWork(Boolean work) {
        this.work = work;
    }

    public Boolean getAsset() {
        return asset;
    }

    public void setAsset(Boolean asset) {
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "VerificationInfo{" +
                "base=" + base +
                ", work=" + work +
                ", asset=" + asset +
                '}';
    }
}
