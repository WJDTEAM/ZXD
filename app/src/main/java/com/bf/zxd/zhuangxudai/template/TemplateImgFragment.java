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

public class TemplateImgFragment extends Fragment{
    Realm realm;
    public static TemplateImgFragment newInstance() {
        TemplateImgFragment fragment = new TemplateImgFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_img, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();

        return view;
    }
}
