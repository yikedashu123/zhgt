package com.example.lenovo.androidtext1_1.trafficUI;

import android.content.Context;
import android.view.View;

import com.example.lenovo.androidtext1_1.R;

public class MenuPagerBase {
    protected Context mContext;
    protected View mView;
    public MenuPagerBase(Context context)
    {
        mContext=context;
        mView=initView();
    }
    protected  View initView()
    {
        View view=View.inflate(mContext, R.layout.base_layout,null);
        return view;
    }
    public View getRootView()
    {
        return mView;
    }

}
