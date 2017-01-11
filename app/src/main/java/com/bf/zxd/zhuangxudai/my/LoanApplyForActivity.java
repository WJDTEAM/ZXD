package com.bf.zxd.zhuangxudai.my;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class LoanApplyForActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.store_name_edi)
    EditText storeNameEdi;
    @BindView(R.id.store_user_name_edi)
    EditText storeUserNameEdi;
    @BindView(R.id.store_nphone_edi)
    EditText storeNphoneEdi;
    @BindView(R.id.loan_apply_for_btn)
    Button loanApplyForBtn;
    @BindView(R.id.loan_man_rad)
    RadioButton loanManRad;
    @BindView(R.id.loan_woman_rad)
    RadioButton loanWomanRad;


    String sex="";
    private void setToolbar(String toolstr) {

        toolbar.setTitle(toolstr);
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
        sex="男";
        loanManRad.setChecked(true);
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
                !storeUserNameEdi.getText().toString().equals("") &&
                !storeNphoneEdi.getText().toString().equals("")) {
            loanApplyForBtn.setEnabled(true);
        } else {
            loanApplyForBtn.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply);
        ButterKnife.bind(this);
        setToolbar("入驻申请");
        initEdi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    CommitDialogFragment dialogFragment;

    public void showDialog() {
        if (dialogFragment == null) {
            dialogFragment = new CommitDialogFragment();
            dialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                @Override
                public void dialogDismiss() {
                    onBackPressed();
                }
            });
        }
        dialogFragment.show(getFragmentManager(), "123");
    }

    @OnClick({R.id.loan_man_rad, R.id.loan_woman_rad, R.id.loan_apply_for_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_man_rad:
                sex="男";
                break;
            case R.id.loan_woman_rad:
                sex="女";
                break;
            case R.id.loan_apply_for_btn:
                showDialog();
                break;
        }
    }
}
