package news.agoda.com.sample;

import news.agoda.com.sample.data.FakeNewsServiceApiImpl;
import news.agoda.com.sample.data.NewsRepository;
import news.agoda.com.sample.data.NewsRepositoryImpl;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class Injection {

    public static NewsRepository provideNewsRepository() {
        return NewsRepositoryImpl.getInstance(new FakeNewsServiceApiImpl());
    }
}
