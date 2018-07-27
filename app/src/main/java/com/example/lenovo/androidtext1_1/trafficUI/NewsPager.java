package com.example.lenovo.androidtext1_1.trafficUI;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.androidtext1_1.R;

import java.util.ArrayList;
import java.util.List;

public class NewsPager extends MenuPagerBase  {
    private ViewPager mPager;
    private List<ImageView> mList;
    private int sign=0;
    private PagerAdapter ad;
    private ImageView mImageView;
    private Handler handler;
    private ListView mView;
    private List<News> mLisetNews;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.view_layout,null,false);
        mPager=view.findViewById(R.id.vp_newsPager);
        mView=view.findViewById(R.id.lv_newsList);
        initNews();
//        mView.setAdapter(new MyListAdapter()); TODO 这里有毒

        gundong();
        initData();

        return view;
    }
    class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mLisetNews.size();
        }

        @Override
        public Object getItem(int i) {
            return mLisetNews.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //TODO 有空指针
           View view1=LayoutInflater.from(mView.getContext()).inflate(R.layout.news_layout,viewGroup,false);
            Log.i("pPPPPPPPPP", "getView: "+view+mContext);
            TextView textView=view1.findViewById(R.id.tv_news_text);
            textView.setText(mLisetNews.get(i).getContent());
            ImageView imageView=view1.findViewById(R.id.iv_news_image);
            if(-1 == mLisetNews.get(i).getImage())
                imageView.setVisibility(View.GONE);
            else {
                imageView.setImageResource( mLisetNews.get(i).getImage());
            }
            return null;
        }
    }
    private void gundong() {
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        mPager.setCurrentItem(sign);
                        sign=sign+1;

                        if(sign==mList.size()+1)
                            sign=0;
                        handler.sendEmptyMessageDelayed(1,2000);
                        break;
                }

            }

        };
        Message message=new Message();
        message.what=1;
        handler.sendMessage(message);
    }


    private void initData() {
        mList=new ArrayList<>();
        int image[]=new int[]{R.drawable.pingguo,R.drawable.caomei,R.drawable.chengzi};
        for(int i=0;i<image.length;i++)
        {
            ImageView imageView=new ImageView(mContext);
            imageView.setImageResource(image[i]);
            mList.add(imageView);
        }
        ad=new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view==o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView=mList.get(position);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageView=imageView;
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        };
        mPager.setAdapter(ad);
        mPager.setCurrentItem(0);
        sign=0;



    }

    private void initNews() {
        mLisetNews =new ArrayList<>();
        News news=new News();
        news.setContent("哈哈哈哈哈哈哈");
        news.setImage(R.drawable.head);
        mLisetNews.add(news);
        News news1=new News();
        news1.setContent("哈哈哈哈哈哈哈");
        news1.setImage(-1);
        mLisetNews.add(news1);
    }
}
