package com.bf.zxd.zhuangxudai.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    private CompositeSubscription compositeSubscription;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        compositeSubscription = new CompositeSubscription();
        mUnbinder=ButterKnife.bind(this);
    }

    @OnClick(R.id.regist_bt)
    public void onClick() {
        String _phone = registPhoneEt.getText().toString();
        String _password = registPasswordEt.getText().toString();
        String _password2 = registPassword2Et.getText().toString();
        if (Phone.IsMobileNO(_phone)){
            if (_password.equals(_password2)) {
                register(_phone,_password);
            }else {
                Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                registPasswordEt.setText("");
                registPassword2Et.setText("");
            }
        }else {
            registPhoneEt.setText("");
            Toast.makeText(this, "手机格式不正确！", Toast.LENGTH_SHORT).show();
        }




    }

    private void register(String phone, String password) {
        NetWork.getNewZXD1_4Service().register(phone,password)
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

                        if ((registResult.getCode()==10001)) {
//                            Log.e("Daniel","---userRequestResult.getT().getPassword()----"+userRequestResult.getT().getTel());
                            RegistSuccessDialogFragment registSuccessDialogFragment = new RegistSuccessDialogFragment();
                            registSuccessDialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                                @Override
                                public void dialogDismiss() {
                                    startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                                }
                            });
                            registSuccessDialogFragment.show(getFragmentManager(),"registSuccessDialogFragment");

                        }else {
                            Toast.makeText(RegistActivity.this, ""+registResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        mUnbinder.unbind();
    }
}
