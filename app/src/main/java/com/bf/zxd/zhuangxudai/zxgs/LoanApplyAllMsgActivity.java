package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.VerificationInfo;
import com.bf.zxd.zhuangxudai.pojo.user;

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
 * Created by johe on 2017/1/23.
 */

public class LoanApplyAllMsgActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_apply_for_header_img)
    ImageView loanApplyForHeaderImg;
    @BindView(R.id.loan_apply_for_header_title)
    TextView loanApplyForHeaderTitle;
    @BindView(R.id.user_msg_is_apply_for)
    TextView userMsgIsApplyFor;
    @BindView(R.id.work_msg_is_apply_for)
    TextView workMsgIsApplyFor;
    @BindView(R.id.money_msg_is_apply_for)
    TextView moneyMsgIsApplyFor;
    @BindView(R.id.loan_apply_for_all_msg_btn)
    Button loanApplyForAllMsgBtn;
    @BindView(R.id.user_msg_is_apply_for_lin)
    LinearLayout userMsgIsApplyForLin;
    @BindView(R.id.work_msg_is_apply_for_lin)
    LinearLayout workMsgIsApplyForLin;
    @BindView(R.id.money_msg_is_apply_for_lin)
    LinearLayout moneyMsgIsApplyForLin;

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

    CompositeSubscription mcompositeSubscription;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_apply_for_allmsg);
        ButterKnife.bind(this);
        setToolbar("贷款申请");
        loanApplyForAllMsgBtn.setEnabled(false);
        mcompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
    }

    public void isApplyFor() {
        //判断三种信息是否全部提交
        Subscription subscription_getZxgs = NetWork.getZxService().getVerificationInfo(realm.where(user.class).findFirst().getUserId())
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
                        if (verificationInfo.getBase()) {
                            userMsgIsApplyFor.setText("已申请");
                            userMsgIsApplyFor.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            workMsgIsApplyFor.setText("未申请");
                            workMsgIsApplyFor.setTextColor(getResources().getColor(R.color.black));
                        }
                        if (verificationInfo.getAsset()) {

                            moneyMsgIsApplyFor.setText("已申请");
                            moneyMsgIsApplyFor.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            workMsgIsApplyFor.setText("未申请");
                            workMsgIsApplyFor.setTextColor(getResources().getColor(R.color.black));
                        }
                        if (verificationInfo.getWork()) {

                            workMsgIsApplyFor.setText("已申请");
                            workMsgIsApplyFor.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            workMsgIsApplyFor.setText("未申请");
                            workMsgIsApplyFor.setTextColor(getResources().getColor(R.color.black));
                        }
                        if (verificationInfo.getBase() && verificationInfo.getAsset() && verificationInfo.getWork()) {
                            loanApplyForAllMsgBtn.setEnabled(true);
                        } else {
                            loanApplyForAllMsgBtn.setEnabled(false);
                        }
                    }
                });
        mcompositeSubscription.add(subscription_getZxgs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isApplyFor();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        mcompositeSubscription.unsubscribe();
    }

    @OnClick({R.id.user_msg_is_apply_for_lin, R.id.work_msg_is_apply_for_lin, R.id.money_msg_is_apply_for_lin,R.id.loan_apply_for_all_msg_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_msg_is_apply_for_lin:
                startActivity(new Intent(LoanApplyAllMsgActivity.this, LoanApplyUserMsgActivity.class));
                break;
            case R.id.work_msg_is_apply_for_lin:
                break;
            case R.id.money_msg_is_apply_for_lin:
                break;
            case R.id.loan_apply_for_all_msg_btn:
                //提交全部资料


                break;


        }
    }


}
