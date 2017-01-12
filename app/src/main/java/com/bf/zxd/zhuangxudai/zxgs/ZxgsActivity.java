package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.zxgs_wjd;
import com.bf.zxd.zhuangxudai.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 2017/1/9.
 */

public class ZxgsActivity extends BaseActivity {
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.recyclerview_zxgongsi)
    RecyclerView recyclerviewZxgongsi;

//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;

    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void initDate() {
        mCompositeSubscription = new CompositeSubscription();
        Utils.init(this);
        getZxgsItem();

    }

    private void getZxgsItem() {
        Subscription Subscription_getZxglItem = NetWork.getZxService().getZxgsItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<zxgs_wjd>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "请求装修公司列表数据失败！");

                    }

                    @Override
                    public void onNext(List<zxgs_wjd> zxgss) {
                        setAdapter(zxgss);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }


    private void setAdapter(final List<zxgs_wjd> zxgses) {
        //init context view
        recyclerviewZxgongsi.setLayoutManager(new LinearLayoutManager(this));
//        recyclerviewZxgongsi.addItemDecoration(new RecycleViewDivider(
//                this.getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));
        ZxgsAdapter zxgsAdapter = new ZxgsAdapter(zxgses, this);
        recyclerviewZxgongsi.setAdapter(zxgsAdapter);

        zxgsAdapter.setOnItemClickListener(new ZxgsAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                int  Zxgs_id = zxgses.get(postion).getZxgs_id();
                Intent intent = new Intent(ZxgsActivity.this,ZxgsDetailActivity.class);
                intent.putExtra("Zxgs_id",Zxgs_id);
                startActivity(intent);
            }
        });



    }

    @Override
    public void initView() {
        setContentView(R.layout.zxgongsi_list);
        mUnbinder = ButterKnife.bind(this);
        setToolBar();

    }

    private void setToolBar() {
        baseToolBar.setTitle("装修公司");
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

    @Override
    public void initEvent() {



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mCompositeSubscription.unsubscribe();
    }
}
