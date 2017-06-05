package data_table;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yu413.netywork.MainActivity;
import com.example.yu413.netywork.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import shanglaxiala.utils.ToastUtils;

public class Data_Table_Name extends AppCompatActivity {
    private DBHelper bhelper;
    private ListView lv_continent;//洲
    private ListView lv_country;//国
    private ListView lv_province;//省
    private ListView lv_city;//市
    //放置  洲  国  省 市的集合
    private ArrayList<Level> continentValues = new ArrayList<Level>();
    private ArrayList<Level> countryValues = new ArrayList<Level>();
    private ArrayList<Level> provinceValues = new ArrayList<Level>();
    private ArrayList<Level> cityValues;
    //洲  国   省  市的适配器
    private LevelListViewAdapter continentAdapter;
    private LevelListViewAdapter countryAdapter;
    private LevelListViewAdapter provinceAdapter;
    private LevelListViewAdapter cityAdapter;
    //集合中的位置
    private int continentPosition = 0;
    private int countryPosition = 0;
    private int provincePosition = 0;
    private int cityPosition = 0;


    private int countryNumber = -1;
    private int provinceNumber = -1;
    private long countryTime = 0;
    private long provinceTime = 0;


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__table__name);
        mContext = this;
        lv_continent = (ListView) findViewById(R.id.lv_continent);
        lv_country = (ListView) findViewById(R.id.lv_country);
        lv_province = (ListView) findViewById(R.id.lv_province);
        lv_city = (ListView) findViewById(R.id.lv_city);

        bhelper = new DBHelper(mContext);
        setContinent();
        setCountry();
        setProvince();
        setCity();
    }

    /**
     * 设置市
     */
    private void setCity() {
        cityValues = getCity(provinceValues.get(provincePosition).getPlaceid());
        if (!(cityValues.isEmpty())) {
            cityAdapter = new LevelListViewAdapter(this, cityValues);
            cityAdapter.setSelectedPositionNoNotify(cityPosition, cityValues);
            lv_city.setAdapter(cityAdapter);
            cityAdapter.setOnItemClickListener(new LevelListViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToastUtils.shortToast(mContext, cityValues.get(position).getPlacename());
                }
            });
        }
    }

    /**
     * 设置省
     */
    private void setProvince() {
        provinceValues = getProvince(countryValues.get(countryPosition).getPlaceid());
        if (!(provinceValues.isEmpty())) {
            provinceAdapter = new LevelListViewAdapter(this, provinceValues);
            provinceAdapter.setSelectedPositionNoNotify(provincePosition, provinceValues);
            lv_province.setAdapter(provinceAdapter);
            provinceAdapter.setOnItemClickListener(new LevelListViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (provinceNumber != position) {//记录不是当前点击的
                        provinceNumber = position;//就记录当前条目
                        provinceTime = System.currentTimeMillis();//并记录第一次时间戳
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                provinceNumber = -1;
                                provinceTime = 0;
                            }
                        }, 300);
                    } else {//记录的是当前点击的
                        long num = System.currentTimeMillis() - provinceTime;//判断时间差，是不是双击
                        if (num <= 300) {//时间差200毫秒内
                            ToastUtils.shortToast(mContext, provinceValues.get(position).getPlacename());
                        }
                        provinceNumber = -1;//重置过的记录
                        provinceTime = 0;//重置时间的记录
                    }
                    cityValues.clear();
                    if (!(provinceValues.isEmpty())) {
                        cityValues = getCity(provinceValues.get(position).getPlaceid());
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    } else {
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 设置国
     */
    private void setCountry() {
        countryValues = getCountry(continentValues.get(continentPosition).getPlaceid());
        if (!(countryValues.isEmpty())) {
            countryAdapter = new LevelListViewAdapter(this, countryValues);
            countryAdapter.setSelectedPositionNoNotify(countryPosition, countryValues);
            lv_country.setAdapter(countryAdapter);
            countryAdapter.setOnItemClickListener(new LevelListViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (countryNumber != position) {//记录不是当前点击的
                        countryNumber = position;//就记录当前条目
                        countryTime = System.currentTimeMillis();//并记录第一次时间戳
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                countryNumber = -1;
                                countryTime = 0;
                            }
                        }, 300);
                    } else {//记录的是当前点击的
                        long num = System.currentTimeMillis() - countryTime;//判断时间差，是不是双击
                        if (num <= 300) {//时间差200毫秒内
                            ToastUtils.shortToast(mContext, countryValues.get(position).getPlacename());
                        }
                        countryNumber = -1;//重置过的记录
                        countryTime = 0;//重置时间的记录
                    }
                    provinceValues.clear();
                    if (!(countryValues.isEmpty())) {
                        provinceValues = getProvince(countryValues.get(position).getPlaceid());
                        provinceAdapter.notifyDataSetChanged();
                        provinceAdapter.setSelectedPositionNoNotify(0, provinceValues);
                        lv_province.smoothScrollToPosition(0);
                    } else {
                        provinceAdapter.notifyDataSetChanged();
                    }

                    cityValues.clear();
                    if (!(provinceValues.isEmpty())) {
                        cityValues = getCity(provinceValues.get(0).getPlaceid());
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    } else {
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 设置洲
     */
    private void setContinent() {
        continentValues = bhelper.getContinent();
        if (!(continentValues.isEmpty())) {
            continentAdapter = new LevelListViewAdapter(this, continentValues);
            continentAdapter.setSelectedPositionNoNotify(continentPosition, continentValues);
            lv_continent.setAdapter(continentAdapter);
            continentAdapter.setOnItemClickListener(new LevelListViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    countryValues.clear();
                    if (!(continentValues.isEmpty())) {
                        countryValues = getCountry(continentValues.get(position).getPlaceid());
                        countryAdapter.notifyDataSetChanged();
                        countryAdapter.setSelectedPositionNoNotify(0, countryValues);
                        lv_country.smoothScrollToPosition(0);
                    } else {
                        countryAdapter.notifyDataSetChanged();
                    }

                    provinceValues.clear();
                    if (!(countryValues.isEmpty())) {
                        provinceValues = getProvince(countryValues.get(0).getPlaceid());
                        provinceAdapter.notifyDataSetChanged();
                        provinceAdapter.setSelectedPositionNoNotify(0, provinceValues);
                        lv_province.smoothScrollToPosition(0);
                    } else {
                        provinceAdapter.notifyDataSetChanged();
                    }

                    cityValues.clear();
                    if (!(provinceValues.isEmpty())) {
                        cityValues = getCity(provinceValues.get(0).getPlaceid());
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    } else {
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 获取国
     *
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getCountry(String placetoid) {
        return bhelper.getCountry(placetoid);
    }

    /**
     * 获取省
     *
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getProvince(String placetoid) {
        return bhelper.getProvince(placetoid);
    }

    /**
     * 获取市
     *
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getCity(String placetoid) {
        return bhelper.getCity(placetoid);
    }
}
