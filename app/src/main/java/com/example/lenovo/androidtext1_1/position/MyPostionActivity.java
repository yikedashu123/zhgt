package com.example.lenovo.androidtext1_1.position;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.lenovo.androidtext1_1.tools.MusicTools;

import java.util.Map;

public class MyPostionActivity extends AppCompatActivity implements View.OnClickListener {
    public LocationClient mClient;
    private MapView mView;
    private BaiduMap baiduMap;
    private boolean isFirst=true;
    private Button mAims,mMine;
    private MyPostionActivity.MyLocationListener mListener;
    private BDLocation mBdLocation;
    private SoundPool sndPool;
    private Map<Integer, Integer> loadSound;
    private int playId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient=new LocationClient(getApplicationContext());
        mListener=new MyPostionActivity.MyLocationListener();
        mClient.registerLocationListener(mListener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_my_postion);
        start();
        mView=findViewById(R.id.mv_my);
        mAims=findViewById(R.id.btn_aims);
        mMine=findViewById(R.id.btn_mine);
        mAims.setOnClickListener(this);
        mMine.setOnClickListener(this);
        baiduMap=mView.getMap();
        baiduMap.setMyLocationEnabled(true);
    }
    private void start() {
        sndPool=new SoundPool(2, AudioManager.STREAM_MUSIC, 5);
        loadSound= MusicTools.loadSound(sndPool,this);
        sndPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                playId=sndPool.play(loadSound.get(3), 5, 5, 0, -1, 1);
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        sndPool.unload(playId);
        sndPool.release();
        sndPool=null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_mine:
                requestClient();
                mClient.registerLocationListener(mListener);
                break;
            case R.id.btn_aims:
                mClient.stop();
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

            mBdLocation=bdLocation;
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||
                    bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                navigateTo(bdLocation);
                Log.i("PPPPPPPPPPPPPPPP", "initLocation: 定位自身");
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
//            isFirst=false;
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
