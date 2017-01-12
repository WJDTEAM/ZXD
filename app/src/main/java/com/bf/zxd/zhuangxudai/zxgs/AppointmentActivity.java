package com.bf.zxd.zhuangxudai.zxgs;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.CommitDialogFragment;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.picker.AddressPickTask;
import com.bf.zxd.zhuangxudai.pojo.DictData;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import de.hdodenhof.circleimageview.CircleImageView;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func6;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AppointmentActivity extends BaseActivity {

    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.loan_apply_for_btn)
    Button loanApplyForBtn;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.gsTitle_txt)
    TextView gsTitleTxt;
    @BindView(R.id.below_txt)
    TextView belowTxt;
    @BindView(R.id.address_txt)
    TextView addressTxt;
    @BindView(R.id.store_name_edi)
    EditText storeNameEdi;
    @BindView(R.id.phone_num_edi)
    EditText phoneNumEdi;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.address_down_edi)
    EditText addressDownEdi;
    @BindView(R.id.house_area_edi)
    TextView houseAreaEdi;
    @BindView(R.id.house_type_edi)
    TextView houseTypeEdi;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;
    @BindView(R.id.house_style_edi)
    TextView houseStyleEdi;

    private CompositeSubscription mcompositeSubscription;

    String houseStyle = "";
    String houseType = "";
    String houseArea = "";
    private String headers[] = {"风格", "户型", "面积"};
    private List<DictData> style;
    private List<DictData> model;
    private List<DictData> area;
    private String mData;

    private String[] str_style = new String[0];
    private String[] str_model = new String[0];
    private String[] str_area = new String[0];

    List<DictData> mDictDatas = new ArrayList<>();

    @DebugLog
    @Override
    public void initDate() {
        mcompositeSubscription = new CompositeSubscription();
        getDictData();

    }

    private void getDictData() {
        Subscription subscription = NetWork.getJzztService().getDictData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mcompositeSubscription.add(subscription);

    }

    Observer<List<DictData>> observer = new Observer<List<DictData>>() {
        @DebugLog
        @Override
        public void onCompleted() {

        }

        @DebugLog
        @Override
        public void onError(Throwable e) {
            Log.i("gqf", "onError" + e.toString());
        }

        @DebugLog
        @Override
        public void onNext(List<DictData> dictData) {
            mDictDatas = dictData;
            filterDictData();
        }
    };

    //过滤数据
    public void filterDictData() {
        Log.e("Daniel", "--- mDictDatas.size()---" + mDictDatas.size());
        style = new ArrayList<>();
        model = new ArrayList<>();
        area = new ArrayList<>();
        for (DictData d : mDictDatas) {
            if (d.getDict_code().equals("houseStyle")) {
                style.add(d);
            } else if (d.getDict_code().equals("houseType")) {
                model.add(d);
            } else {
                area.add(d);
            }
        }
        DictData d = new DictData();
        d.setDict_desc("不限");
        style.add(0, d);
        model.add(0, d);
        area.add(0, d);


        Log.e("Daniel", "--- style.size()---" + style.size());
        Log.e("Daniel", "--- model.size()---" + model.size());
        Log.e("Daniel", "--- area.size()---" + area.size());
        str_style = new String[style.size()];
        str_model = new String[model.size()];
        str_area = new String[area.size()];
        setHouseData();

    }


    private void setHouseData() {
        for (int i = 0; i < style.size(); i++) {
            str_style[i] = style.get(i).getDict_desc();
        }
        for (int i = 0; i < model.size(); i++) {
            str_model[i] = model.get(i).getDict_desc();
        }
        for (int i = 0; i < area.size(); i++) {
            str_area[i] = area.get(i).getDict_desc();
        }
        Log.e("Daniel", "--- str_style.size()---" + str_style.length);
        Log.e("Daniel", "--- str_model.size()---" + str_model.length);
        Log.e("Daniel", "--- str_area.size()---" + str_area.length);
    }


    @DebugLog
    @Override
    public void initView() {
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        setToolBar();
        initSubmit();

    }

    public void onOptionPicker(String[] datas, final int viewId) {
        Log.e("Daniel", "---" + datas.length);
        OptionPicker picker = new OptionPicker(this, datas);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(11);
        picker.setLineConfig(new WheelView.LineConfig(0));//使用最长的线
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
//                showToast("index=" + index + ", item=" + item);
                switch (viewId) {
                    case R.id.house_area_edi:
                        houseAreaEdi.setText(item);
                        break;
                    case R.id.house_style_edi:
                        houseStyleEdi.setText(item);
                        break;
                    case R.id.house_type_edi:
                        houseTypeEdi.setText(item);
                        break;
                }
            }
        });

        picker.show();
    }


    /*提交按钮控制*/
    private void initSubmit() {
        Observable<CharSequence> userName = RxTextView.textChanges(storeNameEdi).skip(1);
        Observable<CharSequence> phoneNum = RxTextView.textChanges(phoneNumEdi).skip(1);
        Observable<CharSequence> address = RxTextView.textChanges(addressTv).skip(1);
        Observable<CharSequence> houseArea = RxTextView.textChanges(houseAreaEdi).skip(1);
        Observable<CharSequence> houseType = RxTextView.textChanges(houseTypeEdi).skip(1);
        Observable<CharSequence> houseStyle = RxTextView.textChanges(houseStyleEdi).skip(1);
        Observable.combineLatest(userName, phoneNum, address, houseArea, houseType, houseStyle, new Func6<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5, CharSequence charSequence6) {
                boolean userNameBl = !TextUtils.isEmpty(charSequence);
                boolean phoneNumBl = !TextUtils.isEmpty(charSequence2);
                boolean addressBl = !TextUtils.isEmpty(charSequence3);
                boolean houseAreaBl = !TextUtils.isEmpty(charSequence4);
                boolean houseTypeBl = !TextUtils.isEmpty(charSequence5);
                boolean houseStyleBl = !TextUtils.isEmpty(charSequence6);
                return userNameBl && phoneNumBl && addressBl && houseAreaBl && houseTypeBl && houseStyleBl;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                loanApplyForBtn.setEnabled(aBoolean);
            }
        });
    }

    @Override
    public void initEvent() {


    }

    private void setToolBar() {
        baseToolBar.setTitle("预约");
        baseToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        baseToolBar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(baseToolBar);
        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    String _msg = province.getAreaName() + city.getAreaName();
                    addressTv.setText(_msg);
//                    showToast(province.getAreaName() + city.getAreaName());
                } else {
                    String _msg = province.getAreaName() + city.getAreaName() + county.getAreaName();
                    addressTv.setText(_msg);
//                    showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("河南", "洛阳", "洛龙");
    }

    @OnClick({R.id.loan_apply_for_btn, R.id.address_tv, R.id.house_area_edi, R.id.house_style_edi, R.id.house_type_edi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_apply_for_btn:
                saveZxyy();
                break;
            case R.id.address_tv:
                onAddressPicker();
                break;
            case R.id.house_area_edi:
                onOptionPicker(str_area, R.id.house_area_edi);

                break;
            case R.id.house_style_edi:
                onOptionPicker(str_style, R.id.house_style_edi);

                break;
            case R.id.house_type_edi:
                onOptionPicker(str_model, R.id.house_type_edi);

                break;
        }
    }

    private void saveZxyy() {
        String _full_name = storeNameEdi.getText().toString();
        String _phone = phoneNumEdi.getText().toString();
        String _address = addressTv.getText().toString() + addressDownEdi.getText().toString();
        String _areaMsg = houseAreaEdi.getText().toString();
        int areaNum = -1;
        for (int i = 0; i < area.size(); i++) {
            String _area = area.get(i).getDict_desc();
            if (_areaMsg.equals(_area)) {
                areaNum = area.get(i).getDict_id();
            }
        }

        String _houseTypeMsg = houseTypeEdi.getText().toString();
        int houseTypeNum = -1;
        for (int i = 0; i < model.size(); i++) {
            String _model = model.get(i).getDict_desc();
            if (_houseTypeMsg.equals(_model)) {
                houseTypeNum = model.get(i).getDict_id();
            }
        }

        String _houseStyleMsg = houseStyleEdi.getText().toString();
        int houseStyleNum = -1;
        for (int i = 0; i < style.size(); i++) {
            String _style = style.get(i).getDict_desc();
            if (_areaMsg.equals(_style)) {
                houseStyleNum = style.get(i).getDict_id();
           }
        }

        int company_id = 16;

        Log.e("Daniel","---_full_name---"+_full_name);
        Log.e("Daniel","---_phone---"+_phone);
        Log.e("Daniel","---company_id---"+company_id);
        Log.e("Daniel","---_address---"+_address);
        Log.e("Daniel","---areaNum---"+areaNum);
        Log.e("Daniel","---houseTypeNum---"+houseTypeNum);
        Log.e("Daniel","---houseStyleNum---"+houseStyleNum);


        NetWork.getZxService().saveZxyy(_full_name, _phone, company_id, _address, areaNum, houseTypeNum, houseStyleNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResuleInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }

                    @DebugLog
                    @Override
                    public void onNext(ResuleInfo resuleInfo) {
                        int resuleCode = resuleInfo.getCode();
                        if (resuleCode == 10000) {
                            Toast.makeText(AppointmentActivity.this, "预约失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            CommitDialogFragment commitDialogFragment = new CommitDialogFragment();
                            commitDialogFragment.show(getFragmentManager(), "commitDialogFragment");
                            commitDialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                                @Override
                                public void dialogDismiss() {
                                    onBackPressed();
                                }
                            });
                        }

                    }
                });


    }
}
