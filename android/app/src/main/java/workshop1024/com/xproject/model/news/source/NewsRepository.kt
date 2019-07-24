package workshop1024.com.xproject.model.news.source

import android.os.Handler

import java.util.ArrayList
import java.util.Arrays
import java.util.LinkedHashMap

import workshop1024.com.xproject.model.news.News
import workshop1024.com.xproject.model.news.NewsDetail

/**
 * 新闻远程数据源
 */
class NewsRepository private constructor() : NewsDataSource {

    override fun getNewsListBySubscribe(publishId: String, loadNewsListCallback: NewsDataSource.LoadNewsListCallback) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSES_SERVICE_DATA!!.values)
            loadNewsListCallback.onNewsLoaded(newsList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getNewsListByTag(tagId: String, loadNewsListCallback: NewsDataSource.LoadNewsListCallback) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSES_SERVICE_DATA!!.values)
            loadNewsListCallback.onNewsLoaded(newsList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getNewsListByFilter(filterId: String, loadNewsListCallback: NewsDataSource.LoadNewsListCallback) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSES_SERVICE_DATA!!.values)
            loadNewsListCallback.onNewsLoaded(newsList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getNewsListBySearch(searchString: String, loadNewsListCallback: NewsDataSource.LoadNewsListCallback) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSES_SERVICE_DATA!!.values)
            loadNewsListCallback.onNewsLoaded(newsList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun markNewsesReadedByNewsId(newsIdList: List<String>) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSES_SERVICE_DATA!!.values)
            for (news in newsList) {
                if (newsIdList.contains(news.newId)) {
                    news.isIsReaded = true
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: NewsDataSource.LoadNewsDetailCallBack) {
        val handler = Handler()
        handler.postDelayed({
            val newsDetailList = ArrayList(NEWSDETAILS_SERVICE_DATA!!.values)
            for (newsDetail in newsDetailList) {
                if (newsId == newsDetail.newId) {
                    loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail)
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getSavedNewsList(loadNewsListCallback: NewsDataSource.LoadNewsListCallback) {
        val handler = Handler()
        handler.postDelayed({
            val resultNewsList = ArrayList<News>()
            val newsList = ArrayList(NEWSDETAILS_SERVICE_DATA!!.values)
            for (news in newsList) {
                if (news.isIsSaved) {
                    resultNewsList.add(news)
                }
            }
            loadNewsListCallback.onNewsLoaded(resultNewsList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun saveNewsById(newsId: String) {
        val handler = Handler()
        handler.postDelayed({
            val newsList = ArrayList(NEWSDETAILS_SERVICE_DATA!!.values)
            for (news in newsList) {
                if (news.newId == newsId) {
                    news.isIsSaved = true
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1000

        private var NEWSES_SERVICE_DATA: MutableMap<String, News>? = null
        private var NEWSDETAILS_SERVICE_DATA: MutableMap<String, NewsDetail>? = null

        private var INSTANCE: NewsRepository? = null

        init {
            NEWSES_SERVICE_DATA = LinkedHashMap(2)
            addNews("n001", "/image1", "news 001 title", "tom", "20180406", false, false)
            addNews("n002", "/image1", "news 002 title", "mike", "20180405", false, false)

            addNews("n003", "/image1", "news 003 title", "lilei", "20180306", false, false)
            addNews("n004", "/image1", "news 004 title", "hanmeimei", "20180206", false, false)
            addNews("n005", "/image1", "news 005 title", "sunny", "20170406", false, false)

            addNews("n006", "/image1", "news 006 title", "tom", "20160406", false, true)
            addNews("n007", "/image1", "news 007 title", "hanmeimei", "20110806", false, false)

            addNews("n008", "/image1", "news 008 title", "sunny", "20120407", false, false)

            addNews("n009", "/image1", "news 009 title", "lilei", "20161006", false, false)
            addNews("n010", "/image1", "news 010 title", "mike", "20181206", false, false)

            NEWSDETAILS_SERVICE_DATA = LinkedHashMap(2)
            addNewsDetail("n001", "/image1", "news 001 title", "The Tech", "20180406", "tom", "news 001 Contentnews 001 Content news 001 Content news 001 Content news 001 Content ", Arrays.asList("android", "ph", "rus", "american", "english", "american", "english", "e", "english"), false, false)
            addNewsDetail("n002", "/image1", "news 002 title", "Engadget", "20180405", "mike", "news 002 Contentnews news 002 Contentnews news 002 Contentnews news 002 Contentnews ", Arrays.asList("ios", "phone"), false, false)

            addNewsDetail("n003", "/image1", "news 003 title", "Lifehacker", "20180306", "lilei", "news 003 Contentnews news 003 Contentnews news 003 Contentnews news 003 Contentnews ", Arrays.asList("china"), false, false)
            addNewsDetail("n004", "/image1", "news 104 title", "ReadWrite", "20180206", "hanmeimei", "news 004 Contentnews news 004 Contentnews news 004 Contentnews news 004 Contentnews ", Arrays.asList("usa"), false, false)

            addNewsDetail("n005", "/image1", "news 005 title", "ReadWrite", "20180206", "sunny", "news 005 Contentnews news 005 Contentnews news 005 Contentnews news 005 Contentnews ", Arrays.asList("usa"), false, true)
            addNewsDetail("n006", "/image1", "news 006 title", "ReadWrite", "20180206", "tom", "news 006 Contentnews news 006 Contentnews news 006 Contentnews news 006 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n007", "/image1", "news 007 title", "ReadWrite", "20180206", "hanmeimei", "news 007 Contentnews news 007 Contentnews news 007 Contentnews news 007 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n008", "/image1", "news 008 title", "ReadWrite", "20180206", "sunny", "news 008 Contentnews news 008 Contentnews news 008 Contentnews news 008 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n009", "/image1", "news 009 title", "ReadWrite", "20180206", "lilei", "news 009 Contentnews news 009 Contentnews news 009 Contentnews news 009 Contentnews ", Arrays.asList("usa"), false, false)
            addNewsDetail("n010", "/image1", "news 010 title", "ReadWrite", "20180206", "mike", "news 010 Contentnews news 010 Contentnews news 010 Contentnews news 010 Contentnews ", Arrays.asList("usa"), false, false)
        }

        private fun addNews(newId: String, bannerUrl: String, title: String, author: String, pubDate: String, isReaded: Boolean, isSaved: Boolean) {
            val news = News(newId, bannerUrl, title, author, pubDate, isReaded, isSaved)
            NEWSES_SERVICE_DATA!![news.newId!!] = news
        }

        private fun addNewsDetail(newId: String, bannerUrl: String, title: String, publisher: String, pubDate: String,
                                  author: String, content: String, tagList: List<String>, isReaded: Boolean, isSaved: Boolean) {
            val newsDetail = NewsDetail(newId, bannerUrl, title, publisher, author, pubDate, content, tagList, isReaded, isSaved)
            NEWSDETAILS_SERVICE_DATA!![newsDetail.newId!!] = newsDetail
        }

        val instance: NewsRepository
            get() {
                if (INSTANCE == null) {
                    INSTANCE = NewsRepository()
                }
                return INSTANCE!!
            }
    }
}
