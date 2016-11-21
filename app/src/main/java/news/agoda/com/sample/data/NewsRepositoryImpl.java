package news.agoda.com.sample.data;

import java.util.List;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class NewsRepositoryImpl implements NewsRepository {
    private static NewsRepository INSTANCE = null;

    private final NewsServiceApi mNewsServiceApi;

    public synchronized static NewsRepository getInstance(NewsServiceApi newsServiceApi) {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepositoryImpl(newsServiceApi);
        }
        return INSTANCE;
    }

    public NewsRepositoryImpl(NewsServiceApi newsServiceApi) {
        mNewsServiceApi = newsServiceApi;
    }

    @Override
    public List<NewsEntity> getNews() {
        return mNewsServiceApi.getAllNews();
    }
}
