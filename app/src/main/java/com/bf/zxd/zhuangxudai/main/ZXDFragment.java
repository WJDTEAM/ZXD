package com.bf.zxd.zhuangxudai.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.AutoHeightLayoutManager;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.Zxd;
import com.bf.zxd.zhuangxudai.template.TemplateListAdapter;
import com.bf.zxd.zhuangxudai.zxgs.LoanApplyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class ZXDFragment extends Fragment {
    Realm realm;
    @BindView(R.id.loan_bank_list)
    RecyclerView loanBankList;

    CompositeSubscription mcompositeSubscription;
    LoanBankListAdapter loanBankListAdapter;

    List<Zxd> mZxds;
    public static ZXDFragment newInstance() {
        ZXDFragment fragment = new ZXDFragment();
        return fragment;
    }
    public interface mListener {

        public void startLoanApplyActivity();
    }

    private mListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mListener) activity;

    }
    public void initData(){
        Subscription Subscription_getZxglItem= NetWork.getZxService().getZxdItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Zxd>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Zxd> zxds) {
                        mZxds=zxds;
                        initListView(zxds);
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);
    }
    public void initListView(List<Zxd> zxds){
        if(loanBankListAdapter==null){
            loanBankListAdapter=new LoanBankListAdapter(getActivity(),zxds);
            AutoHeightLayoutManager autoHeightLayoutManager=new AutoHeightLayoutManager(getActivity());
            autoHeightLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            loanBankList.setLayoutManager(autoHeightLayoutManager);
            loanBankList.setAdapter(loanBankListAdapter);
            loanBankListAdapter.setOnItemClickListener(new TemplateListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    LoanApplyActivity.mZxd=mZxds.get(postion);
                    mListener.startLoanApplyActivity();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_bank, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription=new CompositeSubscription();
        initData();
        return view;
    }
}
