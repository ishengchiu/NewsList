package news.agoda.com.sample.news;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import news.agoda.com.sample.data.NewsRepository;
import news.agoda.com.sample.newsdetail.DetailViewActivity;
import news.agoda.com.sample.Injection;
import news.agoda.com.sample.data.NewsListLoader;
import news.agoda.com.sample.R;
import news.agoda.com.sample.data.NewsEntity;

public class MainActivity extends AppCompatActivity implements NewsContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private RecyclerView mNewsListRecyclerView;
    private NewsListAdapter mNewsListAdapter;

    private ProgressBar mLoadingIndicator;

    private TextView mWarningMsgTextView;

    private NewsContract.Presenter mNewsListPresenter;

    private NewsListAdapter.NewsItemListener mNewsItemListener = new NewsListAdapter.NewsItemListener() {
        @Override
        public void onNewsClick(int position) {
            NewsEntity newsEntity = mNewsListAdapter.getNews(position);
            mNewsListPresenter.openNewsDetail(newsEntity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_progress);
        mWarningMsgTextView = (TextView) findViewById(R.id.warning_msg);

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
        mNewsListAdapter.setNewsItemListner(mNewsItemListener);
        mNewsListRecyclerView.setAdapter(mNewsListAdapter);

        NewsRepository newsRepository = Injection.provideNewsRepository();

        NewsListLoader loader = new NewsListLoader(getApplicationContext(), newsRepository);
        mNewsListPresenter = new NewsListPresenter(this, newsRepository, loader, getSupportLoaderManager());

        mNewsListPresenter.loadNews();
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
    }

    @Override
    public void showNews(List<NewsEntity> newsEntityList) {
        mNewsListRecyclerView.setVisibility(View.VISIBLE);
        mWarningMsgTextView.setVisibility(View.GONE);
        mNewsListAdapter.replaceData(newsEntityList);
    }

    @Override
    public void showNoNews() {
        mWarningMsgTextView.setText(R.string.no_news);
        mWarningMsgTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewsDetail(NewsEntity newsEntity) {
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

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
        }
        mWarningMsgTextView.setVisibility(View.GONE);
        mNewsListRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showNoNetworkConnection() {
        mWarningMsgTextView.setText(R.string.no_internet_connection);
        mWarningMsgTextView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.GONE);
        mNewsListRecyclerView.setVisibility(View.GONE);
    }
}
