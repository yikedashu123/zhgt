package com.example.lenovo.androidtext1_1.Weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.androidtext1_1.R;
import com.example.lenovo.androidtext1_1.connectNet.ConnectWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private TextView mCity,mContent,mTemper;
    private ImageView mType;
    private Weather mWeather;
    private Weather weatherInfo;
    private RecyclerView mWeatherView;
    private List<Forecast> mList;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);//						Bitmap bmp = getURLimage(url);
        mCity=findViewById(R.id.tv_city);
        mContent=findViewById(R.id.tv_time);
        mType =findViewById(R.id.iv_type);
        mTemper=findViewById(R.id.tv_temper);
        mWeatherView=findViewById(R.id.rv_weather);
        mLayout=findViewById(R.id.LL_weatherLayout);
        mList=new ArrayList<>();
        initData();

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json= ConnectWeather.getData("http://wthrcdn.etouch.cn/weather_mini?citykey=101120501");
                Gson gson=new Gson();
                weatherInfo = gson.fromJson(json,new TypeToken<Weather>(){}.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mList=weatherInfo.data.forecast;
                        mCity.setText(weatherInfo.data.city);
                        mContent.setText(weatherInfo.data.forecast.get(0).date+
                                "\n"+weatherInfo.data.forecast.get(0).high+"\n"+weatherInfo.data.forecast.get(0).low);
                        initType(mType,0);
                        mTemper.setText(weatherInfo.data.wendu+"°");
                        LinearLayoutManager manager=new LinearLayoutManager(WeatherActivity.this);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        mWeatherView.setLayoutManager(manager);
                        mWeatherView.setAdapter(new MyWeatherAdapter());
                    }
                });
            }
        }).start();

    }

    private void initType(ImageView imageView,int i) {
        if(weatherInfo.data.forecast.get(i).type.equals("雷阵雨"))
        {
            imageView.setImageResource(R.drawable.liezhengyu);
            if(i==0)
                mLayout.setBackgroundResource(R.drawable.xiayu);
        }else if(weatherInfo.data.forecast.get(i).type.equals("多云")){
            imageView.setImageResource(R.drawable.duoyun);
            if(i==0)
                mLayout.setBackgroundResource(R.drawable.yintian);
        }else if(weatherInfo.data.forecast.get(i).type.equals("晴")){
            imageView.setImageResource(R.drawable.qing);
            if(i==0)
                mLayout.setBackgroundResource(R.drawable.qingtian);
        }
    }
    class MyWeatherAdapter extends RecyclerView.Adapter<MyWeatherAdapter.ViewHolder>
    {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(WeatherActivity.this).inflate(R.layout.weather_item_layout,viewGroup,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String s[]=mList.get(i).date.split("日");
            viewHolder.date.setText(s[1]);
            initType(viewHolder.type,i);
            String high[]=mList.get(i).high.split(" ");
            String low[]=mList.get(i).low.split(" ");
            viewHolder.wd.setText(high[1]+"-"+low[1]);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView date,wd;
            ImageView type;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                date=itemView.findViewById(R.id.tv_weather_time);
                type=itemView.findViewById(R.id.iv_weather_image);
                wd=itemView.findViewById(R.id.tv_weather_wd);
            }
        }
    }
}
