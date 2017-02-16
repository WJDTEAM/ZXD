package com.bf.zxd.zhuangxudai.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.MyCollection;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/2/7.
 */

public class ConllectionListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "type";
    @BindView(R.id.conllection_list_RecyclerView)
    RecyclerView orderListRecyclerView;
    private int type;
    private Realm realm;
    private Unbinder unbinder;
    private CompositeSubscription mcompositeSubscription;
    private MyConllectionListAdapter myConllectionListAdapter;



    public static ConllectionListFragment newInstance(int type) {
        ConllectionListFragment fragment = new ConllectionListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the food_listitem for this fragment
        View view = inflater.inflate(R.layout.fragment_conllection_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm=Realm.getDefaultInstance();
        mcompositeSubscription=new CompositeSubscription();

        if(realm.where(NewUser.class).findFirst()!=null){
            //网络获取
            initData();
        }else{
            //本地获取
            initRealmData();
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void initRealmData(){
        List<MyCollection> myCollections=new ArrayList<>();
        for(MyCollection mc:realm.where(MyCollection.class).findAll()){
            switch (type) {
                case 0:
                    myCollections.add(mc);
                    break;
                case 1:
                    if(mc.getType().equals("01")){
                        myCollections.add(mc);
                    }
                    break;
                case 2:
                    if(mc.getType().equals("02")){
                        myCollections.add(mc);
                    }
                    break;
            }
        }
        initList(myCollections);
    }


    public void initData(){
        Subscription Subscription_getZxglItem= NetWork.getNewZXD1_4Service().getFavoriteItem(realm.where(NewUser.class).findFirst().getUserId())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<List<MyCollection>, Observable<MyCollection>>() {
                    @Override
                    public Observable<MyCollection> call(List<MyCollection> myCollections) {
                        return Observable.from(myCollections);
                    }
                })
                .filter(new Func1<MyCollection, Boolean>() {
                    @Override
                    public Boolean call(MyCollection ogms) {
                        switch (type) {
                            case 1:
                                return ogms.getType().equals("01");
                            case 2:
                                return ogms.getType().equals("02");
                        }
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyCollection>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MyCollection> myCollections) {
                        initList(myCollections);
                    }
                });
        mcompositeSubscription.add(Subscription_getZxglItem);
    }
    public void initList(List<MyCollection> myCollections){

        myConllectionListAdapter=new MyConllectionListAdapter(getActivity(),myCollections);
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderListRecyclerView.setAdapter(myConllectionListAdapter);

        myConllectionListAdapter.setOnItemClickListener(new MyConllectionListAdapter.MyItemClickListener() {
            @Override
            public void onItemCleck(int position) {
                Gson g=new Gson();
                ResultCode resultCode=new ResultCode();
                MyCollection myCollection=new MyCollection();
                myCollection.setType(myConllectionListAdapter.getDataItem(position).getType());
                myCollection.setTitle(myConllectionListAdapter.getDataItem(position).getTitle());
                myCollection.setThumbnails(myConllectionListAdapter.getDataItem(position).getThumbnails());
                myCollection.setHdrq(myConllectionListAdapter.getDataItem(position).getHdrq());
                myCollection.setArticleId(myConllectionListAdapter.getDataItem(position).getArticleId());
                myCollection.setDescription(myConllectionListAdapter.getDataItem(position).getDescription());
                resultCode.setMsg(g.toJson(myCollection));
                resultCode.setCode(Integer.parseInt( myConllectionListAdapter.getDataItem(position).getType()));
                EventBus.getDefault().post(resultCode);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
        mcompositeSubscription.unsubscribe();

    }
}
