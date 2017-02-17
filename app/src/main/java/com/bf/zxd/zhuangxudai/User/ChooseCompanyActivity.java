package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChooseCompanyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivDeleteText)
    ImageView ivDeleteText;
    @BindView(R.id.rlSearchFrameDelete)
    RelativeLayout rlSearchFrameDelete;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.recyclerview_fragment_home)
    RecyclerView recyclerviewFragmentHome;

    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
        ButterKnife.bind(this);
        mCompositeSubscription = new CompositeSubscription();
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().getDecoCompanyItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DecoCompanyItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DecoCompanyItem> decoCompanyItems) {
                        setAdapter(decoCompanyItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }

    private void setAdapter(final List<DecoCompanyItem> decoCompanyItems) {
        //init context view
        recyclerviewFragmentHome.setLayoutManager(new LinearLayoutManager(this));
        //        recyclerviewZxgongsi.addItemDecoration(new RecycleViewDivider(
        //                this.getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));
        ZxgsItemAdapter zxgsAdapter = new ZxgsItemAdapter(decoCompanyItems, this);
        recyclerviewFragmentHome.setAdapter(zxgsAdapter);

        zxgsAdapter.setOnItemClickListener(new ZxgsItemAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                LoanApplyActivity.companyId=decoCompanyItems.get(postion).getCompanyId();
                LoanApplyActivity.mCompanyName=decoCompanyItems.get(postion).getCompanyName();
                finish();
            }
        });


    }

    @OnClick({R.id.btnSearch, R.id.etSearch, R.id.ivDeleteText, R.id.rlSearchFrameDelete, R.id.top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                break;
            case R.id.etSearch:
                break;
            case R.id.ivDeleteText:
                break;
            case R.id.rlSearchFrameDelete:
                break;
            case R.id.top:
                break;
        }
    }

}
