package com.bf.zxd.zhuangxudai.JZZT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.JzhdItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JzztDetialActivity extends AppCompatActivity {


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


    private JzhdItem mJzhdItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jzzt_detial);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        mJzhdItem = (JzhdItem) intent.getSerializableExtra("jzhdItem");
        initView();
        setToolbar();
        initWebView();
    }

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

    int id;

    public void initWebView() {
        id = mJzhdItem.getArticleId();
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
        webView.loadUrl("http://211.149.235.17:8080/zxd/app/getDetail/" + id);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

    }

    @OnClick(R.id.apply_bt)
    public void onClick() {

    }
}
