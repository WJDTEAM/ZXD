package com.bf.zxd.zhuangxudai.my;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.CommitDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private void setToolbar(String toolstr) {

        toolbar.setTitle("");
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(toolstr);
        toolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

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

    public void ediLinsener() {
        if (!storeNameEdi.getText().toString().equals("") &&
                !storeAddressEdi.getText().toString().equals("") &&
                !storeUserNameEdi.getText().toString().equals("") &&
                !storeNphoneEdi.getText().toString().equals("")) {
            applyForBtn.setEnabled(true);
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
        initEdi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.apply_for_btn)
    public void onClick() {
        showDialog();
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
