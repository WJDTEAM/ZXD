package com.bf.zxd.zhuangxudai.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bf.zxd.zhuangxudai.network.exception.CustomizeException;
import com.bf.zxd.zhuangxudai.update.DownloadProgressInterceptor;
import com.bf.zxd.zhuangxudai.update.DownloadProgressListener;
import com.bf.zxd.zhuangxudai.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by JokAr on 16/7/5.
 * 处理下载任务
 */
public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;
    public Retrofit retrofit;

    //下载
    public DownloadAPI(String url, DownloadProgressListener listener) {
        //进度拦截器
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public void downloadAPK(@NonNull String url, final File file, Subscriber subscriber) {
        Log.d(TAG, "downloadAPK: " + url);

        retrofit.create(com.bf.zxd.zhuangxudai.network.api.UpdateService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new CustomizeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
