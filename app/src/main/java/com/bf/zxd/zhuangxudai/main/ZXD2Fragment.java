package com.bf.zxd.zhuangxudai.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.zxd.zhuangxudai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by johe on 2017/1/5.
 */

public class ZXD2Fragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.zxd_tablayout)
    TabLayout zxdTablayout;
    @BindView(R.id.zxd_viewpager)
    ViewPager zxdViewpager;

    public static ZXD2Fragment newInstance() {
        ZXD2Fragment fragment = new ZXD2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "大额贷");
        args.putString(ARG_PARAM2, "小额贷");
        fragment.setArguments(args);
        return fragment;
    }

    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    public ZXD2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_bank2, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        ZXDViewpagerAdapter adapter = new ZXDViewpagerAdapter(getChildFragmentManager());
        zxdViewpager.setAdapter(adapter);
        zxdTablayout.setupWithViewPager(zxdViewpager);
        zxdViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageposition=position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    int pageposition=0;
    public void setPage(int position){
        pageposition=position;
    }

    @Override
    public void onStart() {
        super.onStart();
        zxdViewpager.setCurrentItem(pageposition, false);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
