package workshop1024.com.xproject.feedback.model

import workshop1024.com.xproject.home.controller.model.filter.source.FilterDataSource
import workshop1024.com.xproject.home.controller.model.filter.source.FilterRepository
import workshop1024.com.xproject.feedback.model.message.source.MessageDataSource
import workshop1024.com.xproject.feedback.model.message.source.MessageRepository
import workshop1024.com.xproject.home.model.news.source.NewsDataSource
import workshop1024.com.xproject.home.model.news.source.NewsRepository
import workshop1024.com.xproject.home.model.publisher.source.PublisherDataSource
import workshop1024.com.xproject.home.model.publisher.source.PublisherRepository
import workshop1024.com.xproject.feedback.model.publishertype.source.PublisherTypeDataSource
import workshop1024.com.xproject.feedback.model.publishertype.source.PublisherTypeRepository
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoRepository

object Injection {
    fun provideMessageRepository(): MessageDataSource {
        return MessageRepository.instance
    }
}
