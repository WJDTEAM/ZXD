package com.bf.zxd.zhuangxudai.pojo;

import com.bf.zxd.zhuangxudai.zxgs.ZxgsDetailActivity;

/**
 * Created by johe on 2017/1/11.
 */

public class CompanyIdAndZxgsDetailActivityEvent {
    int CompanyId;
    Class<ZxgsDetailActivity> activityClass;
    public CompanyIdAndZxgsDetailActivityEvent(){}
    public CompanyIdAndZxgsDetailActivityEvent(int companyId, Class<ZxgsDetailActivity> activityClass) {
        this.CompanyId = companyId;
        this.activityClass = activityClass;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public Class<ZxgsDetailActivity> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<ZxgsDetailActivity> activityClass) {
        this.activityClass = activityClass;
    }
}
