package workshop1024.com.xproject.model;

import workshop1024.com.xproject.model.filter.source.FilterDataSource;
import workshop1024.com.xproject.model.filter.source.FilterRepository;
import workshop1024.com.xproject.model.message.source.MessageDataSource;
import workshop1024.com.xproject.model.message.source.MessageRepository;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsRepository;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.publisher.source.PublisherRepository;
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeDataSource;
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeRepository;
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource;
import workshop1024.com.xproject.model.subinfo.source.SubInfoRepository;

public class Injection {
    public static PublisherTypeDataSource providePublisherTypeRepository() {
        return PublisherTypeRepository.getInstance();
    }

    public static PublisherDataSource providePublisherRepository() {
        return PublisherRepository.getInstance();
    }

    public static FilterDataSource provideFilterRepository() {
        return FilterRepository.getInstance();
    }

    public static MessageDataSource provideMessageRepository() {
        return MessageRepository.getInstance();
    }

    public static NewsDataSource provideNewsRepository() {
        return NewsRepository.getInstance();
    }

    public static SubInfoDataSource provideSubInfoRepository() {
        return SubInfoRepository.getInstance();
    }
}
