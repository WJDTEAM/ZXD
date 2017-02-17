package com.bf.zxd.zhuangxudai.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Login.LoginActivity;
import com.bf.zxd.zhuangxudai.Login.LoginHelper;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.User.MyAppointmentActivity;
import com.bf.zxd.zhuangxudai.User.MyLoanActivity;
import com.bf.zxd.zhuangxudai.User.UserInfoActivity;
import com.bf.zxd.zhuangxudai.collection.MyConllectionActivity;
import com.bf.zxd.zhuangxudai.my.LoanApplyForActivity;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by johe on 2017/1/5.
 */

public class UserFragment extends Fragment {
    Realm realm;
    @BindView(R.id.apply_linear)
    LinearLayout applyLinear;
    @BindView(R.id.memoryCode)
    LinearLayout memoryCode;
    @BindView(R.id.myApointment_linear)
    LinearLayout myApointmentLinear;
    @BindView(R.id.aboutOur_tv)
    LinearLayout aboutOurTv;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.nick_tv)
    TextView nickTv;

    private NewUser mUser;


    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        return view;
    }

    LoginHelper loginHelper;

    public boolean initLogin(Class activity) {
        loginHelper = LoginHelper.getInstence();
        return loginHelper.startActivityWithLogin(getActivity(), activity);
    }

    @Override
    public void onStart() {
        super.onStart();

        mUser = realm.where(NewUser.class).findFirst();
        if (mUser==null) {
            nickTv.setText("请点击登录！");
        }else{
            Log.e("Daniel","----mUser---"+mUser.toString());
            if(mUser.getNickname().equals("")){
                nickTv.setText(mUser.getUserName());
            }else {
                nickTv.setText(mUser.getNickname());
            }
            if (mUser.getLogoImg().equals("")){
                Picasso.with(getActivity()).load(R.drawable.avatar).into(image);
            }else {

                Picasso.with(getActivity()).load(mUser.getLogoImg()).into(image);
            }
        }
    }

    @OnClick({R.id.apply_linear, R.id.memoryCode, R.id.myApointment_linear, R.id.aboutOur_tv, R.id.myCollect_linearLayout,R.id.nick_tv,R.id.image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_linear:
                if (initLogin(LoanApplyForActivity.class)) {
                    startActivity(new Intent(getActivity(), LoanApplyForActivity.class));
                }
                break;
            case R.id.memoryCode:
                if (initLogin(MyLoanActivity.class)) {
                    startActivity(new Intent(getActivity(), MyLoanActivity.class));
                }
                break;
            case R.id.myApointment_linear:
                if (initLogin(MyAppointmentActivity.class)) {
                    startActivity(new Intent(getActivity(), MyAppointmentActivity.class));
                }
                break;
            case R.id.aboutOur_tv:
                //                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //                startActivityForResult(intent, 0);
                break;
            case R.id.myCollect_linearLayout:
                    startActivity(new Intent(getActivity(), MyConllectionActivity.class));

                break;
            case R.id.nick_tv:
                if (mUser==null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {

                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                break;
            case R.id.image:
                if (mUser==null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {

                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
}
