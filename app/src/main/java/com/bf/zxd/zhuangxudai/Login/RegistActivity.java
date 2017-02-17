package com.bf.zxd.zhuangxudai.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
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


    String codeMsg = "";
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
                        registBt.setEnabled(true);
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

                        codeMsg = "";
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        mUnbinder.unbind();

        //        if(myReceiver!=null)
        //            this.unregisterReceiver(myReceiver);
    }

    Thread thread;
    int timerIndex = 180;

    public void resultPhoneCodeTimer() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isRun = true;
                while (isRun) {
                    timerIndex--;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                    if (timerIndex != 0) {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        isRun = false;
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                }
            }
        });
        thread.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (getCodeBtn != null) {
                    getCodeBtn.setText(timerIndex + "秒");
                }
            } else {
                if (getCodeBtn != null) {
                    timerIndex = 180;
                    getCodeBtn.setEnabled(true);
                    getCodeBtn.setText("点击获取验证码");
                    codeMsg = "超时";
                }
            }
        }
    };

    public void getCodeMsg(String phone) {
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
                        codeMsg = resultCode;
                        getCodeBtn.setEnabled(false);
                        //开起线程，60秒
                        resultPhoneCodeTimer();
                    }
                });
        compositeSubscription.add(Subscription_getZxglItem);
    }

    @OnClick({R.id.get_code_btn, R.id.regist_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code_btn:
                if (registPhoneEt.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入手机号后点击获取", Toast.LENGTH_SHORT).show();
                } else {
                    getCodeBtn.setEnabled(false);
                    getCodeMsg(registPhoneEt.getText().toString());

                }
                break;
            case R.id.regist_bt:
                String _phone = registPhoneEt.getText().toString();
                String _password = registPasswordEt.getText().toString();
                String _password2 = registPassword2Et.getText().toString();
                boolean isCode = true;
                if (isCode && !codeMsg.equals(registPassword3Et.getText().toString()) || registPassword3Et.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入的验证码不正确", Toast.LENGTH_SHORT).show();
                    isCode = false;
                }
                if(!_phone.equals("")&&!_password.equals("")) {
                    if (isCode) {
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
                }else{
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    SmsReceiver myReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        myReceiver = new SmsReceiver();
        //实例化过滤器并设置要过滤的广播
        //        IntentFilter intentFilter = new IntentFilter();
        //        intentFilter.addAction(SMS_ACTION);
        //        this.registerReceiver(myReceiver,intentFilter);
    }

    /**
     * 内部类
     *
     * @author Administrator  15236296272
     *
     * 13501942774
     */
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_ACTION)) {
                Toast.makeText(context, "监测到系统短信", Toast.LENGTH_SHORT).show();

                //获取intent参数
                Bundle bundle = intent.getExtras();
                //判断bundle内容
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    for (Object pdu : pdus) {
                        SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = message.getOriginatingAddress();
                        Log.i("gqf", "sender" + sender);
                    }
                }

                if (bundle != null) {
                    //获得并解析短信
                    Object[] pdus = (Object[]) bundle.get("pdus");//取 pdus内容
                    SmsMessage[] messages = new SmsMessage[pdus.length];//解析短信
                    //获取短信内容 显示到控件
                    for (int i = 0; i < messages.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                        Log.i("gqf", "messages" + messages[i]);
                        Log.i("gqf", "messages" + messages[0].getDisplayOriginatingAddress().toString());
                        Log.i("gqf", "messages" + messages[0].getDisplayMessageBody().toString());
                    }
                    //取消系统短信广播
                    abortBroadcast();
                }
            }
        }
    }

}
