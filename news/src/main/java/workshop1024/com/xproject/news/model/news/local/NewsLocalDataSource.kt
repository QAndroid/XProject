package workshop1024.com.xproject.news.model.news.local

import android.util.Log
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.news.source.NewsDataSource

class NewsLocalDataSource private constructor(private val mNewsDao: NewsDao, private val mExecutorUtils: ExecutorUtils) : NewsDataSource {

    override fun getNewsesByTypeAndKey(searchType: String, searchKey: String, loadCallback: NewsDataSource.LoadCallback) {
        Log.i("XProject", "NewsLocalDataSource getNewsesByTypeAndKey, searchType = $searchType searchKey = $searchKey")
        val getNewsesByTypeAndKeyRunnable = Runnable {
            val newsList = mNewsDao.getNewsesByTypeAndKey(searchType, searchKey)
            mExecutorUtils.mMainThreadExecutor.execute(Runnable {
                if (!newsList.isEmpty()) {
                    (loadCallback as NewsDataSource.LoadCacheOrLocalNewsesCallback).onCachedOrLocalNewsLoaded(newsList)
                } else {
                    loadCallback.onDataNotAvaiable()
                }
            })
        }

        mExecutorUtils.mDiskIOExecutor.execute(getNewsesByTypeAndKeyRunnable)
    }

    override fun deleteAllNewsesByTypeAndKey(searchType: String, searchKey: String) {
        Log.i("XProject", "NewsLocalDataSource deleteAllNewsesByTypeAndKey, searchType = $searchType searchKey = $searchKey")
        val deleteAllNewsesByTypeAndKeyRunnable = Runnable {
            mNewsDao.deleteAllNewsesByTypeAndKey(searchType, searchKey)
        }

        mExecutorUtils.mDiskIOExecutor.execute(deleteAllNewsesByTypeAndKeyRunnable)
    }

    override fun markNewsesReadedByNewsId(searchType: String, searchKey: String, newsIdList: List<String>) {
        Log.i("XProject", "NewsLocalDataSource markNewsesReadedByNewsId, searchType = $searchType searchKey = $searchKey newsIdList = ${newsIdList.toString()}")
        val markNewsesReadedByNewsIdRunnable = Runnable {
            for (newsId in newsIdList) {
                mNewsDao.markNewsReadedByNewsId(newsId)
            }
        }

        mExecutorUtils.mDiskIOExecutor.execute(markNewsesReadedByNewsIdRunnable)
    }

    override fun addNews(news: News) {
        Log.i("XProject", "NewsLocalDataSource addNews, news = ${news.toString()}")
        val addNewsRunnable = Runnable {
            mNewsDao.addNews(news)
        }

        mExecutorUtils.mDiskIOExecutor.execute(addNewsRunnable)
    }

    override fun getIsRequestRemoteBySearchTypeAndKey(searchType: String, searchKey: String): Boolean {
        return false
    }

    override fun refreshBySearchTypeAndKey(searchType: String, searchKey: String, isRequestRemote: Boolean, isRequestCache: Boolean) {

    }


    companion object {
        private lateinit var INSTANCE: NewsLocalDataSource

        fun getInstance(newsDao: NewsDao, executorUtils: ExecutorUtils): NewsLocalDataSource {
            synchronized(NewsLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = NewsLocalDataSource(newsDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}