package news.agoda.com.sample.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import news.agoda.com.sample.MyApplication
import news.agoda.com.sample.data.di.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    DataModule::class,
    ActivityBuilder::class,
    ViewModelsModule::class
])
interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: MyApplication): Builder
        fun build(): AppComponent
    }
}