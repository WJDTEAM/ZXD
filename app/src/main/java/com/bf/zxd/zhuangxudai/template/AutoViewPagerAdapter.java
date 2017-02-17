package com.bf.zxd.zhuangxudai.template;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by johe on 2017/1/11.
 */

public class AutoViewPagerAdapter extends PagerAdapter {

    List<View> imageViewList;

    public void setList(List<View> imageViewList) {
        this.imageViewList = imageViewList;
    }

    public List<View> getImageViewList() {
        return imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    /**
     * 判断出去的view是否等于进来的view 如果为true直接复用
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewList.get(position));
    }

    /**
     * 创建一个view
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewList.get(position));
        return imageViewList.get(position);
    }
}

