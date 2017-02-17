package com.bf.zxd.zhuangxudai.template;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyYbjDetail;
import com.bf.zxd.zhuangxudai.zxgs.ZxgsDetailActivity;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.bf.zxd.zhuangxudai.R.id.imgs_num_txt;

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
    @BindView(R.id.all_imgs_RecyclerView)
    RecyclerView allImgsRecyclerView;

    TemplateHorizontalListAdapter templateHorizontalListAdapter;
    @BindView(R.id.designInspiration_txt)
    TextView designInspirationTxt;
    @BindView(R.id.housingSituation_txt)
    TextView housingSituationTxt;
    @BindView(imgs_num_txt)
    TextView imgsNumTxt;
    @BindView(R.id.comments_num_txt)
    TextView commentsNumTxt;
    DecoCompanyYbjDetail decoCompanyYbjDetail;
    public static TemplateDetailsFragment newInstance() {
        TemplateDetailsFragment fragment = new TemplateDetailsFragment();
        return fragment;
    }

    @OnClick({R.id.to_comments_lin, R.id.below_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_comments_lin:
                mListener.startActivity(CommentsActivity.class);
                break;
            case R.id.below_txt:
                Log.i("gqf","decoCompanyYbjDetail"+decoCompanyYbjDetail.toString());
                mListener.setCompanyId(decoCompanyYbjDetail.getCompanyId());
                mListener.startActivity(ZxgsDetailActivity.class);
                break;
        }
    }


    public interface mDetailsListener {
        public void show();

        public void hide();

        public int getToolBarheight();

        public boolean isToolBarShow();

        public void changeFragmentByTAG(String fragment, int index);

        public int getCompanyId();

        public void startActivity(Class activity);

        public List<String> getImgAddress();

        public void setCompanyId(int id);
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
                    if (TemplateImgFragment.isNoImg==false&&(upY - downY) > TemplateActivity.slidingDistance && scrolHeight == 0) {
                        mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT, 1);
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


    List<String>  caseImgs;
    public void initListView(List<String> imgs) {
        imgsNumTxt.setText("相关图片（"+imgs.size()+")");
        templateHorizontalListAdapter = new TemplateHorizontalListAdapter(getActivity(), imgs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        allImgsRecyclerView.setLayoutManager(linearLayoutManager);
        allImgsRecyclerView.setAdapter(templateHorizontalListAdapter);
        templateHorizontalListAdapter.setOnItemClickListener(new TemplateHorizontalListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                mListener.changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT, postion + 1);
            }
        });
        caseImgs=imgs;

    }

    public void initSliderLayout(List<String> imgs) {
        DefaultSliderView textSliderView1 = new DefaultSliderView(this.getActivity());
        textSliderView1.image(decoCompanyYbjDetail.getThumbnails()) .setScaleType(BaseSliderView.ScaleType.Fit);


        slider.addSlider(textSliderView1);
        if (imgs.size() > 2) {
            DefaultSliderView textSliderView2 = new DefaultSliderView(this.getActivity());
            textSliderView2.image(imgs.get(imgs.size() - 1)) .setScaleType(BaseSliderView.ScaleType.Fit);

            slider.addSlider(textSliderView2);
        }
        if (imgs.size() > 3) {
            DefaultSliderView textSliderView2 = new DefaultSliderView(this.getActivity());
            textSliderView2.image(imgs.get(imgs.size() - 2)) .setScaleType(BaseSliderView.ScaleType.Fit);

            slider.addSlider(textSliderView2);
        }


        //阴影显示的动画效果
        slider.setCustomAnimation(new DescriptionAnimation());
        //图片的转场效果
        slider.setDuration(3000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            initData(mListener.getCompanyId());
        }
    }

    public void initData(int id) {
        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyYbjDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DecoCompanyYbjDetail>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(DecoCompanyYbjDetail zxgs) {
                        Log.i("gqf", "mListener" + zxgs.toString());
                        decoCompanyYbjDetail=zxgs;
                        mListener.setCompanyId(decoCompanyYbjDetail.getCompanyId());
                        initYBJView(zxgs);
                    }
                });
        mcompositeSubscription.add(subscription);
    }

    public void initYBJView(DecoCompanyYbjDetail zxgs) {
        companyAddress1.setText(zxgs.getBuildingAddr());
        Picasso.with(getActivity()).load(zxgs.getThumbnails())
                .placeholder(R.drawable.home_zxgs)
                .error(R.drawable.home_zxgs)
                .into(image);
        designInspirationTxt.setText(zxgs.getDesignInspiration());
        housingSituationTxt.setText(zxgs.getHousingSituation());
        commentsNumTxt.setText(zxgs.getComments() + "");

        initCompanyData(zxgs.getCompanyId());
    }

    public void initCompanyData(int companyId) {
        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyDetail(companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DecoCompanyDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DecoCompanyDetail decoCompanyDetail) {
                        initCompanyView(decoCompanyDetail);
                    }
                });
        mcompositeSubscription.add(subscription);
    }

    public void initCompanyView(DecoCompanyDetail decoCompanyDetail) {

        if (decoCompanyDetail.getCompanyIcon() != null) {
            if (!decoCompanyDetail.getCompanyIcon().equals("")) {
                Picasso.with(getActivity()).load(decoCompanyDetail.getCompanyIcon())
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo)
                        .into(image);
            }
        }

        companyAddress2.setText(decoCompanyDetail.getAddr());
        companyPhone.setText(decoCompanyDetail.getTel());
        aboveTxt.setText(decoCompanyDetail.getCompanyName());


        initSliderLayout(caseImgs);

    }

}
