package news.agoda.com.sample.helper;

import java.util.List;

import news.agoda.com.sample.model.NewsEntity;

/**
 * This class is used for loading News Feed
 */

public class NewsFeedLoader {
    private static final String SERVER = "http://www.mocky.io/v2/573c89f31100004a1daa8adb";

    public static List<NewsEntity> load() {
        try {
            String newsFeedJson = JsonStringFetcher.fetchUrl(SERVER);
            return NewsFeedParser.gsonParse(newsFeedJson).getNewsEntityList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
