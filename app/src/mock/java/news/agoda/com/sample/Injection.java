package news.agoda.com.sample;

import news.agoda.com.sample.data.FakeNewsServiceApiImpl;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class Injection {

    public static NewsRepository provideNewsRepository() {
        return NewsRepositoryImpl.getInstance(new FakeNewsServiceApiImpl());
    }
}
