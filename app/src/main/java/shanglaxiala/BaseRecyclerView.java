package shanglaxiala;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import shanglaxiala.adapter.BaseRecyclerViewAdapter;

/**
 * Created by Administrator on 2017/5/26.
 */

public class BaseRecyclerView extends RecyclerView {
    private int lastVisibleItemPosition;
    private BaseRecyclerViewAdapter baseRecyclerViewAdapter;
    private boolean isInit;//数组是否初始化
    private float startY;//手指开始的位置
    private float endY;
    public static boolean isLoading;
    private onLoadMoreListener listener;

    public BaseRecyclerView(Context context) {
        this(context, null, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Adapter adapter = getAdapter();
        if (!(adapter instanceof BaseRecyclerViewAdapter)) {//如果这个适配器不是
            throw new IllegalArgumentException("这个适配器必须继承BaseRecyclerViewAdapter");
        }
        baseRecyclerViewAdapter = (BaseRecyclerViewAdapter) adapter;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();

        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] last = null;
            if (!isInit) {
                last = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                isInit = true;
            }
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(last);
            for (int i : lastVisibleItemPositions) {
                lastVisibleItemPosition = i > lastVisibleItemPosition ? i : lastVisibleItemPosition;
            }
        }
        switch (ev.getAction()) {//设置触摸事件
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isLoading) {
                    endY = ev.getY();
                    if ((endY - startY) < 0 && lastVisibleItemPosition == baseRecyclerViewAdapter.getItemCount() - 1) {
                        if (listener == null) {
                            break;
                        }
                        listener.loadMore();
                        isLoading = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = 0;
                endY = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                startY = 0;
                endY = 0;
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public interface onLoadMoreListener {
        void loadMore();
    }

    public void setListener(onLoadMoreListener listener) {
        this.listener = listener;
    }
}
