package com.bf.zxd.zhuangxudai.Dkhd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.Dkhd;
import com.blankj.utilcode.utils.KeyboardUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        baseToolBar.setTitle("");
        ((TextView)baseToolBar.findViewById(R.id.toolbar_title)).setText(toolstr);
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
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        setToolbar("贷款活动");
        initDkhdData();
    }

    Observer<List<Dkhd>> observer=new Observer<List<Dkhd>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i("gqf","onError"+e.toString());
        }

        @Override
        public void onNext(List<Dkhd> Dkhds) {
            initListView(Dkhds);
        }
    };

    public void initDkhdData(){
        Subscription Subscription_getZxglItem= NetWork.getZxService().getDkhdItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mcompositeSubscription.add(Subscription_getZxglItem);
    }

    public void initListView(List<Dkhd> Dkhds){
        if(loanActivityListAdapter==null){
            loanActivityListAdapter=new LoanActivityListAdapter(this,Dkhds);
            loanActivityList.setLayoutManager(new LinearLayoutManager(this));
            loanActivityList.setAdapter(loanActivityListAdapter);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        KeyboardUtils.hideSoftInput(this);
        if (mcompositeSubscription != null && !mcompositeSubscription.isUnsubscribed()) {
            mcompositeSubscription.unsubscribe();
        }
    }
}
