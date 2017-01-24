package com.bf.zxd.zhuangxudai.Login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.LoginResult;
import com.bf.zxd.zhuangxudai.pojo.User;
import com.bf.zxd.zhuangxudai.util.SettingsUtils;
import com.blankj.utilcode.utils.ScreenUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class LoginActivity extends BaseActivity {


    CompositeSubscription compositeSubscription;
    String name;
    String password;
    @BindView(R.id.login_name_et)
    EditText loginNameEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_rememberPassword)
    CheckBox loginRememberPassword;
    @BindView(R.id.login_bt)
    Button loginBt;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.home_regist_btn)
    TextView homeRegistBtn;


    private Realm realm;


    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginBt.setEnabled(false);
        //如果activity集合size不为0则遍历退出activity
        if (((BaseApplication) getApplication()).getListSize() != 0) {
            ((BaseApplication) getApplication()).exit();
        }
        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        initLoginSetting();
        initLogin();
        initLoginBt();

    }

    @Override
    public void initEvent() {

    }

    /**
     * 登录设置
     */
    private void initLoginSetting() {
//        loginAutoLogin.setChecked(SettingsUtils.isAutoLogin(getApplicationContext()));
        loginRememberPassword.setChecked(SettingsUtils.isRememberPassword(getApplicationContext()));
        RxCompoundButton.checkedChanges(loginRememberPassword)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        SettingsUtils.setPrefRememberPassword(getApplicationContext(), aBoolean);
                        if (aBoolean == false) {
//                            loginAutoLogin.setChecked(false);
                            SettingsUtils.setPrefAutoLogin(getApplicationContext(), aBoolean);
                        }
                    }
                });
//        RxCompoundButton.checkedChanges(loginAutoLogin)
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        SettingsUtils.setPrefAutoLogin(getApplicationContext(), aBoolean);
//                        if (aBoolean == true) {
//                            SettingsUtils.setPrefRememberPassword(getApplicationContext(), aBoolean);
//                            loginRememberPassword.setChecked(true);
//                        }
//
//                    }
//                });
        if (SettingsUtils.isRememberPassword(getApplicationContext())) {
            User userInfo = realm.where(User.class).findFirst();
            if (userInfo != null) {
                name = userInfo.getPhone();
                BaseApplication.username = name;
                password = userInfo.getPassword();
                loginNameEt.setText(name);
                loginPasswordEt.setText(password);
                loginBt.setEnabled(true);
                if (SettingsUtils.isAutoLogin(getApplicationContext())) {
                    doLogin();
                }
            }
        }

    }

    /**
     * 对输入框是否为null进行控制
     */
    private void initLogin() {

        Observable<CharSequence> usernameOs = RxTextView.textChanges(loginNameEt).skip(1);
        final Observable<CharSequence> passwordOs = RxTextView.textChanges(loginPasswordEt).skip(1);

        Subscription etSc = Observable.combineLatest(usernameOs, passwordOs, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                boolean usernameBl = !TextUtils.isEmpty(charSequence);
                boolean passwordBl = !TextUtils.isEmpty(charSequence2);
                if (!usernameBl) {
                    loginNameEt.setError("请输入用户名");
                } else {
                    loginNameEt.setError(null);
                }

                if (!passwordBl)
                    loginPasswordEt.setError("请输入密码");
                else
                    loginPasswordEt.setError(null);

                return usernameBl && passwordBl;
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

                name = loginNameEt.getText().toString();
                password = loginPasswordEt.getText().toString();
                loginBt.setEnabled(aBoolean);
            }
        });

        compositeSubscription.add(etSc);
    }

    private void deletUser() {
        realm.beginTransaction();
        User userInfo = realm.where(User.class).findFirst();
        if (userInfo != null) {
            userInfo.deleteFromRealm();
        }
        realm.commitTransaction();
        SettingsUtils.setPrefAutoLogin(getApplicationContext(), false);
        SettingsUtils.setPrefRememberPassword(getApplicationContext(), false);
    }


    private void initLoginBt() {

        Subscription mloginBt = RxView.clicks(loginBt).throttleFirst(400, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        doLogin();
                    }
                });
        compositeSubscription.add(mloginBt);
    }

    private void doLogin() {
        Log.i("gqf", name + password);
        Subscription logSc = NetWork.getUserService().login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onCompleted() {

                    }
                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "登录失败，服务器响应失败", Toast.LENGTH_SHORT).show();
                        deletUser();
                    }
                    @DebugLog
                    @Override
                    public void onNext(LoginResult loginResult) {
                        User userInfo = loginResult.getUser();
                        if (loginResult.getCode() != 10001) {
                            Toast.makeText(LoginActivity.this, "登录失败,用户名密码错误", Toast.LENGTH_SHORT).show();
                            deletUser();
                        } else {
                            User User = realm.where(User.class).findFirst();
                            if (User != null) {
                                //删除本地之前保存的用户信息
                                realm.beginTransaction();
                                User.deleteFromRealm();
                                realm.commitTransaction();
                            }
                            BaseApplication.username = name;
                            realm.beginTransaction();
                            userInfo.setPassword(password);
                            userInfo.setPhone(name);

                            realm.copyToRealmOrUpdate(userInfo);
                            realm.commitTransaction();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        }
                    }
                });

        compositeSubscription.add(logSc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeSubscription.unsubscribe();
    }


    @OnClick(R.id.home_regist_btn)
    public void onClick() {
        startActivity(new Intent(LoginActivity.this,RegistActivity.class));
    }
}
