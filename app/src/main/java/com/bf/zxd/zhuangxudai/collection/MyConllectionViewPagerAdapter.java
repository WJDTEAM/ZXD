package com.bf.zxd.zhuangxudai.collection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjy on 16/8/15.
 *
 */
public class MyConllectionViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment>  datas;

    public MyConllectionViewPagerAdapter(FragmentManager fm) {
        super(fm);
        datas=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=ConllectionListFragment.newInstance(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "全部";
            case 1:
                return "装修";
            case 2:
                return "贷款";
        }
        return super.getPageTitle(position);
    }
}
