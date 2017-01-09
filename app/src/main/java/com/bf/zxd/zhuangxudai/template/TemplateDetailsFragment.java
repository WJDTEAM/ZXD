package com.bf.zxd.zhuangxudai.template;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.zxd.zhuangxudai.R;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by johe on 2017/1/9.
 */

public class TemplateDetailsFragment extends Fragment{
    Realm realm;
    public static TemplateDetailsFragment newInstance() {
        TemplateDetailsFragment fragment = new TemplateDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_details, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();

        return view;
    }
}
