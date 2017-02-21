package com.bf.zxd.zhuangxudai.User;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.AutoHeightLayoutManager;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.PersonLoanItem;
import com.bf.zxd.zhuangxudai.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyLoanActivity extends BaseActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.recyclerview_myLoan)
    RecyclerView recyclerviewMyLoan;

    Realm realm;
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;
    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;


    @Override
    public void initDate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        mCompositeSubscription.unsubscribe();
    }

    private void getDksqItem() {
        Subscription Subscription_getDksqItem = NetWork.getNewZXD1_4Service().getPersonLoanItem(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonLoanItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        lodingIsFailOrSucess(3);
                        Log.e("Daniel", "请求装修公司列表数据失败！");

                    }

                    @Override
                    public void onNext(List<PersonLoanItem> PersonLoanItems) {
                        lodingIsFailOrSucess(2);
                        setAdapter(PersonLoanItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getDksqItem);
    }

    private void setAdapter(final List<PersonLoanItem> PersonLoanItems) {
        //init context view
        recyclerviewMyLoan.setLayoutManager(new AutoHeightLayoutManager(this));
        //        recyclerviewZxgongsi.addItemDecoration(new RecycleViewDivider(
        //                this.getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));
        MyLoanAdapter myLoanAdapter = new MyLoanAdapter(PersonLoanItems, this);
        recyclerviewMyLoan.setAdapter(myLoanAdapter);

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_loan);
        mUnbinder = ButterKnife.bind(this);
        mCompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        Utils.init(this);
        setToolBar();
        lodingIsFailOrSucess(1);
        getDksqItem();



    }

    @Override
    public void initEvent() {

    }
    public void lodingIsFailOrSucess(int i) {
        if (i == 1) {
            //加载中
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLodingTxt.setText("加载中...");
            YBJLoding.setBackgroundResource(R.drawable.loding_anim_lists);
            AnimationDrawable anim = (AnimationDrawable) YBJLoding.getBackground();
            anim.start();

        } else if (i == 2) {
            //加载成功
            YBJLoding.setBackground(null);
            YBJLoding.setVisibility(View.GONE);
            YBJLodingTxt.setVisibility(View.GONE);
        } else {
            //加载失败
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLoding.setBackground(null);
            YBJLodingTxt.setText("加载失败，请检查网络连接");
            YBJLoding.setImageResource(R.drawable.ic_loding_fail);
        }
    }

    private void setToolBar() {
        baseToolBar.setTitle("我的贷款");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
