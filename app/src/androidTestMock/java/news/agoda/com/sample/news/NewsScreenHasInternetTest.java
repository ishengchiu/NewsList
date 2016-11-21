package news.agoda.com.sample.news;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.FakeNewsServiceApiImpl;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.news.MainActivity;
import news.agoda.com.sample.util.NetworkUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by chiu_mac on 2016/11/20.
 */

@RunWith(AndroidJUnit4.class)
public class NewsScreenHasInternetTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    //add fake news to fake api
                    NewsEntity.MediaEntity media = new NewsEntity.MediaEntity("http://www.test.com",
                            "Standard Thumbnail", 75, 75, "image", "photo", "photo", "eason");
                    List<NewsEntity.MediaEntity> mediaEntityList = new ArrayList<>();
                    mediaEntityList.add(media);

                    FakeNewsServiceApiImpl.addNews(new NewsEntity("title1", "summary1", "http://www.test.com", "test", "2015-08-18T04:00:00-5:00",
                            mediaEntityList));
                    //should have internet connection
                    NetworkUtil.INTERNET = true;
                }
            };

    @Test
    public void clickItem_openDetailView() {
        onView(withId(R.id.news_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.summary_content)).check(matches(isDisplayed()));
    }

    public void shouldShowNoInternetMsg() {
        NetworkUtil.INTERNET = false;
        onView(withText(R.string.no_internet_connection)).check(matches(isDisplayed()));
    }
}
