package workshop1024.com.xproject.home.model

import workshop1024.com.xproject.home.controller.model.filter.source.FilterDataSource
import workshop1024.com.xproject.home.controller.model.filter.source.FilterRepository
import workshop1024.com.xproject.home.model.news.source.NewsDataSource
import workshop1024.com.xproject.home.model.news.source.NewsRepository
import workshop1024.com.xproject.home.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.home.model.publisher.source.PublisherRepository
import workshop1024.com.xproject.home.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.home.model.publishertype.source.PublisherTypeRepository
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoRepository

object Injection {
    fun providePublisherTypeRepository(): PublisherTypeDataSource {
        return PublisherTypeRepository.instance
    }

    fun providePublisherRepository(): PublisherDataSource {
        return PublisherRepository.instance
    }

    fun provideFilterRepository(): FilterDataSource {
        return FilterRepository.instance
    }

    fun provideNewsRepository(): NewsDataSource {
        return NewsRepository.instance
    }

    fun provideSubInfoRepository(): SubInfoDataSource {
        return SubInfoRepository.instance
    }
}
