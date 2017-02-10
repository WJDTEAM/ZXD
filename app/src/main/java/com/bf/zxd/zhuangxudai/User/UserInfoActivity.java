package com.bf.zxd.zhuangxudai.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bf.zxd.zhuangxudai.Login.LoginActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.pojo.User;
import com.bf.zxd.zhuangxudai.util.SettingsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.logout)
    Button logout;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setToolBar();
        realm = Realm.getDefaultInstance();
        User userInfo = realm.where(User.class).findFirst();
        userName.setText(userInfo.getUser_name());
    }

    private void setToolBar() {
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.user_name, R.id.logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.user_name:


                break;
            case R.id.logout:
                logout();

                break;
        }
    }

    private void logout() {
        //退出账户是删除此用户
        realm.beginTransaction();
        User userInfo = realm.where(User.class).findFirst();
        if (userInfo != null) {
            userInfo.deleteFromRealm();
        }
        realm.commitTransaction();
        //取消“记住密码，自动登录”
        SettingsUtils.setPrefAutoLogin(getApplicationContext(),false);
        SettingsUtils.setPrefRememberPassword(getApplicationContext(),false);

        startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));//跳转到登录页面
    }
}
