package com.example.yu413.netywork;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class City extends Activity {
    AutoCompleteTextView exCity;
    ArrayList<String> citys = new ArrayList<String>();
    ImageView find;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        exCity = (AutoCompleteTextView) findViewById(R.id.exCity);
        find = (ImageView) findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exCity.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("city_name", name);
                setResult(102, intent);
                finish();
            }
        });
        try {
            InputStream inputStream = getAssets().open("cities.txt");
            StringBuffer stringBuffer = new StringBuffer();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            while (len > 0) {

                stringBuffer.append(new String(bytes, 0, len, "gbk"));
                len = inputStream.read(bytes);

            }
            inputStream.close();

            JSONArray arr = new JSONArray(stringBuffer.toString());
            for (int i = 0; i < arr.length(); i++) {

                JSONObject json = arr.getJSONObject(i);
                String name = json.getString("district");
                System.out.print(citys);
                citys.add(name);

            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citys);
            exCity.setAdapter(arrayAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
