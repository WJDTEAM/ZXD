package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.util.Utils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().getDecoCompanyItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DecoCompanyItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DecoCompanyItem> decoCompanyItems) {
                        setAdapter(decoCompanyItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }


    private void setAdapter(final List<DecoCompanyItem> zxgses) {
        //init context view
        recyclerviewZxgongsi.setLayoutManager(new LinearLayoutManager(this));
//        recyclerviewZxgongsi.addItemDecoration(new RecycleViewDivider(
//                this.getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));
        ZxgsAdapter zxgsAdapter = new ZxgsAdapter(zxgses, this);
        recyclerviewZxgongsi.setAdapter(zxgsAdapter);

        zxgsAdapter.setOnItemClickListener(new ZxgsAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                int  Zxgs_id = zxgses.get(postion).getCompanyId();
                Gson g=new Gson();
                Intent intent = new Intent(ZxgsActivity.this,ZxgsDetailActivity.class);
                intent.putExtra("Zxgs_id",Zxgs_id);
                intent.putExtra("DecoCompanyItemJson",g.toJson(zxgses.get(postion)));
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
    public void initEvent() {



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mCompositeSubscription.unsubscribe();
    }
}
