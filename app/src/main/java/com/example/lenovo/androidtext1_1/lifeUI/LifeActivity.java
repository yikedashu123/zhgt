package com.example.lenovo.androidtext1_1.lifeUI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.androidtext1_1.R;

import java.util.ArrayList;
import java.util.List;

public class LifeActivity extends AppCompatActivity {
    private ListView mListView;
    private List<Life> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        mListView=findViewById(R.id.lv_lifeView);
        initData();
        mListView.setAdapter(new MyLifeAdapter());
    }

    private void initData() {
        Life life;
        Life life1;
        mList=new ArrayList<>();
        life=new Life(8,"立减卷","家政洗衣按摩美业芬","","2016.03.06-2016.03.08有效");
        mList.add(life);
        for(int i=0;i<9;i++)
        {
            life1=new Life(50,"满减卷","仅限昆仑山使用","满99可用","2016.03.06-2016.03.08有效");
            mList.add(life1);
        }
    }
    class MyLifeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1= LayoutInflater.from(LifeActivity.this).inflate(R.layout.life_item,viewGroup,false);
            TextView money=view1.findViewById(R.id.life_money);
            money.setText("￥"+mList.get(i).getMoney());
            TextView type=view1.findViewById(R.id.life_type);
            type.setText(mList.get(i).getType());
            TextView content=view1.findViewById(R.id.life_text);
            content.setText(mList.get(i).getContent());
            TextView minit=view1.findViewById(R.id.life_manjian);
            if(mList.get(i).getType().equals("满减卷"))
            {
                minit.setText(mList.get(i).getMinit());
            }else {
                minit.setVisibility(View.GONE);
                View view2=view1.findViewById(R.id.life_color);
                view2.setBackgroundResource(R.drawable.life_view_yellow);
                money.setTextColor(Color.YELLOW);
                Button button=view1.findViewById(R.id.life_button);
                button.setTextColor(Color.YELLOW);
            }
            TextView time=view1.findViewById(R.id.life_time);
            time.setText(mList.get(i).getTime());
            return view1;
        }
    }
}
