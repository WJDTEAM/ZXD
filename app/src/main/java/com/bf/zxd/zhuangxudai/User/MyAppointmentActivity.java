package com.bf.zxd.zhuangxudai.User;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;
    private Realm realm;
    private NewUser mNewuser;


    @Override
    public void initDate() {
        mCompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        getYysqItem();

    }



    private void getYysqItem() {
        Subscription Subscription_getYysqItem = NetWork.getNewZXD1_4Service().getPersonYyItem( realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonYyItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "请求装修公司列表数据失败！");

                    }

                    @Override
                    public void onNext(List<PersonYyItem> yysqItems) {
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

    }

    @Override
    public void initEvent() {

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
}
