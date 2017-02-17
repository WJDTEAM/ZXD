package com.bf.zxd.zhuangxudai.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

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

public class UserInfoActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {

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
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private static final String TAG = TakePhotoActivity.class.getName();
    CropOptions cropOptions;
    File _file;
    String _path;

    public static int REQUEST_CODE = 1;
    public static int RESULT_PHOTO = 2;
    public static int ALL_PHOTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).addActivity(this);
        setContentView(R.layout.activity_user_info);
        mUnbinder= ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        setToolBar();
        realm = Realm.getDefaultInstance();

        cropOptions=new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        //创建文件路径,头像保存的路径
        _file = FileUitlity.getInstance(getApplicationContext()).makeDir("head_image2");
        //定义图片路径和名称
        _path = _file.getParent() + File.separatorChar + System.currentTimeMillis() + ".jpg";


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
            Picasso.with(this).load(R.drawable.avatar).into(circleImageView);
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

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }



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
                takePhoto.onPickFromGalleryWithCrop(Uri.fromFile(new File(_path)),cropOptions);
//                allPhoto();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
//
                takePhoto.onPickFromCaptureWithCrop(Uri.fromFile(new File(_path)),cropOptions);
            }
        });
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

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
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

    @Override
    public void takeSuccess(TResult result) {
        Log.e(TAG,"takeSuccess：" + result.getImage());
        Log.e(TAG,"takeSuccess：" + result.getImage().getCompressPath());

        Log.e(TAG,"takeSuccess：" + result.getImage().getOriginalPath());

        Bitmap bitmap = convertToBitmap(result.getImage().getOriginalPath(),200,200);


        uploadAvatars(bitmap);


    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.e(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.e(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
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
