package com.bf.zxd.zhuangxudai.zxgs;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.AutoHeightLayoutManager;
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
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;
    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void initDate() {


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
                        lodingIsFailOrSucess(3);
                    }

                    @Override
                    public void onNext(List<DecoCompanyItem> decoCompanyItems) {
                        lodingIsFailOrSucess(2);
                        setAdapter(decoCompanyItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }


    private void setAdapter(final List<DecoCompanyItem> zxgses) {
        //init context view
        recyclerviewZxgongsi.setLayoutManager(new AutoHeightLayoutManager(this));
        ZxgsAdapter zxgsAdapter = new ZxgsAdapter(zxgses, this);
        recyclerviewZxgongsi.setAdapter(zxgsAdapter);
        zxgsAdapter.setOnItemClickListener(new ZxgsAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                int Zxgs_id = zxgses.get(postion).getCompanyId();
                Gson g = new Gson();
                Intent intent = new Intent(ZxgsActivity.this, ZxgsDetailActivity.class);
                intent.putExtra("Zxgs_id", Zxgs_id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initView() {
        setContentView(R.layout.zxgongsi_list);
        mUnbinder = ButterKnife.bind(this);
        setToolBar();
        mCompositeSubscription = new CompositeSubscription();
        Utils.init(this);
        lodingIsFailOrSucess(1);
        getZxgsItem();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
