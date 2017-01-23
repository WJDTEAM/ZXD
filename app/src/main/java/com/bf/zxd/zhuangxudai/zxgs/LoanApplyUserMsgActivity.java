package com.bf.zxd.zhuangxudai.zxgs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func5;
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
    @BindView(R.id.loan_usefor_edi)
    EditText loanUseforEdi;
    @BindView(R.id.loan_apply_for_user_msg_btn)
    Button loanApplyForUserMsgBtn;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    private CompositeSubscription compositeSubscription;
    private Realm realm;

    String name;
    String phone;
    String idNum;
    String city;
    String useFor;
    int isMarry=1;
    int creditType=1;


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
        loanApplyForUserMsgBtn.setEnabled(false);
        initApplyFor();
    }


    /**
     * 对输入框是否为null进行控制
     */
    private void initApplyFor() {

        Observable<CharSequence> nameOs = RxTextView.textChanges(loanNameEdi).skip(1);
         Observable<CharSequence> phoneOs = RxTextView.textChanges(loanPhoneEdi).skip(1);
        Observable<CharSequence> idNumOs = RxTextView.textChanges(loanIdNumEdi).skip(1);
        Observable<CharSequence> cityOs = RxTextView.textChanges(loanCityEdi).skip(1);
        Observable<CharSequence> userForOs = RxTextView.textChanges(loanUseforEdi).skip(1);

        Subscription etSc = Observable.combineLatest(nameOs, phoneOs, idNumOs, cityOs, userForOs, new Func5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {

                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);
                boolean B3 = !TextUtils.isEmpty(charSequence3);
                boolean B4 = !TextUtils.isEmpty(charSequence4);
                boolean B5 = !TextUtils.isEmpty(charSequence5);

                return Bl &&B2&&B3&&B4&&B5;
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
}
