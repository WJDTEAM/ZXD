package com.bf.zxd.zhuangxudai.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.customview.DropDownMenu;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.CompanyIdAndTemplateActivityEvent;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyYbjItem;
import com.bf.zxd.zhuangxudai.pojo.HouseBaseInfo;
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
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class YBJFragment extends Fragment {
    Realm realm;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    RecyclerView contentView;
    TemplateListAdapter templateListAdapter;
    @BindView(R.id.YBJ_loding)
    ImageView YBJLoding;
    @BindView(R.id.YBJ_loding_txt)
    TextView YBJLodingTxt;
    private CompositeSubscription mcompositeSubscription;

    public static YBJFragment newInstance() {
        YBJFragment fragment = new YBJFragment();
        return fragment;
    }

    int houseStyle = 0;
    int houseType = 0;
    int houseArea = 0;
    private String headers[] = {"风格", "户型", "面积"};
    private List<HouseBaseInfo> style;
    private List<HouseBaseInfo> model;
    private List<HouseBaseInfo> area;

    List<HouseBaseInfo> mDictDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        mcompositeSubscription = new CompositeSubscription();
        //判断本地是否有选择条件
        //mDictDatas = realm.where(DictData.class).findAll();
        //没有则网络获取

        lodingIsFailOrSucess(1);

        if (mDictDatas == null) {
            getDictData();
        } else {
            filterDictData();
            initDropDpwnMenu();
        }


        return view;
    }

    Observer<List<HouseBaseInfo>> observer = new Observer<List<HouseBaseInfo>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            lodingIsFailOrSucess(3);
        }

        @Override
        public void onNext(List<HouseBaseInfo> dictDatas) {
            mDictDatas = dictDatas;
            filterDictData();
            initDropDpwnMenu();
        }
    };

    //过滤数据
    public void filterDictData() {
        style = new ArrayList<>();
        model = new ArrayList<>();
        area = new ArrayList<>();
        for (HouseBaseInfo d : mDictDatas) {
            if (d.getDictCode().equals("houseStyle")) {
                style.add(d);
            } else if (d.getDictCode().equals("houseType")) {
                model.add(d);
            } else {
                area.add(d);
            }
        }
        HouseBaseInfo d = new HouseBaseInfo();
        d.setDictDesc("不限");
        style.add(0, d);
        model.add(0, d);
        area.add(0, d);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //获得顶部条件数据
    public void getDictData() {
        Subscription subscription = NetWork.getNewZXD1_4Service().getHouseBaseInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mcompositeSubscription.add(subscription);
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

    //获取样板间数据
    public void initJzztData(int houseStyle, int houseType, int houseArea) {

        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyYbjItem(houseStyle, houseType, houseArea)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DecoCompanyYbjItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lodingIsFailOrSucess(3);
                    }

                    @Override
                    public void onNext(List<DecoCompanyYbjItem> decoCompanyYbjItems) {
                        initListView(decoCompanyYbjItems);
                        lodingIsFailOrSucess(2);
                    }
                });
        mcompositeSubscription.add(subscription);
    }

    public void initListView(List<DecoCompanyYbjItem> lists) {
        if (templateListAdapter == null) {
            templateListAdapter = new TemplateListAdapter(getActivity(), lists);
            contentView.setAdapter(templateListAdapter);
            templateListAdapter.setOnItemClickListener(new TemplateListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    //发送广播通知mainactivity跳转页面
                    CompanyIdAndTemplateActivityEvent companyIdAndTemplateActivityEvent = new CompanyIdAndTemplateActivityEvent();
                    companyIdAndTemplateActivityEvent.setCompanyId(templateListAdapter.getmDatas().get(postion).getCaseId());
                    companyIdAndTemplateActivityEvent.setActivityClass(TemplateActivity.class);
                    EventBus.getDefault().post(companyIdAndTemplateActivityEvent);
                }
            });
        } else {
            templateListAdapter.update(lists);
        }
    }

    GirdDropDownAdapter modelAdapter;
    ListDropDownAdapter areaAdapter;
    ConstellationAdapter styleAdapter;
    private int constellationPosition = 0;
    private List<View> popupViews = new ArrayList<>();

    public void initDropDpwnMenu() {

        //init constellation
        final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        styleAdapter = new ConstellationAdapter(getActivity(), style);
        constellation.setAdapter(styleAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseStyle = constellationPosition == 0 ? 0 : style.get(constellationPosition).getDictId();
                // houseStyle=constellationPosition == 0 ? headers[0]: style.get(constellationPosition).getDict_desc();
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[0] : style.get(constellationPosition).getDictDesc());
                mDropDownMenu.closeMenu();
                initJzztData(houseStyle, houseType, houseArea);
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
                houseType = position == 0 ? 0 : model.get(position).getDictId();
                mDropDownMenu.setTabText(position == 0 ? headers[1] : model.get(position).getDictDesc());
                mDropDownMenu.closeMenu();
                initJzztData(houseStyle, houseType, houseArea);
            }
        });

        areaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                houseArea = position == 0 ? 0 : area.get(position).getDictId();
                mDropDownMenu.setTabText(position == 0 ? headers[2] : area.get(position).getDictDesc());
                mDropDownMenu.closeMenu();
                initJzztData(houseStyle, houseType, houseArea);
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                styleAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });
        //设置内容
        contentView = new RecyclerView(getActivity());

        initJzztData(houseStyle, houseType, houseArea);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        contentView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

}
