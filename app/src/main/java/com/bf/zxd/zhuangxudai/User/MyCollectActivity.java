package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bf.zxd.zhuangxudai.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectActivity extends AppCompatActivity {

    @BindView(R.id.main_seat_tablayout)
    TabLayout mainSeatTablayout;
    @BindView(R.id.main_seat_viewpager)
    ViewPager mainSeatViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);

        MyCollectViewpagerAdapter adapter = new MyCollectViewpagerAdapter(getSupportFragmentManager());
        mainSeatViewpager.setAdapter(adapter);
        mainSeatTablayout.setupWithViewPager(mainSeatViewpager);


    }
}
