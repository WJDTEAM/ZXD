package com.bf.zxd.zhuangxudai.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.User.MyAppointmentActivity;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.pojo.CompanyIdAndTemplateActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.EnterActivityEvent;
import com.blankj.utilcode.utils.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements HomeFragment.mDetailsListener {

    private static final String HOME_TAG = "home_flag";
    private static final String YBJ_TAG = "ybj_flag";
    private static final String ZXD_TAG = "zxd_flag";
    private static final String MY_TAG = "my_flag";
    Realm realm;
    CompositeSubscription mcompositeSubscription;

    public static Boolean isBottom2=false;

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
    @BindView(R.id.bottomBar)
    com.ashokvarma.bottomnavigation.BottomNavigationBar bottomBar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    LoginHelper loginHelper;

    private void setToolbar(String toolstr, int flag) {
        if (flag == 1) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            toolbarImg.setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.GONE);
        } else {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            toolbarTitle.setText(toolstr);
            toolbarImg.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.VISIBLE);
        }


    }

    public void changePageAndSetPagePosition(int position) {
        bottomBar.selectTab(2);
//        bottomBar.selectTabAtPosition(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).addActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //加入activity列表

        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        setContent(CONTENT_HOME);
        //设置底部栏
        initBottomBar();
        loginHelper=LoginHelper.getInstence();
        loginHelper.setmLinsener(new LoginHelper.Linsener() {
            @Override
            public void changeActivity(Class activity) {
                Log.i("gqf","loginHelperactivity");
                startActivity(new Intent(MainActivity.this,activity));
            }
        });

    }

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {

            Toast.makeText(MainActivity.this, "再按一次退出装修贷app", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {

            //            MyConfig.clearSharePre(this, "users");
            ((BaseApplication)getApplication()).exit();
        }
    }

    private void initBottomBar() {
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomBar
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.bottom_img)
                .setBarBackgroundColor(R.color.white);
        bottomBar.addItem(new BottomNavigationItem(R.drawable.home_dake, R.string.firstPage))
                .addItem(new BottomNavigationItem(R.drawable.ybj,  R.string.ybj))
                .addItem(new BottomNavigationItem(R.drawable.zxd, R.string.zxd))
                .addItem(new BottomNavigationItem(R.drawable.my,  R.string.my))
                .initialise();

        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        setContent(CONTENT_HOME);
                        break;
                    case 1:
                        setContent(CONTENT_YBJ);

                        break;
                    case 2:
                        setContent(CONTENT_ZXD);
                        break;
                    case 3:
                        setContent(CONTENT_MY);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 页面切换
     *
     * @param contentHome
     */
    HomeFragment homeFragment;
    ZXD3Fragment zxdFragment;
    YBJFragment YBJFragment;
    UserFragment userFragment;

    public void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_HOME:
                String home_str = getResources().getString(R.string.home_title);
                setToolbar(home_str, CONTENT_HOME);
                homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                setFragment(homeFragment, HOME_TAG);
                break;
            case CONTENT_ZXD:
                String zxd_str = getResources().getString(R.string.zxd);
                setToolbar(zxd_str, CONTENT_ZXD);
                zxdFragment = (ZXD3Fragment) getSupportFragmentManager().findFragmentByTag(ZXD_TAG);
                if (zxdFragment == null) {
                    zxdFragment = ZXD3Fragment.newInstance();
                }
                setFragment(zxdFragment, ZXD_TAG);
                break;
            case CONTENT_YBJ:
                String orders_str = getResources().getString(R.string.ybj);
                setToolbar(orders_str, CONTENT_YBJ);
                YBJFragment = (YBJFragment) getSupportFragmentManager().findFragmentByTag(YBJ_TAG);
                if (YBJFragment == null) {
                    YBJFragment = YBJFragment.newInstance();
                }
                setFragment(YBJFragment, YBJ_TAG);
                break;
            case CONTENT_MY:
                String my_str = getResources().getString(R.string.my);
                setToolbar(my_str, CONTENT_MY);
                userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag(MY_TAG);
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
        fragmentTransaction.commitAllowingStateLoss();
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

    public boolean initLogin(){
        loginHelper=LoginHelper.getInstence();
        return loginHelper.startActivityWithLogin(this, MyAppointmentActivity.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startLoanApplyActivity(EnterActivityEvent enterActivityEventy) {

        boolean isLogin=initLogin();
        if(isLogin){
            startActivity(new Intent(MainActivity.this, enterActivityEventy.getActivityClass()));
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getIntentToBank();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isBottom2=false;
    }

    public void getIntentToBank() {
        if(isBottom2){
            bottomBar.selectTab(2);
        }
//        if (LoanApplyActivity.companyId != -1 || LoanApplyActivity.bankId != -1) {
////            bottomBar.selectTabAtPosition(2);
//            bottomBar.selectTab(2);
//        }
    }
}
