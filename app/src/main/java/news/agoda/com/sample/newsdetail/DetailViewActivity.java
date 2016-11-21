package news.agoda.com.sample.newsdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import news.agoda.com.sample.R;
import news.agoda.com.sample.util.EspressoIdlingResource;

/**
 * News detail view
 */
public class DetailViewActivity extends AppCompatActivity {
    private static final String TAG = DetailViewActivity.class.getSimpleName();

    public static final String EXTRA_STORY_URL = "story_url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SUMMARY = "summary";
    public static final String EXTRA_IMAGE_URL = "image_url";

    private String mStoryURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        mStoryURL = getIntent().getStringExtra(EXTRA_STORY_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String summary = getIntent().getStringExtra(EXTRA_SUMMARY);
        String imageURL = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        TextView titleView = (TextView) findViewById(R.id.title);
        DraweeView imageView = (DraweeView) findViewById(R.id.news_image);
        TextView summaryView = (TextView) findViewById(R.id.summary_content);

        titleView.setText(title);
        summaryView.setText(summary);

        if (imageURL != null) {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                    .setOldController(imageView.getController()).build();
            imageView.setController(draweeController);
        }
    }

    public void onFullStoryClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mStoryURL));
        startActivity(intent);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
