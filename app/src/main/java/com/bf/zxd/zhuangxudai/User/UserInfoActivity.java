package com.bf.zxd.zhuangxudai.User;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.Login.LoginActivity;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.application.BaseApplication;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithImg;
import com.bf.zxd.zhuangxudai.util.FileUitlity;
import com.bf.zxd.zhuangxudai.util.SettingsUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.icon_img)
    LinearLayout iconImg;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    private Realm realm;
    private NewUser userInfo;
    private Unbinder mUnbinder;
    private CompositeSubscription compositeSubscription;

    public static int REQUEST_CODE = 1;
    public static int RESULT_PHOTO = 2;
    public static int ALL_PHOTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).addActivity(this);
        setContentView(R.layout.activity_user_info);
        mUnbinder= ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        setToolBar();
        realm = Realm.getDefaultInstance();


    }
    @DebugLog
    @Override
    protected void onStart() {
        super.onStart();
        userInfo = realm.where(NewUser.class).findFirst();
        Log.e("Daniel","userInfo:::"+userInfo.toString());
        if(userInfo.getNickname().equals("")){
            userName.setText(userInfo.getUserName());
        }else {
            userName.setText(userInfo.getNickname());
        }
        if (userInfo.getLogoImg().equals("")){
            Picasso.with(this).load(R.drawable.demo).into(circleImageView);
        }else {
            Picasso.with(this).load(userInfo.getLogoImg()).into(circleImageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        mUnbinder.unbind();

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

    @OnClick({R.id.user_name, R.id.logout, R.id.icon_img, R.id.nick_linearlayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.icon_img:
                ChangeIcon();
                break;
            case R.id.nick_linearlayout:
                Intent _intent = new Intent(UserInfoActivity.this,ChangeNickActivity.class);
                String _nick = userName.getText().toString();
                _intent.putExtra("nickName",_nick);
                startActivity(_intent);
//                changeNickName(_nick);
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

//    private void changeNickName(String nick) {
//        new MaterialDialog.Builder(getActivity())
//                .title(R.string.search)
//                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                    @Override
//                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        if (!TextUtils.isEmpty(input)) {
////                            doSearch(input.toString());
//                        }
//                    }
//                }).show();
//    }

    private String path;

    private void ChangeIcon() {
        //PopupWindow----START-----这里开始到下面标记的地方是实现点击头像弹出PopupWindow，实现用户从PopupWindow中选择更换头像的方式
        backgroundAlpha(0.3f);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.popu_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        popupWindow.setWidth(dm.widthPixels);
        popupWindow.setAnimationStyle(R.style.popuwindow);
        //显示位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new poponDismissListener());

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
                File file = FileUitlity.getInstance(getApplicationContext()).makeDir("head_image");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果返回码不为-1，则表示不成功
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == ALL_PHOTO) {
            //调用相册
            Cursor cursor = this.getContentResolver().query(data.getData(),
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
                Log.e("Daniel","---bitmap.toString()---"+bitmap.toString());
                uploadAvatars(bitmap);
//
            }
        }
    }


    private void uploadAvatars(Bitmap bitmap) {
        File dcimFile =new File(getApplicationContext().getExternalCacheDir()+"yyy.png");
        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(dcimFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("Daniel","---dcimFile.getAbsolutePath()---"+dcimFile.getAbsolutePath());
        Log.e("Daniel","---dcimFile---"+dcimFile);
        Log.e("Daniel","---userInfo.getUserId()---"+userInfo.getUserId());
        if (dcimFile.exists()){
            Log.e("Daniel","---文件存在---");

            MultipartBody.Builder builder = new MultipartBody.Builder();
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), dcimFile);
            builder.addFormDataPart("zichifile", dcimFile.getName(), photoRequestBody);
            builder.setType(MultipartBody.FORM);
            MultipartBody mb=builder.build();


//            List<MultipartBody.Part> zichifile=new ArrayList<>();
//            for(int i=0;i<mb.size();i++){
//                zichifile.add(mb.part(i));
//            }

           Subscription uploadAvatars= NetWork.getNewZXD1_4Service().uploadAvatars(userInfo.getUserId(),mb.part(0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResultCodeWithImg>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Daniel","---onError---"+e.toString());
                            Toast.makeText(UserInfoActivity.this, "头像更新失败！", Toast.LENGTH_SHORT).show();

                        }
                        @DebugLog
                        @Override
                        public void onNext(ResultCodeWithImg resultCodeWithImg) {
                            Log.e("Daniel","---onNext---");
                            if (resultCodeWithImg.getCode()==10001){
                                saveLogoImg(resultCodeWithImg.getLogoImg());
                                Picasso.with(UserInfoActivity.this).load(resultCodeWithImg.getLogoImg()).into(circleImageView);
                                Toast.makeText(UserInfoActivity.this, ""+resultCodeWithImg.getMsg(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UserInfoActivity.this, ""+resultCodeWithImg.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
            compositeSubscription.add(uploadAvatars);
        }else {
            Log.e("Daniel","---文件不存在---");
        }



    }

    /**
     * 保存头像图片
     */
    private void saveLogoImg(String imgStr) {
        realm.beginTransaction();
        userInfo.setLogoImg(imgStr);
        realm.copyToRealmOrUpdate(userInfo);
        realm.commitTransaction();
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
    class poponDismissListener implements PopupWindow.OnDismissListener {

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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void logout() {
        //退出账户是删除此用户
        realm.beginTransaction();
        NewUser userInfo = realm.where(NewUser.class).findFirst();
        if (userInfo != null) {
            userInfo.deleteFromRealm();
        }
        realm.commitTransaction();
        //取消“记住密码，自动登录”
        SettingsUtils.setPrefAutoLogin(getApplicationContext(), false);
        SettingsUtils.setPrefRememberPassword(getApplicationContext(), false);

        startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));//跳转到登录页面


    }
}
