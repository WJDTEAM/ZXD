package com.bf.zxd.zhuangxudai.zxgs;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.template.AutoViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bf.zxd.zhuangxudai.util.Utils.getContext;

public class CompanyDetailActivity extends AppCompatActivity {


    @BindView(R.id.template_viewpager)
    ViewPager templateViewpager;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.now_num)
    TextView nowNum;
    @BindView(R.id.page_num)
    TextView pageNum;
    @BindView(R.id.template_img_lin)
    LinearLayout templateImgLin;


    List<View> imgViews;
    AutoViewPagerAdapter autoViewPagerAdapter;
    public static String caseName = "";
    public static int CaseId = -1;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        detailsTitle.setText(caseName);
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        getDecoCompanyCaseImages();


    }


    private void getDecoCompanyCaseImages() {
        Log.e("Daniel", "----CaseId---" + CaseId);
        if (CaseId != -1) {
            NetWork.getNewZXD1_4Service().getDecoCompanyCaseImages(CaseId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<String>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<String> strings) {
                            Log.e("Daniel", "----strings.size()---" + strings.size());
                            initImg(strings);
                        }
                    });
        }
    }

    int index = 1;
    int imgSize;

    private void initImg(List<String> strings) {
        imgViews = new ArrayList<>();
        String imgAddresStart = strings.get(0);
        String imgAddresEnd = strings.get(strings.size() - 1);
        strings.add(0, imgAddresEnd);
        strings.add(imgAddresStart);
        imgSize = strings.size();
        pageNum.setText("/" + (imgSize - 2) + "");
        Log.e("Daniel", "----strings.size()---" + strings.size());
        for (int i = 0; i < strings.size(); i++) {
            View imgView1 = LayoutInflater.from(getContext()).inflate(R.layout.template_viewpager_item, null, false);
            ((TextView) imgView1.findViewById(R.id.txt)).setText("");
            if (strings.get(i) != null) {
                if (!strings.get(i).equals("")) {
                    Picasso.with(CompanyDetailActivity.this).load(strings.get(i))
                            .placeholder(R.drawable.demo)
                            .error(R.drawable.demo2)
                            .into(((ImageView) imgView1.findViewById(R.id.template_pager_img)));
                }
            }
            imgViews.add(imgView1);
        }
        Log.e("Daniel", "----imgViews.size()---" + imgViews.size());
        setAdapter(imgViews);
    }

    public void setViewPager(int index) {
        templateViewpager.setCurrentItem(index, false);
        nowNum.setText((index) + "");
    }

    private void setAdapter(List<View> imgViews) {
        autoViewPagerAdapter = new AutoViewPagerAdapter();
        autoViewPagerAdapter.setList(imgViews);
        templateViewpager.setAdapter(autoViewPagerAdapter);
        //没有翻页动画跳转到地2页
        templateViewpager.setCurrentItem(1, false);

        templateViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int position) {
                nowNum.setText((position) + "");
                if (position == imgSize - 1) {
                    setViewPager(1);
                }
                if (position == 0) {
                    setViewPager(imgSize - 2);
                }
                Log.i("gqf", "onPageSelected" + position);
            }
        });


    }


}
