package com.example.lenovo.androidtext1_1.Weather;
import java.util.List;

public class Weather {
    //  外面大括号的字段封装 setter getter toString
    public String desc;
    public int status;
    public Data data;
    @Override
    public String toString() {
        return "Weather [desc=" + desc + ", status=" + status + ", data="
                + data + "]";
    }


}

class Data{
    //  里面大括号的字段封装 setter getter toString
//  该类中包含有数组形和对象形，需要一并的封装在里面
    public String wendu;
    public String ganmao;
    public List<Forecast> forecast;
    public Yesterday yesterday;
    public String aqi;
    public String city;
    @Override
    public String toString() {
        return "Data [wendu=" + wendu + ", ganmao=" + ganmao + ", forecast="
                + forecast + ", yesterday=" + yesterday + ", aqi=" + aqi
                + ", city=" + city + "]";
    }


}

class Forecast{
    //  数组里面的大括号类型字段的封装
    public String fengxiang;
    public String fengli;
    public String high;
    public String type;
    public String low;
    public String date;
    @Override
    public String toString() {
        return "Forecast [fengxiang=" + fengxiang + ", fengli=" + fengli
                + ", high=" + high + ", type=" + type + ", low=" + low
                + ", date=" + date + "]";
    }


}

class Yesterday{
    //   最后{}的字段封装
    public String fl;
    public String fx;
    public String high;
    public String type;
    public String low;
    public String date;
    @Override
    public String toString() {
        return "Yesterday [fl=" + fl + ", fx=" + fx + ", high=" + high
                + ", type=" + type + ", low=" + low + ", date=" + date + "]";
    }

}
