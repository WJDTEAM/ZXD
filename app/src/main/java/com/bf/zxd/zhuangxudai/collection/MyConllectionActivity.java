package com.bf.zxd.zhuangxudai.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bf.zxd.zhuangxudai.Dkhd.LoanDetailsActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.MyCollection;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * Created by johe on 2017/2/16.
 */

public class MyConllectionActivity extends AppCompatActivity {


    @BindView(R.id.my_conllection_toolbar)
    Toolbar myConllectionToolbar;
    @BindView(R.id.my_conllection_tablayout)
    TabLayout myConllectionTablayout;
    @BindView(R.id.my_conllection_viewpager)
    ViewPager myConllectionViewpager;

    Realm realm;

    private void setToolbar(String toolstr) {
        myConllectionToolbar.setTitle(toolstr);
        //        ((TextView)baseToolBar.findViewById(R.id.toolbar_title)).setText(toolstr);
        myConllectionToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(myConllectionToolbar);
        myConllectionToolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        myConllectionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    CompositeSubscription mcompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conllection);
        ButterKnife.bind(this);
        setToolbar("我的收藏");
        realm = Realm.getDefaultInstance();
        EventBus.getDefault().register(this);
        MyConllectionViewPagerAdapter adapter = new MyConllectionViewPagerAdapter(getSupportFragmentManager());
        myConllectionViewpager.setAdapter(adapter);
        myConllectionTablayout.setupWithViewPager(myConllectionViewpager);
        mcompositeSubscription = new CompositeSubscription();
        if (realm.where(NewUser.class).findFirst() != null) {
            initData();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeActivity(ResultCode resultCode) {
        startActivity(new Intent(MyConllectionActivity.this, LoanDetailsActivity.class)
                .putExtra("type", "0" + resultCode.getCode())
                .putExtra("activity_id", resultCode.getMsg()
                ));
    }

    public void updataRealm(List<MyCollection> myCollections) {
        if (myCollections.size() != realm.where(MyCollection.class).findAll().size()) {
            for (MyCollection mc : realm.where(MyCollection.class).findAll()) {
                realm.beginTransaction();
                mc.deleteFromRealm();
                realm.commitTransaction();
            }
            for (MyCollection mc : myCollections) {
                realm.beginTransaction();
                MyCollection Collection = new MyCollection();
                Collection.setArticleId(mc.getArticleId());
                Collection.setDescription(mc.getDescription());
                Collection.setHdrq(mc.getHdrq());
                Collection.setThumbnails(mc.getThumbnails());
                Collection.setTitle(mc.getTitle());
                Collection.setType(mc.getType());
                realm.copyToRealmOrUpdate(Collection);
                realm.commitTransaction();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        mcompositeSubscription.unsubscribe();
        EventBus.getDefault().unregister(this);
    }

    public void initData() {
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().getFavoriteItem(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyCollection>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MyCollection> myCollections) {
                        updataRealm(myCollections);
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);
    }
}
