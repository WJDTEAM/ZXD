package com.bf.zxd.zhuangxudai.my.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.picker.AddressPickTask;
import com.bf.zxd.zhuangxudai.pojo.ApplyForInformatin;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithCompanyFile;
import com.bf.zxd.zhuangxudai.util.FileUitlity;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import hugo.weaving.DebugLog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func4;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.bf.zxd.zhuangxudai.User.UserInfoActivity.ALL_PHOTO;
import static com.bf.zxd.zhuangxudai.User.UserInfoActivity.REQUEST_CODE;
import static com.bf.zxd.zhuangxudai.User.UserInfoActivity.RESULT_PHOTO;

/**
 * Created by Daniel on 2017/2/14.
 */

public class FinancialApplyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.submitInformation_btn)
    Button submitInformationBtn;
    @BindView(R.id.apply_name_edi)
    EditText applyNameEdi;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.area_linearLayout)
    LinearLayout areaLinearLayout;
    @BindView(R.id.detail_address_edi)
    EditText detailAddressEdi;

    @BindView(R.id.businessLicense_img)
    ImageView businessLicenseImg;
    @BindView(R.id.businessLicense_linearLayout)
    LinearLayout businessLicenseLinearLayout;
    @BindView(R.id.idCard_img)
    ImageView idCardImg;
    @BindView(R.id.idCard_linearLayout)
    LinearLayout idCardLinearLayout;
    @BindView(R.id.login_form)
    ScrollView loginForm;
    @BindView(R.id.companyName_tv)
    EditText companyNameTv;


    private String mParam1;
    private String mParam2;

    private boolean isCompanyFile = false;
    private CompositeSubscription mCompositeSubscription;
    private Unbinder mUnbinder;

    public FinancialApplyFragment() {
        // Required empty public constructor
    }

    public static FinancialApplyFragment newInstance(String param1, String param2) {
        FinancialApplyFragment fragment = new FinancialApplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financle_apply, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mCompositeSubscription = new CompositeSubscription();
        return view;
    }

    @OnClick({R.id.submitInformation_btn, R.id.area_linearLayout, R.id.businessLicense_linearLayout, R.id.idCard_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitInformation_btn:
                submitINformation();

                break;
            case R.id.area_linearLayout:
                onAddressPicker();

                break;
            case R.id.businessLicense_linearLayout:
                isCompanyFile = true;
                ChangeIcon();

                break;
            case R.id.idCard_linearLayout:
                ChangeIcon();

                break;
        }
    }

    private void submitINformation() {

        ApplyForInformatin applyForInformatin = new ApplyForInformatin();
        applyForInformatin.setApplyType("02");
        applyForInformatin.setBusinessLicense(mBusinessLicense);
        applyForInformatin.setCompanyName(companyNameTv.getText().toString());
        applyForInformatin.setDetailedAddr(detailAddressEdi.getText().toString());
        applyForInformatin.setIdCard(mIdCard);
        applyForInformatin.setLocationArea(areaTv.getText().toString());
        applyForInformatin.setProposer(applyNameEdi.getText().toString());
        Gson gson = new Gson();

        Subscription saveEnter = NetWork.getNewZXD1_4Service().saveEnter(gson.toJson(applyForInformatin))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultCode resultCode) {
                        if (resultCode.getCode() == 10001) {
                            showToast(resultCode.getMsg());
                        } else {
                            showToast(resultCode.getMsg());
                        }

                    }
                });
        mCompositeSubscription.add(saveEnter);


    }

    /*提交按钮控制*/
    private void initSubmit() {
        Observable<CharSequence> applyName = RxTextView.textChanges(applyNameEdi).skip(1);
        Observable<CharSequence> area = RxTextView.textChanges(areaTv).skip(1);
        Observable<CharSequence> detailAddress = RxTextView.textChanges(detailAddressEdi).skip(1);
        Observable<CharSequence> companyName = RxTextView.textChanges(companyNameTv).skip(1);

        Observable.combineLatest(applyName, area, detailAddress, companyName, new Func4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
                boolean Bl = !TextUtils.isEmpty(charSequence);
                boolean B2 = !TextUtils.isEmpty(charSequence2);
                boolean B3 = !TextUtils.isEmpty(charSequence3);
                boolean B4 = !TextUtils.isEmpty(charSequence4);
                return Bl && B2 && B3 && B4;
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
                submitInformationBtn.setEnabled(aBoolean);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 地址选择器
     */
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(getActivity());
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
                    areaTv.setText(_msg);
                    //                    showToast(province.getAreaName() + city.getAreaName());
                } else {
                    String _msg = province.getAreaName() + city.getAreaName() + county.getAreaName();
                    areaTv.setText(_msg);
                    //                    showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("河南", "洛阳", "洛龙");
    }

    private String path;

    private void ChangeIcon() {
        //PopupWindow----START-----这里开始到下面标记的地方是实现点击头像弹出PopupWindow，实现用户从PopupWindow中选择更换头像的方式
        backgroundAlpha(0.3f);
        View view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.popu_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        popupWindow.setWidth(dm.widthPixels);
        popupWindow.setAnimationStyle(R.style.popuwindow);
        //显示位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new poponDismissListener_FinancialApplyFragment());

        //PopupWindow-----END
        //PopupWindow中对应的选择按钮
        Button button = (Button) view.findViewById(R.id.take_photo);//通过拍照的方式
        Button button1 = (Button) view.findViewById(R.id.all_photo);//通过相册的方式
        Button button2 = (Button) view.findViewById(R.id.out);//取消按钮
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
                //调用手机相册的方法,该方法在下面有具体实现
                allPhoto();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
                //调用手机照相机的方法,通过Intent调用系统相机完成拍照，并调用系统裁剪器裁剪照片
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //创建文件路径,头像保存的路径
                File file = FileUitlity.getInstance(getActivity().getApplicationContext()).makeDir("head_image");
                //定义图片路径和名称
                path = file.getParent() + File.separatorChar + System.currentTimeMillis() + ".jpg";
                //保存图片到Intent中，并通过Intent将照片传给系统裁剪器
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                //图片质量
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                //启动有返回的Intent，即返回裁剪好的图片到RoundImageView组件中显示
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    //该方法实现通过何种方式跟换图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果返回码不为-1，则表示不成功
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == ALL_PHOTO) {
            //调用相册
            Cursor cursor = getActivity().getContentResolver().query(data.getData(),
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            //游标移到第一位，即从第一位开始读取
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            //调用系统裁剪
            startPhoneZoom(Uri.fromFile(new File(path)));
        } else if (requestCode == REQUEST_CODE) {
            //相机返回结果，调用系统裁剪
            startPhoneZoom(Uri.fromFile(new File(path)));
        } else if (requestCode == RESULT_PHOTO) {
            //设置裁剪返回的位图
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bitmap = bundle.getParcelable("data");
                //将裁剪后得到的位图在组件中显示
                // TODO: 2017/2/13 修改头像
                Log.e("Daniel", "---bitmap.toString()---" + bitmap.toString());
                uploadAvatars(bitmap);
                //
            }
        }
    }

    private String mBusinessLicense = "";
    private String mIdCard = "";

    private void uploadAvatars(Bitmap bitmap) {
        File dcimFile = new File(getActivity().getApplicationContext().getExternalCacheDir() + "yyy.png");
        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(dcimFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        Log.e("Daniel","---dcimFile.getAbsolutePath()---"+dcimFile.getAbsolutePath());
        //        Log.e("Daniel","---dcimFile---"+dcimFile);
        //        Log.e("Daniel","---userInfo.getUserId()---"+userInfo.getUserId());
        if (dcimFile.exists()) {
            Log.e("Daniel", "---文件存在---");

            MultipartBody.Builder builder = new MultipartBody.Builder();
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), dcimFile);
            builder.addFormDataPart("zichifile", dcimFile.getName(), photoRequestBody);
            builder.setType(MultipartBody.FORM);
            MultipartBody mb = builder.build();


            //            List<MultipartBody.Part> zichifile=new ArrayList<>();
            //            for(int i=0;i<mb.size();i++){
            //                zichifile.add(mb.part(i));
            //            }

            Subscription uploadCompanyFile = NetWork.getNewZXD1_4Service().uploadCompanyFile(mb.part(0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResultCodeWithCompanyFile>() {
                        @DebugLog
                        @Override
                        public void onCompleted() {


                        }

                        @DebugLog
                        @Override
                        public void onError(Throwable e) {

                        }

                        @DebugLog
                        @Override
                        public void onNext(ResultCodeWithCompanyFile resultCodeWithCompanyFile) {
                            if (resultCodeWithCompanyFile.getCode() == 10001) {
                                if (isCompanyFile) {
                                    mBusinessLicense = resultCodeWithCompanyFile.getCompanyFile();
                                    Picasso.with(getActivity()).load(resultCodeWithCompanyFile.getCompanyFile()).into(businessLicenseImg);
                                } else {
                                    mIdCard = resultCodeWithCompanyFile.getCompanyFile();
                                    Picasso.with(getActivity()).load(resultCodeWithCompanyFile.getCompanyFile()).into(idCardImg);
                                }
                                showToast(resultCodeWithCompanyFile.getMsg());
                            } else {
                                showToast(resultCodeWithCompanyFile.getMsg());
                            }
                            isCompanyFile = false;

                        }
                    });
            mCompositeSubscription.add(uploadCompanyFile);


        } else {
            Log.e("Daniel", "---文件不存在---");
        }


    }


    //调用系统裁剪的方法
    private void startPhoneZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否可裁剪
        intent.putExtra("corp", "true");
        //裁剪器高宽比
        intent.putExtra("aspectY", 1);
        intent.putExtra("aspectX", 1);
        //设置裁剪框高宽
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_PHOTO);
    }

    //调用手机相册
    private void allPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALL_PHOTO);
    }

    /**
     * 添加PopupWindow关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener_FinancialApplyFragment implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
        mUnbinder.unbind();
    }
}
