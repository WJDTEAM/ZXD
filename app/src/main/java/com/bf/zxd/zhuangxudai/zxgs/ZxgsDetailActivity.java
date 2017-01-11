package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.daimajia.slider.library.SliderLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ZxgsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.gsTitle_txt)
    TextView gsTitleTxt;
    @BindView(R.id.below_txt)
    TextView belowTxt;
    @BindView(R.id.address_txt)
    TextView addressTxt;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;
    @BindView(R.id.tab_layout)
    LinearLayout tabLayout;
    @BindView(R.id.template_loan_lin)
    LinearLayout templateLoanLin;
    @BindView(R.id.template_subscribe_lin)
    LinearLayout templateSubscribeLin;
    private String title;

    @Override
    public void initDate() {
        title = getIntent().getStringExtra("name");


    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_zxgs_detail);
        ButterKnife.bind(this);
        setToolBar(title);
        gsTitleTxt.setText(title);

    }

    @Override
    public void initEvent() {


    }

    private void setToolBar(String tooltitle) {
        baseToolBar.setTitle(tooltitle);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        baseToolBar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.template_loan_lin, R.id.template_subscribe_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.template_loan_lin:
                startActivity(new Intent(ZxgsDetailActivity.this, LoanApplyActivity.class));
                break;
            case R.id.template_subscribe_lin:
                startActivity(new Intent(ZxgsDetailActivity.this, AppointmentActivity.class));
                break;
        }
    }
}
