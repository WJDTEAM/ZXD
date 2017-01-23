package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_apply_for_allmsg);
        ButterKnife.bind(this);
        setToolbar("贷款申请");
        loanApplyForAllMsgBtn.setEnabled(false);

    }

    public void isApplyFor() {
        //判断三种信息是否全部提交


    }



    @OnClick(R.id.loan_apply_for_all_msg_btn)
    public void onClick() {
        //提交信息
        startActivity(new Intent(LoanApplyAllMsgActivity.this,LoanApplyUserMsgActivity.class));
    }
}
