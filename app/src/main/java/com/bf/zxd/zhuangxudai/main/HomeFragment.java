package com.bf.zxd.zhuangxudai.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Dkhd.LoanActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.jzzt.JzztActivity;
import com.bf.zxd.zhuangxudai.zxgl.ZxglActivity;
import com.bf.zxd.zhuangxudai.zxgs.ZxgsActivity;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by johe on 2017/1/5.
 */

public class HomeFragment extends Fragment {
    //    @BindView(toolbar_title)
//    TextView toolbarTitle;
//    @BindView(R.id.base_toolBar)
//    Toolbar baseToolBar;
    @BindView(R.id.zhuangxiugonglue_home)
    TextView zhuangxiugonglueHome;
    @BindView(R.id.daikuanhongdong_home)
    TextView daikuanhongdongHome;
    @BindView(R.id.jiazhuangzhuanti_home)
    TextView jiazhuangzhuantiHome;
    @BindView(R.id.zhuangxiugongsi_home)
    TextView zhuangxiugongsiHome;
    @BindView(R.id.recyclerview_fragment_home)
    RecyclerView recyclerviewFragmentHome;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.home_applyLoan_btn)
    TextView homeApplyLoanBtn;
    private Realm realm;
    private Unbinder unbinder;
    private int[] carousels = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        initView();

//        MarqueeView marqueeView = (MarqueeView) findViewById(R.id.marqueeView);

        List<String> info = new ArrayList<>();
        info.add("张小姐                                20万                         158*****111");
        info.add("王先生                                30万                         156*****141");
        info.add("李小姐                                10万                         138*****341");
        info.add("赵先生                                20万                         137*****468");
        info.add("刘小姐                                40万                         156*****123");
        info.add("孙先姐                                60万                         159*****876");
        marqueeView.startWithList(info);
//        String notice = "张小姐                   20万                  158*****111";
//        marqueeView.startWithText(notice);
        setViewPager(carousels);


        return view;
    }

    private void initView() {
//        setToolbar();

    }

    private void setViewPager(int[] carousels) {
        if (carousels.length > 1) {
            slider.startAutoCycle();
        }
        for (int i = 0; i < carousels.length; i++) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView.image(carousels[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            slider.addSlider(textSliderView);
        }
    }

    private void setToolbar() {
        //让原始的toolbar的title不显示
//        baseToolBar.setTitle("");
//        ((AppCompatActivity) getActivity()).setSupportActionBar(baseToolBar);
//        toolbarTitle.setText(getResources().getString(R.string.home_title));
    }

    public interface mDetailsListener {
        void setContent(int id);
    }

    private mDetailsListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mDetailsListener) activity;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.home_applyLoan_btn, R.id.zhuangxiugonglue_home, R.id.daikuanhongdong_home, R.id.jiazhuangzhuanti_home, R.id.zhuangxiugongsi_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhuangxiugonglue_home:
                startActivity(new Intent(getActivity(), ZxglActivity.class));
                break;
            case R.id.daikuanhongdong_home:
                startActivity(new Intent(getActivity(), LoanActivity.class));
                break;
            case R.id.jiazhuangzhuanti_home:
                startActivity(new Intent(getActivity(), JzztActivity.class));
                break;
            case R.id.zhuangxiugongsi_home:
                startActivity(new Intent(getActivity(), ZxgsActivity.class));
                break;
            case R.id.home_applyLoan_btn:
//                mListener.setContent(3);
                break;
        }
    }
}
