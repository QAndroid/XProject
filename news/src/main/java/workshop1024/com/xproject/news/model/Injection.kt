package workshop1024.com.xproject.news.model

import workshop1024.com.xproject.news.model.news.source.NewsDataSource
import workshop1024.com.xproject.news.model.news.source.NewsRepository

object Injection {

    fun provideNewsRepository(): NewsDataSource {
        return NewsRepository.instance
    }
}