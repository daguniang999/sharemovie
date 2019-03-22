package com.example.chenx.sharebook.util;

import android.support.v7.widget.RecyclerView;

import javax.security.auth.callback.Callback;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);


    }


    public static void sendMultipart(String address, RequestBody requestBody,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);


    }

}
