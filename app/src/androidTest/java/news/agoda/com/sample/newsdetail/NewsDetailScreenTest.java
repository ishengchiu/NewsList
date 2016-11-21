package news.agoda.com.sample.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import news.agoda.com.sample.R;
import news.agoda.com.sample.news.MainActivity;
import news.agoda.com.sample.newsdetail.DetailViewActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by chiu_mac on 2016/11/21.
 */

public class NewsDetailScreenTest {

    private static final String TITLE = "Title1";
    private static final String SUMMARY = "Summary1";
    private static final String STORY_URL = "http://www.test.com";
    private static final String IMG_URL = "http://www.test.com";

    @Rule
    public ActivityTestRule<DetailViewActivity> mNewsDetailActivityTestRule =
            new ActivityTestRule<DetailViewActivity>(DetailViewActivity.class);

    @Before
    public void intentWithStubbedNoteId() {
        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(DetailViewActivity.EXTRA_TITLE, TITLE);
        startIntent.putExtra(DetailViewActivity.EXTRA_SUMMARY, SUMMARY);
        startIntent.putExtra(DetailViewActivity.EXTRA_IMAGE_URL, IMG_URL);
        startIntent.putExtra(DetailViewActivity.EXTRA_STORY_URL, STORY_URL);
        mNewsDetailActivityTestRule.launchActivity(startIntent);

        registerIdlingResource();
    }

    @Test
    public void newsDetails_DisplayedInUi() throws Exception {
        // Check that the note title, description and image are displayed
        onView(withId(R.id.title)).check(matches(withText(TITLE)));
        onView(withId(R.id.summary_content)).check(matches(withText(SUMMARY)));
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                mNewsDetailActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        Espresso.registerIdlingResources(
                mNewsDetailActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
