package com.bf.zxd.zhuangxudai.pojo;

import com.bf.zxd.zhuangxudai.template.TemplateActivity;

/**
 * Created by johe on 2017/1/11.
 */

public class CompanyIdAndTemplateActivityEvent {
    int CompanyId;
    Class<TemplateActivity> activityClass;
    public CompanyIdAndTemplateActivityEvent(){}
    public CompanyIdAndTemplateActivityEvent(int companyId, Class<TemplateActivity> activityClass) {
        this.CompanyId = companyId;
        this.activityClass = activityClass;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public Class<TemplateActivity> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<TemplateActivity> activityClass) {
        this.activityClass = activityClass;
    }
}
