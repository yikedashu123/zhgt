package com.example.lenovo.androidtext1_1.position;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lenovo.androidtext1_1.R;

public class MyPostionActivity extends AppCompatActivity implements View.OnClickListener {
    public LocationClient mClient;
    private MapView mView;
    private BaiduMap baiduMap;
    private boolean isFirst=true;
    private Button mAims,mMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient=new LocationClient(getApplicationContext());
        mClient.registerLocationListener(new MyPostionActivity.MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_my_postion);
        mView=findViewById(R.id.mv_my);
        mAims=findViewById(R.id.btn_aims);
        mMine=findViewById(R.id.btn_mine);
        mAims.setOnClickListener(this);
        mMine.setOnClickListener(this);
        baiduMap=mView.getMap();
        baiduMap.setMyLocationEnabled(true);
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
            case R.id.btn_mine:
                requestClient();break;
            case R.id.btn_aims:
                getAims();
                break;
        }
    }

    private void getAims() {
        LatLng p = new LatLng(37.52f,121.39f);

        MapStatus mMapStatus = new MapStatus.Builder().target(p).zoom(11)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        baiduMap = mView.getMap();
        baiduMap.setMapStatus(mMapStatusUpdate);

    }


    public class MyLocationListener implements BDLocationListener
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
