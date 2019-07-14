package news.agoda.com.sample;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import news.agoda.com.sample.di.AppComponent;
import news.agoda.com.sample.di.DaggerAppComponent;

/**
 * Application class
 */
public class MyApplication extends DaggerApplication {

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

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        final AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        return appComponent;
    }
}
