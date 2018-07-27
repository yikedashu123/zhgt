package com.example.lenovo.androidtext1_1.Weather;

public class WeatherItem {
    private String time;
    private String type;
    private String temer;

    public WeatherItem(String time, String type, String temer) {
        this.time = time;
        this.type = type;
        this.temer = temer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemer() {
        return temer;
    }

    public void setTemer(String temer) {
        this.temer = temer;
    }
}
