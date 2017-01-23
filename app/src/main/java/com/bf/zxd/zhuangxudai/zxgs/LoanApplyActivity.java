package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dksqinfo;
import com.bf.zxd.zhuangxudai.pojo.user;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.loan_money_edi)
    EditText loanMoneyEdi;
    @BindView(R.id.loan_time_edi)
    EditText loanTimeEdi;
    @BindView(R.id.loan_usefor_edi)
    EditText loanUseforEdi;
    private int mSex = 1;

    public static int companyId = 0;
    Zxgs mZxgs;
    public static RecommendBank mZxd;
    CompositeSubscription mcompositeSubscription;

    Realm realm;

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        setToolBar();
        mcompositeSubscription = new CompositeSubscription();
        realm=Realm.getDefaultInstance();
        initApplyFor();
        initCompanyMsg();
        initBank();
        initEdi();
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
        realm.close();
        mcompositeSubscription.unsubscribe();
    }

    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
        //跳转sh申请界面
        applyFor();

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

    /**
     * 对输入框是否为null进行控制
     */
    private void initApplyFor() {
        Observable<CharSequence> nameOs = RxTextView.textChanges(loanMoneyEdi).skip(1);
        Observable<CharSequence> phoneOs = RxTextView.textChanges(loanTimeEdi).skip(1);
        Observable<CharSequence> useOs = RxTextView.textChanges(loanUseforEdi).skip(1);

        Subscription etSc = Observable.combineLatest(nameOs, phoneOs,useOs, new Func3<CharSequence, CharSequence, CharSequence,Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2,CharSequence charSequence3) {
                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);
                boolean B3 = !TextUtils.isEmpty(charSequence3);
                return Bl && B2 &&B3;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                loanApplyForBtn.setEnabled(aBoolean);
            }
        });
        mcompositeSubscription.add(etSc);
    }

    /**
     * 贷款申请保存
     */
    //提交信息,并跳转页面
    public void applyFor() {

        Subscription subscription_getZxgs = NetWork.getZxService().saveDksq(mZxd.getBank_id(),realm.where(user.class).findFirst().getUserId(),companyId,
               new BigDecimal(loanMoneyEdi.getText().toString()) ,loanUseforEdi.getText().toString(),loanTimeEdi.getText().toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResuleInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResuleInfo resuleInfo) {
                        if(resuleInfo.getCode()==10001) {
                            startActivity(new Intent(LoanApplyActivity.this, LoanApplyAllMsgActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"信息提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(subscription_getZxgs);


    }

    public void initEdi(){

        loanMoneyEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(Integer.parseInt(editable.toString())>mZxd.getMax_money()){
                    loanMoneyEdi.setText(editable.toString().indexOf(0,editable.toString().length()-1));
                }
            }
        });
        loanTimeEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String [] time=mZxd.getRate_unit().split("~");
                if(Integer.parseInt(editable.toString())>Integer.parseInt(time[1])){
                    loanTimeEdi.setText(editable.toString().indexOf(0,editable.toString().length()-1));
                }else if(Integer.parseInt(editable.toString())<Integer.parseInt(time[0])){
                    loanTimeEdi.setText(editable.toString().indexOf(0,editable.toString().length()-1));
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }
}
