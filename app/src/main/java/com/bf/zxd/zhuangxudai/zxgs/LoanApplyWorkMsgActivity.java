package com.bf.zxd.zhuangxudai.zxgs;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ApplyPersonWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.google.gson.Gson;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 2017/1/23.
 */

public class LoanApplyWorkMsgActivity extends BaseActivity {
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_apply_for_header_img)
    ImageView loanApplyForHeaderImg;
    @BindView(R.id.loan_apply_for_header_title)
    TextView loanApplyForHeaderTitle;
    @BindView(R.id.loan_wage_way_rad_1)
    RadioButton loanWageWayRad1;
    @BindView(R.id.loan_wage_way_rad_2)
    RadioButton loanWageWayRad2;
    @BindView(R.id.loan_wage_way_rad_3)
    RadioButton loanWageWayRad3;
    @BindView(R.id.wage_way_radioGroup)
    RadioGroup wageWayRadioGroup;
    @BindView(R.id.loan_accumulation_rad_true)
    RadioButton loanAccumulationRadTrue;
    @BindView(R.id.loan_accumulation_rad_false)
    RadioButton loanAccumulationRadFalse;
    @BindView(R.id.accumulation_radioGroup)
    RadioGroup accumulationRadioGroup;
    @BindView(R.id.loan_social_security_rad_true)
    RadioButton loanSocialSecurityRadTrue;
    @BindView(R.id.loan_social_security_rad_false)
    RadioButton loanSocialSecurityRadFalse;
    @BindView(R.id.social_security_radioGroup)
    RadioGroup socialSecurityRadioGroup;
    @BindView(R.id.loan_wage_edi)
    EditText loanWageEdi;
    @BindView(R.id.loan_apply_for_user_msg_btn)
    Button loanApplyForUserMsgBtn;

    private CompositeSubscription compositeSubscription;
    private Realm realm;


    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        ((BaseApplication)getApplication()).addActivity(this);
        setContentView(R.layout.loan_apply_for_work_msg);
        ButterKnife.bind(this);
        setToolbar("填写工作信息");
        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        loanApplyForUserMsgBtn.setEnabled(false);
        loanWageEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!loanWageEdi.getText().toString().equals("")) {
                    loanApplyForUserMsgBtn.setEnabled(true);
                }


            }
        });
        loanApplyForHeaderImg.setImageResource(R.drawable.loan_progress_3);

        income_type="1";
        local_cpf="1";
        local_ss="1";

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        isApplyFor();
    }
    public void isApplyFor() {
        //判断三种信息是否全部提交
        Subscription subscription_getZxgs = NetWork.getNewZXD1_4Service().getPersonWork(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyPersonWork>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApplyPersonWork personWorkInfo) {

                        if(personWorkInfo!=null){
                            loanWageEdi.setText(personWorkInfo.getMonthlyIncome()+"");
                            switch (personWorkInfo.getIncomeType()){
                                case 1:loanWageWayRad1.setChecked(true);break;
                                case 2:loanWageWayRad2.setChecked(true); break;
                                case 3:loanWageWayRad3.setChecked(true); break;
                            }
                            switch (personWorkInfo.getLocalCpf()){
                                case 1:loanAccumulationRadTrue.setChecked(true);break;
                                case 0:loanAccumulationRadFalse.setChecked(true); break;
                            }
                            switch (personWorkInfo.getLocalSs()){
                                case 0:loanSocialSecurityRadFalse.setChecked(true);break;
                                case 1:loanSocialSecurityRadTrue.setChecked(true); break;
                            }
                        }

                    }
                });
        compositeSubscription.add(subscription_getZxgs);
    }



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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeSubscription.unsubscribe();
    }

    @OnClick({R.id.loan_wage_way_rad_1, R.id.loan_wage_way_rad_2, R.id.loan_wage_way_rad_3, R.id.loan_accumulation_rad_true, R.id.loan_accumulation_rad_false, R.id.loan_social_security_rad_true, R.id.loan_social_security_rad_false, R.id.loan_apply_for_user_msg_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_wage_way_rad_1:
                income_type="1";
                break;
            case R.id.loan_wage_way_rad_2:
                income_type="2";
                break;
            case R.id.loan_wage_way_rad_3:
                income_type="3";
                break;
            case R.id.loan_accumulation_rad_true:
                local_cpf="1";
                break;
            case R.id.loan_accumulation_rad_false:
                local_cpf="0";
                break;
            case R.id.loan_social_security_rad_true:
                local_ss="1";
                break;
            case R.id.loan_social_security_rad_false:
                local_ss="0";
                break;
            case R.id.loan_apply_for_user_msg_btn:
                saveOrUpdatePersonWork();
                break;
        }
    }

    /**
     * 保存更新个人工作信息
     */
    private String income_type;
    private String monthly_income;
    private String local_cpf;
    private String local_ss;
    private void saveOrUpdatePersonWork() {
        monthly_income = loanWageEdi.getText().toString();

        loanApplyForUserMsgBtn.setEnabled(false);
        ApplyPersonWork applyPersonWork=new ApplyPersonWork();
        applyPersonWork.setPersonId(realm.where(NewUser.class).findFirst().getUserId());
        applyPersonWork.setIncomeType(Integer.parseInt(income_type));
        applyPersonWork.setLocalCpf(Integer.parseInt(local_cpf));
        applyPersonWork.setLocalSs(Integer.parseInt(local_ss));
        applyPersonWork.setMonthlyIncome(new BigDecimal(monthly_income));
        Gson g=new Gson();
        NetWork.getNewZXD1_4Service().saveOrUpdatePersonWork(g.toJson(applyPersonWork))
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
                        if (resuleInfo.getCode() == 10001) {
                            Toast.makeText(getApplicationContext(), "信息提交成功", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(getApplicationContext(), "信息提交失败", Toast.LENGTH_SHORT).show();
                            loanApplyForUserMsgBtn.setEnabled(true);
                        }

                    }
                });

    }
}
