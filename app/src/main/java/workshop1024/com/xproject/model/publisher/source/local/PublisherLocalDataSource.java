package workshop1024.com.xproject.model.publisher.source.local;

import android.util.Log;

import java.util.List;

import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.utils.ExecutorUtils;

/**
 * 发布者本地数据源
 */
public class PublisherLocalDataSource implements PublisherDataSource {
    private static PublisherDataSource INSTANCE;

    private PublisherDao mPublisherDao;
    private ExecutorUtils mExecutorUtils;

    private PublisherLocalDataSource(PublisherDao publisherDao, ExecutorUtils executorUtils) {
        mPublisherDao = publisherDao;
        mExecutorUtils = executorUtils;
    }

    public static PublisherDataSource getInstance(PublisherDao publisherDao, ExecutorUtils executorUtils) {
        if (INSTANCE == null) {
            synchronized (PublisherDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PublisherLocalDataSource(publisherDao, executorUtils);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getPublishersByType(final String type, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherLocalDataSource getPublishersByType =" + type);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Publisher> publisherList = mPublisherDao.getPublishersByType(type);
                mExecutorUtils.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (publisherList.isEmpty()) {
                            loadPublishersCallback.onDataNotAvailable();
                        } else {
                            loadPublishersCallback.onPublishersLoaded(publisherList);
                        }
                    }
                });
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void getPublishersByLanguage(final String language, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherLocalDataSource getPublishersByLanguage =" + language);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Publisher> publisherList = mPublisherDao.getPublishersByLanguage(language);
                mExecutorUtils.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (publisherList.isEmpty()) {
                            loadPublishersCallback.onDataNotAvailable();
                        } else {
                            loadPublishersCallback.onPublishersLoaded(publisherList);
                        }
                    }
                });
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void savePublisher(final Publisher publisher) {
        Log.i("XProject", "PublisherLocalDataSource savePublisher =" + publisher.toString());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPublisherDao.savePublisher(publisher);
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void subscribePublisherById(final String publisherId) {
        Log.i("XProject", "PublisherLocalDataSource subscribePublisherById =" + publisherId);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPublisherDao.subscribePublisher(publisherId);
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void unSubscribePublisherById(final String publisherId) {
        Log.i("XProject", "PublisherLocalDataSource unSubscribePublisherById =" + publisherId);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPublisherDao.unSubscribePublisher(publisherId);
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void refreshPublishers(String type) {
        //不需要实现
    }
}
