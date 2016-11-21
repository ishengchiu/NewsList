package news.agoda.com.sample.news;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.data.NewsListLoader;
import news.agoda.com.sample.data.NewsRepository;
import news.agoda.com.sample.news.NewsContract;
import news.agoda.com.sample.news.NewsListPresenter;
import news.agoda.com.sample.util.NetworkUtil;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by chiu_mac on 2016/11/19.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkUtil.class, Log.class})
public class NewsListPresenterTest {

    private List<NewsEntity> NEWS;

    @Mock
    private NewsContract.View newsListView;

    @Mock
    private NewsListLoader newsListLoader;

    @Mock
    private LoaderManager loaderManager;

    @Captor
    private ArgumentCaptor<List> showNewsArgumentCaptor;

    @Mock
    private NewsRepository newsRepository;

    private NewsListPresenter newsListPresenter;

    @Before
    public void setupNewsListPresenter() {
        MockitoAnnotations.initMocks(this);

        mockStatic(Log.class);

        newsListPresenter = new NewsListPresenter(newsListView, newsRepository, newsListLoader, loaderManager);

        NewsEntity.MediaEntity media = new NewsEntity.MediaEntity("http://www.test.com",
                "Standard Thumbnail", 75, 75, "image", "photo", "photo", "eason");
        List<NewsEntity.MediaEntity> mediaEntityList = new ArrayList<>();
        mediaEntityList.add(media);

        NEWS = new ArrayList<>();
        NEWS.add(new NewsEntity("title1", "summary1", "http://www.test.com", "test", "2015-08-18T04:00:00-5:00",
                mediaEntityList));
    }

    @Test
    public void loadAllNewsAndLoadIntoView() {
        newsListPresenter.onLoadFinished(mock(Loader.class), NEWS);
        verify(newsListView).setLoadingIndicator(false);
        verify(newsListView).showNews(showNewsArgumentCaptor.capture());
        assertThat(showNewsArgumentCaptor.getValue().size(), is(1));
    }

    @Test
    public void clickOnNews_showDetailUI() {
        NewsEntity.MediaEntity media = new NewsEntity.MediaEntity("http://www.test.com",
                "Standard Thumbnail", 75, 75, "image", "photo", "photo", "eason");
        List<NewsEntity.MediaEntity> mediaEntityList = new ArrayList<>();
        mediaEntityList.add(media);
        NewsEntity newsEntity = new NewsEntity("title1", "summary1", "http://www.test.com", "test", "2015-08-18T04:00:00-5:00",
                mediaEntityList);
        assertNotNull(newsEntity);
        newsListPresenter.openNewsDetail(newsEntity);

        verify(newsListView).showNewsDetail(newsEntity);
    }

    @Test
    public void loadNewsShouldShowNoInternetForNoInternet() {
        mockStatic(NetworkUtil.class);
        when(NetworkUtil.hasInternetConnection(any(Context.class))).thenReturn(false);
        newsListPresenter.loadNews();
        verify(newsListView).showNoNetworkConnection();
    }

    @Test
    public void loadNewsShouldShowNoNewsWhenDataIsNull() {
        newsListPresenter.onLoadFinished(mock(Loader.class), null);
        verify(newsListView).setLoadingIndicator(false);
        verify(newsListView).showNoNews();
    }

    @Test
    public void loadNewsShouldShowNoNewsWhenNoNews() {
        List<NewsEntity> newsEntityList = new ArrayList<>();
        newsListPresenter.onLoadFinished(mock(Loader.class), newsEntityList);
        verify(newsListView).setLoadingIndicator(false);
        verify(newsListView).showNoNews();
    }
}
