package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;

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



    public boolean Enable=true;
    List<View> imgViews;
    AutoViewPagerAdapter autoViewPagerAdapter;
    @BindView(R.id.template_viewpager)
    ViewPager templateViewpager;

    public static TemplateImgFragment newInstance() {
        TemplateImgFragment fragment = new TemplateImgFragment();
        return fragment;
    }

    public interface mImgListener {
        public void show();

        public void hide();

        public int getToolBarheight();

        public boolean isToolBarShow();

        public void changeFragmentByTAG(String fragment);
    }

    private mImgListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mImgListener) activity;

    }


    int index=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_img, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        vg = (ViewGroup) view.findViewById(R.id.template_img_lin);
        imgViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            View imgView1 = inflater.inflate(R.layout.template_viewpager_item, null);
            //((ImageView) imgView1.findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
            ((TextView) imgView1.findViewById(R.id.txt)).setText(i-1+"");
            imgViews.add(imgView1);
        }
        autoViewPagerAdapter = new AutoViewPagerAdapter();
        autoViewPagerAdapter.setList(imgViews);
        templateViewpager.setAdapter(autoViewPagerAdapter);
        templateViewpager.setCurrentItem(2,false);
        templateViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1||position==0){
                    //用户左翻页
                   index--;
                }else if(position==3||position==4){
                    //用户右翻页
                    index++;
                }
                ((TextView)autoViewPagerAdapter.getImageViewList().get(0).findViewById(R.id.txt)).setText(index-2+"");
                ((TextView)autoViewPagerAdapter.getImageViewList().get(1).findViewById(R.id.txt)).setText(index-1+"");
                ((TextView)autoViewPagerAdapter.getImageViewList().get(2).findViewById(R.id.txt)).setText(index+"");
                ((TextView)autoViewPagerAdapter.getImageViewList().get(3).findViewById(R.id.txt)).setText(index+1+"");
                ((TextView)autoViewPagerAdapter.getImageViewList().get(4).findViewById(R.id.txt)).setText(index+2+"");
                templateViewpager.setCurrentItem(2,false);
            }
        });
        templateViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downY=motionEvent.getY();
                    downX=motionEvent.getX();
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    upY=motionEvent.getY();
                    upX=motionEvent.getX();
                    if((downY-upY)>TemplateActivity.slidingDistance){
                        mListener.changeFragmentByTAG(TemplateActivity.CHANGE_DETAILS_FRAGMENT);
                        return true;
                    }else{

                        if(Enable&&(downX-upX)<20&&(downX-upX)>-20) {
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
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            Enable=false;
        }else{
            Enable=true;
        }
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
}
