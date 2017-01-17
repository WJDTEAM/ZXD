package com.bf.zxd.zhuangxudai.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wjy on 16/8/15.
 *
 */
public class ZXDViewpagerAdapter extends FragmentPagerAdapter {
    public ZXDViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ZXDListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "全部贷款";
            case 1:
                return "大额贷";
            case 2:
                return "小额贷";
        }
        return super.getPageTitle(position);
    }
}
