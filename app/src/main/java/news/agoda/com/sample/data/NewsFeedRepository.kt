package news.agoda.com.sample.data

interface NewsFeedRepository {

    suspend fun getNewsFeed(): NewsFeed
}