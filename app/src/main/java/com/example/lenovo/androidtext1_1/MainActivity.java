package com.example.lenovo.androidtext1_1;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private List<ImageView> mList;
    private LinearLayout mLayout;
    private View mView;
    private int  mSpace=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
