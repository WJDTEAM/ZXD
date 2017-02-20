package com.bf.zxd.zhuangxudai.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Dkhd.LoanActivity;
import com.bf.zxd.zhuangxudai.JZZT.JzztActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.LoanSuccessItem;
import com.bf.zxd.zhuangxudai.pojo.Recommends;
import com.bf.zxd.zhuangxudai.util.MarqueeView;
import com.bf.zxd.zhuangxudai.zxgl.ZxglActivity;
import com.bf.zxd.zhuangxudai.zxgs.ZxgsActivity;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class HomeFragment extends Fragment implements RecommendBankAdapter.MyItemClickListener {

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
    @BindView(R.id.little_loan_btn)
    RelativeLayout littleLoanBtn;
    @BindView(R.id.big_loan_btn)
    RelativeLayout bigLoanBtn;
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;
    private Realm realm;
    private Unbinder unbinder;
    private RecommendBankAdapter recommendBankAdapter;
    private int[] carousels = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    private CompositeSubscription mCompositeSubscription;


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
        mCompositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        lodingIsFailOrSucess(1);
        initDate();


        //        MarqueeView marqueeView = (MarqueeView) findViewById(R.id.marqueeView);

        return view;
    }

    private void initDate() {
        getBankItem();
        getLoanSuccess();
    }

    public void lodingIsFailOrSucess(int i) {
        if (i == 1) {
            //加载中
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLodingTxt.setText("加载中...");
            YBJLoding.setBackgroundResource(R.drawable.loding_anim_lists);
            AnimationDrawable anim = (AnimationDrawable) YBJLoding.getBackground();
            anim.start();

        } else if (i == 2) {
            //加载成功
            YBJLoding.setBackground(null);
            YBJLoding.setVisibility(View.GONE);
            YBJLodingTxt.setVisibility(View.GONE);
        } else {
            //加载失败
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLoding.setBackground(null);
            YBJLodingTxt.setText("加载失败，请检查网络连接");
            YBJLoding.setImageResource(R.drawable.ic_loding_fail);
        }
    }

    private void getLoanSuccess() {
        Subscription Subscription_getLoanSuccess = NetWork.getNewZXD1_4Service().getLoanSuccess()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LoanSuccessItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "请求贷款成功数据失败！");

                    }

                    @Override
                    public void onNext(List<LoanSuccessItem> LoanSuccessItems) {
                        List<String> info = new ArrayList<>();
                        for (LoanSuccessItem loanSuccessItem : LoanSuccessItems) {
                            info.add("" + loanSuccessItem.getFullName() + "," + loanSuccessItem.getLoanAmount() + "万," + loanSuccessItem.getPhone() + "");
                        }
                        marqueeView.startWithList(info);
                        //        String notice = "张小姐                   20万                  158*****111";
                        //        marqueeView.startWithText(notice);
                        setViewPager(carousels);
                    }
                });
        mCompositeSubscription.add(Subscription_getLoanSuccess);
    }

    private void getBankItem() {
        Subscription Subscription_getBankItem = NetWork.getNewZXD1_4Service().getRecommends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Recommends>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "请求推荐银行列表数据失败！");
                        lodingIsFailOrSucess(3);

                    }

                    @Override
                    public void onNext(List<Recommends> recommends) {
                        lodingIsFailOrSucess(2);
                        setAdapter(recommends);
                    }
                });
        mCompositeSubscription.add(Subscription_getBankItem);
    }

    private void setAdapter(List<Recommends> recommends) {
        recyclerviewFragmentHome.setNestedScrollingEnabled(false);
        //init context view

        recyclerviewFragmentHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (recommendBankAdapter == null) {

            recommendBankAdapter = new RecommendBankAdapter(recommends, getActivity());
        }
        recyclerviewFragmentHome.setAdapter(recommendBankAdapter);
        recommendBankAdapter.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(View view, int postion) {
        Log.e("Daniel", "追踪");


    }

    public interface mDetailsListener {
        void setContent(int id);

        void changePageAndSetPagePosition(int position);
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
        mCompositeSubscription.unsubscribe();

    }

    @OnClick({R.id.little_loan_btn, R.id.big_loan_btn, R.id.zhuangxiugonglue_home, R.id.daikuanhongdong_home, R.id.jiazhuangzhuanti_home, R.id.zhuangxiugongsi_home})
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
            case R.id.little_loan_btn:
                mListener.changePageAndSetPagePosition(2);
                break;
            case R.id.big_loan_btn:
                mListener.changePageAndSetPagePosition(1);
                break;
            //            case R.id.home_applyLoan_btn:
            //                startActivity(new Intent(getActivity(), LoanApplyActivity.class));
            //                break;
        }
    }
}
