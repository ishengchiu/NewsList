package news.agoda.com.sample.data

import retrofit2.http.GET

interface NewsService {
    @GET("/v2/573c89f31100004a1daa8adb")
    suspend fun getNewsFeed(): NewsFeed
}