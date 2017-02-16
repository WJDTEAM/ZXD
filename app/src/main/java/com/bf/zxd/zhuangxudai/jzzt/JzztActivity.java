package com.bf.zxd.zhuangxudai.JZZT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.zxd.zhuangxudai.Dkhd.LoanDetailsActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 2017/1/10.
 */

public class JzztActivity extends AppCompatActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    private static final String YBJ_TAG = "ybj_flag";
    @BindView(R.id.jzhd_list)
    RecyclerView jzhdList;
    CompositeSubscription compositeSubscription;



    public void initData() {
        Subscription subscription_getZxgs = NetWork.getNewZXD1_4Service().getJzhdItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<JzhdItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<JzhdItem> jzhdItems) {
                        Log.i("gqf","JzhdItem"+jzhdItems.toString());
                        initList(jzhdItems);
                    }
                });
        compositeSubscription.add(subscription_getZxgs);
    }
    JzztListAdapter jzztListAdapter;
    public void initList(final List<JzhdItem> jzhdItems){
        if(jzztListAdapter==null){
            jzztListAdapter=new JzztListAdapter(this,jzhdItems);
            jzhdList.setLayoutManager(new LinearLayoutManager(this));
            jzhdList.setAdapter(jzztListAdapter);
            jzztListAdapter.setOnItemClickListener(new JzztListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Gson g=new Gson();
                    startActivity(new Intent(JzztActivity.this,LoanDetailsActivity.class)
                            .putExtra("type","01")
                            .putExtra("activity_id",g.toJson(jzztListAdapter.getmDatas().get(postion))));
                    Intent intent = new Intent();
                    intent.setClass(JzztActivity.this, JzztDetialActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jzhdItem", jzhdItems.get(postion));
                    intent.putExtras(bundle);
                    startActivity(intent);

//                    startActivity(new Intent(JzztActivity.this,JzztDetialActivity.class).putExtra("activity_id",jzztListAdapter.getmDatas().get(postion));
                }
            });
        }else{
            jzztListAdapter.setmDatas(jzhdItems);
        }

    }

    private void setToolbar() {
        baseToolBar.setTitle("家装活动");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jzzt);
        ButterKnife.bind(this);
        //        String orders_str = getResources().getString(R.string.ybj);
        setToolbar();
        compositeSubscription=new CompositeSubscription();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
