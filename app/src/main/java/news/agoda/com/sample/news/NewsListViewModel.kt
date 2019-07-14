package news.agoda.com.sample.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import news.agoda.com.sample.data.NewsFeedRepository
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
        private val newsFeedRepository: NewsFeedRepository
): ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val viewState: MutableLiveData<NewsListViewState> = MutableLiveData()

    init {
        uiScope.launch {
            viewState.value = NewsListViewState.Loading
            val newsFeed = withContext(Dispatchers.IO) {
                newsFeedRepository.getNewsFeed()
            }
            viewState.value = NewsListViewState.ListLoaded(newsFeed.newsEntityList)
        }
    }

    fun viewState(): LiveData<NewsListViewState> {
        return viewState
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
