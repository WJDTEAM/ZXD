package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChangeNickActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.changeNickName_et)
    EditText changeNickNameEt;
    @BindView(R.id.changeNick_bt)
    Button changeNickBt;
    private Realm realm;
    private NewUser mUser;
    private CompositeSubscription compositeSubscription;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).addActivity(this);
        setContentView(R.layout.activity_change_nick);
        compositeSubscription = new CompositeSubscription();
        mUnbinder=ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        String _nickName=getIntent().getStringExtra("nickName");
        changeNickNameEt.setText(_nickName);

    }

    @OnClick(R.id.changeNick_bt)
    public void onClick() {

        Subscription  editNickname=NetWork.getNewZXD1_4Service().editNickname(realm.where(NewUser.class).findFirst().getUserId(),changeNickNameEt.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {

                    }
                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }
                    @DebugLog
                    @Override
                    public void onNext(ResultCode resultCode) {
                        if (resultCode.getCode()==10001){
                            Toast.makeText(ChangeNickActivity.this, ""+resultCode.getMsg(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChangeNickActivity.this, ""+resultCode.getMsg(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        compositeSubscription.add(editNickname);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        mUnbinder.unbind();

    }
}
