package com.hgc.coolweather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Description 服务器交互工具类
 * @Author hanguangchuan
 * Date 2022/6/4 19:32
 */
public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String TOKEN = "token";

    /**
     * get请求
     * @param address url
     * @param callback 回调接口
     */
    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * get请求，携带请求头
     * @param address url
     * @param header 请求头
     * @param callback 回调接口
     */
    public static void sendHeadOkHttpRequest (String address, String header, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(address)
                .addHeader(TOKEN, header)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post请求
     * @param address url
     * @param json 请求体
     * @param callback 回调接口
     */
    public static void sendPostOkHttpRequest(String address, String json, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.Companion.create(json, JSON);
        Request request = new Request
                .Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post请求，携带请求头
     * @param address url
     * @param json 请求体
     * @param header 请求头参数
     * @param callback 回调接口
     */
    public static void sendPostHeadOkhttpRequest (String address, String json, String header, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.Companion.create(json, JSON);
        Request request = new Request
                .Builder()
                .url(address)
                .post(body)
                .addHeader(TOKEN, header)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
