package workshop1024.com.xproject.model

import workshop1024.com.xproject.model.filter.source.FilterDataSource
import workshop1024.com.xproject.model.filter.source.FilterMockRepository
import workshop1024.com.xproject.model.message.source.MessageDataSource
import workshop1024.com.xproject.model.message.source.MessageMockRepository
import workshop1024.com.xproject.model.news.source.NewsDataSource
import workshop1024.com.xproject.model.news.source.NewsMockRepository
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.model.publisher.source.PublisherMockRepository
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeMockRepository
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.model.subinfo.source.SubInfoMockRepository

object Injection {
    fun providePublisherTypeRepository(): PublisherTypeDataSource {
        return PublisherTypeMockRepository.getInstance()
    }

    fun providePublisherRepository(): PublisherDataSource {
        return PublisherMockRepository.getInstance()
    }

    fun provideFilterRepository(): FilterDataSource {
        return FilterMockRepository.instance!!
    }

    fun provideMessageRepository(): MessageDataSource {
        return MessageMockRepository.instance!!
    }

    fun provideNewsRepository(): NewsDataSource {
        return NewsMockRepository.instance!!
    }

    fun provideSubInfoRepository(): SubInfoDataSource {
        return SubInfoMockRepository.getInstance()
    }
}