package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.template.TemplateHorizontalListAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.subscriptions.CompositeSubscription;

public class ZxgsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.gsTitle_txt)
    TextView gsTitleTxt;
    @BindView(R.id.below_txt)
    TextView belowTxt;
    @BindView(R.id.address_txt)
    TextView addressTxt;
//    @BindView(R.id.slider)
//    SliderLayout slider;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;
    @BindView(R.id.tab_layout)
    LinearLayout tabLayout;
    @BindView(R.id.template_loan_lin)
    LinearLayout templateLoanLin;
    @BindView(R.id.template_subscribe_lin)
    LinearLayout templateSubscribeLin;

    @BindView(R.id.all_imgs_RecyclerView)
    RecyclerView allImgsRecyclerView;
    private int Zxgs_id=-1;
    String DecoCompanyItemJson;
    DecoCompanyItem mZxgs;
    private CompositeSubscription compositeSubscription;
    TemplateHorizontalListAdapter templateHorizontalListAdapter;

    public void initDate() {
        compositeSubscription = new CompositeSubscription();
        Gson g=new Gson();
        Zxgs_id = getIntent().getIntExtra("Zxgs_id",-1);
        DecoCompanyItemJson=getIntent().getStringExtra("DecoCompanyItemJson");
        mZxgs=g.fromJson(DecoCompanyItemJson,DecoCompanyItem.class);
        Log.i("gqf",Zxgs_id+"DecoCompanyItem"+mZxgs.toString());
        setToolBar(mZxgs.getCompanyName());
        gsTitleTxt.setText(mZxgs.getCompanyName());
        addressTxt.setText( mZxgs.getAddr());
        belowTxt.setText(mZxgs.getTel());
        if(mZxgs.getCompanyIcon()!=null){
            if(!mZxgs.equals("")){
                Picasso.with(ZxgsDetailActivity.this).load(mZxgs.getCompanyIcon()).error(R.drawable.demo2).into(image);
            }
        }



    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxgs_detail);
        ButterKnife.bind(this);

        compositeSubscription = new CompositeSubscription();
        initDate();

    }

    public void initListView(List<String> zxgs){
        templateHorizontalListAdapter=new TemplateHorizontalListAdapter(ZxgsDetailActivity.this,zxgs);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ZxgsDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        allImgsRecyclerView.setLayoutManager(linearLayoutManager);
        allImgsRecyclerView.setAdapter(templateHorizontalListAdapter);
//        templateHorizontalListAdapter.setOnItemClickListener(new TemplateHorizontalListAdapter.MyItemClickListener() {
//            @Override
//            public void onItemClick(View view, int postion) {
//                mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT,postion+1);
//            }
//        });
    }

    private void setToolBar(String tooltitle) {
        baseToolBar.setTitle(tooltitle);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.template_loan_lin, R.id.template_subscribe_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.template_loan_lin:
                if(LoanApplyActivity.bankId>0) {
                    LoanApplyActivity.companyId = mZxgs.getCompanyId();
                    startActivity(new Intent(ZxgsDetailActivity.this, LoanApplyActivity.class));
                }else{
                    MainActivity.isBottom2=true;
                    startActivity(new Intent(ZxgsDetailActivity.this, MainActivity.class));
                }
//                LoanApplyActivity.companyId=mZxgs.getCompany_id();
//                startActivity(new Intent(ZxgsDetailActivity.this, LoanApplyActivity.class));
                break;
            case R.id.template_subscribe_lin:
                Intent _intent = new Intent(ZxgsDetailActivity.this, AppointmentActivity.class);
                _intent.putExtra("Zxgs_id",Zxgs_id);
                startActivity(_intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
