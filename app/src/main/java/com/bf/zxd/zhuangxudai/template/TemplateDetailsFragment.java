package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Interfaces.ScrollViewListener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.CustomScrollView;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.Zxgs;
import com.daimajia.slider.library.SliderLayout;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateDetailsFragment extends Fragment {
    Realm realm;
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
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

    public float downY;
    public float upY;
    public int scrolHeight = 0;
    @BindView(R.id.template_details_scrol)
    CustomScrollView templateDetailsScrol;
    CompositeSubscription mcompositeSubscription;
    @BindView(R.id.company_address1)
    TextView companyAddress1;
    @BindView(R.id.company_phone)
    TextView companyPhone;
    @BindView(R.id.company_address2)
    TextView companyAddress2;

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

        public int getCompanyId();
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
        mcompositeSubscription = new CompositeSubscription();
        templateDetailsScrol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downY = motionEvent.getY();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    upY = motionEvent.getY();
                    if ((upY - downY) > TemplateActivity.slidingDistance && scrolHeight == 0) {
                        mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT);
                        return true;
                    }
                }
                return false;
            }
        });

        templateDetailsScrol.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrolHeight = y;
                Log.i("gqf", "int x" + x + "int y" + y + "int oldx" + oldx + "int oldy" + oldy);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData(mListener.getCompanyId());

    }

    public void initData(int id) {
        Subscription subscription = NetWork.getZxService().getZxgs(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Zxgs>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf","onError"+e.getMessage());
                    }

                    @Override
                    public void onNext(Zxgs zxgs) {
                        Log.i("gqf","mListener"+zxgs.toString());
                        initView(zxgs);
                    }
                });
        mcompositeSubscription.add(subscription);
    }

    public void initView(Zxgs zxgs) {
        aboveTxt.setText(zxgs.getCompany_name());
        companyAddress1.setText(zxgs.getAddr());
        companyAddress2.setText(zxgs.getAddr());
        companyPhone.setText(zxgs.getTel());
        Picasso.with(getActivity()).load(zxgs.getLogo_img())
                .placeholder(R.drawable.home_zxgs)
                .error(R.drawable.home_zxgs)
                .into(image);
    }
}
