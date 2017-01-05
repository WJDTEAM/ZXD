package com.bf.zxd.zhuangxudai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Daniel on 2017/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setTheme(EatApplication.getInstance().getCurrentTheme());
        //初始化
        init();

        //放入堆栈管理
//        MyActivityManager.getInstance().pushActivity(this);
    }

    /**
     *  初始化
     */
    private void init(){
        initDate();
        initView();
        initEvent();
    }

    /**
     * 初始化数据
     */
    public abstract void initDate();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化事件
     */
    public abstract void initEvent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //删除堆栈中的实例
//        MyActivityManager.getInstance().popActivity(this);
    }

}
