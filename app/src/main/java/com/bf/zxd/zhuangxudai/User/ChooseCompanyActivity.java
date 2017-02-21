package com.bf.zxd.zhuangxudai.User;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyItem;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;

import java.util.ArrayList;
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
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;

    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
        ButterKnife.bind(this);
        mCompositeSubscription = new CompositeSubscription();
        setToolBar();
        lodingIsFailOrSucess(1);
        Subscription Subscription_getZxglItem = NetWork.getNewZXD1_4Service().getDecoCompanyItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DecoCompanyItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lodingIsFailOrSucess(3);
                    }

                    @Override
                    public void onNext(List<DecoCompanyItem> decoCompanyItems) {
                        lodingIsFailOrSucess(2);
                        setAdapter(decoCompanyItems);
                    }
                });
        mCompositeSubscription.add(Subscription_getZxglItem);
    }

    private void setToolBar() {
        toolbar.setTitle("选择公司");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void lodingIsFailOrSucess(int i) {
        if (i == 1) {
            //加载中
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLodingTxt.setText("加载中...");
            YBJLoding.setBackgroundResource(R.drawable.loding_anim_lists);
            AnimationDrawable anim = (AnimationDrawable) YBJLoding.getBackground();
            anim.start();

        } else if (i == 2) {
            //加载成功
            YBJLoding.setBackground(null);
            YBJLoding.setVisibility(View.GONE);
            YBJLodingTxt.setVisibility(View.GONE);
        } else {
            //加载失败
            YBJLoding.setVisibility(View.VISIBLE);
            YBJLodingTxt.setVisibility(View.VISIBLE);
            YBJLoding.setBackground(null);
            YBJLodingTxt.setText("加载失败，请检查网络连接");
            YBJLoding.setImageResource(R.drawable.ic_loding_fail);
        }
    }

    ZxgsItemAdapter zxgsAdapter;
    List<DecoCompanyItem> decoCompanyItems;

    private void setAdapter(List<DecoCompanyItem> decoCompanyItes) {
        decoCompanyItems = decoCompanyItes;
        //init context view
        recyclerviewFragmentHome.setLayoutManager(new LinearLayoutManager(this));
        zxgsAdapter = new ZxgsItemAdapter(decoCompanyItems, this);
        recyclerviewFragmentHome.setAdapter(zxgsAdapter);
        zxgsAdapter.setOnItemClickListener(new ZxgsItemAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                LoanApplyActivity.companyId = decoCompanyItems.get(postion).getCompanyId();
                LoanApplyActivity.mCompanyName = decoCompanyItems.get(postion).getCompanyName();
                finish();
            }
        });
        initEdi();

    }

    public void initEdi() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    List<DecoCompanyItem> decoCompanyItemList = new ArrayList<DecoCompanyItem>();
                    for (DecoCompanyItem decoCompanyItem : decoCompanyItems) {
                        if (editable.toString().contains(decoCompanyItem.getCompanyName())) {
                            decoCompanyItemList.add(decoCompanyItem);
                        } else if (decoCompanyItem.getCompanyName().contains(editable.toString())) {
                            decoCompanyItemList.add(decoCompanyItem);
                        }
                    }
                    zxgsAdapter.setdatas(decoCompanyItemList);
                } else {
                    zxgsAdapter.setdatas(decoCompanyItems);
                }

            }
        });
    }

    @OnClick({R.id.etSearch, R.id.ivDeleteText, R.id.rlSearchFrameDelete, R.id.top})
    public void onClick(View view) {
        switch (view.getId()) {
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
