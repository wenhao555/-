package data_table;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yu413.netywork.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/27.
 */

public class LevelListViewAdapter extends BaseAdapter {
    private Context mContext;
    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;
    private int selectedPos = -1;
    private String selectedText = "";
    private ArrayList<Level> mData;

    public LevelListViewAdapter(Context context, ArrayList<Level> level) {
        this.mContext = context;
        this.mData = level;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos, ArrayList<Level> level) {
        selectedPos = pos;
        mData = level;
        if (mData != null && pos < mData.size()) {
            selectedText = mData.get(pos).getPlacename();
        }
    }

    /**
     * 设置选中的position,并通知刷新其它列表
     */
    public void setSelectedPosition(int pos) {
        if (mData != null && pos < mData.size()) {
            selectedPos = pos;
            selectedText = mData.get(pos).getPlacename();
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.activity_adapter_levellistview, null);
        TextView continent_text = (TextView) view.findViewById(R.id.continent_text);
        String placeName = mData.get(position).getPlacename();
        continent_text.setText(placeName);
        if (selectedText != null && selectedText.equals(placeName)) {
            continent_text.setBackgroundResource(R.drawable.choose_item_selected);
        } else {
            continent_text.setBackgroundResource(R.drawable.choose_eara_item_selector);
        }
        view.setTag(position);
        view.setOnClickListener(onClickListener);
        return view;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }
}