package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import static com.bf.zxd.zhuangxudai.util.Utils.getContext;

public class ApplyScheduleActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    public static String applyType;
    public static int applyId;

    //    private  VerticalStepViewReverseFragment mVerticalStepViewFragment;
    private List<ApplyStatusItem> mApplyStatusItems;
    TextView titleTv;
    TextView commentsTv;
    TextView dateTv;
    LinearLayout linear;
    @BindView(R.id.linearLayout_ll)
    LinearLayout linearLayoutLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).addActivity(this);
        setContentView(R.layout.activity_apply_schedule);
        ButterKnife.bind(this);
        setToolBar("进度申请");

        NetWork.getNewZXD1_4Service().getApplyStatusItem(applyType, applyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ApplyStatusItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Daniel", "----e--" + e.toString());
                    }

                    @DebugLog

                    @Override
                    public void onNext(List<ApplyStatusItem> applyStatusItems) {
                        mApplyStatusItems = applyStatusItems;
                        Log.e("Daniel", "----applyStatusItems.size()--" + applyStatusItems.size());
                        StepView(applyStatusItems);
                    }
                });


    }

    private void StepView(List<ApplyStatusItem> applyStatusItems) {

        for (int i = 0; i < applyStatusItems.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.content_applyschedule, null, false);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 定义布局管理器的参数
            linearLayout.setLayoutParams(param);
            titleTv = (TextView) view.findViewById(R.id.title_tv);
            commentsTv = (TextView) view.findViewById(R.id.comments_tv);
            dateTv = (TextView) view.findViewById(R.id.date_tv);

            ApplyStatusItem applyStatusItem = mApplyStatusItems.get(i);
            Log.e("Daniel", applyStatusItem.toString());
            switch (i) {
                case 0:
                    titleTv.setText("申请已提交");
                    break;
                case 1:
                    titleTv.setText("正在审核");
                    break;
                case 2:
                    titleTv.setText("申请失败");
                    break;
                case 3:
                    titleTv.setText("申请成功！");
                    break;

            }
            commentsTv.setText(applyStatusItem.getComment());
            dateTv.setText(applyStatusItem.getStatusRq());
            linearLayoutLl.addView(view);

        }


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
