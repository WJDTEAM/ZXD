package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.Login.LoginActivity;
import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.User.ChooseCompanyActivity;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ApplyLoan;
import com.bf.zxd.zhuangxudai.pojo.LoanCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithLoanInfoId;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dksqinfo;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
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
    @BindView(R.id.product_desc_loanapply_tx)
    TextView productDescLoanapplyTx;
    @BindView(R.id.application_loanapply_tx)
    TextView applicationLoanapplyTx;
    @BindView(R.id.required_loanapply_tx)
    TextView requiredLoanapplyTx;
    @BindView(R.id.loan_money_edi)
    EditText loanMoneyEdi;
    @BindView(R.id.loan_time_edi)
    EditText loanTimeEdi;
    @BindView(R.id.loan_usefor_edi)
    EditText loanUseforEdi;
    @BindView(R.id.makeLoadDays_txt)
    TextView makeLoadDays_txt;
    @BindView(R.id.bankpic_img2)
    ImageView bankpicImg2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.cb_st)
    CheckBox cbSt;
    @BindView(R.id.recommendPerson_name_cb)
    CheckBox recommendPersonNameCb;
    @BindView(R.id.recommendPerson_name)
    TextView recommendPersonName;
    @BindView(R.id.company_name_txt)
    TextView companyNameTxt;
    @BindView(R.id.loan_referrer_edi)
    EditText loanReferrerEdi;
    private int mSex = 1;

    public static int companyId = 0;
    public static String mCompanyName = "";
    public static int bankId = -1;
    Zxgs mZxgs;
    public RecommendBank mRecommendBank;
    public LoanCompanyDetail mZxd;
    CompositeSubscription mcompositeSubscription;

    Realm realm;

    @Override
    public void initDate() {


    }

    public void initCheckBox() {
        cbSt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chooseCompany.setVisibility(View.INVISIBLE);
                    companyId = 0;
                    mCompanyName="";
                    companyNameTxt.setText("");
                    companyNameTxt.setVisibility(View.GONE);
                } else {
                    chooseCompany.setVisibility(View.VISIBLE);
                }
            }
        });
        recommendPersonNameCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    loanReferrerEdi.setVisibility(View.INVISIBLE);
                    loanReferrerEdi.setText("");
                } else {
                    loanReferrerEdi.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void getBankDetail() {
        Log.e("Daniel", "----bankId---" + bankId);
        NetWork.getNewZXD1_4Service().getLoanCompanyDetail(bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoanCompanyDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(LoanCompanyDetail loanCompanyDetail) {
                        mZxd = loanCompanyDetail;
                        Log.i("gqf", "onNext" + mZxd.toString());
                        initBank();
                    }
                });

    }

    LoginHelper loginHelper;

    public boolean initLogin() {
        loginHelper = LoginHelper.getInstence();
        return loginHelper.startActivityWithLogin(this, LoanApplyActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        setToolBar();
        Log.i("gqf", "activity_loan");
        mcompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        initApplyFor();

        getBankDetail();
        initCheckBox();
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


    @Override
    protected void onStart() {
        super.onStart();

    }

    public void initBank() {
        if (mZxd != null) {
            Log.i("gqf", "mZxd" + mZxd.toString());
            bankTopLinear.setVisibility(View.VISIBLE);
            applyMoneyTv.setText("申请金额(万)");

            if (mZxd.getCompanyIcon() != null && !mZxd.getCompanyIcon().equals("")) {
                Picasso.with(this).load(mZxd.getCompanyIcon()).into(bankpicImg);

            }
            StringBuilder minMoney=new StringBuilder();
            minMoney.append(mZxd.getMinMoney().toString());
//            while (minMoney.charAt(minMoney.length()-1)=='0'){
//                minMoney.substring(0,minMoney.length()-2);
//            }
//            if(minMoney.charAt(minMoney.length()-1)=='.'){
//                minMoney.substring(0,minMoney.length()-2);
//            }

            StringBuilder maxMoney=new StringBuilder();
            maxMoney.append(mZxd.getMaxMoney().toString());
//            while (maxMoney.charAt(maxMoney.length()-1)=='0'){
//                maxMoney.substring(0,maxMoney.length()-2);
//            }
//            if(maxMoney.charAt(maxMoney.length()-1)=='.'){
//                maxMoney.substring(0,maxMoney.length()-2);
//            }


            bankNameTv.setText(mZxd.getCompanyName());
            moneyRangeTv.setText(minMoney + "~" + maxMoney + "/万");
            cycleUnitTv.setText("还款期限(月)");
            cycleTv.setText("" + mZxd.getMinCycle() + "~" + mZxd.getMaxCycle() + "/月");
            rateUnitTv.setText("月费率");
            rateTv.setText(mZxd.getRate() + "/月");
            productDescLoanapplyTx.setText(mZxd.getProductDesc());
            applicationLoanapplyTx.setText(mZxd.getApplication());
            requiredLoanapplyTx.setText(mZxd.getRequired());
            loanMoneyEdi.setHint(minMoney + "-" +maxMoney + "/万");
            loanTimeEdi.setHint("" + mZxd.getMinCycle() + "-" + mZxd.getMaxCycle() + "/月");
            makeLoadDays_txt.setText("到账速度：" + mZxd.getMakeLoadDays() + mZxd.getLoadUnit() + "内");


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
        isApply = false;
        bankId = -1;
        realm.close();
        mcompositeSubscription.unsubscribe();
    }

    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
        //跳转sh申请界面

        Double d1 = Double.parseDouble(loanMoneyEdi.getText().toString());
        Double d2 = Double.parseDouble(mZxd.getMaxMoney() + "");
        Double d3 = Double.parseDouble(mZxd.getMinMoney() + "");

        Log.i("gqf", "onClick" + cbSt.isChecked() + "onClick" + loanReferrerEdi.getText().toString() + "onClick" + d3);

        if (d1 < d3) {
            Toast.makeText(getApplicationContext(), "您所申请的金额不符合银行规定", Toast.LENGTH_SHORT).show();
        } else if (d1 > d2) {
            Toast.makeText(getApplicationContext(), "您所申请的金额不符合银行规定", Toast.LENGTH_SHORT).show();

        } else if (((Integer.parseInt(loanTimeEdi.getText().toString()) < Integer.parseInt(mZxd.getMinCycle() + "")
                && Integer.parseInt(loanTimeEdi.getText().toString()) > Integer.parseInt(mZxd.getMaxCycle() + "")))) {
            Toast.makeText(getApplicationContext(), "您所申请的还款时间不符合银行规定", Toast.LENGTH_SHORT).show();
        } else if (!cbSt.isChecked() && companyId <= 0) {
            Toast.makeText(getApplicationContext(), "当前没有选择公司，无公司请取消选择", Toast.LENGTH_SHORT).show();
        } else if (!recommendPersonNameCb.isChecked() && loanReferrerEdi.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "当前没有推荐人，无推荐人请取消选择", Toast.LENGTH_SHORT).show();
        } else {
            isApply = true;
            boolean isLogin = initLogin();
            LoginActivity.activity = null;
            if (isLogin) {
                applyFor();
            }

        }

    }

    Boolean isApply = false;
    private dksqinfo mDksqInfo;

    @Override
    protected void onRestart() {
        super.onRestart();
        //initCompanyMsg();
        initBank();
        if (isApply && realm.where(NewUser.class).findFirst() != null) {
            applyFor();
        } else {
            isApply = false;
        }
        if (companyId > 0) {
            chooseCompany.setText("已选择公司");
            companyNameTxt.setText(mCompanyName);
            companyNameTxt.setVisibility(View.VISIBLE);
        } else {
            chooseCompany.setText("点击选择公司");
            companyNameTxt.setText("");
            companyNameTxt.setVisibility(View.GONE);
        }


    }

    public void onOptionPicker(String[] datas) {
        Log.e("Daniel", "---" + datas.length);
        OptionPicker picker = new OptionPicker(this, datas);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(11);
        picker.setLineConfig(new WheelView.LineConfig(0));//使用最长的线
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                //                showToast("index=" + index + ", item=" + item);
                companyName.setText(item);
                companyId = map.get(item);
                Log.e("Daniel", "-companyId--" + companyId);


            }
        });

        picker.show();
    }

    @OnClick({R.id.choose_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_company:
                //选择公司
                //                getZxgsItem();

                startActivity(new Intent(LoanApplyActivity.this, ChooseCompanyActivity.class));

                break;
        }
    }

    private String[] mCompanyDatas;
    private HashMap<String, Integer> map;


    /**
     * 对输入框是否为null进行控制
     */
    private void initApplyFor() {
        Observable<CharSequence> nameOs = RxTextView.textChanges(loanMoneyEdi).skip(1);
        Observable<CharSequence> phoneOs = RxTextView.textChanges(loanTimeEdi).skip(1);
        Observable<CharSequence> useOs = RxTextView.textChanges(loanUseforEdi).skip(1);

        Subscription etSc = Observable.combineLatest(nameOs, phoneOs, useOs, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);
                boolean B3 = !TextUtils.isEmpty(charSequence3);


                return Bl && B2 && B3;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @DebugLog
            @Override
            public void onNext(Boolean aBoolean) {
                Log.e("Daniel", "---aBoolean---" + aBoolean);
                loanApplyForBtn.setEnabled(aBoolean);
            }
        });
        mcompositeSubscription.add(etSc);
    }

    /**
     * 贷款申请保存
     * "fromUserId":Integer 用户ID
     * "toLoanCompanyId":Integer 借贷公司ID
     * "toDecoCompanyId":Integer 装修公司ID（若无，则默认为0）
     * "loanPurpose":String 贷款用途
     * "loanTerm":Integer 贷款周期
     * "loanAmount":String 贷款金额
     * "referrer":String 推荐人
     */
    //提交信息,并跳转页面
    public void applyFor() {
        ApplyLoan applyLoan = new ApplyLoan();
        applyLoan.setFromUserId(realm.where(NewUser.class).findFirst().getUserId());
        applyLoan.setToLoanCompanyId(bankId);
        applyLoan.setToDecoCompanyId(companyId);
        applyLoan.setLoanTerm(Integer.parseInt(loanTimeEdi.getText().toString()));
        applyLoan.setLoanAmount(new BigDecimal(loanMoneyEdi.getText().toString()));
        applyLoan.setReferrer(loanReferrerEdi.getText().toString());
        applyLoan.setLoanPurpose(loanUseforEdi.getText().toString());
        Gson g = new Gson();
        Subscription subscription_getZxgs = NetWork.getNewZXD1_4Service().saveLoanInfo(g.toJson(applyLoan))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCodeWithLoanInfoId>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultCodeWithLoanInfoId resuleInfo) {
                        if (resuleInfo.getCode() == 10001) {
                            LoanApplyAllMsgActivity.APPLYBASEID = resuleInfo.getApplyId();
                            Intent _intent = new Intent(LoanApplyActivity.this, LoanApplyAllMsgActivity.class);
                            _intent.putExtra("Apply_base_id", resuleInfo.getApplyId());
                            startActivity(_intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "信息提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(subscription_getZxgs);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }
}
