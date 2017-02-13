package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;

public class ApplyScheduleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).addActivity(this);
        setContentView(R.layout.activity_apply_schedule);
                VerticalStepViewReverseFragment mVerticalStepViewFragment = new VerticalStepViewReverseFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, mVerticalStepViewFragment).commit();

    }
}
