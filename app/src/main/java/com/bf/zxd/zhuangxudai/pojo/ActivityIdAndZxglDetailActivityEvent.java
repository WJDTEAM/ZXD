package com.bf.zxd.zhuangxudai.pojo;

import com.bf.zxd.zhuangxudai.zxgl.ZxglDetailsActivity;

/**
 * Created by johe on 2017/1/11.
 */

public class ActivityIdAndZxglDetailActivityEvent {
    int ActivityId;
    Class<ZxglDetailsActivity> activityClass;

    public ActivityIdAndZxglDetailActivityEvent(int activityId, Class<ZxglDetailsActivity> activityClass) {
        ActivityId = activityId;
        this.activityClass = activityClass;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public Class<ZxglDetailsActivity> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<ZxglDetailsActivity> activityClass) {
        this.activityClass = activityClass;
    }
}
