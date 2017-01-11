package com.bf.zxd.zhuangxudai.zxgs;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Func3;

public class LoanApplyActivity extends BaseActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.store_name_edi)
    EditText storeNameEdi;
    @BindView(R.id.loan_man_rad)
    RadioButton loanManRad;
    @BindView(R.id.loan_woman_rad)
    RadioButton loanWomanRad;
    @BindView(R.id.store_user_name_edi)
    EditText storeUserNameEdi;
    @BindView(R.id.store_nphone_edi)
    EditText storeNphoneEdi;
    @BindView(R.id.loan_apply_for_btn)
    Button loanApplyForBtn;

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        setToolBar();
        initSubmit();

    }

    private void initSubmit() {
        Observable<CharSequence> userName = RxTextView.textChanges(storeNameEdi).skip(1);
        Observable<CharSequence> phoneNum = RxTextView.textChanges(storeUserNameEdi).skip(1);
        Observable<CharSequence> loanNum = RxTextView.textChanges(storeNphoneEdi).skip(1);

        Observable.combineLatest(userName, phoneNum, loanNum, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean userNameBl = !TextUtils.isEmpty(charSequence);
                boolean phoneNumBl = !TextUtils.isEmpty(charSequence2);
                boolean loanNumBl = !TextUtils.isEmpty(charSequence3);
                return userNameBl&&phoneNumBl&&loanNumBl;
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


    @OnClick(R.id.loan_apply_for_btn)
    public void onClick() {
    }
}
