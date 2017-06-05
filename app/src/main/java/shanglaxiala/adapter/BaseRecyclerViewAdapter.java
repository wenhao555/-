package shanglaxiala.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
//创建一个上拉下拉适配器
public class BaseRecyclerViewAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final T mBase;
    private int position;
    private static final int HEADER_VIEW_TYPE = -1000;
    private static final int FOOTER_VIEW_TYPE = -2000;
    //存放头文件的
    private final List<View> mHeaders = new ArrayList<View>();
    //存放下部分文件的
    private final List<View> mFooters = new ArrayList<View>();

    //适配器的包装
    public BaseRecyclerViewAdapter(T mBase) {
        this.mBase = mBase;
    }

    //    得到了基本适配器,这是包装。
    public T getWrappedAdapter() {
        return mBase;
    }

    public void addHeader(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("你不能有一个空的头");
        }
        mHeaders.add(view);
    }

    public void addFooter(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("你不能有一个空的脚");

        }
        mFooters.add(view);
    }

    //设置下拉是否显示
    public void setHeaderVisibility(boolean shouldShow) {
        for (View header : mHeaders) {
            header.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
    }

    //设置上拉是否显示
    public void setFooterVisibility(boolean shouldShow) {
        for (View footer : mFooters) {
            footer.setVisibility(shouldShow ? View.VISIBLE : View.GONE);

        }
    }

    //得到下拉的刷新数
    public int getHeaderCount() {
        return mHeaders.size();
    }

    //得到上拉的刷新数
    public int getFooterCount() {
        return mFooters.size();
    }

    //得到显示的头，如果不存在或null
    public View getHeader(int i) {
        return i < mHeaders.size() ? mHeaders.get(i) : null;
    }

    public View getFooder(int i) {
        return i < mFooters.size() ? mFooters.get(i) : null;
    }

    private boolean isHeader(int viewType) {
        return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + mHeaders.size());
    }

    private boolean isFooter(int viewType) {
        return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + mFooters.size());
    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);//取绝对值
            View headerView = mHeaders.get(whichHeader);
            return new RecyclerView.ViewHolder(headerView) {

            };
        } else if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
            View footerView = mFooters.get(whichFooter);
            return new RecyclerView.ViewHolder(footerView) {
            };
        } else {
            return mBase.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mHeaders.size()) {
            this.position = 0;
        } else if (position < mHeaders.size() + mBase.getItemCount()) {
            mBase.onBindViewHolder(holder, position - mHeaders.size());
            this.position = position - mHeaders.size();
        } else {
            this.position = mBase.getItemCount() - 1;
        }
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + mBase.getItemCount() + mFooters.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mHeaders.size()) {
            return HEADER_VIEW_TYPE + position;
        } else if (position < (mHeaders.size() + mBase.getItemCount())) {
            return mBase.getItemViewType(position - mHeaders.size());
        } else {
            return FOOTER_VIEW_TYPE + position - mHeaders.size() - mBase.getItemCount();
        }
    }
}
