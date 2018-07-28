package com.example.lenovo.androidtext1_1.tools;

import android.app.Activity;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MusicTools {

    private static Map<Integer, Integer> soundMap;

    public static Map<Integer,Integer> loadSound(final SoundPool soundPool, final Activity activity)
    {
        try {
            soundMap = new HashMap<>();
            soundMap.put(0,soundPool.load(activity.getAssets().openFd("home.mp3"), 1));
            soundMap.put(1,soundPool.load(activity.getAssets().openFd("tianqi.mp3"), 1));
            soundMap.put(2,soundPool.load(activity.getAssets().openFd("jiaotong.mp3"), 1));
            soundMap.put(3,soundPool.load(activity.getAssets().openFd("weizhi.mp3"), 1));
        } catch (IOException e) {
                    e.printStackTrace();
                }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }).start();
        Log.i("PPPPPPPPPPPPPPP", "onCreate: 工具"+ soundMap.size());
        return soundMap;
    }
}
