package com.bf.zxd.zhuangxudai.template;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyCase;
import com.bf.zxd.zhuangxudai.util.SystemBarTintManager;
import com.bf.zxd.zhuangxudai.zxgs.AppointmentActivity;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateActivity extends AppCompatActivity implements TemplateImgFragment.mImgListener, TemplateDetailsFragment.mDetailsListener {

    TemplateImgFragment templateImgFragment;
    TemplateDetailsFragment mTemplateDetailsFragment;
    //    @BindView(R.id.toolbar_title)
    //    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.template_rel)
    RelativeLayout template_rel;

    @BindView(R.id.template_details_fragment)
    FrameLayout templateDetailsFragment;

    BottomSheetBehavior mBottomSheetBehavior;


    CompositeSubscription mcompositeSubscription;

    int CompanyId;//样板间CaseId

    public int compId = 0;//公司id

    List<DecoCompanyCase> decoCompanyCases;//公司案例
    public int toolBarheight = 0;
    LoginHelper loginHelper;
    public boolean isToolBarShow = false;
    public static final String CHANGE_DETAILS_FRAGMENT = "tempalte_details_fragment";

    public static final String CHANGE_IMG_FRAGMENT = "tempalte_img_fragment";
    public static final float slidingDistance = 300;

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
                    if(imgAddress.size()==0){
                        onBackPressed();
                    }else {
                        changeFragmentByTAG(TemplateActivity.CHANGE_IMG_FRAGMENT, 1);
                    }
                }
            });
            baseToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }


    public void startActivity(Class activity) {
        if (activity.equals(CommentsActivity.class)) {

            startActivity(new Intent(TemplateActivity.this, activity).putExtra("Zxgs_id", CompanyId));
        } else {
            startActivity(new Intent(TemplateActivity.this, activity).putExtra("Zxgs_id", compId));
        }

    }

    public void setCompanyId(int id) {
        compId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempalte_details);
        ButterKnife.bind(this);
        mcompositeSubscription = new CompositeSubscription();
        changeSystemBarColor(R.color.black_dark);
        setToolbar(1, "");
        CompanyId = getIntent().getIntExtra("CompanyId", 0);
        Log.i("gqf", "CompanyId" + CompanyId);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));
        //加载图片fragment
        templateImgFragment = TemplateImgFragment.newInstance();
        mTemplateDetailsFragment = TemplateDetailsFragment.newInstance();

        changeFragment(templateImgFragment, CHANGE_IMG_FRAGMENT);
        initImgData(CompanyId);

    }


    PopupWindow window;
    static boolean isPopuShow = false;

    public void popuWindow() {
        isPopuShow = true;
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.template_popuwindow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.tempopu_rel);

        rel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                window.dismiss();
                return false;
            }
        });
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 必须要给调用这个方法，否则点击popWindow以外部分，popWindow不会消失
        window.setBackgroundDrawable(new BitmapDrawable());

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 在参照的View控件下方显示
        // window.showAsDropDown(MainActivity.this.findViewById(R.id.start));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(template_rel,
                Gravity.CENTER, 0, 0);

        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mcompositeSubscription.unsubscribe();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    Timer timer;

    @Override
    protected void onResume() {
        super.onResume();
        //获得toolbar高度
        if (toolBarheight == 0) {
            ViewTreeObserver vto = baseToolBar.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    baseToolBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    toolBarheight = baseToolBar.getHeight();
                    hide();
                    Log.i("gqf", "onResume");
                }
            });
        }
        if (!isPopuShow) {
//            TimerTask task = new TimerTask() {
//                public void run() {
//                    template_rel.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            popuWindow();
//                            timer.cancel();
//                        }
//                    });
//                }
//            };
//            timer = new Timer(true);
//            timer.schedule(task, 200, 10000000);
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
            if (!baseToolBar.getTitle().equals("")) {
                changeFragmentByTAG(CHANGE_IMG_FRAGMENT, 1);
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void changeFragmentByTAG(String fragment, int index) {
        Log.i("gqf", "index" + index);
        if (fragment.equals(CHANGE_DETAILS_FRAGMENT)) {
            //imgfragment上划出屏幕，显示toolbar和底部栏
            setToolbar(2, "现代风格时尚复式装");
            changeFragment(mTemplateDetailsFragment, fragment);
            show();
        } else {
            //imgfragment下划入屏幕，隐藏toolbar和底部栏
            setToolbar(1, "");
            changeFragment(templateImgFragment, fragment);
            templateImgFragment.index = index;
            templateImgFragment.setViewPager(index);
            hide();
        }
    }

    public void changeFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments() == null) {
            transaction.add(R.id.template_details_fragment, mTemplateDetailsFragment);
            transaction.add(R.id.template_details_fragment, fragment);
            transaction.show(fragment);
            transaction.commitNow();
        } else {
            if (fragmentTag.equals(CHANGE_DETAILS_FRAGMENT)) {
                transaction.setCustomAnimations(R.anim.base_slide_remain, R.anim.side_fragment_out);
                transaction.hide(templateImgFragment);
                transaction.show(fragment);
                transaction.commitNow();

            } else {
                transaction.setCustomAnimations(R.anim.side_fragment_in, R.anim.base_slide_remain);
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
                if (LoanApplyActivity.bankId > 0) {
                    LoanApplyActivity.companyId=compId;
                    Log.i("gqf","compId"+compId);
                    startActivity(new Intent(TemplateActivity.this, LoanApplyActivity.class));
                } else {
                    MainActivity.isBottom2 = true;
                    startActivity(new Intent(TemplateActivity.this, MainActivity.class));
                }
                break;
            case R.id.template_subscribe_lin:
                AppointmentActivity.CompanyId=compId;
                Log.i("gqf","compId"+compId);
                Intent _intent = new Intent(TemplateActivity.this, AppointmentActivity.class);
                startActivity(new Intent(_intent));
                break;
        }
    }

    List<String> imgAddress;

    public void initImgData(int caseeId) {
        imgAddress = new ArrayList<>();
        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyCaseImages(caseeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "e" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {

                        if(strings.size()>0&&isPopuShow==false){
                            isPopuShow=true;
                            popuWindow();
                        }
                        templateImgFragment.initImg(strings);
                        mTemplateDetailsFragment.initListView(strings);
                    }
                });
        mcompositeSubscription.add(subscription);
    }

    public List<String> getImgAddress() {
        return imgAddress;
    }

}


