package workshop1024.com.xproject.news.model.newsdetail.remote

import android.os.Handler
import android.util.Log
import workshop1024.com.xproject.news.model.newsdetail.NewsDetail
import workshop1024.com.xproject.news.model.newsdetail.sources.NewsDetailDataSource
import java.util.*
import kotlin.collections.LinkedHashMap

class NewsDetailRemoteDataSource : NewsDetailDataSource {

    override fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: NewsDetailDataSource.LoadNewsDetailCallBack) {
        Log.i("XProject", "NewsDetailRemoteDataSource getNewsDetailByNewsId, newsId = $newsId")
        Handler().postDelayed({
            for (newsDetail in NEWSDETAILS_SERVICE_DATA.values) {
                if (newsDetail.mNewsId.equals(newsId)) {
                    loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun deleteNewsDetailsById(newsId: String) {
        Log.i("XProject", "NewsDetailRemoteDataSource deleteNewsDetailsById, ")
        Handler().postDelayed({
            NEWSDETAILS_SERVICE_DATA.clear()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun addNewsDetail(newsDetail: NewsDetail) {
        Log.i("XProject", "NewsDetailRemoteDataSource addNewsDetail, newsDetail = ${newsDetail.toString()}")
        Handler().postDelayed({
            NEWSDETAILS_SERVICE_DATA.put(newsDetail.mNewsId, newsDetail)
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun saveNewsById(newsId: String) {
        Log.i("XProject", "NewsDetailRemoteDataSource saveNewsById, newsId = $newsId")
        Handler().postDelayed({
            val newsDetail = NEWSDETAILS_SERVICE_DATA.get(newsId)
            if (newsDetail != null) {
                newsDetail.mIsSaved = true
            }
        }, SERVICE_LATENCY_IN_MILLIS)
    }


    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 1000

        private var NEWSDETAILS_SERVICE_DATA: MutableMap<String, NewsDetail>

        private lateinit var INSTANCE: NewsDetailRemoteDataSource

        init {
            NEWSDETAILS_SERVICE_DATA = LinkedHashMap(2)
            addNewsDetail("n001", "/image1", "news 001 mTitle", "The Tech", "20180406", "tom", "news 001 Contentnews 001 Content news 001 Content news 001 Content news 001 Content ", Arrays.asList("android", "ph", "rus", "american", "english", "american", "english", "e", "english"), false, false)
            addNewsDetail("n002", "/image1", "news 002 mTitle", "Engadget", "20180405", "mike", "news 002 Contentnews news 002 Contentnews news 002 Contentnews news 002 Contentnews ", Arrays.asList("ios", "phone"), false, false)

            addNewsDetail("n003", "/image1", "news 003 mTitle", "Lifehacker", "20180306", "lilei", "news 003 Contentnews news 003 Contentnews news 003 Contentnews news 003 Contentnews ", Arrays.asList("china"), false, false)
            addNewsDetail("n004", "/image1", "news 104 mTitle", "ReadWrite", "20180206", "hanmeimei", "news 004 Contentnews news 004 Contentnews news 004 Contentnews news 004 Contentnews ", Arrays.asList("usa"), false, false)

            addNewsDetail("n005", "/image1", "news 005 mTitle", "ReadWrite", "20180206", "sunny", "news 005 Contentnews news 005 Contentnews news 005 Contentnews news 005 Contentnews ", Arrays.asList("usa"), false, true)
            addNewsDetail("n006", "/image1", "news 006 mTitle", "ReadWrite", "20180206", "tom", "news 006 Contentnews news 006 Contentnews news 006 Contentnews news 006 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n007", "/image1", "news 007 mTitle", "ReadWrite", "20180206", "hanmeimei", "news 007 Contentnews news 007 Contentnews news 007 Contentnews news 007 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n008", "/image1", "news 008 mTitle", "ReadWrite", "20180206", "sunny", "news 008 Contentnews news 008 Contentnews news 008 Contentnews news 008 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n009", "/image1", "news 009 mTitle", "ReadWrite", "20180206", "lilei", "news 009 Contentnews news 009 Contentnews news 009 Contentnews news 009 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n010", "/image1", "news 010 mTitle", "ReadWrite", "20180206", "mike", "news 010 Contentnews news 010 Contentnews news 010 Contentnews news 010 Contentnews ", Arrays.asList("usa"), false, false)
        }

        private fun addNewsDetail(newId: String, bannerUrl: String, title: String, publisher: String, pubDate: String,
                                  author: String, content: String, tagList: List<String>, isReaded: Boolean, isSaved: Boolean) {
            val newsDetail = NewsDetail(newId, bannerUrl, title, publisher, author, pubDate, content, tagList, isReaded, isSaved)
            NEWSDETAILS_SERVICE_DATA[newsDetail.mNewsId] = newsDetail
        }

        val instance: NewsDetailRemoteDataSource
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = NewsDetailRemoteDataSource()
                }
                return INSTANCE
            }
    }
}