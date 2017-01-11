package com.bf.zxd.zhuangxudai.zxgs;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.BaseActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.picker.AddressPickTask;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.functions.Func5;

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

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        setToolBar();
        addressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddressPicker();
            }
        });

        initSubmit();


    }
    private void initSubmit() {
        Observable<CharSequence> userName = RxTextView.textChanges(storeNameEdi).skip(1);
        Observable<CharSequence> phoneNum = RxTextView.textChanges(phoneNumEdi).skip(1);
        Observable<CharSequence> address = RxTextView.textChanges(addressTv).skip(1);
        Observable<CharSequence> houseArea = RxTextView.textChanges(houseAreaEdi).skip(1);
        Observable<CharSequence> houseType = RxTextView.textChanges(houseTypeEdi).skip(1);
        Observable.combineLatest(userName, phoneNum, address, houseArea, houseType, new Func5<CharSequence,CharSequence,CharSequence,CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
                boolean userNameBl = !TextUtils.isEmpty(charSequence);
                boolean phoneNumBl = !TextUtils.isEmpty(charSequence2);
                boolean addressBl = !TextUtils.isEmpty(charSequence3);
                boolean houseAreaBl = !TextUtils.isEmpty(charSequence4);
                boolean houseTypeBl = !TextUtils.isEmpty(charSequence5);
                return userNameBl&&phoneNumBl&&addressBl&&houseAreaBl&&houseTypeBl;
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
                    showToast(province.getAreaName() + city.getAreaName());
                } else {
                    showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("河南", "洛阳", "洛龙");
    }

    @OnClick({R.id.loan_apply_for_btn, R.id.address_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_apply_for_btn:
                break;
            case R.id.address_tv:
                break;
        }
    }
}
