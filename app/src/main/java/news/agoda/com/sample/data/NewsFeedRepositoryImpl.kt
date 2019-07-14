package news.agoda.com.sample.data

class NewsFeedRepositoryImpl(private val newsService: NewsService): NewsFeedRepository {

    override suspend fun getNewsFeed(): NewsFeed =
            newsService.getNewsFeed()
}
