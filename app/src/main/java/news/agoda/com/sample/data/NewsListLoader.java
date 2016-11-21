package news.agoda.com.sample.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by chiu_mac on 2016/11/19.
 */
public class NewsListLoader extends AsyncTaskLoader<List<NewsEntity>> {

    private NewsRepository mNewsRepository;

    public NewsListLoader(Context context, @NonNull NewsRepository newsRepository) {
        super(context);
        onContentChanged();
        mNewsRepository = newsRepository;
    }

    @Override
    public List<NewsEntity> loadInBackground() {
        return mNewsRepository.getNews();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<NewsEntity> data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }
}
