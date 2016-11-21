package news.agoda.com.sample.news;

import java.util.List;

import news.agoda.com.sample.data.NewsEntity;

/**
 * Created by chiu_mac on 2016/11/20.
 */
public interface NewsContract {
    interface View {
        void showNews(List<NewsEntity> newsEntityList);

        void showNoNews();

        void showNoNetworkConnection();

        void showNewsDetail(NewsEntity newsEntity);

        void setLoadingIndicator(boolean active);
    }
    interface Presenter {
        void loadNews();
        void openNewsDetail(NewsEntity newsEntity);
    }
}
