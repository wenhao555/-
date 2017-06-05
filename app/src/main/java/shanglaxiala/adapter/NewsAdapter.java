package shanglaxiala.adapter;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yu413.netywork.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import shanglaxiala.been.Data;


/**
 * Created by Administrator on 2017/5/26.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.BannerViewHolder> {
    private ArrayList<Data> lists = new ArrayList<Data>();
    private DisplayImageOptions options;
    private onItemClickListener listener;

    public NewsAdapter(ArrayList<Data> lists) {
        this.lists = lists;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.empty)//加载显示的图片
                .showImageOnFail(R.mipmap.place_holder)//加载失败现实的图片
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyeclerview_item, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, final int position) {
        Data data = lists.get(position);
        holder.textView.setText(data.getText());
        if (data.getUrl() != null && data.getUrl().length() > 0) {
            ImageLoader.getInstance().displayImage(data.getUrl(), holder.imageView, options);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null) {
                    return;
                }
                listener.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;//图片
        TextView textView;//文本

        public BannerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface onItemClickListener {
        void click(int position);
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
