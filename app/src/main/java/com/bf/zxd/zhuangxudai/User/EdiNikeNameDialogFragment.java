package com.bf.zxd.zhuangxudai.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/2/20.
 */

public class EdiNikeNameDialogFragment extends DialogFragment {

    String nickName = "";
    @BindView(R.id.fuifu_msg)
    EditText fuifuMsg;
    @BindView(R.id.positiveButton)
    Button positiveButton;
    @BindView(R.id.negativeButton)
    Button negativeButton;

    Realm realm;

    public  interface DimssLinsener{
        public void fragmentDimss();

    }

    DimssLinsener dimssLinsener;

    public void setDimssLinsener( DimssLinsener dimssLinsene){
        dimssLinsener=dimssLinsene;
    }

    CompositeSubscription compositeSubscription;
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.edi_nikename_dialog, container);
        realm=Realm.getDefaultInstance();
        compositeSubscription=new CompositeSubscription();
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.positiveButton, R.id.negativeButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.positiveButton:
                changeNickName();
                break;
            case R.id.negativeButton:
                this.dismiss();
                break;
        }
    }
    public void changeNickName() {
        nickName = fuifuMsg.getText().toString();
        Subscription editNickname = NetWork.getNewZXD1_4Service().editNickname(realm.where(NewUser.class).findFirst().getUserId(), nickName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @DebugLog
                    @Override
                    public void onNext(ResultCode resultCode) {
                        if (resultCode.getCode() == 10001) {
                            saveNickName(nickName);
                            Toast.makeText(getActivity(), "修改昵称成功", Toast.LENGTH_SHORT).show();
                            dimssLinsener.fragmentDimss();
                            EdiNikeNameDialogFragment.this.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "" + resultCode.getMsg(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        compositeSubscription.add(editNickname);


    }
    /**
     * 保存新昵称
     *
     * @param newNickName
     */
    private void saveNickName(String newNickName) {
        NewUser mUser=realm.where(NewUser.class).findFirst();
        realm.beginTransaction();
        mUser.setNickname(newNickName);
        realm.copyToRealmOrUpdate(mUser);
        realm.commitTransaction();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeSubscription.unsubscribe();
    }
}
