package news.agoda.com.sample;

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
import java.util.Collection;
import java.util.List;

import news.agoda.com.sample.model.NewsEntity;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<NewsEntity> mNewsList = new ArrayList<>();
    private View.OnClickListener mOnClickListener;

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void addAll(Collection<? extends NewsEntity> collection) {
        mNewsList.addAll(collection);
        notifyDataSetChanged();
    }

    public void add(NewsEntity news) {
        mNewsList.add(news);
        notifyDataSetChanged();
    }

    public NewsEntity getNewsEntity(int location) {
        if (location >= 0 && location < getItemCount()) {
            return mNewsList.get(location);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View newsItem = LayoutInflater.from(context).inflate(
                R.layout.list_item_news, parent, false);
        if (mOnClickListener != null) {
            newsItem.setOnClickListener(mOnClickListener);
        }
        ViewHolder viewHolder = new ViewHolder(newsItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
}
