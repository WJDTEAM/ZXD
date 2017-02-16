package com.bf.zxd.zhuangxudai.Dkhd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.Login.LoginActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.bf.zxd.zhuangxudai.pojo.MyCollection;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.bf.zxd.zhuangxudai.zxgs.AppointmentActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/10.
 */

public class LoanDetailsActivity extends AppCompatActivity {


    Realm realm;
    CompositeSubscription mcompositeSubscription;

    @BindView(R.id.imageUrl_top_img)
    ImageView imageUrlTopImg;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.sponsor_tv)
    TextView sponsorTv;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.apply_bt)
    Button applyBt;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;


    MyCollection myCollection;
    boolean isCollection = false;
    @BindView(R.id.web_top_lin)
    LinearLayout webTopLin;

    public void saveData() {
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().saveFavorite(realm.where(NewUser.class).findFirst().getUserId(),
                id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultCode resultCode) {
                        if (resultCode.getCode() == 10001) {
                            isCollection = true;
                            invalidateOptionsMenu();
                            //保存在本地
                            if (realm.where(MyCollection.class).equalTo("articleId", id).findAll().size() == 0) {
                                realm.beginTransaction();
                                MyCollection Collection = realm.createObject(MyCollection.class);
                                Collection.setArticleId(id);
                                Collection.setDescription(myCollection.getDescription());
                                Collection.setHdrq(myCollection.getHdrq());
                                Collection.setThumbnails(myCollection.getThumbnails());
                                Collection.setTitle(myCollection.getTitle());
                                Collection.setType(myCollection.getType());
                                realm.commitTransaction();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "收藏失败，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCollection == false) {
            getMenuInflater().inflate(R.menu.collection_article_false, menu);
        } else {
            getMenuInflater().inflate(R.menu.collection_article_true, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isCollection == false && item.getItemId() == R.id.collection_false) {
            //收藏当前文章
            if (realm.where(NewUser.class).findFirst() == null) {
                startActivity(new Intent(LoanDetailsActivity.this, LoginActivity.class));
            } else {
                saveData();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar(String toolstr) {
        baseToolBar.setTitle(toolstr);
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
        setContentView(R.layout.activity_jzzt_detial);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        setToolbar("活动详情");
        initWebView();


    }

    boolean isJzhdItem = false;
    JzhdItem mJzhdItem;

    private void initView() {
        if (mJzhdItem.getThumbnails() != null) {
            Picasso.with(this).load(mJzhdItem.getThumbnails()).into(imageUrlTopImg);
        } else {
            Picasso.with(this).load(R.drawable.demo).into(imageUrlTopImg);
        }
        titleTv.setText(mJzhdItem.getTitle());
        timeTv.setText(mJzhdItem.getHdrq());
        addressTv.setText(mJzhdItem.getAddr());
        sponsorTv.setText(mJzhdItem.getSponsor());

    }

    int id;
    String type = "0";

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
                        isCollection = judgeCollection(myCollections);
                        invalidateOptionsMenu();
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);

    }

    public boolean judgeCollection(List<MyCollection> myCollections) {
        Log.i("gqf", myCollections.toString());
        for (MyCollection myCollection : myCollections) {
            if (myCollection.getArticleId() == id) {
                return true;
            }
        }
        return false;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (realm.where(NewUser.class).findFirst() != null) {
            initData();
        } else {
            isCollection = judgeCollection(realm.where(MyCollection.class).findAll());
            invalidateOptionsMenu();
        }
    }

    public void initWebView() {
        Gson g = new Gson();
        myCollection = g.fromJson(getIntent().getStringExtra("activity_id"), MyCollection.class);
        type = getIntent().getStringExtra("type");
        id = myCollection.getArticleId();
        isJzhdItem = getIntent().getBooleanExtra("isJzhdItem", false);
        if (isJzhdItem) {
            mJzhdItem = g.fromJson(getIntent().getStringExtra("activity_id"), JzhdItem.class);
            webTopLin.setVisibility(View.VISIBLE);
            applyBt.setVisibility(View.VISIBLE);
            imageUrlTopImg.setVisibility(View.VISIBLE);
            initView();
        } else {
            webTopLin.setVisibility(View.GONE);
            applyBt.setVisibility(View.GONE);
            imageUrlTopImg.setVisibility(View.GONE);
        }


        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextSize(WebSettings.TextSize.LARGER);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(100);

        //适配图片大小
        settings.setUseWideViewPort(true);
        //最小字体
        settings.setMinimumFontSize(40);
        settings.setMinimumLogicalFontSize(40);
        //支持js
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setInitialScale(57);
        webView.getSettings().setBlockNetworkImage(false);


        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);// 设置缩放
        webView.getSettings().setDisplayZoomControls(false);/*
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.setWebViewClient(new CustomWebViewClient());*/
        webView.loadUrl("http://211.149.235.17:8080/zxd/app/getDetail/" + id);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        if (mcompositeSubscription != null && !mcompositeSubscription.isUnsubscribed()) {
            mcompositeSubscription.unsubscribe();
        }
    }

    @OnClick(R.id.apply_bt)
    public void onClick() {
        AppointmentActivity.CompanyId=mJzhdItem.getCompanyId();
        startActivity(new Intent(LoanDetailsActivity.this,AppointmentActivity.class));
    }
}
