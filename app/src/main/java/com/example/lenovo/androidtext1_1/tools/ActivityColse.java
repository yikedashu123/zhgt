package com.example.lenovo.androidtext1_1.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityColse {
    public static List<Activity> list=new ArrayList<>();
    public static void addActivity(Activity activity)
    {
        list.add(activity);
    }
    public static void removeAll()
    {
        for(Activity activity:list)
        {
            if(!activity.isFinishing())
                activity.finish();
        }
    }
}
