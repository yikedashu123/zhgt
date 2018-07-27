package com.example.lenovo.androidtext1_1.connectNet;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectWeather {
    public static String getData(String url)
    {
        try {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()//10.0.2.2
                    .url(url)
                    .build();
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful())
            {
                String str=response.body().string();
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
