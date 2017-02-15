package com.bf.zxd.zhuangxudai.my;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.my.fragment.CompanyApplyFragment;
import com.bf.zxd.zhuangxudai.my.fragment.FinancialApplyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/6.
 */

public class LoanApplyForActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    private List<Fragment> fmList;

    private void setToolbar(String toolstr) {

        baseToolBar.setTitle(toolstr);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply_new);
        ButterKnife.bind(this);
        setToolbar("入驻申请");
        fmList = new ArrayList<Fragment>();
        fmList.add(CompanyApplyFragment.newInstance("装修公司入驻申请", ""));
        fmList.add(FinancialApplyFragment.newInstance("金融机构入驻申请", ""));
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fmList.get(position);
            }

            @Override
            public int getCount() {
                return fmList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "装修公司";
                    case 1:
                        return "金融机构";

                }
                return super.getPageTitle(position);
            }
        });

        tablayout.setupWithViewPager(viewpager);


        //        initEdi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
