package com.example.lenovo.androidtext1_1.dadi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lenovo.androidtext1_1.R;
import com.example.lenovo.androidtext1_1.tools.CacheUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CallTaxiActivity extends AppCompatActivity implements View.OnClickListener {
    public LocationClient mClient;
    private MapView mView;
    private BaiduMap baiduMap;
    private boolean isFirst=true;
    private long start;
    private float money;
    private long sign;
    private int s;
    private TextView mMoney,mTime;
    private Timer timer;
    private Button mStart,mStop,mPay;
    private boolean isStart;
    private long l=0;
    private long m=0;
    public static final String KEY="callTaxi";
    public static final String KEY1="callStop";
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient=new LocationClient(getApplicationContext());
        mClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_call_taxi);
        mView=findViewById(R.id.mp_view);
        baiduMap=mView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mMoney=findViewById(R.id.tv_callMoney);
        mTime=findViewById(R.id.tv_callTime);
        mStart=findViewById(R.id.btn_start);
        mStop=findViewById(R.id.btn_stop);
        mPay=findViewById(R.id.btn_pay);
        if(CacheUtils.getBooleans(CallTaxiActivity.this,KEY,false))
        {
            long h=CacheUtils.getLong(CallTaxiActivity.this,KEY1,0);
            Date dt= new Date();
            Long time= dt.getTime();
            Log.i("PPPPPPPPPPPPPP开始", "onCreate: "+h);
            Log.i("PPPPPPPPPPPPPP重新打开", "onCreate: "+time);
            l=(time-h)/1000;//除以1000是为了转换成秒
            Log.i("PPPPPPPPPPPPPP", "onCreate: "+l);
            initTime();
        }
        requestClient();
        //
        listener();
    }

    private void listener() {
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mPay.setOnClickListener(this);
    }

    private void initTime() {
        start=l;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(start>10)
                {
                    money=(start-10)/2*3;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long hour1 = start / 3600;            //转换小时
                        sign=start%3600;
                        long minute1 = sign /60;
                        sign=sign%60;
                        long second1=sign;             //转换分钟
                        mTime.setText("  已花费时长  "+hour1+":"+minute1+":"+second1);
                        mMoney.setText("按时金额"+money+"  元");
                    }
                });
                start=start+1;
            }
        },1000,1000);
    }


    private void requestClient() {
        initLocation();
        mClient.start();
    }

    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
        option.setIsNeedAddress(true);
        mClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mClient.stop();
        mView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_start:
                if(timer!=null)
                    return;
                Date dt= new Date();
                Long time= dt.getTime();
                CacheUtils.setLong(CallTaxiActivity.this,KEY1,time);
                Log.i("PPPPPPPPPPPPPP 开始", "onCreate: "+time);
                CacheUtils.setBooleans(CallTaxiActivity.this,KEY,true);
                start=1;
                money=5;
                initTime();
                break;
            case R.id.btn_stop:
                if(timer!=null)
                {
                    timer.cancel();
                    timer=null;
                }
                CacheUtils.setBooleans(CallTaxiActivity.this,KEY,false);
                break;
            case R.id.btn_pay:
                mTime.setText("  已花费时长  00:00:00");
                mMoney.setText("按时金额 0 元");
                break;
        }
    }

    class MyLocationListener implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||
                    bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                navigateTo(bdLocation);
            }
            MyLocationData.Builder builder=new MyLocationData.Builder();
            builder.latitude(bdLocation.getLatitude());
            builder.longitude(bdLocation.getLongitude());
            MyLocationData myLocationData=builder.build();
            baiduMap.setMyLocationData(myLocationData);
        }
    }

    private void navigateTo(BDLocation bdLocation) {
        if(isFirst)
        {
            LatLng ll=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirst=false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

}
