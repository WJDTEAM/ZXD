package com.bf.zxd.zhuangxudai.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.my.ApplyForActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by johe on 2017/1/5.
 */

public class UserFragment extends Fragment {
    Realm realm;
    @BindView(R.id.apply_linear)
    LinearLayout applyLinear;

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

    @OnClick(R.id.apply_linear)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.apply_linear:
                startActivity(new Intent(getActivity(),ApplyForActivity.class));
                break;
        }
    }
}
