package workshop1024.com.xproject.model.subscribe.source.local;

import android.util.Log;

import java.util.List;

import workshop1024.com.xproject.model.subscribe.Subscribe;
import workshop1024.com.xproject.model.subscribe.source.SubscribeDataSource;
import workshop1024.com.xproject.utils.ExecutorUtils;

/**
 * 已订阅发布者本地数据源
 */
public class SubscribeLocalDataSource implements SubscribeDataSource {
    private static volatile SubscribeLocalDataSource INSTANCE;

    //已订阅发布者数据库层
    private SubscribeDao mSubscribeDao;
    //线程执行工具
    private ExecutorUtils mExecutorUtils;

    /**
     * 私有构造方法
     *
     * @param subscribeDao  已订阅发布者数据库层
     * @param executorUtils 线程执行工具
     */
    private SubscribeLocalDataSource(SubscribeDao subscribeDao, ExecutorUtils executorUtils) {
        mSubscribeDao = subscribeDao;
        mExecutorUtils = executorUtils;
    }

    /**
     * 获取已订阅发布者本地数据源单例对象
     *
     * @param subscribeDao  已订阅发布者数据库层
     * @param executorUtils 线程执行工具
     * @return 已订阅发布者本地数据源单例对象
     */
    public static SubscribeLocalDataSource getInstance(SubscribeDao subscribeDao, ExecutorUtils
            executorUtils) {
        synchronized (SubscribeLocalDataSource.class) {
            if (INSTANCE == null) {
                INSTANCE = new SubscribeLocalDataSource(subscribeDao, executorUtils);
            }
        }

        return INSTANCE;
    }

    @Override
    public void getSubscribes(final LoadSubscribesCallback loadSubscribesCallback) {
        Log.i("XProject","SubscribeLocalDataSource getSubscribes");
        Runnable getSubscribesRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Subscribe> subscribeList = mSubscribeDao.getSubscribes();
                mExecutorUtils.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (subscribeList.isEmpty()) {
                            loadSubscribesCallback.onDataNotAvailable();
                        } else {
                            loadSubscribesCallback.onPublishersLoaded(subscribeList);
                        }
                    }
                });
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(getSubscribesRunnable);
    }

    @Override
    public void unSubscribeById(final String subscribeId) {
        Log.i("XProject","SubscribeLocalDataSource unSubscribeById =" + subscribeId);
        Runnable unSubscribeRunnable = new Runnable() {
            @Override
            public void run() {
                mSubscribeDao.deleteSubscribeById(subscribeId);
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(unSubscribeRunnable);
    }

    @Override
    public void reNameSubscribeById(final String subscribeId, final String customName) {
        Log.i("XProject","SubscribeLocalDataSource reNameSubscribeById =" + subscribeId);
        Runnable reNameRunnable = new Runnable() {
            @Override
            public void run() {
                mSubscribeDao.updateName(subscribeId, customName);
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(reNameRunnable);
    }

    @Override
    public void deleteAllSubscribes() {
        Log.i("XProject","SubscribeLocalDataSource deleteAllSubscribes");
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mSubscribeDao.deleteAllSubscribes();

            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(deleteRunnable);
    }

    @Override
    public void saveSubscribe(final Subscribe subscribe) {
        Log.i("XProject","SubscribeLocalDataSource saveSubscribe =" + subscribe.toString());
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mSubscribeDao.insertSubscribe(subscribe);
            }
        };
        mExecutorUtils.getDiskIOExecutor().execute(saveRunnable);
    }

    @Override
    public void refreshSubscribes() {
        Log.i("XProject","SubscribeLocalDataSource refreshSubscribes");
        //用于刷新内存和本地缓存数据接口方法，本地数据源不实现
    }
}
