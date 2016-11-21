package news.agoda.com.sample;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Application class
 */
public class MyApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
