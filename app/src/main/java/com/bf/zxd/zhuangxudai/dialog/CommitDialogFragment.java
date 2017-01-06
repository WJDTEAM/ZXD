package com.bf.zxd.zhuangxudai.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by johe on 2017/1/6.
 */

public class CommitDialogFragment extends DialogFragment {

    @BindView(R.id.commit_btn)
    Button commitBtn;

    DialogFragmentDismissLinsener dialogFragmentDismissLinsener;

    public DialogFragmentDismissLinsener getDialogFragmentDismissLinsener() {
        return dialogFragmentDismissLinsener;
    }

    public void setDialogFragmentDismissLinsener(DialogFragmentDismissLinsener dialogFragmentDismissLinsener) {
        this.dialogFragmentDismissLinsener = dialogFragmentDismissLinsener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commit_dialog, container, false);
        ButterKnife.bind(this, v);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @OnClick(R.id.commit_btn)
    public void onClick() {
        dismiss();
        if(dialogFragmentDismissLinsener!=null){
            dialogFragmentDismissLinsener.dialogDismiss();
        }
    }
}
