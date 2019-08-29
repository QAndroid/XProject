package workshop1024.com.xproject.home.model

import workshop1024.com.xproject.home.model.filter.source.FilterDataSource
import workshop1024.com.xproject.home.model.news.source.NewsDataSource
import workshop1024.com.xproject.home.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.home.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.model.filter.source.FilterMockRepository
import workshop1024.com.xproject.model.news.source.NewsMockRepository
import workshop1024.com.xproject.model.publisher.source.PublisherMockRepository
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeMockRepository
import workshop1024.com.xproject.model.subinfo.source.SubInfoMockRepository

object Injection {
    fun providePublisherTypeRepository(): PublisherTypeDataSource {
        return PublisherTypeMockRepository.instance
    }

    fun providePublisherRepository(): PublisherDataSource {
        return PublisherMockRepository.instance
    }

    fun provideFilterRepository(): FilterDataSource {
        return FilterMockRepository.instance
    }

    fun provideNewsRepository(): NewsDataSource {
        return NewsMockRepository.instance
    }

    fun provideSubInfoRepository(): SubInfoDataSource {
        return SubInfoMockRepository.instance
    }
}