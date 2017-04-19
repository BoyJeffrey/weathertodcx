package com.weathertodcx.android.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 和服务器交互工具
 * Created by Administrator on 2017/4/19.
 */

public class HttpUtil {

    /**
     * 发送http请求
     *
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 发送https请求
     *
     * @param address
     * @param callback
     */
    public static void sendOkHttpsRequest(String address, Callback callback) {
        //You can build your own connection spec with a custom set of TLS versions and cipher suites. For example, this configuration is limited to three highly-regarded cipher suites. Its drawback is that it requires Android 5.0+ and a similarly current webserver.

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


}
