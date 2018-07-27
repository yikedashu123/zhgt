package com.example.lenovo.androidtext1_1.connectNet;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectWeb {
    public static String getData()
    {
        try {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()//10.0.2.2
                    .url("http://192.168.3.144:8088/hahaha.json")
                    .build();
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful())
            {
                String str=response.body().string();
                Log.i("PPPPPPPPPPPPPPPPP", "onClick: 登录成功！"+str);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
