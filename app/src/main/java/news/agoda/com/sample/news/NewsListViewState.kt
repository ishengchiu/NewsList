package news.agoda.com.sample.news

import news.agoda.com.sample.data.NewsEntity

sealed class NewsListViewState {
    object Loading: NewsListViewState()
    object NoInternetConnection: NewsListViewState()
    data class ListLoaded(val news: List<NewsEntity>): NewsListViewState()
}
