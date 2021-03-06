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
import com.bf.zxd.zhuangxudai.pojo.ApplyDecorate;
import com.bf.zxd.zhuangxudai.pojo.DecoCompanyDetail;
import com.bf.zxd.zhuangxudai.pojo.HouseBaseInfo;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.util.Phone;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

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
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func5;
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
    EditText houseAreaEdi;
    @BindView(R.id.house_type_edi)
    EditText houseTypeEdi;
    @BindView(R.id.activity_product_exhibition)
    RelativeLayout activityProductExhibition;
//    @BindView(R.id.house_style_edi)
//    TextView houseStyleEdi;

    private CompositeSubscription mcompositeSubscription;
    public static int CompanyId=-1;

    String houseStyle = "";
    String houseType = "";
    String houseArea = "";
    private String headers[] = {"风格", "户型", "面积"};
    private List<HouseBaseInfo> style;
    private List<HouseBaseInfo> model;
    private List<HouseBaseInfo> area;
    private String mData;

    private String[] str_style = new String[0];
    private String[] str_model = new String[0];
    private String[] str_area = new String[0];

    List<HouseBaseInfo> mDictDatas = new ArrayList<>();

    private Realm realm;
    private NewUser mNewUser;

    @DebugLog
    @Override
    public void initDate() {
        mcompositeSubscription = new CompositeSubscription();
        getDictData();
        Log.e("Daniel","---CompanyId---"+CompanyId);
        getZxgs(CompanyId);

    }

    private void getZxgs(int CompanyId) {
        Log.e("Daniel","---CompanyId---"+CompanyId);
        Subscription subscription = NetWork.getNewZXD1_4Service().getDecoCompanyDetail(CompanyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DecoCompanyDetail>() {
                    @Override
                    public void onCompleted() {
                        Log.i("gqf", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(DecoCompanyDetail zxgs) {
                        Log.e("Daniel","---zxgs---"+zxgs.toString());
                        Picasso.with(AppointmentActivity.this)
                                .load(zxgs.getCompanyIcon())
                                .placeholder(R.drawable.demo)
                                .error(R.drawable.demo)
                                .into(image);

                        gsTitleTxt.setText(zxgs.getCompanyName());
                        belowTxt.setText(zxgs.getTel());
                            addressTxt.setText(zxgs.getAddr());
                    }

                });
        mcompositeSubscription.add(subscription);

    }

    private void getDictData() {
        Subscription subscription = NetWork.getNewZXD1_4Service().getHouseBaseInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mcompositeSubscription.add(subscription);

    }

    Observer<List<HouseBaseInfo>> observer = new Observer<List<HouseBaseInfo>>() {
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
        public void onNext(List<HouseBaseInfo> dictData) {
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
            str_style[i] = style.get(i).getDictDesc();
        }
        for (int i = 0; i < model.size(); i++) {
            str_model[i] = model.get(i).getDictDesc();
        }
        for (int i = 0; i < area.size(); i++) {
            str_area[i] = area.get(i).getDictDesc();
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

        realm = Realm.getDefaultInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewUser = realm.where(NewUser.class).findFirst();

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
//                    case R.id.house_style_edi:
//                        houseStyleEdi.setText(item);
//                        break;
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
//        Observable<CharSequence> houseStyle = RxTextView.textChanges(houseStyleEdi).skip(1);
        Observable.combineLatest(userName, phoneNum, address, houseArea, houseType, new Func5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
                boolean userNameBl = !TextUtils.isEmpty(charSequence);
                boolean phoneNumBl = !TextUtils.isEmpty(charSequence2);
                boolean addressBl = !TextUtils.isEmpty(charSequence3);
                boolean houseAreaBl = !TextUtils.isEmpty(charSequence4);
                boolean houseTypeBl = !TextUtils.isEmpty(charSequence5);
//                boolean houseStyleBl = !TextUtils.isEmpty(charSequence6);
                return userNameBl && phoneNumBl && addressBl && houseAreaBl && houseTypeBl;
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
        baseToolBar.setNavigationIcon(R.drawable.barcode__back_arrow);
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

    @OnClick({R.id.loan_apply_for_btn, R.id.address_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_apply_for_btn:
                String _phone =phoneNumEdi.getText().toString() ;
                if (Phone.IsMobileNO(_phone)){

                    saveZxyy();
                }
                else{

                    Toast.makeText(this, "手机格式不正确！", Toast.LENGTH_SHORT).show();
                    phoneNumEdi.setText("");
                }
                break;
            case R.id.address_tv:
                onAddressPicker();
                break;
//            case R.id.house_area_edi:
//                onOptionPicker(str_area, R.id.house_area_edi);
//
//                break;
//            case R.id.house_style_edi:
//                onOptionPicker(str_style, R.id.house_style_edi);
//
//                break;
//            case R.id.house_type_edi:
//                onOptionPicker(str_model, R.id.house_type_edi);
//
//                break;
        }
    }
    int userId;
    private void saveZxyy() {
        if (mNewUser!=null){
            userId = mNewUser.getUserId();
        }else {
            userId=-1;
        }
        String _full_name = storeNameEdi.getText().toString() ;
        String _phone =phoneNumEdi.getText().toString() ;
        String _address =(addressTv.getText().toString() + addressDownEdi.getText().toString()) ;
        String _areaMsg =houseAreaEdi.getText().toString();
        String houseType = houseTypeEdi.getText().toString();

        int areaNum = -1;
        for (int i = 0; i < area.size(); i++) {
            String _area = area.get(i).getDictDesc();
            if (_areaMsg.equals(_area)) {
                areaNum = area.get(i).getDictId();
            }
        }

        String _houseTypeMsg = houseTypeEdi.getText().toString();
        int houseTypeNum = -1;
        for (int i = 0; i < model.size(); i++) {
            String _model = model.get(i).getDictDesc();
            if (_houseTypeMsg.equals(_model)) {
                houseTypeNum = model.get(i).getDictId();
            }
        }

//        String _houseStyleMsg = houseStyleEdi.getText().toString();
        int houseStyleNum = -1;
        for (int i = 0; i < style.size(); i++) {
            String _style = style.get(i).getDictDesc();
            if (_areaMsg.equals(_style)) {
                houseStyleNum = style.get(i).getDictId();
           }
        }

//        int company_id = 16;
        Log.e("Daniel","---userId---"+userId);
        Log.e("Daniel","---CompanyId---"+CompanyId);
        Log.e("Daniel","---_full_name---"+_full_name);
        Log.e("Daniel","---_phone---"+_phone);
        Log.e("Daniel","---_address---"+_address);
        Log.e("Daniel","---_areaMsg---"+_areaMsg);
        Log.e("Daniel","---houseType---"+houseType);

        ApplyDecorate applyDecorate = new ApplyDecorate();
        applyDecorate.setProposer(_full_name);
        applyDecorate.setAddr(_address);
        applyDecorate.setFromUserId(userId);
        applyDecorate.setHouseArea(_areaMsg);
        applyDecorate.setHouseType(houseType);
        applyDecorate.setTel(_phone);
        applyDecorate.setToCompanyId(CompanyId);
        Gson gson = new Gson();

        NetWork.getNewZXD1_4Service().saveZxyy(gson.toJson(applyDecorate))
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
