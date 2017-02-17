package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ApplyStatusItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplyScheduleActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    public static String applyType;
    public static int applyId;

    private  VerticalStepViewReverseFragment mVerticalStepViewFragment;
    public static List<ApplyStatusItem>  mApplyStatusItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).addActivity(this);
        setContentView(R.layout.activity_apply_schedule);
        ButterKnife.bind(this);
        setToolBar("进度申请");

        NetWork.getNewZXD1_4Service().getApplyStatusItem(applyType,applyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ApplyStatusItem>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @DebugLog

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel","----e--"+e.toString());
                    }
                    @DebugLog

                    @Override
                    public void onNext(List<ApplyStatusItem> applyStatusItems) {
                        mApplyStatusItems=applyStatusItems;
                        Log.e("Daniel","----applyStatusItems.size()--"+applyStatusItems.size());
                        if (mVerticalStepViewFragment==null){
                            mVerticalStepViewFragment = new VerticalStepViewReverseFragment(applyStatusItems);
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mVerticalStepViewFragment).commit();

                    }
                });



    }

    private void setToolBar(String title) {
        baseToolBar.setTitle(title);
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
