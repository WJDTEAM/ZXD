package com.bf.zxd.zhuangxudai.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.ZXD.allAdapter;
import com.bf.zxd.zhuangxudai.ZXD.moneyAdapter;
import com.bf.zxd.zhuangxudai.ZXD.rateAdapter;
import com.bf.zxd.zhuangxudai.ZXD.timeAdapter;
import com.bf.zxd.zhuangxudai.customview.DropDownMenu;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ZxdBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/5.
 */

public class ZXD3Fragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.dropDownMenu_bank)
    DropDownMenu dropDownMenuBank;
    private String headers[] = {"全部","金额", "利率", "期限"};//
    private String all[] = {"全部","装修贷","房贷"};
    private String money[] = {"全部","3-20万","20-30万","30-80万","80-200万"};
    private String rate[] = {"全部","0.3%","0.4%","0.5%"};
    private String time[] = {"全部","12个月","24个月","36个月"};

    String chooseAll;
    String chooseMoney;
    String chooseRate;
    String chooseTime;

    public static ZXD3Fragment newInstance() {
        ZXD3Fragment fragment = new ZXD3Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    public ZXD3Fragment() {
        // Required empty public constructor
    }

    CompositeSubscription compositeSubscription;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_bank3, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        compositeSubscription=new CompositeSubscription();
        dropMenu();

        return view;
    }

    RecyclerView contentView;
    private List<View> popupViews = new ArrayList<>();
    private int AdapterPosition = 0;

    moneyAdapter moneyAdapter;
    EditText minMoney;
    EditText maxMoney;
    String minMoneyStr;
    String maxMoneyStr;

    allAdapter allAdapter;

    rateAdapter rateAdapter;
    EditText Rate;

    timeAdapter timeAdapter;
    EditText Time;

    private int rateAdapterPosition = 0;

    public void dropMenu(){
        //init money
        final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.zxd_money_adapter_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.zxd_money_gird);
        moneyAdapter = new moneyAdapter(getActivity(),money);
        constellation.setAdapter(moneyAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
         minMoney=ButterKnife.findById(constellationView, R.id.zxd_money_edi_min);
         maxMoney=ButterKnife.findById(constellationView, R.id.zxd_money_edi_max);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((minMoney.getText()==null||minMoney.getText().toString().equals(""))&&(maxMoney.getText()==null||maxMoney.getText().toString().equals(""))) {
                    chooseMoney = AdapterPosition == 0 ? headers[1] : money[AdapterPosition];
                    if(AdapterPosition!=0) {
                        String monetStr = chooseMoney.substring(0, chooseMoney.length() - 1);
                        String[] Str = monetStr.split("-");
                        minMoneyStr = Str[0];
                        maxMoneyStr = Str[1];
                    }else{
                        minMoneyStr="";
                        maxMoneyStr="";
                    }
                }
                else{
                    chooseMoney=minMoney.getText().toString()+"~"+maxMoney.getText().toString()+"万";
                    minMoneyStr=minMoney.getText().toString();
                    maxMoneyStr=maxMoney.getText().toString();
                }
                t.setText("装修贷"+minMoneyStr+maxMoneyStr+"万"+chooseAll+"类型"+chooseRate+"利率"+chooseTime+"期限");
                dropDownMenuBank.setTabText(chooseMoney);
                dropDownMenuBank.closeMenu();
                initData(chooseAll,minMoneyStr,maxMoneyStr,chooseRate,chooseTime);
            }
        });
        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moneyAdapter.setCheckItem(i);
                moneyAdapter.notifyDataSetChanged();
                AdapterPosition = i;
                minMoney.setText("");
                maxMoney.setText("");
            }
        });


        //init rate
        final View rateView = getActivity().getLayoutInflater().inflate(R.layout.zxd_rate_adapter_layout, null);
        GridView rateGrid = ButterKnife.findById(rateView, R.id.zxd_rate_gird);
        rateAdapter = new rateAdapter(getActivity(),rate);
        rateGrid.setAdapter(rateAdapter);
        TextView okrate = ButterKnife.findById(rateView, R.id.ok);
        Rate=ButterKnife.findById(rateView, R.id.zxd_rate_edi);
        okrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Rate.getText().toString()==null||Rate.getText().toString().equals("")) {
                    if(AdapterPosition!=0){
                        chooseRate = (AdapterPosition == 0 ? headers[2] : rate[AdapterPosition]).substring(0,(AdapterPosition == 0 ? headers[2] : rate[AdapterPosition]).length()-1);

                    }else{
                        chooseRate="";
                    }
                    dropDownMenuBank.setTabText(AdapterPosition == 0 ? headers[2] : rate[AdapterPosition]);
                }
                else{
                    chooseRate=Rate.getText().toString();
                    dropDownMenuBank.setTabText(Rate.getText().toString()+"%");
                }

                t.setText("装修贷"+minMoneyStr+maxMoneyStr+"万"+chooseAll+"类型"+chooseRate+"利率"+chooseTime+"期限");
                dropDownMenuBank.closeMenu();
                initData(chooseAll,minMoneyStr,maxMoneyStr,chooseRate,chooseTime);
            }
        });
        rateGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rateAdapter.setCheckItem(i);
                AdapterPosition = i;
                Rate.setText("");
            }
        });

        //init time
        final View timeView = getActivity().getLayoutInflater().inflate(R.layout.zxd_time_adapter_layout, null);
        GridView timeGrid = ButterKnife.findById(timeView, R.id.zxd_time_gird);
        timeAdapter = new timeAdapter(getActivity(),time);
        timeGrid.setAdapter(timeAdapter);
        TextView timeok = ButterKnife.findById(timeView, R.id.ok);
        Time=ButterKnife.findById(timeView, R.id.zxd_time_edi_min);
        timeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Time.getText()==null||Time.getText().toString().equals(""))){
                    if(AdapterPosition!=0){
                        chooseTime = (AdapterPosition == 0 ? headers[3] : time[AdapterPosition]).substring(0,(AdapterPosition == 0 ? headers[3] : time[AdapterPosition]).length()-2);

                    }else{
                        chooseTime="";
                    }
                    dropDownMenuBank.setTabText(AdapterPosition == 0 ? headers[3] : time[AdapterPosition]);
                }
                else{
                    chooseTime=Time.getText().toString()+"个月";
                }
                t.setText("装修贷"+minMoneyStr+maxMoneyStr+"万"+chooseAll+"类型"+chooseRate+"利率"+chooseTime+"期限");

                dropDownMenuBank.closeMenu();
                initData(chooseAll,minMoneyStr,maxMoneyStr,chooseRate,chooseTime);
            }
        });
        timeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                timeAdapter.setCheckItem(i);
                AdapterPosition = i;
                Time.setText("");

            }
        });

        //init type menu
        final ListView allView = new ListView(getActivity());
        allView.setDividerHeight(0);
        allAdapter = new allAdapter(getActivity(), all);
        allView.setAdapter(allAdapter);
        allView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                allAdapter.setCheckItem(i);
                chooseAll= i+"";
                if(i==0){
                    chooseAll="";
                }
                dropDownMenuBank.setTabText(i == 0 ? headers[0] : all[i]);
                dropDownMenuBank.closeMenu();
                t.setText("装修贷"+minMoneyStr+maxMoneyStr+"万"+chooseAll+"类型"+chooseRate+"利率"+chooseTime+"期限");
                initData(chooseAll,minMoneyStr,maxMoneyStr,chooseRate,chooseTime);
            }
        });
        popupViews.add(allView);
        popupViews.add(constellationView);
        popupViews.add(rateView);
        popupViews.add(timeView);
        //contentView = new RecyclerView(getActivity());
        t=new TextView(getActivity());
        t.setText("装修贷"+minMoneyStr+maxMoneyStr+"万"+chooseAll+"类型"+chooseRate+"利率"+chooseTime+"期限");
        dropDownMenuBank.setDropDownMenu(Arrays.asList(headers), popupViews, t);


    }
    TextView t;

    /**
     *  "loan_type":String (非必须) 贷款类型1:房贷;2:装修

     "min_money":String (非必须) 最小金额

     "max_money":String (非必须) 最大金额

     "rate":String (非必须) 费率

     "cycle":String(非必须) 贷款期限
     */
    public void initData(String loan_type,String min_money,String max_money,String rate,String cycle){

        Subscription subscription_getZxgs = NetWork.getZxService().getZxdItem(loan_type,min_money,max_money,rate,cycle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ZxdBank>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf","onError"+e.toString());
                    }

                    @Override
                    public void onNext(List<ZxdBank> zxdBanks) {

                        Log.i("gqf",zxdBanks.toString());
                    }
                });
        compositeSubscription.add(subscription_getZxgs);

    }

    @Override
    public void onStart() {
        super.onStart();
        initData(chooseAll,minMoneyStr,maxMoneyStr,chooseRate,chooseTime);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeSubscription.unsubscribe();
    }
}
