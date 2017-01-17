package com.bf.zxd.zhuangxudai.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ZxdItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ZXDListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "type";
    private static List<ZxdItem> zxdItems;
    @BindView(R.id.zxd_recyclerview)
    RecyclerView zxd_recyclerview;

    private int type;
    private Unbinder unbinder;
    private LoanBankList2Adapter loanBankList2Adapter;
    private Subscription subscription;
    private Realm realm;
    private long hotelId;



    public ZXDListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SeatFragment.
     */
    public static ZXDListFragment newInstance(int type) {
        ZXDListFragment fragment = new ZXDListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        setList("");
    }
    private void setList(String dkfw) {

        subscription = NetWork.getZxService().getZxdItem(dkfw).subscribeOn(Schedulers.io())
                //过滤
                .flatMap(new Func1<List<ZxdItem>, Observable<ZxdItem>>() {
                    @Override
                    public Observable<ZxdItem> call(List<ZxdItem> zxdItems) {
                        Log.i("gqf","Observable"+zxdItems.toString());
                        return Observable.from(zxdItems);
                    }
                })
                .filter(new Func1<ZxdItem, Boolean>() {
                    @Override
                    public Boolean call(ZxdItem zxdItem) {
                            switch (type) {
                            case 1:
                                return zxdItem.getDkfw().equals("大额贷");
                            case 2:
                                return zxdItem.getDkfw().equals("小额贷");
                        }
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ZxdItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf","onError"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<ZxdItem> zxdItem) {

                        Log.i("gqf",zxdItem.toString());
                        initList(zxdItem);
                    }
                });
        mcompositeSubscription.add(subscription);
    }
    public void initList(List<ZxdItem> zxdItem){
            loanBankList2Adapter=new LoanBankList2Adapter(getActivity(),zxdItem);
            zxd_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            zxd_recyclerview.setAdapter(loanBankList2Adapter);

    }
    private CompositeSubscription mcompositeSubscription;
    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the food_listitem for this fragment
        View view = inflater.inflate(R.layout.fragment_zxd_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mcompositeSubscription=new CompositeSubscription();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        mcompositeSubscription.unsubscribe();
    }
}
