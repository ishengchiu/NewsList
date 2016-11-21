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
public class NewsScreenNoInternetTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    //no internet connection
                    NetworkUtil.INTERNET = false;
                }
            };

    @Test
    public void shouldShowNoInternetMsg() {
        onView(withText(R.string.no_internet_connection)).check(matches(isDisplayed()));
    }
}
