package com.bf.zxd.zhuangxudai.jzzt;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;

public class ProductExhibitionActivity extends BaseActivity {


    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_product_exhibition);

    }

    @Override
    public void initEvent() {

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
//            if (!StringUtils.isEmpty(m.getImageUrl()))
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
