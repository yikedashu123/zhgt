package com.example.lenovo.androidtext1_1.loginandre;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.androidtext1_1.R;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    Button mPhone,mEmail;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        mEmail=findViewById(R.id.btn_email);
        mPhone=findViewById(R.id.btn_phone);
        initData();
    }
//初始化数据
    private void initData() {
        mPhone.setTextColor(Color.GREEN);
        mEmail.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_layout,new PhoneFragment(),"ss");
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_phone:
                mPhone.setTextColor(Color.GREEN);
                mEmail.setTextColor(Color.BLACK);
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_layout,new PhoneFragment(),"ss");
                transaction.commit();
                break;
            case R.id.btn_email:
                mEmail.setTextColor(Color.GREEN);
                mPhone.setTextColor(Color.BLACK);
                manager=getSupportFragmentManager();
                transaction= manager.beginTransaction();
                transaction.replace(R.id.fl_layout,new EmailFragment(),"ss");
                transaction.commit();
                break;
        }
    }
}
