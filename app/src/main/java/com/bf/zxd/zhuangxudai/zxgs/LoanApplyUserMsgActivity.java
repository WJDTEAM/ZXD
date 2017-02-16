package com.bf.zxd.zhuangxudai.zxgs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ApplyPersonBase;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.bf.zxd.zhuangxudai.util.UrlEncoded;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func4;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/23.
 */

public class LoanApplyUserMsgActivity extends AppCompatActivity {


    @BindView(R.id.loan_apply_for_header_img)
    ImageView loanApplyForHeaderImg;
    @BindView(R.id.loan_apply_for_header_title)
    TextView loanApplyForHeaderTitle;
    @BindView(R.id.loan_name_edi)
    EditText loanNameEdi;
    @BindView(R.id.loan_phone_edi)
    EditText loanPhoneEdi;
    @BindView(R.id.loan_id_num_edi)
    EditText loanIdNumEdi;
    @BindView(R.id.loan_marry_rad_true)
    RadioButton loanMarryRadTrue;
    @BindView(R.id.loan_marry_rad_false)
    RadioButton loanMarryRadFalse;
    @BindView(R.id.marry_radioGroup)
    RadioGroup marryRadioGroup;
    @BindView(R.id.loan_credit_rad_1)
    RadioButton loanCreditRad1;
    @BindView(R.id.loan_credit_rad_2)
    RadioButton loanCreditRad2;
    @BindView(R.id.loan_credit_rad_3)
    RadioButton loanCreditRad3;
    @BindView(R.id.loan_credit_rad_4)
    RadioButton loanCreditRad4;
    @BindView(R.id.credit_radioGroup)
    RadioGroup creditRadioGroup;
    @BindView(R.id.loan_city_edi)
    EditText loanCityEdi;
    @BindView(R.id.loan_apply_for_user_msg_btn)
    Button loanApplyForUserMsgBtn;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    private CompositeSubscription compositeSubscription;
    private Realm realm;

    private void setToolbar(String toolstr) {
        baseToolBar.setTitle(toolstr);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        //        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(toolstr);
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
        setContentView(R.layout.loan_apply_for_user_msg);
        ButterKnife.bind(this);
        setToolbar("填写个人信息");
        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        loanMarryRadFalse.setChecked(true);
        loanApplyForUserMsgBtn.setEnabled(true);
//        initApplyFor();
        initData();
        loanApplyForHeaderImg.setImageResource(R.drawable.loan_progress_2);
    }


    /**
     * 对输入框是否为null进行控制
     */
    private void initApplyFor() {

        Observable<CharSequence> nameOs = RxTextView.textChanges(loanNameEdi).skip(1);
        Observable<CharSequence> phoneOs = RxTextView.textChanges(loanPhoneEdi).skip(1);
        Observable<CharSequence> idNumOs = RxTextView.textChanges(loanIdNumEdi).skip(1);
        Observable<CharSequence> cityOs = RxTextView.textChanges(loanCityEdi).skip(1);

        Subscription etSc = Observable.combineLatest(nameOs, phoneOs, idNumOs, cityOs, new Func4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {

                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);
                boolean B3 = !TextUtils.isEmpty(charSequence3);
                boolean B4 = !TextUtils.isEmpty(charSequence4);

                return Bl && B2 && B3 && B4;
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
                loanApplyForUserMsgBtn.setEnabled(aBoolean);
            }
        });
        compositeSubscription.add(etSc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeSubscription.unsubscribe();
    }



    String marital_status="0";
    String credit_status="1";
    ApplyPersonBase mloanPersonBase;

    public void initData() {
        Subscription subscription_getZxgs = NetWork.getNewZXD1_4Service().getPersonBase(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyPersonBase>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApplyPersonBase loanPersonBase) {
                        Log.i("gqf","initData+"+"onNext"+loanPersonBase.toString());
                        loanNameEdi.setText(loanPersonBase.getFullName());
                        loanPhoneEdi.setText(loanPersonBase.getMobilePhone());
                        loanCityEdi.setText(loanPersonBase.getAddr());
                        loanIdNumEdi.setText(loanPersonBase.getIdCard());
                        if (loanPersonBase.getMaritalStatus()==1) {
                            loanMarryRadTrue.setChecked(true);
                            loanMarryRadFalse.setChecked(false);
                            marital_status = "1";
                        } else {
                            loanMarryRadFalse.setChecked(true);
                            loanMarryRadTrue.setChecked(false);
                            marital_status = "0";
                        }
                        if (loanPersonBase.getCreditStatus()==1) {
                            loanCreditRad1.setChecked(true);
                            credit_status = "1";
                        } else if (loanPersonBase.getCreditStatus()==2) {
                            loanCreditRad2.setChecked(true);
                            credit_status = "2";
                        } else if (loanPersonBase.getCreditStatus()==3) {
                            loanCreditRad3.setChecked(true);
                            credit_status = "3";
                        } else {
                            loanCreditRad4.setChecked(true);
                            credit_status = "4";
                        }
                        mloanPersonBase = loanPersonBase;
                    }
                });
        compositeSubscription.add(subscription_getZxgs);
    }

    /*
    (@Field("user_id") String user_id, @Field("full_name") String full_name, @Field("mobile_phone") String mobile_phone,
     @Field("marital_status") String marital_status, @Field("credit_status") String credit_status, @Field("addr") String addr,
     @Field("id_card") String id_card);
     */
    public void applyForLoanPersonBase() {
        loanApplyForUserMsgBtn.setEnabled(false);
        ApplyPersonBase applyPersonBase=new ApplyPersonBase();
        applyPersonBase.setPersonId(realm.where(NewUser.class).findFirst().getUserId());
        applyPersonBase.setFullName(UrlEncoded.toURLEncoded(loanNameEdi.getText().toString()));
        applyPersonBase.setMobilePhone(loanPhoneEdi.getText().toString());
        applyPersonBase.setMaritalStatus(Integer.parseInt(marital_status));
        applyPersonBase.setCreditStatus(Integer.parseInt(credit_status));
        applyPersonBase.setAddr(UrlEncoded.toURLEncoded(loanCityEdi.getText().toString()));
        applyPersonBase.setIdCard(loanIdNumEdi.getText().toString());
        Gson g=new Gson();
        Subscription subscription_getZxgs = NetWork.getNewZXD1_4Service().saveOrUpdatePersonBase(g.toJson(applyPersonBase))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(ResultCode resuleInfo) {
                        if(resuleInfo.getCode()==10001){
                            Toast.makeText(getApplicationContext(),"个人信息提交成功",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(getApplicationContext(),"个人信息提交失败",Toast.LENGTH_SHORT).show();
                            loanApplyForUserMsgBtn.setEnabled(true);
                        }

                    }
                });
        compositeSubscription.add(subscription_getZxgs);


    }

    @OnClick({R.id.loan_apply_for_user_msg_btn,R.id.loan_marry_rad_true, R.id.loan_marry_rad_false, R.id.loan_credit_rad_1, R.id.loan_credit_rad_2, R.id.loan_credit_rad_3, R.id.loan_credit_rad_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_marry_rad_true:
                marital_status="1";
                break;
            case R.id.loan_marry_rad_false:
                marital_status="0";
                break;
            case R.id.loan_credit_rad_1:
                credit_status="1";
                break;
            case R.id.loan_credit_rad_2:
                credit_status="2";
                break;
            case R.id.loan_credit_rad_3:
                credit_status="3";
                break;
            case R.id.loan_credit_rad_4:
                credit_status="4";
                break;
            case R.id.loan_apply_for_user_msg_btn:
                applyForLoanPersonBase();
                break;
        }
    }
}
