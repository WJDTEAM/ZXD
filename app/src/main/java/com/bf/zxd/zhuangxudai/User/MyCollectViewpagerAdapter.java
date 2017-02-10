package com.bf.zxd.zhuangxudai.User;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wjy on 16/8/15.
 *
 */
public class MyCollectViewpagerAdapter extends FragmentPagerAdapter {

    public MyCollectViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MyCollectFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "装修";
            case 1:
                return "贷款";

        }
        return super.getPageTitle(position);
    }


}
