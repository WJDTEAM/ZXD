package com.bf.zxd.zhuangxudai.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.DropDownMenu;
import com.bf.zxd.zhuangxudai.customview.RecycleViewDivider;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.DictData;
import com.bf.zxd.zhuangxudai.template.ConstellationAdapter;
import com.bf.zxd.zhuangxudai.template.GirdDropDownAdapter;
import com.bf.zxd.zhuangxudai.template.ListDropDownAdapter;
import com.bf.zxd.zhuangxudai.template.TemplateActivity;
import com.bf.zxd.zhuangxudai.template.TemplateListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class YBJFragment extends Fragment {
    Realm realm;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private CompositeSubscription mcompositeSubscription;
    public static YBJFragment newInstance() {
        YBJFragment fragment = new YBJFragment();
        return fragment;
    }


    private String headers[] = {"风格", "户型", "面积"};
    private List<DictData> style;
    private List<DictData> model;
    private List<DictData> area;

    List<DictData> mDictDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription=new CompositeSubscription();
        //判断本地是否有选择条件
        //mDictDatas = realm.where(DictData.class).findAll();
        //没有则网络获取
        if(mDictDatas==null){
            getDictData();
        }else{
            filterDictData();
            initDropDpwnMenu();
        }


        return view;
    }
    Observer<List<DictData>> observer=new Observer<List<DictData>>() {
        @Override
        public void onCompleted() {
            Log.i("gqf","onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.i("gqf","onError"+e.toString());
        }

        @Override
        public void onNext(List<DictData> dictData) {
            mDictDatas=dictData;
            filterDictData();
            initDropDpwnMenu();
            Log.i("gqf",dictData.toString());
        }
    };
    //过滤数据
    public void filterDictData(){
        style=new ArrayList<>();
        model=new ArrayList<>();
        area=new ArrayList<>();
        for(DictData d:mDictDatas){
            if(d.getDict_code().equals("houseStyle")){
                style.add(d);
            }else if(d.getDict_code().equals("houseType")){
                model.add(d);
            }else{
                area.add(d);
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    //获得顶部条件
    public void getDictData(){

        Subscription subscription = NetWork.getJzztService().getDictData()
                .subscribeOn(Schedulers.io())
                //遍历
                .flatMap(new Func1<List<DictData>, Observable<DictData>>() {
                    @Override
                    public Observable<DictData> call(List<DictData> dictDatas) {
                        return Observable.from(dictDatas);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(observer);
        mcompositeSubscription.add(subscription);
    }
    GirdDropDownAdapter modelAdapter;
    ListDropDownAdapter areaAdapter;
    ConstellationAdapter styleAdapter;
    private int constellationPosition = 0;
    private List<View> popupViews = new ArrayList<>();
    public void initDropDpwnMenu(){


        //init constellation
        final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        styleAdapter = new ConstellationAdapter(getActivity(),style);
        constellation.setAdapter(styleAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : style.get(constellationPosition).getDict_desc());
                mDropDownMenu.closeMenu();
            }
        });


        //init model menu
        final ListView modelView = new ListView(getActivity());
        modelView.setDividerHeight(0);
        modelAdapter = new GirdDropDownAdapter(getActivity(), model);
        modelView.setAdapter(modelAdapter);

        //init area menu
        final ListView areaView = new ListView(getActivity());
        areaView.setDividerHeight(0);
        areaAdapter = new ListDropDownAdapter(getActivity(), area);
        areaView.setAdapter(areaAdapter);


        popupViews.add(constellationView);
        popupViews.add(modelView);
        popupViews.add(areaView);

        modelView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modelAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : model.get(position).getDict_desc());
                mDropDownMenu.closeMenu();
            }
        });

        areaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : area.get(position).getDict_desc());
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                styleAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });
        //init context view
        RecyclerView contentView = new RecyclerView(getActivity());
        /*contentView.addItemDecoration(new RecycleViewDivider(
                getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, R.drawable.template_divider_shap));*/

        contentView.addItemDecoration(new RecycleViewDivider(
                getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gary_dark)));

        List<String> date=new ArrayList<>();
        date.add("1");
        date.add("1");
        TemplateListAdapter templateListAdapter=new TemplateListAdapter(getActivity(),date);
        contentView.setAdapter(templateListAdapter);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        templateListAdapter.setOnItemClickListener(new TemplateListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                //发送广播通知mainactivity跳转页面
                EventBus.getDefault().post(TemplateActivity.class);
                Log.i("gqf","postionTemplateActivity");

            }
        });

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

}
