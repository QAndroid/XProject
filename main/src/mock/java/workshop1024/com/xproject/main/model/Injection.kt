package workshop1024.com.xproject.main.model

import workshop1024.com.xproject.main.model.filter.source.FilterDataSource
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.main.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.model.filter.source.FilterMockRepository
import workshop1024.com.xproject.model.publisher.source.PublisherMockRepository
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeMockRepository

object Injection {
    fun providePublisherTypeRepository(): workshop1024.com.xproject.main.model.publishertype.source.PublisherTypeDataSource {
        return PublisherTypeMockRepository.instance
    }

    fun providePublisherRepository(): workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource {
        return PublisherMockRepository.instance
    }

    fun provideFilterRepository(): workshop1024.com.xproject.main.model.filter.source.FilterDataSource {
        return FilterMockRepository.instance
    }
}