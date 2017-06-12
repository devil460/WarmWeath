package com.ryan.warmweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Think on 2017/6/12.
 */

public class HttpUtil {
/**
 * 创建一个发送网络请求的工具类
 * * */
    public static void sendOkhttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
