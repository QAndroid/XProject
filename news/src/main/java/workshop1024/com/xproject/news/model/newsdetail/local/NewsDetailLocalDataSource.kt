package workshop1024.com.xproject.news.model.newsdetail.local

import android.util.Log
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.news.model.news.local.NewsDatabase
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail
import workshop1024.com.xproject.news.model.newsdetail.sources.NewsDetailDataSource

class NewsDetailLocalDataSource private constructor(private val mNewsDetailDao: NewsDetailDao, private val mExecutorUtils: ExecutorUtils)
    : NewsDetailDataSource {

    override fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: NewsDetailDataSource.LoadNewsDetailCallBack) {
        Log.i("XProject", "NewsDetailLocalDataSource getNewsDetailByNewsId, newsId = $newsId")
        val getNewsDetailByNewsIdRunnable = Runnable {
            val newsDetail = mNewsDetailDao.getNewsDetailByNewsId(newsId)
            loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
        }

        mExecutorUtils.mDiskIOExecutor.execute(getNewsDetailByNewsIdRunnable)
    }

    override fun deleteAllNewsDetails() {
        Log.i("XProject", "NewsDetailLocalDataSource deleteAllNewsDetails")
        val deleteAllNewsDetailsRunnable = Runnable {
            mNewsDetailDao.deleteAllNewsDetails()
        }

        mExecutorUtils.mDiskIOExecutor.execute(deleteAllNewsDetailsRunnable)
    }

    override fun addNewsDetail(newsDetail: NewsDetail) {
        Log.i("XProject", "NewsDetailLocalDataSource addNewsDetail, newsDetail = ${newsDetail.toString()}")
        val addNewsDetailRunnable = Runnable {
            mNewsDetailDao.addNewsDetail(newsDetail)
        }

        mExecutorUtils.mDiskIOExecutor.execute(addNewsDetailRunnable)
    }

    override fun saveNewsById(newsId: String) {

    }

    companion object {
        private lateinit var INSTANCE: NewsDetailLocalDataSource

        fun getInstance(newsDetailDao: NewsDetailDao, executorUtils: ExecutorUtils): NewsDetailLocalDataSource {
            synchronized(NewsDetailLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = NewsDetailLocalDataSource(newsDetailDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}