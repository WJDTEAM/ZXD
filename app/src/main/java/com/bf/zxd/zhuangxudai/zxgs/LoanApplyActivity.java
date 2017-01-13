package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.CommitDialogFragment;
import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.Zxd;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.store_name_edi)
    EditText storeNameEdi;
    @BindView(R.id.loan_man_rad)
    RadioButton loanManRad;
    @BindView(R.id.loan_woman_rad)
    RadioButton loanWomanRad;
    @BindView(R.id.loan_apply_for_btn)
    Button loanApplyForBtn;
    @BindView(R.id.sex_radioGroup)
    RadioGroup sexRadioGroup;
    @BindView(R.id.store_phone_num_edi)
    EditText storePhoneNumEdi;
    @BindView(R.id.loan_num_edi)
    EditText loanNumEdi;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.choose_company)
    Button chooseCompany;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.choose_bank)
    Button chooseBank;
    private int mSex = 1;

    public static int companyId = 0;
    Zxgs mZxgs;
    public static Zxd mZxd;
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
        initSubmit();
        initCompanyMsg();
        initBank();
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i("Daniel", "---i---" + i);

            }
        });

    }

    private void initSubmit() {
        Observable<CharSequence> userName = RxTextView.textChanges(storeNameEdi).skip(1);
        Observable<CharSequence> phoneNum = RxTextView.textChanges(storePhoneNumEdi).skip(1);
        Observable<CharSequence> loanNum = RxTextView.textChanges(loanNumEdi).skip(1);

        Observable.combineLatest(userName, phoneNum, loanNum, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean userNameBl = !TextUtils.isEmpty(charSequence);
                boolean phoneNumBl = !TextUtils.isEmpty(charSequence2);
                boolean loanNumBl = !TextUtils.isEmpty(charSequence3);
                boolean companyAndbank=false;
                if(companyId!=0&&mZxd!=null)companyAndbank=true;
                return userNameBl && phoneNumBl && loanNumBl&&companyAndbank;
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
    }

    @Override
    public void initEvent() {

    }

    private void setToolBar() {
        baseToolBar.setTitle("贷款申请");
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
            bankName.setText(mZxd.getBank_name());
            chooseBank.setText("点击更换银行");
        } else {
            bankName.setText("当前没有选择银行");
            chooseBank.setText("点击选择银行");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        companyId = 0;
        mZxd = null;
        mZxgs=null;
    }

    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
        saveDksq();
    }

    private void saveDksq() {
        String _full_name = storeNameEdi.getText().toString();
        String _phone = storePhoneNumEdi.getText().toString();
        int _company_id = companyId;

        String _apply_money = loanNumEdi.getText().toString();
        int _bank_id = mZxd.getBank_id();
        NetWork.getZxService().saveDksq(_full_name, _phone, _company_id, mSex, _apply_money, _bank_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResuleInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i("gqf","onError"+e.getMessage());
                    }

                    @Override
                    public void onNext(ResuleInfo resuleInfo) {
                        int resuleCode = resuleInfo.getCode();
                        if (resuleCode == 10000) {
                            Toast.makeText(LoanApplyActivity.this, "贷款申请失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            CommitDialogFragment commitDialogFragment = new CommitDialogFragment();
                            commitDialogFragment.show(getFragmentManager(), "commitDialogFragment");
                            commitDialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                                @Override
                                public void dialogDismiss() {
                                    onBackPressed();
                                }
                            });
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

    @Override
    protected void onRestart() {
        super.onRestart();
        initCompanyMsg();
        initBank();
        if(companyId!=0&&mZxd!=null&&!storeNameEdi.getText().toString().equals("")&&!storePhoneNumEdi.getText().toString().equals("")&&!loanNumEdi.getText().toString().equals("")){
            loanApplyForBtn.setEnabled(true);
        }
    }

    @OnClick({R.id.choose_company, R.id.choose_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_company:
                //选择公司
                startActivity(new Intent(LoanApplyActivity.this, ZxgsActivity.class));
                break;
            case R.id.choose_bank:
                //选择银行
                startActivity(new Intent(LoanApplyActivity.this, MainActivity.class).putExtra("bank","bank"));
                break;
        }
    }
}
