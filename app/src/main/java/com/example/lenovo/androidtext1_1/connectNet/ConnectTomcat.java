package com.example.lenovo.androidtext1_1.connectNet;

import android.util.Log;

import com.example.lenovo.androidtext1_1.userData.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectTomcat {
    public static boolean getData(User user)
    {
        try {
            String url = "http://10.0.2.2:8088/AndroidTest/UserLogin";
            OkHttpClient client=new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json;charset=utf-8");
            RequestBody body = RequestBody.create(JSON,
                    user.getName()+" "+user.getPass());//+" "+news.getNumber()
            Request request = new Request.Builder().url(url).post(body).build();
            Call call =client.newCall(request);
            Response response = call.execute();
            String s=response.body().string();
            String str[]=s.split("/");
            Log.i("hhhhhhhhhhhhh", "getData: "+s);
            if(str[1].equals("登录成功"))
            {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
