package workshop1024.com.xproject.model.publisher.source;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.publisher.Publisher;

/**
 * 发布者数据源
 */
public class PublisherRepository implements PublisherDataSource {
    //发布者数据源单例对象
    private static PublisherRepository INSTANCE = null;

    //发布者远程数据源
    private final PublisherDataSource mPublisherRemoteDataSource;
    //发布者本地数据源
    private final PublisherDataSource mPublisherLocalDataSource;

    //内存中缓存的发布者信息
    private Map<String, Publisher> mCachedPublisherMaps;
    //发布者数据是否为脏数据
    private boolean mIsCachedDirty;

    /**
     * 发布者数据源私有构造方法
     *
     * @param publisherRemoteDataSource 发布者远程数据源
     * @param publisherLocalDataSource  发布者本地数据源
     */
    private PublisherRepository(PublisherDataSource publisherRemoteDataSource, PublisherDataSource
            publisherLocalDataSource) {
        mPublisherRemoteDataSource = publisherRemoteDataSource;
        mPublisherLocalDataSource = publisherLocalDataSource;
    }

    /**
     * 销毁发布者数据源单例对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * 获取发布者数据源单例对象
     *
     * @return 发布者数据源对象
     */
    public static PublisherRepository getInstance(PublisherDataSource publisherRemoteDataSource, PublisherDataSource
            publisherLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PublisherRepository(publisherRemoteDataSource, publisherLocalDataSource);
        }

        return INSTANCE;
    }

    @Override
    public void getPublishers(LoadPublishersCallback loadPublishersCallback) {
        if (mCachedPublisherMaps != null && !mIsCachedDirty) {
            loadPublishersCallback.onPublishersLoaded(new ArrayList<>(mCachedPublisherMaps.values()));
            return;
        }

        if (mIsCachedDirty) {
            getPublishersFromRemoteDataSource(loadPublishersCallback);
        } else {
            getPublishersFromLocalDataSource(loadPublishersCallback);
        }
    }

    /**
     * 从本地数据源获取发布者信息
     *
     * @param loadPublishersCallback 加载发布者信息回调x
     */
    private void getPublishersFromLocalDataSource(final LoadPublishersCallback loadPublishersCallback) {
        mPublisherLocalDataSource.getPublishers(new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                refreshCache(publisherList);
                loadPublishersCallback.onPublishersLoaded(new ArrayList<>(mCachedPublisherMaps.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * 从远程数据源获取发布者信息
     *
     * @param loadPublishersCallback 加载发布者信息回调
     */
    private void getPublishersFromRemoteDataSource(final LoadPublishersCallback loadPublishersCallback) {
        mPublisherRemoteDataSource.getPublishers(new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                refreshCache(publisherList);
                refreshLocalDataSource(publisherList);
                loadPublishersCallback.onPublishersLoaded(new ArrayList<>(mCachedPublisherMaps.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * 更新本地发布者信息
     *
     * @param publisherList 要更新的发布者信息
     */
    private void refreshLocalDataSource(List<Publisher> publisherList) {
        mPublisherLocalDataSource.deleteAllPublishers();
        for (Publisher publisher : publisherList) {
            mPublisherLocalDataSource.savePublisher(publisher);
        }
    }

    /**
     * 更新缓存的发布者信息
     *
     * @param publisherList 要更新的发布者信息
     */
    private void refreshCache(List<Publisher> publisherList) {
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }

        mCachedPublisherMaps.clear();

        for (Publisher publisher : publisherList) {
            mCachedPublisherMaps.put(publisher.getId(), publisher);
        }

        mIsCachedDirty = false;
    }

    @Override
    public void getPublishersByType(final String type, final LoadPublishersCallback loadPublishersCallback) {
        getPublishers(new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                List<Publisher> publishersToShow = new ArrayList<>();

                for (Publisher publisher : publisherList) {
                    if (publisher.getType().equals(type)) {
                        publishersToShow.add(publisher);
                    }
                }

                loadPublishersCallback.onPublishersLoaded(publishersToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getPublishersByLanguage(final String language, final LoadPublishersCallback loadPublishersCallback) {
        getPublishers(new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                List<Publisher> publishersToShow = new ArrayList<>();

                for (Publisher publisher : publisherList) {
                    if (publisher.getLanguage().equals(language)) {
                        publishersToShow.add(publisher);
                    }
                }

                loadPublishersCallback.onPublishersLoaded(publishersToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void deleteAllPublishers() {

    }

    @Override
    public void savePublisher(Publisher publisher) {

    }

    @Override
    public void subscribePublisher(Publisher publisher) {
        mPublisherRemoteDataSource.subscribePublisher(publisher);
        mPublisherLocalDataSource.subscribePublisher(publisher);

        Publisher subscribedPublisher = new Publisher(publisher.getId(), publisher.getType(), publisher.getLanguage()
                , publisher.geticonUrl(), publisher.getName(), publisher.getSubscribeNum(), true, publisher
                .getBannerUrl(), publisher.getNewsCount());
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        mCachedPublisherMaps.put(publisher.getId(), subscribedPublisher);
    }

    @Override
    public void unSubscribePublisher(Publisher publisher) {
        mPublisherRemoteDataSource.unSubscribePublisher(publisher);
        mPublisherLocalDataSource.unSubscribePublisher(publisher);

        Publisher unSubscribedPublisher = new Publisher(publisher.getId(), publisher.getType(), publisher.getLanguage()
                , publisher.geticonUrl(), publisher.getName(), publisher.getSubscribeNum(), false, publisher
                .getBannerUrl(), publisher.getNewsCount());
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        mCachedPublisherMaps.put(publisher.getId(), unSubscribedPublisher);
    }

    @Override
    public void refreshPublishers() {
        mIsCachedDirty = true;
    }

    @Override
    public void getSubscribedPublishers(final LoadPublishersCallback loadPublishersCallback) {
        getPublishers(new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                List<Publisher> subscribedPublishers = new ArrayList<>();
                for (Publisher publisher : publisherList) {
                    if (publisher.isSubscribed()) {
                        subscribedPublishers.add(publisher);
                    }
                }
                loadPublishersCallback.onPublishersLoaded(subscribedPublishers);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
