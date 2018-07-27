package com.example.lenovo.androidtext1_1.mainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lenovo.androidtext1_1.R;
import com.example.lenovo.androidtext1_1.Weather.WeatherActivity;
import com.example.lenovo.androidtext1_1.dadi.CallTaxiActivity;
import com.example.lenovo.androidtext1_1.lifeUI.LifeActivity;
import com.example.lenovo.androidtext1_1.loginandre.MainUIActivity;
import com.example.lenovo.androidtext1_1.position.MyPostionActivity;
import com.example.lenovo.androidtext1_1.tools.Gradient;
import com.example.lenovo.androidtext1_1.tools.ShakeListener;
import com.example.lenovo.androidtext1_1.trafficUI.TrafficActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeUIActivity extends AppCompatActivity implements View.OnClickListener {
    private Gradient mImage;
    private List<ImageView> mList;
    private ShakeListener mListener;
    private Button mTraffic,mLife,mWeather,mTaxi,mMine;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);
        mImage=findViewById(R.id.g_gra);
        mTraffic=findViewById(R.id.btn_traffic);
        mLife=findViewById(R.id.btn_life);
        mWeather=findViewById(R.id.btn_weather);
        mTaxi=findViewById(R.id.btn_callTaxi);
        mMine=findViewById(R.id.btn_mineHome);
        Log.i("PPPPPPPPP", "listener: 进入");
        listener();
        huanye();
        initShake();

    }

    private void listener() {
        mTraffic.setOnClickListener(this);
        mLife.setOnClickListener(this);
        mWeather.setOnClickListener(this);
        mTaxi.setOnClickListener(this);
        mMine.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_traffic:
                intent = new Intent(HomeUIActivity.this, TrafficActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_life:
                 intent=new Intent(HomeUIActivity.this, LifeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_weather:
                intent=new Intent(HomeUIActivity.this, WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_callTaxi:
                intent=new Intent(HomeUIActivity.this, CallTaxiActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mineHome:
                intent=new Intent(HomeUIActivity.this, MyPostionActivity.class);
                startActivity(intent);
                break;
        }

    }
    private void huanye() {
        mList=new ArrayList<>();
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.main_viewpager_one);
        mList.add(imageView);
        ImageView view=new ImageView(this);
        view.setImageResource(R.drawable.main_viewpager_two);
        mList.add(view);
        mImage.setImageViews(mList);
        mImage.setTime(2500);
    }

    private void initShake() {
        mListener=new ShakeListener(this);
        mListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                Intent intent=new Intent(HomeUIActivity.this, MainUIActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mListener!=null)
            mListener.stop();
    }


}
