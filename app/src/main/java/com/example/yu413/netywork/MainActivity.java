package com.example.yu413.netywork;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    EditText mEdit;
    Button mBtn;
    ListView mList;
    ProgressDialog dialog;
    Handler handler;
    String mUrl;
    List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<Map<String, String>>();
        mEdit = (EditText) findViewById(R.id.mEdit);
        mBtn = (Button) findViewById(R.id.mBtn);
        mList = (ListView) findViewById(R.id.mList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//              解析HTML标签
                Spanned string = Html.fromHtml(list.get(position).get("result"));
                builder.setTitle("天气状况");
                builder.setMessage(list.get(position).get("date"));
                builder.setPositiveButton("确定", null);
                builder.create().show();
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//              得到输入的文字
                list.clear();
                String weather = mEdit.getText().toString().trim();
                if (weather.equals("")) {
                    Toast.makeText(MainActivity.this, "请输入地名", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("正在拼命加载中");
                dialog.show();
                mUrl = "http://api.avatardata.cn/Weather/Query?key=ab5e64054a6d450f9014b4c97892caf0&cityname=" + weather;

                new weather(mUrl).start();
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (dialog != null) {
//              dialog在加载之后消失
                    dialog.dismiss();
                }
                String result = msg.obj.toString();
                System.out.print("result= " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
//                    得到数组
                    JSONArray array = jsonObject.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject child = array.getJSONObject(i);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("date", child.getString("date"));
                        map.put("week", child.getString("week"));
                        map.put("nongli", child.getString("nongli"));
                        map.put("info", child.getString("info"));
                        map.put("night", child.getString("night"));
                        list.add(map);
                        System.out.print(list+"++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    MyAdapter adapter = new MyAdapter();
                    mList.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setPadding(10, 10, 10, 10);
            textView.setTextSize(20);
            textView.setText(list.get(position).get("date") + list.get(position).get("week") + list.get(position).get("nongli") + list.get(position).get("info") + list.get(position).get("night"));


            return textView;
        }
    }

    class weather extends Thread {
        //类和类传递参数使用构造方法
        private String s;

        public weather(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            super.run();
            String result = "";
            try {
                URL url = new URL(s);
//                通过这个 来获取网上数据
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                字节流转换字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String len = null;
                while ((len = reader.readLine()) != null) {
                    result += len;
                }
                reader.close();
                Message message = handler.obtainMessage();
                message.obj = result;
                handler.sendMessage(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
