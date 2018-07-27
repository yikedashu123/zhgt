package com.example.lenovo.androidtext1_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.androidtext1_1.loginandre.MainUIActivity;
import com.example.lenovo.androidtext1_1.mainUI.HomeUIActivity;
import com.example.lenovo.androidtext1_1.tools.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private List<ImageView> mList;
    private LinearLayout mLayout;
    public static final String IS_FIRST="YES";
    public static final String IS_LOGIN="login";
    private View mView;
    private int  mSpace=0;
    private int currentItem;
    private Boolean noFirst;
    float startX=0;
    float endX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            list.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }if(!list.isEmpty())
        {
            String []p=list.toArray(new String[list.size()]);
            ActivityCompat.requestPermissions(this,p,1);
        }else {
            initAll();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0)
                {
                    for(int i:grantResults)
                    {
                        if(i!=PackageManager.PERMISSION_DENIED)
                        {
                            Toast.makeText(MainActivity.this,"必须允许权限！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    initAll();
                }
        }
    }
    private void initAll() {
        noFirst= CacheUtils.getBoolean(this,IS_FIRST,false);
        if(noFirst)
        {
            Intent intent=new Intent(this,HomeUIActivity.class);
            startActivity(intent);
        }
        mPager=findViewById(R.id.vp_view);
        mLayout=findViewById(R.id.ll_layout);
        mView=findViewById(R.id.v_point);
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mList.size()==0)
                    return;
                mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mSpace=mLayout.getChildAt(1).getLeft()-mLayout.getChildAt(0).getLeft();
            }
        });
        initData();
        listener();
    }

    //监听是否为最后一页
    private void listener() {
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX=event.getX();
                        WindowManager windowManager= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        //获取屏幕的宽度
                        Point size = new Point();
                        windowManager.getDefaultDisplay().getSize(size);
                        int width=size.x;
                        //首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
                        if(currentItem==(mList.size()-1) && startX-endX>0 && startX-endX>=(width/4)){
                            Intent intent=new Intent(MainActivity.this,MainUIActivity.class);
                            startActivity(intent);
                            finish();
                            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                        }
                        break;
                }
                return false;
            }
               // return false;
        });
    }

    private void initData() {
        int image[]=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
        mList=new ArrayList<>();
        for(int i=0;i<image.length;i++)
        {
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(image[i]);
            mList.add(imageView);

            View view=new View(this);
            view.setBackgroundResource(R.drawable.point);
            LinearLayout.LayoutParams linearLayout=new LinearLayout.LayoutParams(30,30);
            if(i!=0)
                linearLayout.leftMargin=10;
            mLayout.addView(view,linearLayout);
        }
        mPager.setAdapter(new WelcomePagerAdapter());
        mPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        RelativeLayout.LayoutParams params=
                (RelativeLayout.LayoutParams) mView.getLayoutParams();
        params.leftMargin= (int) (mSpace*i+mSpace*v+0.5f);
    }

    @Override
    public void onPageSelected(int i) {
        currentItem=i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    class WelcomePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if(mList.size()==0)
                return 0;
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view=mList.get(position);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


}
