package com.bf.zxd.zhuangxudai.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.RegistSuccessDialogFragment;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithUser;
import com.bf.zxd.zhuangxudai.util.Phone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.regist_phone_et)
    EditText registPhoneEt;
    @BindView(R.id.regist_password_et)
    EditText registPasswordEt;
    @BindView(R.id.regist_password2_et)
    EditText registPassword2Et;
    @BindView(R.id.regist_password3_et)
    EditText registPassword3Et;
    @BindView(R.id.get_code_btn)
    Button getCodeBtn;
    @BindView(R.id.regist_bt)
    Button registBt;


    String codeMsg="";
    private CompositeSubscription compositeSubscription;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        compositeSubscription = new CompositeSubscription();
        mUnbinder = ButterKnife.bind(this);
    }


    private void register(String phone, String password) {
        NetWork.getNewZXD1_4Service().register(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCodeWithUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }

                    @DebugLog
                    @Override
                    public void onNext(ResultCodeWithUser registResult) {

                        if ((registResult.getCode() == 10001)) {
                            //                            Log.e("Daniel","---userRequestResult.getT().getPassword()----"+userRequestResult.getT().getTel());
                            RegistSuccessDialogFragment registSuccessDialogFragment = new RegistSuccessDialogFragment();
                            registSuccessDialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                                @Override
                                public void dialogDismiss() {
                                    startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                                }
                            });
                            registSuccessDialogFragment.show(getFragmentManager(), "registSuccessDialogFragment");

                        } else {
                            Toast.makeText(RegistActivity.this, "" + registResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        registBt.setEnabled(true);
                        codeMsg="";
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        mUnbinder.unbind();
    }

    Thread thread;
    int timerIndex=60;
    public void resultPhoneCodeTimer(){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(timerIndex!=0){
                    timerIndex--;
                    if(timerIndex==0){
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                }
            }
        });
        thread.start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                getCodeBtn.setEnabled(false);
                getCodeBtn.setText(timerIndex+"秒");

            }else{
                timerIndex=60;
                getCodeBtn.setEnabled(true);
                getCodeBtn.setText("点击获取验证码");
                codeMsg="超时";
            }


        }

    };


    public void getCodeMsg(String phone){
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().shortMsg(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String resultCode) {
                        codeMsg=resultCode;
                        getCodeBtn.setEnabled(true);
                    }
                });
        compositeSubscription.add(Subscription_getZxglItem);
    }
    @OnClick({R.id.get_code_btn, R.id.regist_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code_btn:
                if(registPhoneEt.getText().toString().equals("")){
                    Toast.makeText(this, "请输入手机号后点击获取", Toast.LENGTH_SHORT).show();
                }else{
                    getCodeBtn.setEnabled(false);
                    getCodeMsg(registPhoneEt.getText().toString());
                    //开起线程，60秒
                    resultPhoneCodeTimer();
                }
                break;
            case R.id.regist_bt:
                String _phone = registPhoneEt.getText().toString();
                String _password = registPasswordEt.getText().toString();
                String _password2 = registPassword2Et.getText().toString();
                boolean isCode=true;
                if(codeMsg.equals("超时")&&!registPassword3Et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"验证码过期，请重新获取",Toast.LENGTH_SHORT).show();
                    isCode=false;
                }
                if(isCode&&!codeMsg.equals(registPassword3Et.getText().toString())||registPassword3Et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入的验证码不正确",Toast.LENGTH_SHORT).show();
                    isCode=false;
                }
                if(isCode){
                    if (Phone.IsMobileNO(_phone)) {
                        if (_password.equals(_password2)) {
                            registBt.setEnabled(false);
                            register(_phone, _password);
                        } else {
                            Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                            registPasswordEt.setText("");
                            registPassword2Et.setText("");
                        }
                    } else {
                        registPhoneEt.setText("");
                        Toast.makeText(this, "手机格式不正确！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
