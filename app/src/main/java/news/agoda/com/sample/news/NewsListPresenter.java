package news.agoda.com.sample.news;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

import news.agoda.com.sample.MyApplication;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.data.NewsListLoader;
import news.agoda.com.sample.data.NewsRepository;
import news.agoda.com.sample.util.NetworkUtil;

/**
 * Created by chiu_mac on 2016/11/19.
 */

public class NewsListPresenter implements NewsContract.Presenter,
        LoaderManager.LoaderCallbacks<List<NewsEntity>> {
    private static final String TAG = NewsListPresenter.class.getSimpleName();
    private static final int LOAD_NEWS = 1;

    private NewsContract.View mNewsListView;
    private NewsListLoader mLoader;
    private LoaderManager mLoaderManager;
    private NewsRepository mNewsDataStore;

    public NewsListPresenter(NewsContract.View newsListView,
                             NewsRepository newsDataStore,
                             NewsListLoader loader,
                             LoaderManager loaderManager) {
        mNewsListView = newsListView;
        mLoader = loader;
        mLoaderManager = loaderManager;
        mNewsDataStore = newsDataStore;
    }

    @Override
    public void loadNews() {
        Log.d(TAG, "loadNews");
        boolean hasInternet = NetworkUtil.hasInternetConnection(MyApplication.getContext());
        if (hasInternet) {
            mLoaderManager.initLoader(LOAD_NEWS, null, this);
        } else {
            mNewsListView.showNoNetworkConnection();
        }
    }

    @Override
    public void openNewsDetail(NewsEntity newsEntity) {
        mNewsListView.showNewsDetail(newsEntity);
    }

    @Override
    public Loader<List<NewsEntity>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        mNewsListView.setLoadingIndicator(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<NewsEntity>> loader, List<NewsEntity> data) {
        mNewsListView.setLoadingIndicator(false);
        if (data == null || data.size() == 0) {
            mNewsListView.showNoNews();
        } else {
            mNewsListView.showNews(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsEntity>> loader) {
    }
}
