package com.bf.zxd.zhuangxudai.my;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.CommitDialogFragment;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/6.
 */

public class ApplyForActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar toolbar;
    @BindView(R.id.store_name_edi)
    EditText storeNameEdi;
    @BindView(R.id.store_address_edi)
    EditText storeAddressEdi;
    @BindView(R.id.store_user_name_edi)
    EditText storeUserNameEdi;
    @BindView(R.id.store_nphone_edi)
    EditText storeNphoneEdi;
    @BindView(R.id.apply_for_btn)
    Button applyForBtn;
    CompositeSubscription mcompositeSubscription;

    private void setToolbar(String toolstr) {

        toolbar.setTitle(toolstr);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(toolstr);
        toolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void initEdi() {
        storeNameEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeAddressEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeUserNameEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeNphoneEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
    }
    String unit_name;
    String unit_addr;
    String full_name;
    String phone;
    public void ediLinsener() {
        if (!storeNameEdi.getText().toString().equals("") &&
                !storeAddressEdi.getText().toString().equals("") &&
                !storeUserNameEdi.getText().toString().equals("") &&
                !storeNphoneEdi.getText().toString().equals("")) {
            applyForBtn.setEnabled(true);
            unit_name=storeNameEdi.getText().toString();
            unit_addr=storeAddressEdi.getText().toString();
            full_name=storeUserNameEdi.getText().toString();
            phone=storeNphoneEdi.getText().toString();
        } else {
            applyForBtn.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for);
        ButterKnife.bind(this);
        setToolbar("入驻申请");
        mcompositeSubscription=new CompositeSubscription();
        initEdi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.apply_for_btn)
    public void onClick() {

        //提交申请
        saveRzsq();

    }
    public void saveRzsq(){
        Subscription Subscription_getZxglItem= NetWork.getZxService().saveRzsq(unit_name,unit_addr,full_name,phone)
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
                            showDialog();
                        }else{
                           Toast.makeText(getApplicationContext(),"信息提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);
    }
    CommitDialogFragment dialogFragment;
    public void showDialog(){
        if(dialogFragment==null) {
            dialogFragment = new CommitDialogFragment();
            dialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                @Override
                public void dialogDismiss() {
                    onBackPressed();
                }
            });
        }
        dialogFragment.show(getFragmentManager(),"123");
    }

}
