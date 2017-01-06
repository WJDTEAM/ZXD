package com.bf.zxd.zhuangxudai.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.daimajia.slider.library.SliderLayout;

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
    private Realm realm;
    private Unbinder unbinder;


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
//        setViewPager(carousels);


        return view;
    }

    private void initView() {
//        setToolbar();

    }
//    private void setViewPager(List<Carousel> carousels) {
//        if (carousels.size() > 1) {
//            mSliderLayout.startAutoCycle();
//        }
//        for (Carousel m : carousels) {
//
//            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
//            if (!StringUtils.isEmpty(m.getImage_url()))
//                textSliderView.image(m.getImage_url())
//                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
//                        .setOnSliderClickListener(this);
//
//            mSliderLayout.addSlider(textSliderView);
//        }
//    }
    private void setToolbar() {
        //让原始的toolbar的title不显示
//        baseToolBar.setTitle("");
//        ((AppCompatActivity) getActivity()).setSupportActionBar(baseToolBar);
//        toolbarTitle.setText(getResources().getString(R.string.home_title));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.zhuangxiugonglue_home, R.id.daikuanhongdong_home, R.id.jiazhuangzhuanti_home, R.id.zhuangxiugongsi_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhuangxiugonglue_home:
                break;
            case R.id.daikuanhongdong_home:
                break;
            case R.id.jiazhuangzhuanti_home:
                break;
            case R.id.zhuangxiugongsi_home:
                break;
        }
    }
}
