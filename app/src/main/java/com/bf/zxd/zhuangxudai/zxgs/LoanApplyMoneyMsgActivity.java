package com.bf.zxd.zhuangxudai.zxgs;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.model.PersonAssetInfo;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.User;
import com.bf.zxd.zhuangxudai.pojo.VerificationInfo;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 2017/1/23.
 */

public class LoanApplyMoneyMsgActivity extends BaseActivity {
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_apply_for_header_img)
    ImageView loanApplyForHeaderImg;
    @BindView(R.id.loan_apply_for_header_title)
    TextView loanApplyForHeaderTitle;
    @BindView(R.id.loan_home_rad_no)
    RadioButton loanHomeRadNo;
    @BindView(R.id.loan_home_rad_have)
    RadioButton loanHomeRadHave;
    @BindView(R.id.loan_home_rad_have_no)
    RadioButton loanHomeRadHaveNo;
    @BindView(R.id.home_radioGroup)
    android.widget.RadioGroup homeRadioGroup;
    @BindView(R.id.loan_wage_edi)
    EditText loanWageEdi;
    @BindView(R.id.loan_home_type_rad_1)
    RadioButton loanHomeTypeRad1;
    @BindView(R.id.loan_home_type_rad_2)
    RadioButton loanHomeTypeRad2;
    @BindView(R.id.loan_home_type_rad_3)
    RadioButton loanHomeTypeRad3;
    @BindView(R.id.loan_home_type_rad_4)
    RadioButton loanHomeTypeRad4;
    @BindView(R.id.loan_home_type_rad_5)
    RadioButton loanHomeTypeRad5;
    @BindView(R.id.loan_home_type_rad_6)
    RadioButton loanHomeTypeRad6;
    @BindView(R.id.loan_home_type_rad_7)
    RadioButton loanHomeTypeRad7;
    @BindView(R.id._radioGroup)
    android.widget.RadioGroup RadioGroup;
    @BindView(R.id.loan_use_home_rad_true)
    RadioButton loanUseHomeRadTrue;
    @BindView(R.id.loan_use_home_rad_false)
    RadioButton loanUseHomeRadFalse;
    @BindView(R.id.use_home_radioGroup)
    android.widget.RadioGroup useHomeRadioGroup;
    @BindView(R.id.loan_city_edi)
    EditText loanCityEdi;
    @BindView(R.id.loan_car_rad_1)
    RadioButton loanCarRad1;
    @BindView(R.id.loan_car_rad_2)
    RadioButton loanCarRad2;
    @BindView(R.id.loan_car_rad_3)
    RadioButton loanCarRad3;
    @BindView(R.id.loan_car_rad_4)
    RadioButton loanCarRad4;
    @BindView(R.id.car_radioGroup)
    android.widget.RadioGroup carRadioGroup;
    @BindView(R.id.loan_use_car_rad_true)
    RadioButton loanUseCarRadTrue;
    @BindView(R.id.loan_use_car_rad_false)
    RadioButton loanUseCarRadFalse;
    @BindView(R.id.use_car_radioGroup)
    android.widget.RadioGroup useCarRadioGroup;
    @BindView(R.id.loan_apply_for_user_msg_btn)
    Button loanApplyForUserMsgBtn;

    private CompositeSubscription compositeSubscription;
    private Realm realm;


    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.loan_apply_for_money_msg);
        ButterKnife.bind(this);
        setToolbar("填写资产信息");
        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        loanApplyForUserMsgBtn.setEnabled(false);
        initApplyFor();
        loanApplyForHeaderImg.setImageResource(R.drawable.loan_progress_1);

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
        Subscription subscription_getZxgs = NetWork.getZxService().getVerificationInfo(realm.where(User.class).findFirst().getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VerificationInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VerificationInfo verificationInfo) {

                        if (verificationInfo.getAsset()) {
                            initAssetInfo();

                        } else {

                        }

                    }
                });
        compositeSubscription.add(subscription_getZxgs);
    }

    private void initAssetInfo() {
        NetWork.getNewZxService().getLoanPersonAsset(realm.where(User.class).findFirst().getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonAssetInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PersonAssetInfo personAssetInfo) {
                        loanWageEdi.setText(personAssetInfo.getHouse_value());
                        loanCityEdi.setText(personAssetInfo.getCar_value());
                        switch (personAssetInfo.getMy_house()){
                            case "1":loanHomeRadHave.setChecked(true);break;
                            case "2":loanHomeRadHaveNo.setChecked(true); break;
                            case "0":loanHomeRadNo.setChecked(true); break;
                        }
                        switch (personAssetInfo.getHouse_type()){
                            case "1":loanHomeTypeRad1.setChecked(true);break;
                            case "2":loanHomeTypeRad2.setChecked(true);break;
                            case "3":loanHomeTypeRad3.setChecked(true);break;
                            case "4":loanHomeTypeRad4.setChecked(true);break;
                            case "5":loanHomeTypeRad5.setChecked(true);break;
                            case "6":loanHomeTypeRad6.setChecked(true);break;
                            case "7":loanHomeTypeRad7.setChecked(true);break;
                        }
                        switch (personAssetInfo.getHouse_guaranty()){
                            case "1":loanUseHomeRadTrue.setChecked(true);break;
                            case "2":loanUseHomeRadFalse.setChecked(true); break;
                        }
                        switch (personAssetInfo.getMy_car()){
                            case "1":loanCarRad1.setChecked(true);break;
                            case "2":loanCarRad2.setChecked(true);break;
                            case "3":loanCarRad3.setChecked(true);break;
                            case "4":loanCarRad4.setChecked(true);break;
                        }
                        switch (personAssetInfo.getCar_guaranty()){
                            case "1":loanUseCarRadTrue.setChecked(true);break;
                            case "2":loanUseCarRadFalse.setChecked(true); break;
                        }
                    }
                });

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
    /**
     * 对输入框是否为null进行控制
     */
    private void initApplyFor() {

        Observable<CharSequence> loanWage = RxTextView.textChanges(loanWageEdi).skip(1);
        Observable<CharSequence> loanCity = RxTextView.textChanges(loanCityEdi).skip(1);
        Subscription etSc = Observable.combineLatest(loanWage, loanCity, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {

                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);


                return Bl && B2 ;
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

    @OnClick({R.id.loan_home_rad_no, R.id.loan_home_rad_have, R.id.loan_home_rad_have_no, R.id.loan_home_type_rad_1, R.id.loan_home_type_rad_2, R.id.loan_home_type_rad_3, R.id.loan_home_type_rad_4, R.id.loan_home_type_rad_5, R.id.loan_home_type_rad_6, R.id.loan_home_type_rad_7, R.id.loan_use_home_rad_true, R.id.loan_use_home_rad_false, R.id.loan_car_rad_1, R.id.loan_car_rad_2, R.id.loan_car_rad_3, R.id.loan_car_rad_4, R.id.loan_use_car_rad_true, R.id.loan_use_car_rad_false, R.id.loan_apply_for_user_msg_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_home_rad_no:
                my_house="0";
                break;
            case R.id.loan_home_rad_have:
                my_house="1";
                break;
            case R.id.loan_home_rad_have_no:
                my_house="2";
                break;
            case R.id.loan_home_type_rad_1:
                house_type="1";
                break;
            case R.id.loan_home_type_rad_2:
                house_type="2";
                break;
            case R.id.loan_home_type_rad_3:
                house_type="3";
                break;
            case R.id.loan_home_type_rad_4:
                house_type="4";
                break;
            case R.id.loan_home_type_rad_5:
                house_type="5";
                break;
            case R.id.loan_home_type_rad_6:
                house_type="6";
                break;
            case R.id.loan_home_type_rad_7:
                house_type="7";
                break;
            case R.id.loan_use_home_rad_true:
                house_guaranty="1";
                break;
            case R.id.loan_use_home_rad_false:
                house_guaranty="2";
                break;
            case R.id.loan_car_rad_1:
                my_car="1";
                break;
            case R.id.loan_car_rad_2:
                my_car="2";
                break;
            case R.id.loan_car_rad_3:
                my_car="3";
                break;
            case R.id.loan_car_rad_4:
                my_car="4";
                break;
            case R.id.loan_use_car_rad_true:
                car_guaranty = "1";
                break;
            case R.id.loan_use_car_rad_false:
                car_guaranty = "2";
                break;
            case R.id.loan_apply_for_user_msg_btn:
                saveOrUpdatePersonWork();
                break;
        }
    }
    String  my_house;
    String  house_value;
    String  house_type;
    String  house_guaranty;
    String  my_car;
    String  car_value;
    String  car_guaranty;
    private void saveOrUpdatePersonWork() {
        house_value = loanWageEdi.getText().toString();
        car_value = loanCityEdi.getText().toString();
        NetWork.getNewZxService().saveOrUpdatePersonAsset(realm.where(User.class).findFirst().getUser_id(),my_house,house_value,house_type,house_guaranty,my_car,car_value,car_guaranty)
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
                        if(resuleInfo.getCode()==10001){
                            Toast.makeText(getApplicationContext(),"个人资产信息提交成功",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(getApplicationContext(),"个人资产信息提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
