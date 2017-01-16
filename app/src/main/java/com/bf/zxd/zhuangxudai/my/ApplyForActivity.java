package com.bf.zxd.zhuangxudai.my;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bf.zxd.zhuangxudai.Interfaces.DialogFragmentDismissLinsener;
import com.bf.zxd.zhuangxudai.R;
import com.bf.zxd.zhuangxudai.dialog.CommitDialogFragment;
import com.bf.zxd.zhuangxudai.network.NetWork;
import com.bf.zxd.zhuangxudai.pojo.ResuleInfo;
import com.bf.zxd.zhuangxudai.pojo.applyinfo;
import com.bf.zxd.zhuangxudai.util.UrlEncoded;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/1/6.
 */

public class ApplyForActivity extends AppCompatActivity {


    @BindView(R.id.base_toolBar)
    Toolbar toolbar;

    @BindView(R.id.store_user_name_edi)
    EditText storeUserNameEdi;
    @BindView(R.id.store_nphone_edi)
    EditText storeNphoneEdi;
    @BindView(R.id.apply_for_btn)
    Button applyForBtn;

    CompositeSubscription mcompositeSubscription;
    @BindView(R.id.store_name_edi)
    AppCompatEditText storeNameEdi;
    @BindView(R.id.store_address_edi)
    AppCompatEditText storeAddressEdi;
    private applyinfo mApplyinfo;

    private void setToolbar(String toolstr) {
        toolbar.setTitle(toolstr);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(toolstr);
        toolbar.setNavigationIcon(R.drawable.barcode__back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void initEdi() {
        storeNameEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeAddressEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeUserNameEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
        storeNphoneEdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ediLinsener();
            }
        });
    }

    public void ediLinsener() {
        if (!storeNameEdi.getText().toString().equals("") &&
                !storeAddressEdi.getText().toString().equals("") &&
                !storeUserNameEdi.getText().toString().equals("") &&
                !storeNphoneEdi.getText().toString().equals("")) {
            applyForBtn.setEnabled(true);

        } else {
            applyForBtn.setEnabled(false);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for);
        ButterKnife.bind(this);
        setToolbar("入驻申请");
        mcompositeSubscription = new CompositeSubscription();
        initEdi();
    }

    /**
     * 发送POST请求
     *
     * @return
     */
    protected String submitPostRequest(String urlAddress, Map<String, String> params) {
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            //connection.setRequestProperty("UA-CPU", "x86");
            //connection.setRequestProperty("Accept-Encoding", "gzip");
            // 很重要
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            // 发送POST请求必须设置这两项
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (params != null) {
                // 请求参数
                int paramSize = params.size();
                int index = 1;
                StringBuilder paramsBuilder = new StringBuilder();
                for (String key : params.keySet()) {
                    paramsBuilder.append(key).append("=").append(params.get(key));
                    if (index < paramSize) {
                        paramsBuilder.append("&");
                        index++;
                    }
                }

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(paramsBuilder.toString());
                writer.flush();
                writer.close();
            }

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                result = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String unit_name;
    private String unit_addr;
    private String full_name;
    private String phone;

    @OnClick(R.id.apply_for_btn)
    public void onClick() {
        //提交申请
        unit_name = UrlEncoded.toURLEncoded(storeNameEdi.getText().toString()) ;
        unit_addr =UrlEncoded.toURLEncoded(storeAddressEdi.getText().toString()) ;
        full_name =UrlEncoded.toURLEncoded(storeUserNameEdi.getText().toString()) ;
        phone =UrlEncoded.toURLEncoded(storeNphoneEdi.getText().toString()) ;
        Log.e("Daniel", "---unit_name---" + unit_name);
        Log.e("Daniel", "---unit_addr---" + unit_addr);
        Log.e("Daniel", "---full_name---" + full_name);
        Log.e("Daniel", "---phone---" + phone);

//        Map<String, String> params = new HashMap<>();
//        params.put("unit_name", unit_name);
//        params.put("unit_addr", unit_addr);
//        params.put("full_name", full_name);
//        params.put("phone", phone);
//        submitPostRequest("http://211.149.235.17:8080/zxd/app/saveRzsq", params);
        saveRzsq();

    }

    public void saveRzsq() {
//        String str_unit_name=null;
//        String str_unit_addr=null;
//        String str_full_name=null;
//        String str_phone= null;
//        try {
//            str_phone = URLEncoder.encode(phone, "UTF-8");
//             str_unit_name=URLEncoder.encode(unit_name, "UTF-8");
//             str_unit_addr=URLEncoder.encode(unit_addr, "UTF-8");
//             str_full_name=URLEncoder.encode(full_name, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Subscription Subscription_saveRzsq = NetWork.getZxService().saveRzsq(unit_name,
                unit_addr, full_name, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResuleInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResuleInfo resuleInfo) {
                        if (resuleInfo.getCode() == 10001) {
                            showDialog();
                        } else {
                            Toast.makeText(getApplicationContext(), "信息提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mcompositeSubscription.add(Subscription_saveRzsq);
    }

    CommitDialogFragment dialogFragment;

    public void showDialog() {
        if (dialogFragment == null) {
            dialogFragment = new CommitDialogFragment();
            dialogFragment.setDialogFragmentDismissLinsener(new DialogFragmentDismissLinsener() {
                @Override
                public void dialogDismiss() {
                    onBackPressed();
                }
            });
        }
        dialogFragment.show(getFragmentManager(), "123");
    }

}
