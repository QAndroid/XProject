package workshop1024.com.xproject.news.model.news.source

import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail

/**
 * 新闻数据源接口，定义了关于新闻数据的接口
 */
interface NewsDataSource {
    fun getNewsesByTypeAndKey(searchType: String, searchKey: String, loadCallback: LoadCallback)

    fun deleteAllNewsesByTypeAndKey(searchType: String, searchKey: String)

    fun addNews(news: News)

    fun markNewsesReadedByNewsId(searchType: String, searchKey: String, newsIdList: List<String>)

    fun getIsRequestRemoteBySearchTypeAndKey(searchType: String, searchKey: String): Boolean

    fun refreshBySearchTypeAndKey(searchType: String, searchKey: String, isRequestRemote: Boolean, isRequestCache: Boolean)

    interface LoadNewsesCallback : LoadCacheOrLocalNewsesCallback, LoadRemoteNewsesCallback

    interface LoadCacheOrLocalNewsesCallback : LoadCallback {
        fun onCachedOrLocalNewsLoaded(newsList: List<News>)
    }

    interface LoadRemoteNewsesCallback : LoadCallback {
        fun onRemoteNewsLoaded(newsList: List<News>)
    }

    interface LoadCallback {
        fun onDataNotAvaiable()
    }
}
