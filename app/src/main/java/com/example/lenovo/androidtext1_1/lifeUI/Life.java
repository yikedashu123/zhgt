package com.example.lenovo.androidtext1_1.lifeUI;

public class Life {
    private float money;
    private String type;
    private String content;
    private String minit;
    private String time;

    public Life(float money, String type, String content, String minit, String time) {
        this.money = money;
        this.type = type;
        this.content = content;
        this.minit = minit;
        this.time = time;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMinit() {
        return minit;
    }

    public void setMinit(String minit) {
        this.minit = minit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
