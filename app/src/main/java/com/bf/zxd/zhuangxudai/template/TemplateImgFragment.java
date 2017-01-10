package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static TemplateImgFragment newInstance() {
        TemplateImgFragment fragment = new TemplateImgFragment();
        return fragment;
    }

    public interface mImgListener {
        public void show();
        public void hide();
        public int getToolBarheight() ;
        public boolean isToolBarShow() ;
        public void changeFragmentByTAG(String fragment);
    }

    private mImgListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mImgListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_img, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        vg=(ViewGroup)view.findViewById(R.id.template_img_lin);
        templateImgRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    mListener.changeFragmentByTAG(TemplateActivity.CHANGE_DETAILS_FRAGMENT);
                }
                return false;
            }
        });
        return view;
    }

    ViewGroup vg;
    //宽高改变动画
    public void clickEvent(int x){
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(200);
        transition.setInterpolator(new DecelerateInterpolator());
        TransitionManager.beginDelayedTransition(vg,transition);
        setViewSize(x/3*2);
    }
    public void setViewSize(int x){
        ViewGroup.LayoutParams params=templateImgLin.getLayoutParams();
        params.height=params.height+x;
        templateImgLin.setLayoutParams(params);
    }
    //toolbar和底部栏的显隐
    @OnClick(R.id.template_img_rel)
    public void onClick() {
        /*if(mListener.isToolBarShow()){
            mListener.hide();
            clickEvent(-mListener.getToolBarheight());
        }else{
            mListener.show();
            clickEvent(mListener.getToolBarheight());
        }*/
    }
}
