package workshop1024.com.xproject.news.model.newsdetail.sources

import workshop1024.com.xproject.news.model.newsdetail.NewsDetail

interface NewsDetailDataSource {
    fun getNewsDetailByNewsId(newsId: String, loadNewsDetailCallBack: LoadNewsDetailCallBack)

    fun deleteAllNewsDetails()

    fun addNewsDetail(newsDetail: NewsDetail)

    fun saveNewsById(newsId: String)

    interface LoadNewsDetailCallBack {
        fun onNewsDetailLoaded(newsDetail: NewsDetail)

        fun onDataNotAvaiable()
    }
}