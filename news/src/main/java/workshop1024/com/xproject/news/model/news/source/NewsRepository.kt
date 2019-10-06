package workshop1024.com.xproject.news.model.news.source

import android.util.Log

import java.util.LinkedHashMap

import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.news.local.NewsLocalDataSource
import workshop1024.com.xproject.news.model.news.remote.NewsRemoteDataSource

/**
 * 新闻远程数据源
 */
class NewsRepository private constructor(private val mNewsRemoteDataSource: NewsRemoteDataSource,
                                         private val mNewsLocalDataSource: NewsLocalDataSource) : NewsDataSource {

    private lateinit var mCacheNewsMaps: MutableMap<String, News>

    private val mIsRequestCacheMaps = mutableMapOf<String, Boolean>()
    private val mIsRequestRemoteMaps = mutableMapOf<String, Boolean>()

    override fun getNewsesByTypeAndKey(searchType: String, searchKey: String, loadCallback: NewsDataSource.LoadCallback) {
        Log.i("XProject", "NewsRepository getNewsesByTypeAndKey")
        //如果该typeId还没有请求过数据，则初始化请求控制变量
        if (mIsRequestCacheMaps["$searchType//_$searchKey"] == null && mIsRequestRemoteMaps["$searchType//_$searchKey"] == null) {
            mIsRequestCacheMaps.put("$searchType//_$searchKey", true)
            mIsRequestRemoteMaps.put("$searchType//_$searchKey", true)
        }

        Log.i("XProject", "NewsRepository getNewsesByTypeAndKey, mIsRequestRemote = ${mIsRequestRemoteMaps.get("$searchType//_$searchKey")}, mIsRequestCache = ${mIsRequestCacheMaps.get("$searchType//_$searchKey")}")
        if (mIsRequestCacheMaps["$searchType//_$searchKey"]!!) {
            if (this::mCacheNewsMaps.isInitialized) {
                val newsList = getNewsesByTypeAndKeyFromCache(searchType, searchKey)
                if (!newsList.isEmpty()) {
                    (loadCallback as NewsDataSource.LoadCacheOrLocalNewsesCallback).onCachedOrLocalNewsLoaded(newsList)
                } else {
                    getNewsesByTypeAndKeyFromLocal(searchType, searchKey, loadCallback)
                }
            } else {
                getNewsesByTypeAndKeyFromLocal(searchType, searchKey, loadCallback)
            }
        }

        if (mIsRequestRemoteMaps["$searchType//_$searchKey"]!!) {
            getNewsesByTypeAndIdFromRemote(searchType, searchKey, loadCallback)
        }
    }

    private fun getNewsesByTypeAndKeyFromLocal(searchType: String, searchKey: String, loadCallback: NewsDataSource.LoadCallback) {
        Log.i("XProject", "NewsRepository getNewsesByTypeAndKeyFromLocal, searchType = $searchType searchKey = $searchKey")
        mNewsLocalDataSource.getNewsesByTypeAndKey(searchType, searchKey, object : NewsDataSource.LoadCacheOrLocalNewsesCallback {
            override fun onCachedOrLocalNewsLoaded(newsList: List<News>) {
                Log.i("XProject", "NewsRepository getNewsesByTypeAndKeyFromLocal onCachedOrLocalNewsLoaded, newsList = ${newsList.toString()}")
                refreshCachedBySearchTypeAndKey(searchType, searchKey, newsList)
                (loadCallback as NewsDataSource.LoadCacheOrLocalNewsesCallback).onCachedOrLocalNewsLoaded(newsList)
            }

            override fun onDataNotAvaiable() {
                Log.i("XProject", "NewsRepository getNewsesByTypeAndKeyFromLocal onDataNotAvaiable")
            }
        })
    }

    private fun refreshCachedBySearchTypeAndKey(searchType: String, searchKey: String, newsList: List<News>) {
        Log.i("XProject", "NewsRepository refreshCachedBySearchTypeAndKey, searchKey = $searchKey, newsList = ${newsList.toString()}")
        if (!this::mCacheNewsMaps.isInitialized) {
            mCacheNewsMaps = LinkedHashMap()
        }

        val iterator = mCacheNewsMaps.values.iterator()
        while (iterator.hasNext()) {
            val news = iterator.next()
            if (news.mSearchType.equals(searchType) && news.mSearchKey.equals(searchKey)) {
                iterator.remove()
            }
        }

        for (news in newsList) {
            mCacheNewsMaps.put(news.mSaveId, news)
        }

        mIsRequestCacheMaps["$searchType//_$searchKey"] = true
    }


    private fun getNewsesByTypeAndIdFromRemote(searchType: String, searchKey: String, loadCallback: NewsDataSource.LoadCallback) {
        Log.i("XProject", "NewsRepository getNewsesByTypeAndIdFromRemote, searchType = $searchType searchKey = $searchKey")
        mNewsRemoteDataSource.getNewsesByTypeAndKey(searchType, searchKey, object : NewsDataSource.LoadRemoteNewsesCallback {
            override fun onRemoteNewsLoaded(newsList: List<News>) {
                Log.i("XProject", "NewsRepository getNewsesByTypeAndIdFromRemote onRemoteNewsLoaded, newsList = ${newsList.toString()}")
                refreshCachedBySearchTypeAndKey(searchType, searchKey, newsList)
                refreshLocalBySearchTypeAndKey(searchType, searchKey, newsList)
                (loadCallback as NewsDataSource.LoadRemoteNewsesCallback).onRemoteNewsLoaded(newsList)

                mIsRequestRemoteMaps["$searchType//_$searchKey"] = false
            }

            override fun onDataNotAvaiable() {
                Log.i("XProject", "NewsRepository getNewsesByTypeAndIdFromRemote onDataNotAvaiable")
            }
        })
    }

    private fun refreshLocalBySearchTypeAndKey(searchType: String, searchKey: String, newsList: List<News>) {
        Log.i("XProject", "NewsRepository refreshLocalBySearchTypeAndKey, searchKey = $searchKey,newsList = ${newsList.toString()}")
        mNewsLocalDataSource.deleteAllNewsesByTypeAndKey(searchType, searchKey)

        for (news in newsList) {
            mNewsLocalDataSource.addNews(news)
        }

        mIsRequestCacheMaps["$searchType//_$searchKey"] = true
    }

    private fun getNewsesByTypeAndKeyFromCache(searchType: String, searchKey: String): List<News> {
        Log.i("XProject", "NewsRepository getNewsesByTypeAndKeyFromCache, searchType = $searchType searchKey = $searchKey")
        val resultNewsList = mutableListOf<News>()
        for (news in mCacheNewsMaps.values) {
            if (news.mSearchType.equals(searchType) && news.mSearchKey.equals(searchKey)) {
                if ((News.SUBSCRIBE_TYPE.equals(searchType) && news.mSubscribeId.equals(searchKey))
                        || (News.TAG_TYPE.equals(searchType) && news.mTagIdList.contains(searchKey))
                        || (News.FILTER_TYPE.equals(searchType) && news.mFilterIdList.contains(searchKey))
                        || (News.SAVED_TYPE.equals(searchType) && news.mTitle.contains(searchKey))) {
                    resultNewsList.add(news)
                }
            }
        }
        return resultNewsList
    }

    override fun deleteAllNewsesByTypeAndKey(searchType: String, searchKey: String) {
        //FIXME 没有外部调用，不实现
    }

    override fun markNewsesReadedByNewsId(searchType: String, searchKey: String, newsIdList: List<String>) {
        Log.i("XProject", "NewsRepository markNewsesReadedByNewsId, searchType = $searchType searchKey = $searchKey")
        mNewsRemoteDataSource.markNewsesReadedByNewsId(searchType, searchKey, newsIdList)
        mNewsLocalDataSource.markNewsesReadedByNewsId(searchType, searchKey, newsIdList)

        if (!this::mCacheNewsMaps.isInitialized) {
            mCacheNewsMaps = LinkedHashMap()
        }

        for (newsId in newsIdList) {
            for (news in mCacheNewsMaps.values) {
                if (news.mNewsId.equals(newsId) && news.mSearchType.equals(searchType) && news.mSearchKey.equals(searchKey)) {
                    news.mIsReaded = true
                }
            }
        }
    }

    override fun addNews(news: News) {
        //FIXME 没有外部调用，不实现
    }

    override fun getIsRequestRemoteBySearchTypeAndKey(searchType: String, searchKey: String): Boolean {
        return mIsRequestRemoteMaps["$searchType//_$searchKey"]!!
    }

    override fun refreshBySearchTypeAndKey(searchType: String, searchKey: String, isRequestRemote: Boolean, isRequestCache: Boolean) {
        mIsRequestRemoteMaps["$searchType//_$searchKey"] = isRequestRemote
        mIsRequestCacheMaps["$searchType//_$searchKey"] = isRequestCache
    }

    companion object {
        private lateinit var INSTANCE: NewsRepository


        fun getInstance(newsRemoteDataSource: NewsRemoteDataSource, newsLocalDataSource: NewsLocalDataSource): NewsRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = NewsRepository(newsRemoteDataSource, newsLocalDataSource)
            }
            return INSTANCE
        }
    }
}

