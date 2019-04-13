package workshop1024.com.xproject.model;

import workshop1024.com.xproject.model.filter.source.FilterDataSource;
import workshop1024.com.xproject.model.filter.source.FilterMockRepository;
import workshop1024.com.xproject.model.message.source.MessageDataSource;
import workshop1024.com.xproject.model.message.source.MessageMockRepository;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.news.source.NewsMockRepository;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.publisher.source.PublisherMockRepository;
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeDataSource;
import workshop1024.com.xproject.model.publishertype.source.PublisherTypeMockRepository;
import workshop1024.com.xproject.model.subinfo.source.SubInfoDataSource;
import workshop1024.com.xproject.model.subinfo.source.SubInfoMockRepository;

public class Injection {
    public static PublisherTypeDataSource provideContentTypeRepository() {
        return PublisherTypeMockRepository.getInstance();
    }

    public static FilterDataSource provideFilterRepository() {
        return FilterMockRepository.getInstance();
    }

    public static MessageDataSource provideMessageRepository() {
        return MessageMockRepository.getInstance();
    }

    public static NewsDataSource provideNewsRepository() {
        return NewsMockRepository.getInstance();
    }

    public static PublisherDataSource providePublisherRepository() {
        return PublisherMockRepository.getInstance();
    }

    public static SubInfoDataSource provideSubInfoRepository() {
        return SubInfoMockRepository.getInstance();
    }
}
