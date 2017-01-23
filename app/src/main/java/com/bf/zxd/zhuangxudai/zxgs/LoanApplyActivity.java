package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dksqinfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoanApplyActivity extends BaseActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.bankpic_img)
    ImageView bankpicImg;
    @BindView(R.id.bank_name_tv)
    TextView bankNameTv;
    @BindView(R.id.apply_money_tv)
    TextView applyMoneyTv;
    @BindView(R.id.money_range_tv)
    TextView moneyRangeTv;
    @BindView(R.id.cycle_unit_tv)
    TextView cycleUnitTv;
    @BindView(R.id.cycle_tv)
    TextView cycleTv;
    @BindView(R.id.rate_unit_tv)
    TextView rateUnitTv;
    @BindView(R.id.rate_tv)
    TextView rateTv;
    @BindView(R.id.bank_top_linear)
    LinearLayout bankTopLinear;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.choose_company)
    Button chooseCompany;
    @BindView(R.id.loan_apply_for_btn)
    Button loanApplyForBtn;
    private int mSex = 1;

    public static int companyId = 0;
    Zxgs mZxgs;
    public static RecommendBank mZxd;
    CompositeSubscription mcompositeSubscription;

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        setToolBar();
        mcompositeSubscription = new CompositeSubscription();
        initCompanyMsg();
        initBank();

    }



    @Override
    public void initEvent() {

    }

    private void setToolBar() {
        baseToolBar.setTitle("贷款详情");
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

    public void initCompanyMsg() {
        if (companyId != 0) {
            Log.i("gqf", "companyId" + companyId);
            Subscription subscription = NetWork.getZxService().getZxgs(companyId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Zxgs>() {
                        @Override
                        public void onCompleted() {
                            Log.i("gqf", "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("gqf", "onError" + e.getMessage());
                        }

                        @Override
                        public void onNext(Zxgs zxgs) {
                            Log.i("gqf", "mListener" + zxgs.toString());
                            mZxgs = zxgs;
                            companyName.setText(mZxgs.getCompany_name());
                            chooseCompany.setText("点击更换公司");
                        }
                    });
            mcompositeSubscription.add(subscription);

        } else {
            companyName.setText("当前没有选择公司");
            chooseCompany.setText("点击选择公司");
        }
    }

    public void initBank() {
        if (mZxd != null) {
            bankTopLinear.setVisibility(View.VISIBLE);
            applyMoneyTv.setText("申请金额（元）");

            Picasso.with(this).load(mZxd.getBank_img()).into(bankpicImg);
            bankNameTv.setText(mZxd.getBank_name());
            moneyRangeTv.setText(mZxd.getMoney_range());
            cycleUnitTv.setText("申请期限（" + mZxd.getCycle_unit() + ")");
            cycleTv.setText(mZxd.getCycle());
            rateUnitTv.setText(mZxd.getCycle_unit() + "费率");
            rateTv.setText(mZxd.getRate());

        } else {
            bankTopLinear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        companyId = 0;
        mZxd = null;
        mZxgs = null;
    }

    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
        //跳转sh申请界面

    }

    private dksqinfo mDksqInfo;

    @Override
    protected void onRestart() {
        super.onRestart();
        initCompanyMsg();
        initBank();

    }

    @OnClick({R.id.choose_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_company:
                //选择公司
                startActivity(new Intent(LoanApplyActivity.this, ZxgsActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
