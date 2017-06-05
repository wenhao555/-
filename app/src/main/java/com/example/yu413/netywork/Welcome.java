package com.example.yu413.netywork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import data_table.Data_Table_Name;
import datalibrary.DataPackers;
import dataview.DataView;
import game.MyGame;
import hintpopup.HPWindow;
import shanglaxiala.ShangLaXiaLa;

public class Welcome extends AppCompatActivity {
    private ListView MyCalalogue;
    private String list[] = {"天气预报", "数据增删改查", "上拉加载，下拉刷新", "List联动", "模糊效果", "数据图","别踩白块"};
    private Intent intent;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = this;
        MyCalalogue = (ListView) findViewById(R.id.MyCatalogue);
        MyCalalogue.setAdapter(new MyAdapter());
        MyCalalogue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0://天气预报
                        intent = new Intent(mContext, Weather.class);
                        startActivity(intent);
                        break;
                    case 1://数据库
                        intent = new Intent(mContext, DataPackers.class);
                        startActivity(intent);
                        break;
                    case 2://上拉下拉
                        intent = new Intent(mContext, ShangLaXiaLa.class);
                        startActivity(intent);
                        break;
                    case 3://List联动
                        intent = new Intent(mContext, Data_Table_Name.class);
                        startActivity(intent);
                        break;
                    case 4://模糊效果
                        intent = new Intent(mContext, HPWindow.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(mContext, DataView.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(mContext, MyGame.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public Object getItem(int i) {
            return list[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(Welcome.this);
            textView.setText(list[i]);
            textView.setTextSize(20);
            return textView;

        }
    }
}
