package com.bf.zxd.zhuangxudai.zxgl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.RecycleViewDivider;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ActivityIdAndZxglDetailActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.zxgl;
import com.bf.zxd.zhuangxudai.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class ZxglActivity extends BaseActivity {
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.recyclerview_zhuangxiugonglue)
    RecyclerView recyclerviewZhuangxiugonglue;
    private Unbinder mUnbinder;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void initDate() {
        mCompositeSubscription = new CompositeSubscription();
        Utils.init(this);
        getZxglItem();

    }

    private void getZxglItem() {
        Subscription Subscription_getZxglItem = NetWork.getZxService().getZxglItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<zxgl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "请求装修攻略列表数据失败！");

                    }

                    @Override
                    public void onNext(List<zxgl> zxgls) {
                        setAdapter(zxgls);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }

    private void setAdapter(List<zxgl> zxgls) {
        //init context view
        recyclerviewZhuangxiugonglue.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewZhuangxiugonglue.addItemDecoration(new RecycleViewDivider(
                this.getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));
        ZxglAdapter zxglAdapter = new ZxglAdapter(zxgls, this);
        recyclerviewZhuangxiugonglue.setAdapter(zxglAdapter);
    }

    @Override
    public void initView() {
        setContentView(R.layout.zhuangxiugonglue_list);
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setToolBar();

    }
    /**
     * 页面跳转
     */
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startActivity(ActivityIdAndZxglDetailActivityEvent v) {
        Intent intent = new Intent(ZxglActivity.this,v.getActivityClass());
        intent.putExtra("activityId",v.getActivityId());
        startActivity(intent);
    }
    private void setToolBar() {
        baseToolBar.setTitle("装修攻略");
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
        EventBus.getDefault().unregister(this);
    }
}
