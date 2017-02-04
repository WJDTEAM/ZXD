package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
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
import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.model.BankDetail;
import com.bf.zxd.zhuangxudai.model.LoanApplyResult;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.RecommendBank;
import com.bf.zxd.zhuangxudai.pojo.User;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.pojo.dksqinfo;
import com.bf.zxd.zhuangxudai.pojo.zxgs_wjd;
import com.bf.zxd.zhuangxudai.util.UrlEncoded;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
    private int mSex = 1;

    public static int companyId = -1;
    public static int bankId = -1;
    Zxgs mZxgs;
    public  RecommendBank mRecommendBank;
    public  BankDetail mZxd;
    CompositeSubscription mcompositeSubscription;

    Realm realm;

    @Override
    public void initDate() {
        if (bankId != -1) {
            getBankDetail();
        }
        //getBankItem();


    }
//    private void getBankItem() {
//        Subscription Subscription_getBankItem = NetWork.getZxService().getBankItem()
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Func1<List<RecommendBank>, Observable<RecommendBank>>() {
//                    @Override
//                    public Observable<RecommendBank> call(List<RecommendBank> recommendBanks) {
//                        return Observable.from(recommendBanks);
//                    }
//                })
//                .filter(new Func1<RecommendBank, Boolean>() {
//                    @Override
//                    public Boolean call(RecommendBank recommendBank) {
//                        Log.e("Daniel", "----bankId---" + bankId);
//                        Log.e("Daniel", "----recommendBank.getBank_id()---" + recommendBank.getBank_id());
//
//                        return recommendBank.getBank_id()==bankId;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<RecommendBank>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                    @DebugLog
//                    @Override
//                    public void onNext(RecommendBank recommendBank) {
//                        mRecommendBank=recommendBank;
//                        Log.e("Daniel", "----mRecommendBank.getBank_id()---" + mRecommendBank.getBank_id());
//
//
//                    }
//                });
//        mcompositeSubscription.add(Subscription_getBankItem);
//    }

    private void getBankDetail() {
        Log.e("Daniel", "----bankId---" + bankId);
        NetWork.getNewZxService().getBankDetail(bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BankDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BankDetail bankDetail) {
                        mZxd = bankDetail;
                        initBank();

                    }
                });

    }

    LoginHelper loginHelper;
    public boolean initLogin(){
        loginHelper=LoginHelper.getInstence();
        return loginHelper.startActivityWithLogin(this, LoanApplyActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isLogin=initLogin();
        if(isLogin){
            int userId=realm.where(User.class).findFirst().getUser_id();
            initApplyFor();
            initCompanyMsg();
            initBank();
            initEdi();
        }else {
            finish();
        }
    }

//    @Override
//    protected void onP() {
//        super.onStart();
//        boolean isLogin=initLogin();
//        if(isLogin){
//            int userId=realm.where(User.class).findFirst().getUser_id();
//            initApplyFor();
//            initCompanyMsg();
//            initBank();
//            initEdi();
//        }
//    }
    @Override
    public void initView() {
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        setToolBar();
        Log.i("gqf","activity_loan");
        mcompositeSubscription = new CompositeSubscription();
        realm=Realm.getDefaultInstance();


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
        if (companyId != -1) {
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
            applyMoneyTv.setText("申请金额（万）");
            Log.e("Daniel",""+mZxd.getBank_logo());
            if (mZxd.getBank_logo()!=null) {
                Picasso.with(this).load(mZxd.getBank_logo()).into(bankpicImg);
            }
            bankNameTv.setText(mZxd.getBank_name());
            moneyRangeTv.setText(mZxd.getMoney_range());
            cycleUnitTv.setText("申请期限（月）");
            cycleTv.setText(mZxd.getCycle());
            rateUnitTv.setText("月费率");
            rateTv.setText(mZxd.getRate());
            productDescLoanapplyTx.setText(mZxd.getProduct_desc());
            applicationLoanapplyTx.setText(mZxd.getApplication());
            requiredLoanapplyTx.setText(mZxd.getRequired());
            loanMoneyEdi.setHint(mZxd.getMoney_range()+"/万");
            loanTimeEdi.setHint(""+mZxd.getCycle()+"/月");


        } else {
            bankTopLinear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        companyId = -1;
        mZxd = null;
        mZxgs = null;
        bankId=-1;
        realm.close();
        mcompositeSubscription.unsubscribe();
    }

    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
        //跳转sh申请界面

        String [] money=mZxd.getMoney_range().split("~");
        Double d1=Double.parseDouble(loanMoneyEdi.getText().toString());
        Double d2=Double.parseDouble(money[1]);
        Double d3=Double.parseDouble(money[0]);
        String [] time=mZxd.getCycle().split("~");

        Log.i("gqf","onClick"+d1+"onClick"+d2+"onClick"+d3);

        if(companyId==0) {
            Toast.makeText(getApplicationContext(),"请选择公司",Toast.LENGTH_SHORT).show();

        }
        else if(d1<d3){
            Toast.makeText(getApplicationContext(),"您所申请的金额不符合银行规定",Toast.LENGTH_SHORT).show();;
        }
        else if(d1>d2){
            Toast.makeText(getApplicationContext(),"您所申请的金额不符合银行规定",Toast.LENGTH_SHORT).show();;
        }
        else if(!((Integer.parseInt(loanTimeEdi.getText().toString()) < Integer.parseInt(time[1])
                && Integer.parseInt(loanTimeEdi.getText().toString()) > Integer.parseInt(time[0])))){
            Toast.makeText(getApplicationContext(),"您所申请的还款时间不符合银行规定",Toast.LENGTH_SHORT).show();
        }
        else {
            applyFor();
        }

    }

    private dksqinfo mDksqInfo;

    @Override
    protected void onRestart() {
        super.onRestart();
        initCompanyMsg();
        initBank();
//        Log.e("Daniel","------onRestart-----");
//        initApplyFor();



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
                getZxgsItem();

                break;
        }
    }
    private String[] mCompanyDatas;
    private HashMap<String,Integer> map;
    private void getZxgsItem() {
        Subscription subscription_getZxgsItem = NetWork.getZxService().getZxgsItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<zxgs_wjd>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<zxgs_wjd> zxgs_wjds) {
                        int _length = zxgs_wjds.size();
                        mCompanyDatas = new String[_length];
                        map = new HashMap();
                        for (int i = 0; i < _length; i++) {
                            zxgs_wjd _company =zxgs_wjds.get(i);
                            mCompanyDatas[i]=_company.getZxgs_name();

                            map.put(_company.getZxgs_name(),_company.getZxgs_id());

                        }

                        onOptionPicker(mCompanyDatas);

                    }
                });
        mcompositeSubscription.add(subscription_getZxgsItem);

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
            @DebugLog
            @Override
            public void onNext(Boolean aBoolean) {
                Log.e("Daniel","---aBoolean---"+aBoolean);
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
        //realm.where(User.class).findFirst().getUserId()
        Subscription subscription_getZxgs = NetWork.getZxService().saveDksq(mZxd.getBank_id(),realm.where(User.class).findFirst().getUser_id(),companyId,
               new BigDecimal(Double.parseDouble(loanMoneyEdi.getText().toString())) , UrlEncoded.toURLEncoded(loanUseforEdi.getText().toString()),loanTimeEdi.getText().toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoanApplyResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoanApplyResult resuleInfo) {
                        if(resuleInfo.getCode()==10001) {
                            LoanApplyAllMsgActivity.APPLYBASEID=resuleInfo.getApply_base_id();
                            Intent _intent = new Intent(LoanApplyActivity.this, LoanApplyAllMsgActivity.class);
                            _intent.putExtra("Apply_base_id",resuleInfo.getApply_base_id());
                            startActivity(_intent);
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

                if(!editable.toString().equals("")){
                    String [] money=mZxd.getMoney_range().split("~");
                    int m=Integer.parseInt(money[1].substring(0,money[1].indexOf(".")));
                    if(editable.toString().toString().length()>(m+"").length()){
                        editable.delete(loanMoneyEdi.getSelectionStart()-1, loanMoneyEdi.getSelectionStart());
                    }
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
                String [] time=mZxd.getCycle().split("~");
                if(!editable.toString().equals("")) {
                    if (Integer.parseInt(editable.toString()) > Integer.parseInt(time[1])) {
                        editable.delete(loanTimeEdi.getSelectionStart()-1, loanTimeEdi.getSelectionStart());
                    }
                }

            }
        });

    }
}
