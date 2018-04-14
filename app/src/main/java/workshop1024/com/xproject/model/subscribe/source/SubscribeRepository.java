package workshop1024.com.xproject.model.subscribe.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.subscribe.Subscribe;

/**
 * 已订阅发布者数据源
 */
public class SubscribeRepository implements SubscribeDataSource {
    //已订阅发布者数据源单例对象
    private static SubscribeRepository INSTANCE = null;

    //已订阅发布者远程数据源
    private SubscribeDataSource mSubscribeRemoteDataSource;
    //已订阅发布者本地数据源
    private SubscribeDataSource mSubscribeLocalDataSource;

    //内存缓存的已订阅发布者信息
    private Map<String, Subscribe> mCachedSubscribeMaps;
    //内存和本地缓存的已订阅发布者数据是否为“脏”数据
    private boolean mIsCachedSubscribeDirty = false;

    /**
     * 已订阅发布者私有构造方法
     *
     * @param subscribeRemoteDataSource 已订阅发布者远程数据源
     * @param subscribeLocalDataSource  已订阅发布者本地数据源
     */
    private SubscribeRepository(SubscribeDataSource subscribeRemoteDataSource, SubscribeDataSource
            subscribeLocalDataSource) {
        mSubscribeRemoteDataSource = subscribeRemoteDataSource;
        mSubscribeLocalDataSource = subscribeLocalDataSource;
    }

