package news.agoda.com.sample.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import news.agoda.com.sample.data.*
import news.agoda.com.sample.util.NewsEntityDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(NewsEntity::class.java, NewsEntityDeserializer())
        return builder.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService
            = retrofit.create(NewsService::class.java)

    @Provides
    @Singleton
    fun provideNewsFeedRepository(newsService: NewsService): NewsFeedRepository =
            NewsFeedRepositoryImpl(newsService)
}
