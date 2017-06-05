package shanglaxiala;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yu413.netywork.R;

import java.util.ArrayList;

import shanglaxiala.adapter.BaseRecyclerViewAdapter;
import shanglaxiala.adapter.NewsAdapter;
import shanglaxiala.been.Data;
import shanglaxiala.utils.AnimationUtils;
import shanglaxiala.utils.SimpleItemDecoration;
import shanglaxiala.utils.ToastUtils;

//上拉加载下拉刷新
public class ShangLaXiaLa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NewsAdapter.onItemClickListener, BaseRecyclerView.onLoadMoreListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseRecyclerView recyclerView;
    private TextView textView;
    private BaseRecyclerViewAdapter baseRecyclerViewAdapter;
    private NewsAdapter newsAdapter;
    private ArrayList<Data> lists;//数据源
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://刷新数据   //下拉刷新
                    for (int i = 0; i < 10; i++) {
                        Data data = new Data();
                        data.setText("我是刷新的数据" + i);
                        lists.add(i, data);
                    }
                    baseRecyclerViewAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 1://加载数据   上拉加载
                    for (int i = 0; i < 10; i++) {
                        Data data = new Data();
                        data.setText("我是加载的数据" + i);
                        lists.add(data);
                    }
                    baseRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_la_xia_la);
        initView();
        initData();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_light), getResources().getColor(R.color.green_light), getResources().getColor(R.color.orange_light), getResources().getColor(R.color.red_light));
        recyclerView = (BaseRecyclerView) findViewById(R.id.recycleview);
        recyclerView.setListener(this);
        textView = (TextView) findViewById(R.id.text);
    }

    //数据的初始化
    private void initData() {
        //recyclerView 的相关初始化
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //SimpleItemDecoration 的第一个参数代表item距离左右以及上下的距离，第二个参数用于判断水平最后一个item(多列的情况)
        //第二个参数的值要和StaggeredGridLayoutManager的第一个参数的值相等
        recyclerView.addItemDecoration(new SimpleItemDecoration(40, 1));
        View header = LayoutInflater.from(this).inflate(R.layout.activity_recyeclerview_item, null);
        lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Data data = new Data();
            data.setText("我是初始化的数据" + i);
            lists.add(data);
        }
        newsAdapter = new NewsAdapter(lists);

        newsAdapter.setListener(this);
        baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<>(newsAdapter);
        //添加头部
        baseRecyclerViewAdapter.addHeader(header);
        recyclerView.setAdapter(baseRecyclerViewAdapter);
    }

    //刷新的监听
    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    //adapter的item的点击监听
    @Override
    public void click(int position) {
        ToastUtils.shortToast(this, lists.get(position).getText());
    }

    //加载的监听
    @Override
    public void loadMore() {

        AnimationUtils.showAndHide(textView, "正在加载数据...");

        handler.sendEmptyMessageDelayed(1, 2000);

    }
}