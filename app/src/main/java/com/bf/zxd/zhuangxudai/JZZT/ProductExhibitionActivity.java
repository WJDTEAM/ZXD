package com.bf.zxd.zhuangxudai.JZZT;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.zxgs.AppointmentActivity;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.daimajia.slider.library.SliderLayout;
import com.sevenheaven.segmentcontrol.SegmentControl;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductExhibitionActivity extends BaseActivity implements SegmentControl.OnSegmentControlClickListener {


    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.segment_control)
    SegmentControl segmentControl;
//    @BindView(R.id.slider)
//    SliderLayout slider;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.gsTitle_txt)
    TextView gsTitleTxt;
    @BindView(R.id.below_txt)
    TextView belowTxt;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_product_exhibition);
        ButterKnife.bind(this);

    }

    @Override
    public void initEvent() {
        segmentControl.setOnSegmentControlClickListener(this);
        ButterKnife.bind(this);

    }

    @Override
    public void onSegmentControlClick(int index) {
        switch (index) {
            case 0:
                startActivity(new Intent(ProductExhibitionActivity.this, LoanApplyActivity.class));
                break;
            case 1:
                startActivity(new Intent(ProductExhibitionActivity.this, AppointmentActivity.class));
                break;
        }

    }

    /**
     * 轮播图
     * @param movieInfos
     */

//    private void setViewPager(List<MovieInfo> movieInfos) {
//        if (movieInfos.size() > 1) {
//            mSlider.startAutoCycle();
//        }
//        for (MovieInfo m : movieInfos) {
//            TextSliderView textSliderView = new TextSliderView(getActivity());
//            if (!StringUtils2.isEmpty(m.getImageUrl()))
//                textSliderView.description(m.getFileName()).image(m.getImageUrl()).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this);
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("url", m.getVodUrl() == null ? "no" : m.getVodUrl());
//            textSliderView.getBundle().putString("name", m.getFileName());
//            textSliderView.getBundle().putString("imgUrl", m.getImageUrl());
//
//            mSlider.addSlider(textSliderView);
//        }
//    }
}
