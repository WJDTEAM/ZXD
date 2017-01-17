package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/1/17.
 */

public class EnterActivityEvent {
    Class activityClass;

    public EnterActivityEvent(Class activityClass) {
        this.activityClass = activityClass;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }
}
