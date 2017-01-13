package com.bf.zxd.zhuangxudai.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.pojo.CompanyIdAndTemplateActivityEvent;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements HomeFragment.mDetailsListener, ZXDFragment.mListener {

    private static final String HOME_TAG = "home_flag";
    private static final String YBJ_TAG = "ybj_flag";
    private static final String ZXD_TAG = "zxd_flag";
    private static final String MY_TAG = "my_flag";
    Realm realm;
    CompositeSubscription mcompositeSubscription;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private static final int CONTENT_HOME = 1;
    private static final int CONTENT_YBJ = 2;
    private static final int CONTENT_ZXD = 3;
    private static final int CONTENT_MY = 4;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;

    private void setToolbar(String toolstr,int flag) {
        if (flag==1){
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            toolbarImg.setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.GONE);
        }else {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            toolbarTitle.setText(toolstr);
            toolbarImg.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //加入activity列表
        ((BaseApplication) getApplication()).addActivity(this);
        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
//        setToolbar("首页");

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        setContent(CONTENT_HOME);
                        break;
                    case R.id.tab_zxd:
                        setContent(CONTENT_ZXD);
                        break;
                    case R.id.tab_ybj:
                        setContent(CONTENT_YBJ);
                        break;

                    case R.id.tab_mine:
                        setContent(CONTENT_MY);
                        break;
                }
            }
        });

    }

    /**
     * 页面切换
     *
     * @param contentHome
     */

    public void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_HOME:
                String home_str = getResources().getString(R.string.home_title);
                setToolbar(home_str,CONTENT_HOME);
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                setFragment(homeFragment, HOME_TAG);
                break;
            case CONTENT_ZXD:
                String zxd_str = getResources().getString(R.string.zxd);
                setToolbar(zxd_str,CONTENT_ZXD);
                ZXDFragment zxdFragment = (ZXDFragment) getSupportFragmentManager().findFragmentByTag(ZXD_TAG);
                if (zxdFragment == null) {
                    zxdFragment = ZXDFragment.newInstance();
                }
                setFragment(zxdFragment, ZXD_TAG);
                break;
            case CONTENT_YBJ:
                String orders_str = getResources().getString(R.string.ybj);
                setToolbar(orders_str,CONTENT_YBJ);
                YBJFragment YBJFragment = (YBJFragment) getSupportFragmentManager().findFragmentByTag(YBJ_TAG);
                if (YBJFragment == null) {
                    YBJFragment = YBJFragment.newInstance();
                }
                setFragment(YBJFragment, YBJ_TAG);
                break;
            case CONTENT_MY:
                String my_str = getResources().getString(R.string.my);
                setToolbar(my_str,CONTENT_MY);
                UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag(MY_TAG);
                if (userFragment == null) {
                    userFragment = UserFragment.newInstance();
                }
                setFragment(userFragment, MY_TAG);
                break;
        }

    }

    /**
     * 页面跳转
     */
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startActivity(CompanyIdAndTemplateActivityEvent companyIdAndTemplateActivityEvent) {
        startActivity(new Intent(MainActivity.this, companyIdAndTemplateActivityEvent.getActivityClass()).putExtra("CompanyId", companyIdAndTemplateActivityEvent.getCompanyId()));
    }

    /**
     * 设置fragment
     *
     * @param fragment
     */
    @DebugLog
    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        realm.close();
        KeyboardUtils.hideSoftInput(this);
        if (mcompositeSubscription != null && !mcompositeSubscription.isUnsubscribed()) {
            mcompositeSubscription.unsubscribe();
        }
    }

    public void startLoanApplyActivity() {
        startActivity(new Intent(MainActivity.this, LoanApplyActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getIntentToBank();
    }

    public void getIntentToBank() {
        if (LoanApplyActivity.companyId != 0 || LoanApplyActivity.mZxd != null) {
            bottomBar.selectTabAtPosition(2);
        }
    }
}
