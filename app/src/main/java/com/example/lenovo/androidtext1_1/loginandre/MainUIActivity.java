package com.example.lenovo.androidtext1_1.loginandre;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.androidtext1_1.R;

public class MainUIActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mForget;
    private EditText mName;
    private Button mRegistered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        mName=findViewById(R.id.et_name);
        mForget=findViewById(R.id.tv_forget);
        mRegistered=findViewById(R.id.btn_registered);
        listener();
        initData();
    }

    private void listener() {
        mRegistered.setOnClickListener(this);
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
        }
    }
}