    /**
     * 获取已发布者数据源单例对象
     *
     * @param subscribeRemoteDataSource 已订阅发布者远程数据源
     * @param subscribeLocalDataSource  已订阅发布者本地数据源
     * @return 已订阅发布者数据源
     */
    public static SubscribeRepository getInstance(SubscribeDataSource subscribeRemoteDataSource,
                                                  SubscribeDataSource subscribeLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SubscribeRepository(subscribeRemoteDataSource, subscribeLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * 销毁已订阅发布者数据源单例对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getSubscribes(LoadSubscribesCallback loadSubscribesCallback) {
        if (mCachedSubscribeMaps != null && !mIsCachedSubscribeDirty) {
            Log.i("XProject","SubscribeRepository getSubscribes from cache");
            loadSubscribesCallback.onPublishersLoaded(new ArrayList<>(mCachedSubscribeMaps.values()));
            return;
        }

        Log.i("XProject","SubscribeRepository getSubscribes mIsCachedSubscribeDirty =" + mIsCachedSubscribeDirty);
        if (mIsCachedSubscribeDirty) {
            getSubscribesFromRemote(loadSubscribesCallback);
        } else {
            getSubscribesFromLocal(loadSubscribesCallback);
        }
    }

    /**
     * 从远程获取已订阅发布者信息
     *
     * @param loadSubscribesCallback 获取已订阅发布者回调
     */
    private void getSubscribesFromRemote(final LoadSubscribesCallback loadSubscribesCallback) {
        Log.i("XProject","SubscribeRepository getSubscribes from remote");
        mSubscribeRemoteDataSource.getSubscribes(new LoadSubscribesCallback() {
            @Override
            public void onPublishersLoaded(List<Subscribe> subscribeList) {
                Log.i("XProject","SubscribeRepository getSubscribes from remote onPublishersLoaded =" + subscribeList.toString());
                Log.i("XProject","SubscribeRepository getSubscribes from remote onPublishersLoaded refreshCached");
                refreshCached(subscribeList);
                Log.i("XProject","SubscribeRepository getSubscribes from remote onPublishersLoaded refreshLocal");
                refreshLocal(subscribeList);
                loadSubscribesCallback.onPublishersLoaded(subscribeList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("XProject","SubscribeRepository getSubscribes from remote onDataNotAvailable");
                loadSubscribesCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * 刷新内存缓存的已订阅发布者信息
     *
     * @param subscribeList 要刷新的已订阅发布者信息
     */
    private void refreshCached(List<Subscribe> subscribeList) {
        if (mCachedSubscribeMaps == null) {
            mCachedSubscribeMaps = new LinkedHashMap<>();
        }

        mCachedSubscribeMaps.clear();
        for (Subscribe subscribe : subscribeList) {
            mCachedSubscribeMaps.put(subscribe.getSubscribeId(), subscribe);
        }

        mIsCachedSubscribeDirty = false;
    }

    /**
     * 刷新本地缓存的已订阅发布者信息
     *
     * @param subscribeList 要刷新的已订阅发布者信息
     */
    private void refreshLocal(List<Subscribe> subscribeList) {
        mSubscribeLocalDataSource.deleteAllSubscribes();
        for (Subscribe subscribe : subscribeList) {
            mSubscribeLocalDataSource.saveSubscribe(subscribe);
        }
    }


    /**
     * 从本地获取已订阅发布者信息
     *
     * @param loadSubscribesCallback 获取已订阅发布者回调
     */
    private void getSubscribesFromLocal(final LoadSubscribesCallback loadSubscribesCallback) {
        Log.i("XProject","SubscribeRepository getSubscribes from local");
        mSubscribeLocalDataSource.getSubscribes(new LoadSubscribesCallback() {
            @Override
            public void onPublishersLoaded(List<Subscribe> subscribeList) {
                Log.i("XProject","SubscribeRepository getSubscribes from local onPublishersLoaded =" + subscribeList.toString());
                Log.i("XProject","SubscribeRepository getSubscribes from local onPublishersLoaded refreshCached");
                refreshCached(subscribeList);
                loadSubscribesCallback.onPublishersLoaded(subscribeList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("XProject","SubscribeRepository getSubscribes from local onDataNotAvailable getSubscribesFromRemote");
                getSubscribesFromRemote(loadSubscribesCallback);
            }
        });
    }


    @Override
    public void unSubscribeById(String subscribeId) {
        Log.i("XProject","SubscribeRepository unSubscribeById =" + subscribeId);
        mSubscribeRemoteDataSource.unSubscribeById(subscribeId);
        mSubscribeLocalDataSource.unSubscribeById(subscribeId);

        if (mCachedSubscribeMaps == null) {
            mCachedSubscribeMaps = new LinkedHashMap<>();
        }

      mCachedSubscribeMaps.remove(subscribeId);
    }

    @Override
    public void reNameSubscribeById(String subscribeId, String newNameString) {
        Log.i("XProject","SubscribeRepository reNameSubscribeById =" + subscribeId);
        mSubscribeRemoteDataSource.reNameSubscribeById(subscribeId, newNameString);
        mSubscribeLocalDataSource.reNameSubscribeById(subscribeId, newNameString);

        if (mCachedSubscribeMaps == null) {
            mCachedSubscribeMaps = new LinkedHashMap<>();
        }
        Subscribe subscribe = mCachedSubscribeMaps.get(subscribeId);
        if (subscribe != null) {
            subscribe.setCustomName(newNameString);
        }
    }

    @Override
    public void deleteAllSubscribes() {
        Log.i("XProject","SubscribeRepository deleteAllSubscribes");
        mSubscribeRemoteDataSource.deleteAllSubscribes();
        mSubscribeLocalDataSource.deleteAllSubscribes();

        if (mCachedSubscribeMaps == null) {
            mCachedSubscribeMaps = new LinkedHashMap<>();
        }
        mCachedSubscribeMaps.clear();
    }

    @Override
    public void saveSubscribe(Subscribe subscribe) {
        Log.i("XProject","SubscribeRepository saveSubscribe =" + subscribe.toString());
        mSubscribeRemoteDataSource.saveSubscribe(subscribe);
        mSubscribeLocalDataSource.saveSubscribe(subscribe);

        if (mCachedSubscribeMaps == null) {
            mCachedSubscribeMaps = new LinkedHashMap<>();
        }
        mCachedSubscribeMaps.put(subscribe.getSubscribeId(), subscribe);
    }

    @Override
    public void refreshSubscribes() {
        mIsCachedSubscribeDirty = true;
    }
}
