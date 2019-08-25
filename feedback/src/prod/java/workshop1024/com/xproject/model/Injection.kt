package workshop1024.com.xproject.model

import workshop1024.com.xproject.model.filter.source.FilterDataSource
import workshop1024.com.xproject.model.filter.source.FilterRepository
import workshop1024.com.xproject.model.message.source.MessageDataSource
import workshop1024.com.xproject.model.message.source.MessageRepository
import workshop1024.com.xproject.model.news.source.NewsDataSource
import workshop1024.com.xproject.model.news.source.NewsRepository
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.model.publisher.source.PublisherRepository
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeRepository
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.model.subinfo.source.SubInfoRepository

object Injection {

    fun provideMessageRepository(): MessageDataSource {
        return MessageRepository.instance
    }
}
