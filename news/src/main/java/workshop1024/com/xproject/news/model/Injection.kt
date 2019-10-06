package workshop1024.com.xproject.news.model

import android.content.Context
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.news.model.news.local.NewsDatabase
import workshop1024.com.xproject.news.model.news.local.NewsLocalDataSource
import workshop1024.com.xproject.news.model.news.remote.NewsRemoteDataSource
import workshop1024.com.xproject.news.model.news.source.NewsDataSource
import workshop1024.com.xproject.news.model.news.source.NewsRepository
import workshop1024.com.xproject.news.model.newsdetail.local.NewsDetailDatabase
import workshop1024.com.xproject.news.model.newsdetail.local.NewsDetailLocalDataSource
import workshop1024.com.xproject.news.model.newsdetail.remote.NewsDetailRemoteDataSource
import workshop1024.com.xproject.news.model.newsdetail.sources.NewsDetailDataSource
import workshop1024.com.xproject.news.model.newsdetail.sources.NewsDetailRepository

object Injection {

    fun provideNewsDetailRepository(context: Context): NewsDetailDataSource {
        return NewsDetailRepository.getInstance(NewsDetailRemoteDataSource.instance,
                NewsDetailLocalDataSource.getInstance(NewsDetailDatabase.getInstance(context).newsDetailDao(), ExecutorUtils()))
    }

    fun provideNewsRepository(context: Context): NewsDataSource {
        return NewsRepository.getInstance(NewsRemoteDataSource.instance,
                NewsLocalDataSource.getInstance(NewsDatabase.getInstance(context).newsDao(), ExecutorUtils()))
    }
}