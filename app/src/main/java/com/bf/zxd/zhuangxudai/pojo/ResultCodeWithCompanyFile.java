package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/14.
 */

public class ResultCodeWithCompanyFile extends ResultCode {
    String companyFile;

    public String getCompanyFile() {
        return companyFile;
    }

    public void setCompanyFile(String companyFile) {
        this.companyFile = companyFile;
    }

    @Override
    public String toString() {
        return "ResultCodeWithCompanyFile{" +
                "companyFile='" + companyFile + '\'' +
                '}';
    }
}
