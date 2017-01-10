package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.daimajia.slider.library.SliderLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateDetailsFragment extends Fragment {
    Realm realm;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.above_txt)
    TextView aboveTxt;
    @BindView(R.id.below_txt)
    TextView belowTxt;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;

    public static TemplateDetailsFragment newInstance() {
        TemplateDetailsFragment fragment = new TemplateDetailsFragment();
        return fragment;
    }

    public interface mDetailsListener {
        public void show();

        public void hide();

        public int getToolBarheight();

        public boolean isToolBarShow();

        public void changeFragmentByTAG(String fragment);
    }

    private mDetailsListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mDetailsListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_details, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();

        activityProductExhibition.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT);
                }
                return false;
            }
        });
        return view;
    }
}
