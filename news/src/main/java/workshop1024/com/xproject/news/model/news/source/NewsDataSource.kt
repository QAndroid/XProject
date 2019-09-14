package workshop1024.com.xproject.news.model.news.source

import workshop1024.com.xproject.news.model.news.News
import workshop1024.com.xproject.news.model.news.NewsDetail

/**
 * 新闻数据源接口，定义了关于新闻数据的接口
 */
interface NewsDataSource {

    fun getNewsListBySubscribe(publishId: String, loadNewsListCallback: LoadNewsListCallback)

    fun getNewsListByTag(tagId: String, loadNewsListCallback: LoadNewsListCallback)

    fun getNewsListByFilter(filterId: String, loadNewsListCallback: LoadNewsListCallback)

    fun getNewsListBySearch(searchString: String, loadNewsListCallback: LoadNewsListCallback)

    fun markNewsesReadedByNewsId(newsIdList: List<String>, markNewsesReadedCallback: MarkNewsesReadedCallback)

    fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: LoadNewsDetailCallBack)

    fun getSavedNewsList(loadNewsListCallback: LoadNewsListCallback)

    fun saveNewsById(newsId: String)

    interface LoadNewsListCallback {
        fun onNewsLoaded(newsList: List<News>)

        fun onDataNotAvaiable()
    }

    interface LoadNewsDetailCallBack {
        fun onNewsDetailLoaded(newsDetail: NewsDetail)

        fun onDataNotAvaiable()
    }

    interface MarkNewsesReadedCallback {
        fun onMarkNewsesReadedSuccess()

        fun onMarkNewsesReadedFaild()
    }
}
