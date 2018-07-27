package com.example.lenovo.androidtext1_1.loginandre;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidtext1_1.MainActivity;
import com.example.lenovo.androidtext1_1.R;
import com.example.lenovo.androidtext1_1.connectNet.ConnectWeb;
import com.example.lenovo.androidtext1_1.mainUI.HomeUIActivity;
import com.example.lenovo.androidtext1_1.tools.CacheUtils;
import com.example.lenovo.androidtext1_1.userData.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainUIActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mForget;
    private EditText mName,mPass;
    private Button mRegistered,mLogin;
    private List<User> mList;
    private boolean isLogin=false;
    private String TAG="hhhhhhhhhhhhh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        mName=findViewById(R.id.et_name);
        mPass=findViewById(R.id.et_pass);
        mForget=findViewById(R.id.tv_forget);
        mLogin=findViewById(R.id.btn_login);
        mRegistered=findViewById(R.id.btn_registered);
        listener();
        initData();
    }

    private void listener() {
        mRegistered.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    private void initData() {
        //文字加下划线
        mForget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_registered:
                Intent intent=new Intent(this,RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                mList=new ArrayList<>();
                Log.i(TAG, "onClick: 准备登录");
                loginOK();
            break;
        }
    }
    //登录判断
    private void loginOK() {
        isLogin=false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s=ConnectWeb.getData();
                Gson gson=new Gson();
                mList=gson.fromJson(s,new TypeToken<List<User>>(){}.getType());
                //TODO 这里应该没什么用
                if(s==null) {
                    CacheUtils.list=mList;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<mList.size();i++) {
                            User user = mList.get(i);
                            if (mName.getText().toString().equals(user.getName()) && mPass.getText().toString().equals(user.getPass())) {
                                isLogin=true;
                                Log.i(TAG, "onClick: 登陆成功！");
                                CacheUtils.setBoolean(new MainActivity(),MainActivity.IS_FIRST,true);
                                Toast.makeText(MainUIActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainUIActivity.this, HomeUIActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            if(i==mList.size())
                                Toast.makeText(MainUIActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
        //Toast.makeText(MainUIActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
    }

}
