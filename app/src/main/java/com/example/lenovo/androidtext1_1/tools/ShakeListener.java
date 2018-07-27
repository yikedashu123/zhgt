package com.example.lenovo.androidtext1_1.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
*
* @作者 ：ykds
*
* @创建日期：2018/7/26
*
**/

public class ShakeListener implements SensorEventListener {
    private static final int SPEED_SHRESHOLD=2000;
    public static final int UPTATE=70;
    private SensorManager sensorManager;
    private Sensor sensor;
    private OnShakeListener onShakeListener;
    private Context context;
    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;
    public ShakeListener(Context c)
    {
        this.context=c;
        start();
    }
    public void start()
    {
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null)
        {
            sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if(sensor!=null)
        {
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
        }else{
            Toast.makeText(context,"您的手机不支持该功能",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long currentUpdateTime=System.currentTimeMillis();
        long timeInterval=currentUpdateTime-lastUpdateTime;
        if(timeInterval<UPTATE)
            return;
        lastUpdateTime=currentUpdateTime;
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];

        float deltaX=x-lastX;
        float deltaY=y-lastY;
        float deltaZ=z-lastZ;

        lastX=x;
        lastY=y;
        lastZ=z;

        double speed=Math.sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ)/timeInterval*10000;
        if(speed>=SPEED_SHRESHOLD)
        {
            onShakeListener.onShake();
        }
    }
    public interface OnShakeListener{

        public void onShake();
    }

    public void stop()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void setOnShakeListener(OnShakeListener listener)
    {
        onShakeListener=listener;
    }
}
