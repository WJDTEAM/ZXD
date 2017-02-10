package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

        public void changeFragmentByTAG(String fragment,int index);
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
                    if (position == 1 || position == 0) {
                        //用户左翻页
                        index--;
                    } else if (position == 3 || position == 4) {
                        //用户右翻页
                        index++;
                    }
                if(index==15){
                    index=1;
                }else if(index==0){
                    index=14;
                }
                    setViewPager(index);

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
                        mListener.changeFragmentByTAG(TemplateActivity.CHANGE_DETAILS_FRAGMENT,0);
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


       // popuWindow();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        popuWindow();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            if(mListener.isToolBarShow()){
                clickEvent(-mListener.getToolBarheight());
            }
            Enable=false;
        }else{
            Enable=true;
        }
    }

    public void setViewPager(int index){

        List<Integer> nums=new ArrayList<>();
        for(int i=0;i<5;i++){
            if(index-2+i==0){
                nums.add(14);
            }else if(index-2+i==-1){
                nums.add(13);
            }
            else if(index-2+i==15){
                nums.add(1);
            }else if(index-2+i==16){
                nums.add(2);
            }
            else{
                nums.add(index-2+i);
            }
            ((TextView)autoViewPagerAdapter.getImageViewList().get(i).findViewById(R.id.txt)).setText(nums.get(i)+"");
        }

        if(index%2==0){
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(0).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo2);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(1).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(2).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo2);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(3).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(4).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo2);
        }else{
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(0).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(1).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo2);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(2).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(3).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo2);
            ((ImageView)autoViewPagerAdapter.getImageViewList().get(4).findViewById(R.id.template_pager_img)).setImageResource(R.drawable.demo);
        }
        nowNum.setText(""+index);
        templateViewpager.setCurrentItem(2,false);
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
    PopupWindow window;
    public void popuWindow(){

            // 利用layoutInflater获得View
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.template_popuwindow, null);

            // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        RelativeLayout rel=(RelativeLayout)view.findViewById(R.id.tempopu_rel);

        rel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                window.dismiss();
                return false;
            }
        });
            window = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
            window.setFocusable(true);

            // 必须要给调用这个方法，否则点击popWindow以外部分，popWindow不会消失
            window.setBackgroundDrawable(new BitmapDrawable());

            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            window.setBackgroundDrawable(dw);

            // 在参照的View控件下方显示
            // window.showAsDropDown(MainActivity.this.findViewById(R.id.start));
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.5f;
            getActivity().getWindow().setAttributes(lp);

            // 设置popWindow的显示和消失动画
            window.setAnimationStyle(R.style.mypopwindow_anim_style);
            // 在底部显示
//            window.showAtLocation(getActivity().findViewById(R.id.template_details_fragment),
//                    Gravity.CENTER, 0, 0);

            // popWindow消失监听方法
            window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.alpha = 1f;
                    getActivity().getWindow().setAttributes(lp);
                }
            });
        }

}
