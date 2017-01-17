package com.bf.zxd.zhuangxudai.template;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.util.SystemBarTintManager;
import com.bf.zxd.zhuangxudai.zxgs.AppointmentActivity;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;
import com.bf.zxd.zhuangxudai.zxgs.ZxgsDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateActivity extends AppCompatActivity implements TemplateImgFragment.mImgListener,TemplateDetailsFragment.mDetailsListener{

    TemplateImgFragment templateImgFragment;
    TemplateDetailsFragment mTemplateDetailsFragment;
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.template_details_fragment)
    FrameLayout templateDetailsFragment;
    BottomSheetBehavior mBottomSheetBehavior;


    int CompanyId;

    public int toolBarheight = 0;

    public boolean isToolBarShow = false;
    public static final String CHANGE_DETAILS_FRAGMENT="tempalte_details_fragment";

    public static final String CHANGE_IMG_FRAGMENT="tempalte_img_fragment";
    public static final float slidingDistance=300;
    private void setToolbar(int status, String toolstr) {

        baseToolBar.setTitle(toolstr);
//        ((TextView)baseToolBar.findViewById(R.id.toolbar_title)).setText(toolstr);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(baseToolBar);
        //图片界面
        if (status == 1) {
            baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
            baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            baseToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            //详情界面
            baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //切换fragment
                    changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT,0);
                }
            });
            baseToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }


    public void startActivity(Class<ZxgsDetailActivity> activity){
        startActivity(new Intent(TemplateActivity.this,activity).putExtra("Zxgs_id",CompanyId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempalte_details);
        ButterKnife.bind(this);

        changeSystemBarColor(R.color.black_dark);
        setToolbar(1, "");
        CompanyId=getIntent().getIntExtra("CompanyId",0);
        Log.i("gqf","CompanyId"+CompanyId);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));
        //加载图片fragment
        templateImgFragment = TemplateImgFragment.newInstance();
        mTemplateDetailsFragment=TemplateDetailsFragment.newInstance();

        changeFragment(templateImgFragment,CHANGE_IMG_FRAGMENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获得toolbar高度
        if(toolBarheight==0) {
            ViewTreeObserver vto = baseToolBar.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    baseToolBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    toolBarheight = baseToolBar.getHeight();
                    hide();
                    Log.i("gqf","onResume");
                }
            });
        }
    }
    //隐藏toolbar和底部栏
    public void hide() {
        baseToolBar.animate().translationY(0 - toolBarheight).setInterpolator(new AccelerateDecelerateInterpolator());
        isToolBarShow = false;
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
    //显示
    public void show() {
        baseToolBar.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
        isToolBarShow = true;
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if(!baseToolBar.getTitle().equals("")){
                changeFragmentByTAG(CHANGE_IMG_FRAGMENT,0);
            }else{
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void changeFragmentByTAG(String fragment,int index){
        Log.i("gqf","index"+index);
        if(fragment.equals(CHANGE_DETAILS_FRAGMENT)){
            //imgfragment上划出屏幕，显示toolbar和底部栏
            setToolbar(2, "现代风格时尚复式装");
            changeFragment(mTemplateDetailsFragment,fragment);
            show();
        }else{
            //imgfragment下划入屏幕，隐藏toolbar和底部栏
            setToolbar(1, "");
            changeFragment(templateImgFragment,fragment);
            if(index!=0){
                templateImgFragment.index=index;
                templateImgFragment.setViewPager(index);
            }

            hide();
        }
    }
    public void changeFragment(Fragment fragment,String fragmentTag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(fragmentManager.getFragments()==null) {
            transaction.add(R.id.template_details_fragment, mTemplateDetailsFragment);
            transaction.add(R.id.template_details_fragment, fragment);
            transaction.show(fragment);
            transaction.commitNow();
        }else {
                if (fragmentTag.equals(CHANGE_DETAILS_FRAGMENT)) {
                    transaction.setCustomAnimations(R.anim.base_slide_remain,R.anim.side_fragment_out);
                    transaction.hide(templateImgFragment);
                    transaction.show(fragment);
                    transaction.commitNow();

                } else {
                    transaction.setCustomAnimations(R.anim.side_fragment_in,R.anim.base_slide_remain);
                    transaction.hide(mTemplateDetailsFragment);
                    transaction.show(fragment);
                    transaction.commitNow();
                    //templateImgFragment.Enable=true;
                }
        }
    }

    public void changeSystemBarColor(int id) {
        // 改变状�?�栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(id);// 通知栏所�?颜色
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public int getToolBarheight() {
        return toolBarheight;
    }

    public boolean isToolBarShow() {
        return isToolBarShow;
    }

    public int getCompanyId() {
        return CompanyId;
    }
    @OnClick({R.id.template_loan_lin, R.id.template_subscribe_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.template_loan_lin:
                LoanApplyActivity.companyId=CompanyId;
                startActivity(new Intent(TemplateActivity.this, LoanApplyActivity.class));
                break;
            case R.id.template_subscribe_lin:
                startActivity(new Intent(TemplateActivity.this, AppointmentActivity.class));
                break;
        }
    }
}


