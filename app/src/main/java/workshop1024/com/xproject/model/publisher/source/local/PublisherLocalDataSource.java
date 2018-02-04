package workshop1024.com.xproject.model.publisher.source.local;

import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;

/**
 * 发布者本地数据源
 */
public class PublisherLocalDataSource implements PublisherDataSource {
    private static PublisherDataSource INSTANCE;

    private PublisherLocalDataSource() {
    }

    public static PublisherDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherLocalDataSource();
        }

        return INSTANCE;
    }

    @Override
    public void getPublishers(LoadPublishersCallback loadPublishersCallback) {

    }

    @Override
    public void getPublishersByType(String type, LoadPublishersCallback loadPublishersCallback) {

    }

    @Override
    public void getPublishersByLanguage(String language, LoadPublishersCallback loadPublishersCallback) {

    }

    @Override
    public void deleteAllPublishers() {

    }

    @Override
    public void savePublisher(Publisher publisher) {

    }

    @Override
    public void subscribePublisher(Publisher publisher) {

    }

    @Override
    public void unSubscribePublisher(Publisher publisher) {

    }

    @Override
    public void refreshPublishers() {

    }
}
