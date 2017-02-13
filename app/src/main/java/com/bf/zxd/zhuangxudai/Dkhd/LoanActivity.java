package com.bf.zxd.zhuangxudai.Dkhd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DkhdItem;
import com.bf.zxd.zhuangxudai.template.TemplateListAdapter;
import com.blankj.utilcode.utils.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/10.
 */

public class LoanActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_activity_list)
    RecyclerView loanActivityList;
    Realm realm;
    CompositeSubscription mcompositeSubscription;

    LoanActivityListAdapter loanActivityListAdapter;
    private void setToolbar(String toolstr) {
        baseToolBar.setTitle(toolstr);
//        ((TextView)baseToolBar.findViewById(R.id.toolbar_title)).setText(toolstr);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_activity_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        setToolbar("贷款活动");
        initDkhdData();
    }
    /**
     * 页面跳转
     */
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startActivity(Class<Activity> activity) {
        startActivity(new Intent(LoanActivity.this,activity));
    }

    Observer<List<DkhdItem>> observer=new Observer<List<DkhdItem>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i("gqf","onError"+e.toString());
        }

        @Override
        public void onNext(List<DkhdItem> Dkhds) {
            initListView(Dkhds);
        }
    };

    public void initDkhdData(){
        Subscription Subscription_getZxglItem= NetWork.getNewZXD1_4Service().getDkhdItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mcompositeSubscription.add(Subscription_getZxglItem);
    }

    public void initListView(List<DkhdItem> Dkhds){
        if(loanActivityListAdapter==null){
            loanActivityListAdapter=new LoanActivityListAdapter(this,Dkhds);
            loanActivityList.setLayoutManager(new LinearLayoutManager(this));
            loanActivityList.setAdapter(loanActivityListAdapter);
            loanActivityListAdapter.setOnItemClickListener(new TemplateListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    startActivity(new Intent(LoanActivity.this,LoanDetailsActivity.class).putExtra("activity_id",loanActivityListAdapter.getmDatas().get(postion).getArticleId()));
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        KeyboardUtils.hideSoftInput(this);
        EventBus.getDefault().unregister(this);
        if (mcompositeSubscription != null && !mcompositeSubscription.isUnsubscribed()) {
            mcompositeSubscription.unsubscribe();
        }
    }
}
