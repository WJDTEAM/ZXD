package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.bf.zxd.zhuangxudai.template.TemplateHorizontalListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZxgsDetailActivity extends BaseActivity implements View.OnClickListener {

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
    Zxgs mZxgs;
    TemplateHorizontalListAdapter templateHorizontalListAdapter;

    @Override
    public void initDate() {
        Zxgs_id = getIntent().getIntExtra("Zxgs_id",-1);

        if(Zxgs_id==-1){

        }
        NetWork.getZxService().getZxgs(Zxgs_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Zxgs>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Zxgs zxgs) {
                        String _title = zxgs.getCompany_name();
                        setToolBar(_title);
                        gsTitleTxt.setText(_title);
                        String _address = zxgs.getAddr();
                        addressTxt.setText(_address);
                        String _tel = zxgs.getTel();
                        belowTxt.setText(_tel);
                        Picasso.with(ZxgsDetailActivity.this).load(zxgs.getLogo_img()).error(R.drawable.demo2).into(image);
                        mZxgs=zxgs;
                    }
                });


    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_zxgs_detail);
        ButterKnife.bind(this);

        initListView();


    }

    @Override
    public void initEvent() {


    }

    List<String> data;
    public void initListView(){
        data=new ArrayList<>();
        for(int i=0;i<14;i++){
            data.add("");
        }
        templateHorizontalListAdapter=new TemplateHorizontalListAdapter(ZxgsDetailActivity.this,data);
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
        baseToolBar.setNavigationIcon(R.drawable.back);
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
                LoanApplyActivity.companyId=mZxgs.getCompany_id();
                startActivity(new Intent(ZxgsDetailActivity.this, LoanApplyActivity.class));
                break;
            case R.id.template_subscribe_lin:
                startActivity(new Intent(ZxgsDetailActivity.this, AppointmentActivity.class));
                break;
        }
    }
}
