package news.agoda.com.sample.data;

import java.util.List;

import news.agoda.com.sample.util.JsonStringFetcher;
import news.agoda.com.sample.util.NewsFeedParser;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class NewsServiceApiImpl implements NewsServiceApi {
    private static final String SERVER = "http://www.mocky.io/v2/573c89f31100004a1daa8adb";

    @Override
    public List<NewsEntity> getAllNews() {
        try {
            String jsonString = JsonStringFetcher.fetch(SERVER);
            if (jsonString != null) {
                return NewsFeedParser.parse(jsonString).getNewsEntityList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
