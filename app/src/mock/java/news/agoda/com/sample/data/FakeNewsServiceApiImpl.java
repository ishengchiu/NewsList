package news.agoda.com.sample.data;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class FakeNewsServiceApiImpl implements NewsServiceApi {
    private static List<NewsEntity> NEWS = new ArrayList<>();

    @Override
    public List<NewsEntity> getAllNews() {
        return NEWS;
    }

    @VisibleForTesting
    public static void addNews(NewsEntity news) {
        NEWS.add(news);
    }
}
