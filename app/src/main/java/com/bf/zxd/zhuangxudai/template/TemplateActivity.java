package com.bf.zxd.zhuangxudai.template;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.util.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateActivity extends AppCompatActivity {

    TemplateImgFragment templateImgFragment;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.template_details_fragment)
    FrameLayout templateDetailsFragment;
    BottomSheetBehavior mBottomSheetBehavior;
    public int toolBarheight = 0;

    public boolean isToolBarShow = false;

    private void setToolbar(int status, String toolstr) {

        baseToolBar.setTitle(toolstr);
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
            baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
            baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //切换fragment

                    //重置toolbar
                    setToolbar(1, "");
                }
            });
            baseToolBar.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempalte_details);
        ButterKnife.bind(this);
        changeSystemBarColor(R.color.black_dark);
        setToolbar(1, "");
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));

        //加载图片fragment
        templateImgFragment = TemplateImgFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.template_details_fragment, templateImgFragment);
        transaction.commit();





    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获得toolbar高度
        ViewTreeObserver vto = baseToolBar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                baseToolBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hide(baseToolBar.getHeight());
                toolBarheight = baseToolBar.getHeight();
            }
        });
    }
    //隐藏toolbar和底部栏
    public void hide(int height) {
        baseToolBar.animate().translationY(0 - height).setInterpolator(new AccelerateDecelerateInterpolator());
        isToolBarShow = false;
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
    //显示
    public void show() {
        baseToolBar.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
        isToolBarShow = true;
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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


}


