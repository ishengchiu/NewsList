package news.agoda.com.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import news.agoda.com.sample.helper.NewsFeedLoader;
import news.agoda.com.sample.model.NewsEntity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private RecyclerView mNewsListRecyclerView;
    private NewsListAdapter mNewsListAdapter;

    private LoadNewsFeedThread mLoadingNewsFeedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configure RecyclerView
        mNewsListRecyclerView = (RecyclerView)findViewById(R.id.news_list);
        if (mNewsListRecyclerView.getLayoutManager() instanceof  GridLayoutManager) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                ((GridLayoutManager) mNewsListRecyclerView.getLayoutManager()).setSpanCount(3);
            } else {
                ((GridLayoutManager) mNewsListRecyclerView.getLayoutManager()).setSpanCount(4);
            }

        }
        mNewsListAdapter = new NewsListAdapter();
        mNewsListAdapter.setOnItemClickListener(this);
        mNewsListRecyclerView.setAdapter(mNewsListAdapter);

        loadResource();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingNewsFeedThread != null) {
            mLoadingNewsFeedThread.terminate();
            mLoadingNewsFeedThread.interrupt();
        }
    }

    private void loadResource() {
        if (mLoadingNewsFeedThread != null) {
            mLoadingNewsFeedThread.terminate();
            mLoadingNewsFeedThread.interrupt();
        }
        mLoadingNewsFeedThread = new LoadNewsFeedThread();
        mLoadingNewsFeedThread.start();
    }

    private void onNewsFeedLoadComplete(List<NewsEntity> newsList) {
        Log.d(TAG, "onNewsFeedLoadComplete");
        mNewsListAdapter.addAll(newsList);
    }

    private void onNewsFeedLoadFail() {
        Log.w(TAG, "onNewsFeedLoadFail");
    }

    @Override
    public void onClick(View v) {
        int position = mNewsListRecyclerView.getChildLayoutPosition(v);
        Log.d(TAG, "item " + position + " is clicked");
        NewsEntity newsEntity = mNewsListAdapter.getNewsEntity(position);
        String title = newsEntity.getTitle();
        Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
        intent.putExtra(DetailViewActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailViewActivity.EXTRA_STORY_URL, newsEntity.getStoryUrl());
        intent.putExtra(DetailViewActivity.EXTRA_SUMMARY, newsEntity.getSummary());
        if (newsEntity.getMediaEntity().size() > 0) {
            List<NewsEntity.MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
            String imgUrl = null;
            //Find medium 3x2 image
            for (int i = 0 ; i < mediaEntityList.size() ; i++) {
                NewsEntity.MediaEntity mediaEntity = mediaEntityList.get(i);
                if (mediaEntity.getFormat().equals(NewsEntity.MediaEntity.FORMAT_MEDIUM_3_2)) {
                    imgUrl = mediaEntity.getUrl();
                }
            }
            if (imgUrl == null) {
                imgUrl = newsEntity.getMediaEntity().get(0).getUrl();
            }
            intent.putExtra(DetailViewActivity.EXTRA_IMAGE_URL, imgUrl);
        }
        startActivity(intent);
    }

    class LoadNewsFeedThread extends Thread {
        private boolean isTerminated = false;

        @Override
        public void run() {
            super.run();
            try {
                final List<NewsEntity> newsList = NewsFeedLoader.load();
                if (!isTerminated) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (newsList != null) {
                                onNewsFeedLoadComplete(newsList);
                            } else {
                                onNewsFeedLoadFail();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void terminate() {
            isTerminated = true;
        }
    }
}
