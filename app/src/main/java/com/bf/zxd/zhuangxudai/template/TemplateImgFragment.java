package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyCase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateImgFragment extends Fragment {
    Realm realm;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.page_num)
    TextView pageNum;
    @BindView(R.id.now_num)
    TextView nowNum;
    @BindView(R.id.template_img_lin)
    LinearLayout templateImgLin;
    @BindView(R.id.template_img_rel)
    RelativeLayout templateImgRel;

    public float downY;
    public float upY;

    public float downX;
    public float upX;


    public boolean Enable = true;
    List<View> imgViews;
    List<DecoCompanyCase> DecoCompanyCases;
    AutoViewPagerAdapter autoViewPagerAdapter;
    @BindView(R.id.template_viewpager)
    ViewPager templateViewpager;

    public static boolean isNoImg=false;

    public static TemplateImgFragment newInstance() {
        TemplateImgFragment fragment = new TemplateImgFragment();
        return fragment;
    }

    public interface mImgListener {
        public void show();

        public void hide();

        public int getToolBarheight();

        public boolean isToolBarShow();

        public void changeFragmentByTAG(String fragment, int index);


    }

    private mImgListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mImgListener) activity;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    int index = 1;
    int imgSize = 15;
    LayoutInflater inflaters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_img, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        vg = (ViewGroup) view.findViewById(R.id.template_img_lin);
        inflaters = inflater;
        // popuWindow();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mListener.isToolBarShow()) {
                clickEvent(-mListener.getToolBarheight());
            }
            Enable = false;
        } else {
            Enable = true;
        }
    }

    public void setViewPager(int index) {
        templateViewpager.setCurrentItem(index, false);
        nowNum.setText((index) + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isNoImg=false;
    }

    ViewGroup vg;

    //宽高改变动画
    public void clickEvent(int x) {
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(200);
        transition.setInterpolator(new DecelerateInterpolator());
        TransitionManager.beginDelayedTransition(vg, transition);
        setViewSize(x / 3 * 2);
    }

    public void setViewSize(int x) {
        ViewGroup.LayoutParams params = templateImgLin.getLayoutParams();
        params.height = params.height + x;
        templateImgLin.setLayoutParams(params);
    }

    //toolbar和底部栏的显隐
    @OnClick(R.id.template_img_rel)
    public void onClick() {
        if (mListener.isToolBarShow()) {
            mListener.hide();
            clickEvent(-mListener.getToolBarheight());
        } else {
            mListener.show();
            clickEvent(mListener.getToolBarheight());
        }
    }

    List<String> imgAddres;

    public void initImg(List<String> imgAddress) {
        if(imgAddress.size()==0){
            isNoImg=true;
            mListener.changeFragmentByTAG(TemplateActivity.CHANGE_DETAILS_FRAGMENT, 0);
        }

        imgViews = new ArrayList<>();
        imgAddres = new ArrayList<>();
        for (String s : imgAddress) {
            imgAddres.add(s);
        }
        String imgAddresStart = imgAddress.get(0);
        String imgAddresEnd = imgAddress.get(imgAddress.size() - 1);
        imgAddres.add(0, imgAddresEnd);
        imgAddres.add(imgAddresStart);
        imgSize = imgAddres.size();
        pageNum.setText("/" + (imgSize - 2) + "");
        for (int i = 0; i < imgAddres.size(); i++) {
            View imgView1 = inflaters.inflate(R.layout.template_viewpager_item, null);
            ((TextView) imgView1.findViewById(R.id.txt)).setText("");
            if (imgAddres.get(i) != null) {
                if (!imgAddres.get(i).equals("")) {
                    Picasso.with(getActivity()).load(imgAddres.get(i))
                            .placeholder(R.drawable.demo)
                            .error(R.drawable.demo2)
                            .into(((ImageView) imgView1.findViewById(R.id.template_pager_img)));
                }
            }
            imgViews.add(imgView1);
        }


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
        templateViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downY = motionEvent.getY();
                    downX = motionEvent.getX();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    upY = motionEvent.getY();
                    upX = motionEvent.getX();
                    if ((downY - upY) > TemplateActivity.slidingDistance) {
                        mListener.changeFragmentByTAG(TemplateActivity.CHANGE_DETAILS_FRAGMENT, 0);
                        return true;
                    } else {

                        if (Enable && (downX - upX) < 20 && (downX - upX) > -20) {
                            if (mListener.isToolBarShow()) {
                                mListener.hide();
                                clickEvent(-mListener.getToolBarheight());
                            } else {
                                mListener.show();
                                clickEvent(mListener.getToolBarheight());
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

}
