package workshop1024.com.xproject.news.model.news.remote

import android.os.Handler
import android.util.Log
import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.news.source.NewsDataSource
import kotlin.collections.LinkedHashMap

class NewsRemoteDataSource : NewsDataSource {
    override fun getNewsesByTypeAndKey(searchType: String, searchKey: String, loadCallback: NewsDataSource.LoadCallback) {
        Log.i("XProject", "NewsRemoteDataSource getNewsesByTypeAndKey, searchType = $searchType searchKey = $searchKey")
        Handler().postDelayed({
            val resultNews = mutableListOf<News>()
            for (news in NEWSES_SERVICE_DATA.values) {
                if ((News.SUBSCRIBE_TYPE.equals(searchType) && news.mSubscribeId.equals(searchKey))
                        || (News.TAG_TYPE.equals(searchType) && news.mTagIdList.contains(searchKey))
                        || (News.FILTER_TYPE.equals(searchType) && news.mFilterIdList.contains(searchKey))
                        || (News.SEARCH_TYPE.equals(searchType) && news.mTitle.contains(searchKey))) {
                    news.mSaveId = "$searchType//_$searchKey//_${news.mNewsId}"
                    news.mSearchType = searchType
                    news.mSearchKey = searchKey
                    resultNews.add(news)
                }
            }
            if (!resultNews.isEmpty()) {
                (loadCallback as NewsDataSource.LoadRemoteNewsesCallback).onRemoteNewsLoaded(resultNews)
            } else {
                (loadCallback as NewsDataSource.LoadRemoteNewsesCallback).onDataNotAvaiable()
            }

        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun deleteAllNewsesByTypeAndKey(searchType: String, searchKey: String) {
        //远程不调用，不实现
    }

    override fun addNews(news: News) {
        //远程不调用，不实现
    }

    override fun markNewsesReadedByNewsId(searchType: String, searchKey: String, newsIdList: List<String>) {
        Log.i("XProject", "NewsRemoteDataSource markNewsesReadedByNewsId, searchType = $searchType searchKey = $searchKey")
        for (newsId in newsIdList) {
            for (news in NEWSES_SERVICE_DATA.values) {
                if (newsId.equals(news.mNewsId)) {
                    news.mIsReaded = true
                }
            }
        }
    }

    override fun getIsRequestRemoteBySearchTypeAndKey(searchType: String, searchKey: String): Boolean {
        return false
    }

    override fun refreshBySearchTypeAndKey(searchType: String, searchKey: String, isRequestRemote: Boolean, isRequestCache: Boolean) {

    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 1000

        private var NEWSES_SERVICE_DATA: MutableMap<String, News>

        private lateinit var INSTANCE: NewsRemoteDataSource

        init {
            NEWSES_SERVICE_DATA = LinkedHashMap(2)
            addNews("n001", "p001", listOf("f001", "f002"), listOf("t001"), "/image1", "The Tech", "tom", "20180406", false, false)
            addNews("n002", "p001", listOf("f002", "p003"), listOf("t002"), "/image1", "The Tech2", "mike", "20180405", false, false)
            addNews("n003", "p001", listOf("f003", "f004"), listOf("t001", "t002"), "/image1", "The Tech3", "lilei", "20180306", false, false)
            addNews("n004", "p001", listOf("f101", "f001"), listOf("t001", "t002"), "/image1", "The Tech4", "hanmeimei", "20180206", false, false)
            addNews("n005", "p001", listOf("f002", "f003"), listOf("t001", "t002"), "/image1", "The Tech5", "sunny", "20180206", false, true)
            addNews("n006", "p002", listOf("f003", "f004"), listOf("t001", "t002"), "/image1", "Engadget1", "tom", "20180206", false, false)
            addNews("n007", "p002", listOf("f004", "p101"), listOf("t001", "t002"), "/image1", "Engadget2", "hanmeimei", "20180206", false, false)
            addNews("n008", "p002", listOf("p001", "p002"), listOf("t001", "t002"), "/image1", "Engadget3", "sunny", "20180206", false, false)
            addNews("n009", "p003", listOf("p002", "p003"), listOf("t001", "t002"), "/image1", "Lifehacker1", "lilei", "20180206", false, false)
            addNews("n010", "p003", listOf("p004", "p005"), listOf("t001", "t002"), "/image1", "Lifehacker2", "mike", "20180206", false, false)
        }

        private fun addNews(newId: String, subscribeId: String, filterIdList: List<String>, tagIdList: List<String>, bannerUrl: String,
                            title: String, publisher: String, pubDate: String, isIsReaded: Boolean, isIsSaved: Boolean) {
            val news = News("null", newId, subscribeId, filterIdList, tagIdList, bannerUrl, title, publisher, pubDate, isIsReaded, isIsSaved, "null", "null")
            NEWSES_SERVICE_DATA[news.mNewsId] = news
        }

        val instance: NewsRemoteDataSource
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = NewsRemoteDataSource()
                }
                return INSTANCE
            }
    }
}