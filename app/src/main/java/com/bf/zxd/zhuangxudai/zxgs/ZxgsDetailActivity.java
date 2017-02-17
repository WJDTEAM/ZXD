package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyCase;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyDetail;
import com.bf.zxd.zhuangxudai.template.TemplateDetailsFragment;
import com.bf.zxd.zhuangxudai.template.TemplateHorizontalListAdapter;
import com.bf.zxd.zhuangxudai.template.TemplateImgFragment;
import com.bf.zxd.zhuangxudai.util.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.bf.zxd.zhuangxudai.zxgs.AppointmentActivity.CompanyId;

public class ZxgsDetailActivity extends AppCompatActivity implements View.OnClickListener,TemplateImgFragment.mImgListener, TemplateDetailsFragment.mDetailsListener {

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
    DecoCompanyDetail mZxgs;
    private CompositeSubscription compositeSubscription;
    TemplateHorizontalListAdapter templateHorizontalListAdapter;

    public void initDate() {
        compositeSubscription = new CompositeSubscription();
        Zxgs_id = getIntent().getIntExtra("Zxgs_id",-1);

        initCompanyData(Zxgs_id);
        getDecoCompanyCase(Zxgs_id);
    }

    private void getDecoCompanyCase(int zxgs_id) {
        Subscription getDecoCompanyCaseImages = NetWork.getNewZXD1_4Service().getDecoCompanyCase(zxgs_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DecoCompanyCase>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DecoCompanyCase> decoCompanyCases) {
                        List<String> strings = new ArrayList<String>();
                        for (DecoCompanyCase decoCompanyCase:decoCompanyCases){
                            strings.add(decoCompanyCase.getThumbnails());
                        }
                        initListView(strings);

                    }
                });
        compositeSubscription.add(getDecoCompanyCaseImages);
    }


    public void initCompanyData(int id){
        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DecoCompanyDetail>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(DecoCompanyDetail zxgs) {
                        mZxgs =zxgs;
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
                });
        compositeSubscription.add(subscription);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxgs_detail);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
//        changeSystemBarColor(R.color.black_dark);
        initDate();

    }

    public void initListView(List<String> zxgs){
        templateHorizontalListAdapter=new TemplateHorizontalListAdapter(ZxgsDetailActivity.this,zxgs);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ZxgsDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        allImgsRecyclerView.setLayoutManager(linearLayoutManager);
        allImgsRecyclerView.setAdapter(templateHorizontalListAdapter);
        templateHorizontalListAdapter.setOnItemClickListener(new TemplateHorizontalListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                // TODO: 2017/2/17 公司详情案例
//                mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT,postion+1);
            }
        });
    }

    public void changeSystemBarColor(int id) {
        // 改变状�?�栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(id);// 通知栏所�?颜色
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
    LoginHelper loginHelper;
    public boolean initLogin(Class activity) {
        loginHelper = LoginHelper.getInstence();
        return loginHelper.startActivityWithLogin(ZxgsDetailActivity.this, activity);
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
                    CompanyId=Zxgs_id;
                if (initLogin(AppointmentActivity.class)) {
                    Log.e("Daniel","---Zxgs_id---"+CompanyId);
                    Intent _intent = new Intent(ZxgsDetailActivity.this, AppointmentActivity.class);
                    startActivity(_intent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    @Override
    public int getCompanyId() {
        return 0;
    }

    @Override
    public void startActivity(Class activity) {

    }

    @Override
    public List<String> getImgAddress() {
        return null;
    }

    @Override
    public void setCompanyId(int id) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public int getToolBarheight() {
        return 0;
    }

    @Override
    public boolean isToolBarShow() {
        return false;
    }

    @Override
    public void changeFragmentByTAG(String fragment, int index) {

    }
}
