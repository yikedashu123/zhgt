package com.example.lenovo.androidtext1_1.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lenovo.androidtext1_1.userData.User;

import java.util.List;


public class CacheUtils {
    public static final String NAME="zhjt";
    private static SharedPreferences sp;
    public static List<User> list;
    public static SharedPreferences getSp(Context context) {
        if(sp==null)
            sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp;
    }

    public static void setBoolean(Context context,String key,boolean value)
    {
        sp=getSp(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getBoolean(Context context, String key)
    {
        sp=getSp(context);
        return sp.getBoolean(key,false);
    }
    public static boolean getBoolean(Context context,String key,boolean deValue)
    {
        SharedPreferences sp=getSp(context);
        return sp.getBoolean(key,deValue);
    }

    public static void setBooleans(Context context,String key,boolean value)
    {
        sp=getSp(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getBooleans(Context context,String key,boolean deValue)
    {
        SharedPreferences sp=getSp(context);
        return sp.getBoolean(key,deValue);
    }

    public static void setLong(Context context,String key,long value)
    {
        sp=getSp(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putLong(key,value);
        editor.commit();
    }
    public static long getLong(Context context,String key,long deValue)
    {
        SharedPreferences sp=getSp(context);
        return sp.getLong(key,deValue);
    }


    public static void setUser(Context context,String[] key,User value)
    {
        sp=getSp(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key[0],value.getName());
        editor.putString(key[1],value.getPass());
        editor.commit();
    }
    public static User getUser(Context context,String[] key,User deValue)
    {
        SharedPreferences sp=getSp(context);
        User user=new User();
        user.setName(sp.getString(key[0],deValue.getName()));
        user.setPass(sp.getString(key[1],deValue.getName()));
        return user;
    }
}
