package news.agoda.com.sample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import news.agoda.com.sample.news.MainActivity
import news.agoda.com.sample.news.di.MainActivityModule

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}
