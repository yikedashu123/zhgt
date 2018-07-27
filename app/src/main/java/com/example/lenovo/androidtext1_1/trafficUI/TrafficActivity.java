package com.example.lenovo.androidtext1_1.trafficUI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.androidtext1_1.R;

import java.util.ArrayList;
import java.util.List;

public class TrafficActivity extends AppCompatActivity{
    private RecyclerView mView;
    private ViewPager mPager;
    private List<String> mTitleList;
    private List<MenuPagerBase> mListPager;
    int sign=-1;
    private MyRecyclerViewAdapter.ViewHolder mViewHolder=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        mView=findViewById(R.id.rv_view);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mView.setLayoutManager(manager);
        mView.setAdapter(new MyRecyclerViewAdapter());
        mPager=findViewById(R.id.vp_pager);
        initData();
        mPager.setAdapter(new MyPagerAdapter());

    }

    private void initData() {
        mTitleList=new ArrayList<>();
        mTitleList.add("要闻");
        mTitleList.add("军事");
        mTitleList.add("财经");
        mTitleList.add("社会");
        mTitleList.add("体育");
        mListPager=new ArrayList<>();
        mListPager.add(new NewsPager(this));
        mListPager.add(new MilitaryPager(this));
        mListPager.add(new MenuPagerBase(this));
        mListPager.add(new MenuPagerBase(this));
        mListPager.add(new MenuPagerBase(this));
       mPager.setCurrentItem(0);
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(TrafficActivity.this).
                    inflate(R.layout.item_news_layout,viewGroup,false);
            final ViewHolder viewHolder=new ViewHolder(view);
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mViewHolder!=null)
                    {
                        mViewHolder.textView.setBackgroundResource(R.drawable.news_item);
                    }
                    viewHolder.textView.setBackgroundResource(R.drawable.select_item);
                    mViewHolder=viewHolder;
                    sign=viewHolder.getAdapterPosition();
                    mPager.setCurrentItem(viewHolder.getLayoutPosition());
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if(i==0){
                viewHolder.textView.setBackgroundResource(R.drawable.select_item);
                mViewHolder=viewHolder;
            }
            viewHolder.textView.setText(mTitleList.get(i));

        }

        @Override
        public int getItemCount() {
            return mTitleList.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.tv_news_title);
            }
        }
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if(mListPager.size()==0)
                return 0;
            return mListPager.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MenuPagerBase base=mListPager.get(position);
            View view=base.getRootView();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
