package com.bf.zxd.zhuangxudai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.main.MainActivity;
import com.blankj.utilcode.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.startPage_img)
    ImageView startPageImg;
    @BindView(R.id.activity_time_tv)
    TextView activityTimeTv;
    @BindView(R.id.activity_start_page)
    RelativeLayout activityStartPage;

    private CompositeSubscription mCompositeSubscription;
    private static final int TIMETOCOUNT = 3;
    private static Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_welcome);
        mCompositeSubscription = new CompositeSubscription();
        ButterKnife.bind(this);
        Picasso.with(WelcomeActivity.this).load(R.drawable.startpage).into(startPageImg);
        countToEnter();
    }

    /**
     * 倒计时
     */
    @DebugLog
    private void countToEnter() {
        Subscription subscriptionCount = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .limit(TIMETOCOUNT)
                .map(new Func1<Long, Long>() {
                    @DebugLog
                    @Override
                    public Long call(Long aLong) {
                        return TIMETOCOUNT - aLong;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        WelcomeActivity.this.finish();
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }

                    @DebugLog
                    @Override
                    public void onNext(Long aLong) {
//                        activityTimeTv.setText(aLong + "秒");
                    }
                });
        mCompositeSubscription.add(subscriptionCount);

    }
}
