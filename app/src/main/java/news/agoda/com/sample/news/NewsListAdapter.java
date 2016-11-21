package news.agoda.com.sample.news;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.NewsEntity;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<NewsEntity> mNewsList = new ArrayList<>();
    private NewsItemListener mNewsItemListener;

    public void replaceData(List<NewsEntity> newsEntityList) {
        mNewsList = newsEntityList;
        notifyDataSetChanged();
    }

    public void setNewsItemListner(NewsItemListener listener) {
        mNewsItemListener = listener;
    }

    public NewsEntity getNews(int position) {
        return mNewsList.get(position);
    }

    @Override
    public int getItemCount() {
        if (mNewsList != null) {
            return mNewsList.size();
        } else {
            return 0;
        }
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View newsItem = LayoutInflater.from(context).inflate(
                R.layout.list_item_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(newsItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NewsEntity newsEntity = mNewsList.get(position);
        holder.newsTitle.setText(newsEntity.getTitle());
        List<NewsEntity.MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
        if (mediaEntityList.size() > 0) {
            String thumbnailURL = mediaEntityList.get(0).getUrl();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                    (Uri.parse(thumbnailURL))).setOldController(holder.imageView.getController()).build();
            holder.imageView.setController(draweeController);
        } else {
            holder.imageView.setImageResource(R.drawable.place_holder);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsItemListener.onNewsClick(position);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        DraweeView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView)itemView.findViewById(R.id.news_title);
            imageView = (DraweeView)itemView.findViewById(R.id.news_item_image);
        }
    }

    public interface NewsItemListener {
        void onNewsClick(int position);
    }
}
