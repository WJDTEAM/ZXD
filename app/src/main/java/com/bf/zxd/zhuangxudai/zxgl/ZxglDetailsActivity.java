package com.bf.zxd.zhuangxudai.zxgl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bf.zxd.zhuangxudai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/12.
 */

public class ZxglDetailsActivity extends AppCompatActivity{


    Realm realm;
    CompositeSubscription mcompositeSubscription;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_activity_details_web)
    WebView webView;
    private int mActivityId;

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
        setContentView(R.layout.activity_loan_activity_details);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        mActivityId = getIntent().getIntExtra("activityId",-1);
        setToolbar("活动详情");
//        NetWork.getZxService()
        initWebView();



    }
    public void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(100);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
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


        webView.loadUrl("http://211.149.235.17:8080/zxd/app/getDetail/"+mActivityId+"");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

    }
}

