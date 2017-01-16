package com.bf.zxd.zhuangxudai.util;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by Daniel on 2017/1/16.
 */

public class UrlEncoded {
    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.d("toURLEncoded error:", paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            Log.e("toURLEncoded error:", paramString);
        }

        return "";
    }
}
