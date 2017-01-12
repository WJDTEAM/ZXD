package com.bf.zxd.zhuangxudai.jzzt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.main.YBJFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by Daniel on 2017/1/10.
 */

public class JzztActivity extends BaseActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;

    private static final String YBJ_TAG = "ybj_flag";

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_jzzt);
        ButterKnife.bind(this);
//        String orders_str = getResources().getString(R.string.ybj);
        setToolbar();
        YBJFragment YBJFragment = (YBJFragment) getSupportFragmentManager().findFragmentByTag(YBJ_TAG);
        if (YBJFragment == null) {
            YBJFragment = YBJFragment.newInstance();
        }
        setFragment(YBJFragment, YBJ_TAG);

    }

    private void setToolbar() {
        baseToolBar.setTitle("家装专题");
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationIcon(R.drawable.back);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置fragment
     *
     * @param fragment
     */
    @DebugLog
    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void initEvent() {

    }

}
