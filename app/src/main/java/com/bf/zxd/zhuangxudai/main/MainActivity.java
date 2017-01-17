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
import com.bf.zxd.zhuangxudai.pojo.EnterActivityEvent;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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

    /**
     * 发送POST请求
     * @return
     */
    protected String submitPostRequest(String urlAddress, Map<String, String> params) {
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            //connection.setRequestProperty("UA-CPU", "x86");
            //connection.setRequestProperty("Accept-Encoding", "gzip");
            // 很重要
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            // 发送POST请求必须设置这两项
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (params != null) {
                // 请求参数
                int paramSize = params.size();
                int index = 1;
                StringBuilder paramsBuilder = new StringBuilder();
                for (String key : params.keySet()) {
                    paramsBuilder.append(key).append("=").append(params.get(key));
                    if (index < paramSize) {
                        paramsBuilder.append("&");
                        index++;
                    }
                }

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(paramsBuilder.toString());
                writer.flush();
                writer.close();
            }

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                result = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
    public void changePageAndSetPagePosition(int position){
        bottomBar.selectTabAtPosition(2);
        zxdFragment.setPage(position);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        Test t = new Test();
//        Map<String, String> params = new HashMap<>();
//        params.put("unit_name", "测试公司");
//        submitPostRequest("http://211.149.235.17:8080/zxd/app/saveRzsq", params);
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
    HomeFragment homeFragment;
    ZXD2Fragment zxdFragment;
    YBJFragment YBJFragment;
    UserFragment userFragment;
    public void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_HOME:
                String home_str = getResources().getString(R.string.home_title);
                setToolbar(home_str,CONTENT_HOME);
                homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                setFragment(homeFragment, HOME_TAG);
                break;
            case CONTENT_ZXD:
                String zxd_str = getResources().getString(R.string.zxd);
                setToolbar(zxd_str,CONTENT_ZXD);
                zxdFragment = (ZXD2Fragment) getSupportFragmentManager().findFragmentByTag(ZXD_TAG);
                if (zxdFragment == null) {
                    zxdFragment = ZXD2Fragment.newInstance();
                }
                setFragment(zxdFragment, ZXD_TAG);
                break;
            case CONTENT_YBJ:
                String orders_str = getResources().getString(R.string.ybj);
                setToolbar(orders_str,CONTENT_YBJ);
                YBJFragment = (YBJFragment) getSupportFragmentManager().findFragmentByTag(YBJ_TAG);
                if (YBJFragment == null) {
                    YBJFragment = YBJFragment.newInstance();
                }
                setFragment(YBJFragment, YBJ_TAG);
                break;
            case CONTENT_MY:
                String my_str = getResources().getString(R.string.my);
                setToolbar(my_str,CONTENT_MY);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startLoanApplyActivity(EnterActivityEvent enterActivityEventy) {
        startActivity(new Intent(MainActivity.this, enterActivityEventy.getActivityClass()));
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
