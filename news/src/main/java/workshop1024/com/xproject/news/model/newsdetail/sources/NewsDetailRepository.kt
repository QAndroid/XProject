package workshop1024.com.xproject.news.model.newsdetail.sources

import android.util.Log
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail
import java.util.LinkedHashMap

class NewsDetailRepository private constructor(private val mNewsDetailRemoteDataSource: NewsDetailDataSource,
                                               private val mNewsDetailLocalDataSource: NewsDetailDataSource) : NewsDetailDataSource {
    private lateinit var mCacheNewsDetailMaps: MutableMap<String, NewsDetail>
    private var mIsRequestRemote: Boolean = true
    private var mIsRequestCache: Boolean = true

    override fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: NewsDetailDataSource.LoadNewsDetailCallBack) {
        Log.i("XProject", "NewsDetailRepository getNewsDetailByNewsId, newsId = $newsId")
        if (mIsRequestCache) {
            if (this::mCacheNewsDetailMaps.isInitialized) {
                val newsDetail = getNewsDetailByNewsIdFromCache(newsId)
                if (newsDetail != null) {
                    loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
                }
            } else {
                getNewsDetailByNewsIdFromLocal(newsId, loadNewsDetailCallBack)
            }
        } else {
            getNewsDetailByNewsIdFromLocal(newsId, loadNewsDetailCallBack)
        }

        if (mIsRequestRemote) {
            getNewsDetailByNewsIdFromRemote(newsId, loadNewsDetailCallBack)
        }
    }

    private fun getNewsDetailByNewsIdFromRemote(newsId: String, loadNewsDetailCallBack: NewsDetailDataSource.LoadNewsDetailCallBack) {
        Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromRemote")
        mNewsDetailRemoteDataSource.getNewsDetailByNewsId(newsId, object : NewsDetailDataSource.LoadNewsDetailCallBack {
            override fun onNewsDetailLoaded(newsDetail: NewsDetail) {
                Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromRemote onNewsDetailLoaded")
                refreshCached(newsDetail)
                refreshLoacal(newsDetail)
                loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
            }

            override fun onDataNotAvaiable() {
                Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromRemote onDataNotAvaiable")
            }
        })
    }

    private fun refreshLoacal(newsDetail: NewsDetail) {
        Log.i("XProject", "NewsRepository  refreshLoacal, newsDetail = ${newsDetail.toString()}")
        mNewsDetailLocalDataSource.deleteAllNewsDetails()

        mNewsDetailLocalDataSource.addNewsDetail(newsDetail)

        mIsRequestCache = true
    }

    private fun getNewsDetailByNewsIdFromLocal(newsId: String, loadNewsDetailCallBack: NewsDetailDataSource.LoadNewsDetailCallBack) {
        Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromLocal")
        mNewsDetailLocalDataSource.getNewsDetailByNewsId(newsId, object : NewsDetailDataSource.LoadNewsDetailCallBack {
            override fun onNewsDetailLoaded(newsDetail: NewsDetail) {
                Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromLocal onNewsDetailLoaded")
                refreshCached(newsDetail)
                loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
            }

            override fun onDataNotAvaiable() {
                Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromLocal onDataNotAvaiable")
            }
        })
    }

    private fun refreshCached(newsDetail: NewsDetail) {
        Log.i("XProject", "NewsRepository refreshCached, newsDetail = ${newsDetail.toString()}")
        if (!this::mCacheNewsDetailMaps.isInitialized) {
            mCacheNewsDetailMaps = LinkedHashMap()
        }

        mCacheNewsDetailMaps.clear()

        mCacheNewsDetailMaps.put(newsDetail.mNewId, newsDetail)

        mIsRequestCache = true
    }

    private fun getNewsDetailByNewsIdFromCache(newsId: String): NewsDetail? {
        Log.i("XProject", "NewsRepository getNewsDetailByNewsIdFromCache")
        var resultNewsDetail: NewsDetail? = null
        for (newsDetail in mCacheNewsDetailMaps.values) {
            if (newsDetail.mNewId.equals(newsId)) {
                resultNewsDetail = newsDetail
            }
        }
        return resultNewsDetail
    }

    override fun deleteAllNewsDetails() {

    }

    override fun addNewsDetail(newsDetail: NewsDetail) {

    }

    override fun saveNewsById(newsId: String) {

    }

    companion object {
        private lateinit var INSTANCE: NewsDetailRepository

        fun getInstance(mNewsDetailRemoteDataSource: NewsDetailDataSource, mNewsDetailLocalDataSource: NewsDetailDataSource): NewsDetailRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = NewsDetailRepository(mNewsDetailRemoteDataSource, mNewsDetailLocalDataSource)
            }
            return INSTANCE
        }
    }
}