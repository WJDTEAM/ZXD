package com.bf.zxd.zhuangxudai.application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.NewUser;
import com.bf.zxd.zhuangxudai.pojo.ResultCodeWithUser;
import com.bf.zxd.zhuangxudai.util.SettingsUtils;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by johe on 2017/1/5.
 */

public class BaseApplication extends Application {
    private static List<Activity> mList ;

    //获取集合size
    public  int getListSize(){
        return mList.size();
    }

    /**
     * add Activity
     * @param activity
     */
    public static void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 遍历退出activity
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * OnLowMemory是Android提供的API，在系统内存不足，
     * 所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，系统会调用OnLowMemory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        realm.close();
        //垃圾回收
        System.gc();
    }

    //Realm初始化
    public static String username;
    Realm realm;
    @Override
    public void onCreate() {
        super.onCreate();
        //        LeakCanary.install(this);
        mList = new ArrayList<>();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).schemaVersion(2).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        realm=Realm.getDefaultInstance();
        //initLogin();
    }

    public void initLogin(){
        if (SettingsUtils.isRememberPassword(getApplicationContext())) {
            NewUser userInfo = realm.where(NewUser.class).findFirst();
            if (userInfo != null) {
                username =userInfo.getUserName();
                if (SettingsUtils.isAutoLogin(getApplicationContext())) {
                    doLogin(userInfo);
                }
            }
        }
    }
    private void doLogin(NewUser userInfo) {
        NetWork.getNewZXD1_4Service().login(userInfo.getUserName(), userInfo.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultCodeWithUser>() {
                    @Override
                    public void onCompleted() {

                    }
                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }
                    @DebugLog
                    @Override
                    public void onNext(ResultCodeWithUser loginResult) {
                        Log.e("Daniel",loginResult.toString());
                        NewUser userInfo = loginResult.getUser();
                        if (loginResult.getCode() != 10001) {

                        } else {
                            NewUser User = realm.where(NewUser.class).findFirst();
                            if (User != null) {
                                //删除本地之前保存的用户信息
                                realm.beginTransaction();
                                User.deleteFromRealm();
                                realm.commitTransaction();
                            }
                            realm.beginTransaction();
                            userInfo.setPassword(User.getPassword());
                            userInfo.setUserName(User.getUserName());
                            realm.copyToRealmOrUpdate(userInfo);
                            realm.commitTransaction();
                        }
                    }
                });
    }
    /**
     * 使用默认字体
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
