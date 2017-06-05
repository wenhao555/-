package com.example.yu413.netywork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather extends Activity {
    private Handler handler;
    private String mUrl;
    ProgressDialog dialog;
    Intent intent;
    Animation animation;
    TextView kongtiao, yundong, ziwaixian, ganmao, xiche, wuran, chuanyi, cities, City1, time, clear, T, weather, wind, power1, humidity1, oneDate, twoDate, threeDate, fourDate, fiveDate, twoWeather, threeWeather, fourWeather, fiveWeather, oneWeather;
    ImageView img;
    List<Map<String, String>> list;
    LinearLayout layout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        list = new ArrayList<>();
        City1 = (TextView) findViewById(R.id.City);
        layout = (LinearLayout) findViewById(R.id.lyout);
        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(Weather.this, R.anim.anim_one);
                Intent intent = new Intent(Weather.this, City.class);
                layout.startAnimation(animation);
                startActivityForResult(intent, 101);
            }
        });
        T = (TextView) findViewById(R.id.T);
        weather = (TextView) findViewById(R.id.weather);

        kongtiao = (TextView) findViewById(R.id.kongtiao);
        yundong = (TextView) findViewById(R.id.yundong);
        ziwaixian = (TextView) findViewById(R.id.ziwaixian);
        ganmao = (TextView) findViewById(R.id.ganmao);
        xiche = (TextView) findViewById(R.id.xiche);
        wuran = (TextView) findViewById(R.id.wuran);
        chuanyi = (TextView) findViewById(R.id.chuanyi);

        oneDate = (TextView) findViewById(R.id.oneDate);
        twoDate = (TextView) findViewById(R.id.twoDate);
        threeDate = (TextView) findViewById(R.id.threeDate);
        fourDate = (TextView) findViewById(R.id.fourDate);
        fiveDate = (TextView) findViewById(R.id.fiveDate);

        twoWeather = (TextView) findViewById(R.id.twoWeather);
        threeWeather = (TextView) findViewById(R.id.threeWeather);
        fourWeather = (TextView) findViewById(R.id.fourWeather);
        fiveWeather = (TextView) findViewById(R.id.fiveWeather);
        oneWeather = (TextView) findViewById(R.id.oneWeather);

        time = (TextView) findViewById(R.id.time);
        wind = (TextView) findViewById(R.id.wind);
        power1 = (TextView) findViewById(R.id.power);
        humidity1 = (TextView) findViewById(R.id.humidity);
        clear = (TextView) findViewById(R.id.clear);
        cities = (TextView) findViewById(R.id.cities);
        cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Weather.this, City.class);

                startActivity(intent);
            }
        });
        WeatherTask weatherTask = new WeatherTask();
        try {
            String u = "http://api.avatardata.cn/Weather/Query?key=ab5e64054a6d450f9014b4c97892caf0&cityname=" + URLEncoder.encode("大连", "utf-8");
            weatherTask.execute(new URL(u));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     *  参数1：表示执行任务的参数类型
     *  参数2：表示在后台线程处理的过程中，可以阶段性的发布结果的数据类型
     *  参数3：表示任务全部完成后所返回的数据类型
     *  onPpreExecute为运行在主线程的方法
     *  doInBacjground 运行在工作线程中的，完成后立即执行，用于执行好使任务
     *  onProgressUpdate
     *   onPostExecute  当doInBackground执行完成之后，返回值就会作为参数在主线程中传入到onPostExecute方法中，根据结果来进行执行结果更新UI
     */
    class WeatherTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
//                通过这个 来获取网上数据
                HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();
//                字节流转换字符流
//                将InputStream转换成reader的类InputStreamReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String len = null;
                while ((len = reader.readLine()) != null) {

                     stringBuffer.append(len);

                }
                reader.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.print(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jResult = jsonObject.getJSONObject("result");
                JSONObject jRealtime = jResult.getJSONObject("realtime");
                JSONObject jWeather = jRealtime.getJSONObject("weather");
//天气
                JSONObject jWind = jRealtime.getJSONObject("wind");
                JSONObject jLife = jResult.getJSONObject("life");
                JSONObject jinfo = jLife.getJSONObject("info");
//各项指数
                JSONArray kt = jinfo.getJSONArray("kongtiao");
                JSONArray yd = jinfo.getJSONArray("yundong");
                JSONArray zwx = jinfo.getJSONArray("ziwaixian");
                JSONArray gm = jinfo.getJSONArray("ganmao");
                JSONArray xc = jinfo.getJSONArray("xiche");
                JSONArray wr = jinfo.getJSONArray("wuran");
                JSONArray cy = jinfo.getJSONArray("chuanyi");
                String kt1 = kt.getString(0);
                String kt2 = kt.getString(1);
                String yd1 = yd.getString(0);
                String yd2 = yd.getString(1);
                String zwx1 = zwx.getString(0);
                String zwx2 = zwx.getString(1);
                String gm1 = gm.getString(0);
                String gm2 = gm.getString(1);
                String xc1 = xc.getString(0);
                String xc2 = xc.getString(1);
                String wr1 = wr.getString(0);
                String wr2 = wr.getString(1);
                String cy1 = cy.getString(0);
                String cy2 = cy.getString(1);

                kongtiao.setText("空调指数:" + "\n" + kt1 + "\n" + kt2);
                yundong.setText("运动指数:" + "\n" + yd1 + "\n" + yd2);
                ziwaixian.setText("紫外线:" + "\n" + zwx1 + "\n" + zwx2);
                ganmao.setText("发病指数:" + "\n" + gm1 + "\n" + gm2);
                wuran.setText("污染物:" + "\n" + xc1 + "\n" + xc2);
                xiche.setText("洗车指数:" + "\n" + wr1 + "\n" + wr2);
                chuanyi.setText("穿衣指数:" + "\n" + cy1 + "\n" + cy2);
                String humidity = jWeather.getString("humidity");
                String ti = jRealtime.getString("time");
                String temperature = jWeather.getString("temperature");
                String info = jWeather.getString("info");
                String direct = jWind.getString("direct");
                String power = jWind.getString("power");
                String chengshi = jRealtime.getString("city_name");
                humidity1.setText("相对湿度：" + humidity);
                T.setText("温度" + temperature + "°");
                weather.setText(info);
                time.setText("更新时间" + ti);
                wind.setText("风向：" + direct);
                power1.setText("风力：" + power);
                City1.setText(chengshi);


                JSONArray jweather = jResult.getJSONArray("weather");

                for (int j = 0; j < jweather.length(); j++) {
                    JSONObject child = jweather.getJSONObject(j);
                    JSONObject info1 = child.getJSONObject("info");
                    JSONArray day = info1.getJSONArray("day");
                    JSONArray night = info1.getJSONArray("night");
                    String day1 = day.getString(1);
                    String nday1 = night.getString(1);
                    String T1 = day.getString(2);
                    String nT1 = night.getString(2);
                    String wind1 = day.getString(3);
                    String nwind1 = night.getString(3);
                    String power1 = day.getString(4);
                    String npower1 = night.getString(4);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("date", child.getString("date"));
                    map.put("week", child.getString("week"));
                    map.put("nongli", child.getString("nongli"));
                    map.put("day1", day1);
                    map.put("T1", T1);
                    map.put("wind1", wind1);
                    map.put("power1", power1);
                    list.add(map);
                }

                oneWeather.setText("天气：" + list.get(0).get("day1") + "\n" + "温度：" + list.get(0).get("T1") + "°" + "\n" + "风向：" + list.get(0).get("wind1") + "\n" + "风力：" + list.get(0).get("power1"));
                twoWeather.setText("天气：" + list.get(1).get("day1") + "\n" + "温度：" + list.get(1).get("T1") + "°" + "\n" + "风向：" + list.get(1).get("wind1") + "\n" + "风力：" + list.get(1).get("power1"));
                threeWeather.setText("天气：" + list.get(2).get("day1") + "\n" + "温度：" + list.get(2).get("T1") + "°" + "\n" + "风向：" + list.get(2).get("wind1") + "\n" + "风力：" + list.get(2).get("power1"));
                fourWeather.setText("天气：" + list.get(3).get("day1") + "\n" + "温度：" + list.get(3).get("T1") + "°" + "\n" + "风向：" + list.get(3).get("wind1") + "\n" + "风力：" + list.get(3).get("power1"));
                fiveWeather.setText("天气：" + list.get(4).get("day1") + "\n" + "温度：" + list.get(4).get("T1") + "°" + "\n" + "风向：" + list.get(4).get("wind1") + "\n" + "风力：" + list.get(4).get("power1"));

                oneDate.setText(list.get(0).get("date") + "\n" + "星期" + list.get(0).get("week") + "\n" + "农历" + list.get(0).get("nongli"));
                twoDate.setText(list.get(1).get("date") + "\n" + "星期" + list.get(1).get("week") + "\n" + "农历" + list.get(1).get("nongli"));
                threeDate.setText(list.get(2).get("date") + "\n" + "星期" + list.get(2).get("week") + "\n" + "农历" + list.get(2).get("nongli"));
                fourDate.setText(list.get(3).get("date") + "\n" + "星期" + list.get(3).get("week") + "\n" + "农历" + list.get(3).get("nongli"));
                fiveDate.setText(list.get(4).get("date") + "\n" + "星期" + list.get(4).get("week") + "\n" + "农历" + list.get(4).get("nongli"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == 102) {
            String name = data.getStringExtra("city_name");
            City1.setText(name);
            WeatherTask weatherTask = new WeatherTask();
            try {
                String u = "http://api.avatardata.cn/Weather/Query?key=ab5e64054a6d450f9014b4c97892caf0&cityname=" + URLEncoder.encode(name, "utf-8");
                weatherTask.execute(new URL(u));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
