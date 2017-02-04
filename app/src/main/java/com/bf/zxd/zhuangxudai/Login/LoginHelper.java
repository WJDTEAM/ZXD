package com.bf.zxd.zhuangxudai.Login;

import android.content.Context;
import android.content.Intent;

import com.bf.zxd.zhuangxudai.pojo.User;

import io.realm.Realm;

/**
 * Created by johe on 2017/2/4.
 */

public class LoginHelper {

    private static class helper{
        static LoginHelper loginHelper=new LoginHelper();
    }

    Realm realm;

    public static LoginHelper getInstence(){

        return helper.loginHelper;
    }

    private LoginHelper(){

    }

    public boolean startActivityWithLogin(Context mContext,Class activity){
        if(realm==null){
            realm=Realm.getDefaultInstance();
        }
        if(realm.where(User.class).findFirst()==null){
            Intent intent=new Intent(mContext,LoginActivity.class);
            LoginActivity.activity=activity;
            mContext.startActivity(intent);
            return false;
        }else{
            return true;
        }
    }
    public void changeActivity(Class activity){
        if(mLinsener!=null){
            mLinsener.changeActivity(activity);
        }
    }

    public void setmLinsener(Linsener mLinsener) {
        this.mLinsener = mLinsener;
    }

    Linsener mLinsener;
    public interface Linsener {
        public void changeActivity(Class activity);
    }

}
