package com.bf.zxd.zhuangxudai.User;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.PersonYyItem;

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

public class MyAppointmentActivity extends BaseActivity {
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.recyclerview_myAppointment)
    RecyclerView recyclerviewMyAppointment;
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;
    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;
    private Realm realm;
    private NewUser mNewuser;


    @Override
    public void initDate() {


    }


    private void getYysqItem() {
        Subscription Subscription_getYysqItem = NetWork.getNewZXD1_4Service().getPersonYyItem(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonYyItem>>() {
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
                    public void onNext(List<PersonYyItem> yysqItems) {
                        lodingIsFailOrSucess(2);
                        setAdapter(yysqItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getYysqItem);
    }

    private void setAdapter(final List<PersonYyItem> yysqItems) {
        //init context view
        recyclerviewMyAppointment.setLayoutManager(new LinearLayoutManager(this));
        MyAppointmentAdapter myAppointmentAdapter = new MyAppointmentAdapter(yysqItems, this);
        recyclerviewMyAppointment.setAdapter(myAppointmentAdapter);

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_appointment);
        mUnbinder = ButterKnife.bind(this);
        setToolBar();
        mCompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        lodingIsFailOrSucess(1);
        getYysqItem();

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
        baseToolBar.setTitle("我的预约");
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
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
        mUnbinder.unbind();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
