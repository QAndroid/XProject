package workshop1024.com.xproject.model.publisher.source;

import android.util.Log;

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
    private PublisherDataSource mPublisherRemoteDataSource;
    //发布者本地数据源
    private PublisherDataSource mPublisherLocalDataSource;

    //内存中缓存的发布者信息
    private Map<String, Publisher> mCachedPublisherMaps;

    //内存和本地缓存的指定类型发布者数据是否为“脏”数据
    private boolean mIsCachedTypeDirty = false;
    //内存和本地缓存的指定语言发布者数据是否为“脏”数据
    private boolean mIsCachedLanguageDirty = false;

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

    /**
     * 销毁发布者数据源单例对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getPublishersByType(final String type, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByType =" + type);
        //是否有缓存发布者信息，并查找指定类型
        if (mCachedPublisherMaps != null && !mIsCachedTypeDirty) {
            List<Publisher> publishersToShow = new ArrayList<>();
            for (Publisher publisher : mCachedPublisherMaps.values()) {
                if (type.equals(publisher.getType())) {
                    publishersToShow.add(publisher);
                }
            }

            //如果有指定类型则返回显示,如果没有则从本地或远程获取
            if (publishersToShow.size() != 0) {
                Log.i("XProject", "PublisherRepository getPublishersByType from cache");
                loadPublishersCallback.onPublishersLoaded(publishersToShow);
                return;
            }
        }

        //如果数据“脏”了，则从远程获取，否则尝试从本地缓存获取
        Log.i("XProject", "PublisherRepository getPublishersByType mIsCachedDirty =" + mIsCachedTypeDirty);
        if (mIsCachedTypeDirty) {
            getPublishersByTypeFromRemoteDataSource(type, loadPublishersCallback);
        } else {
            getPublishersByTypeFromLocalDataSource(type, loadPublishersCallback);
        }
    }

    /**
     * 从远程数据源获取指定类型的发布者
     *
     * @param type                   获取指定发布者的类型
     * @param loadPublishersCallback 获取发布者信息回调
     */
    private void getPublishersByTypeFromRemoteDataSource(String type, final LoadPublishersCallback
            loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByType from remote");
        mPublisherRemoteDataSource.getPublishersByType(type, new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                Log.i("XProject", "PublisherRepository getPublishersByType from remote onPublishersLoaded" + publisherList.toString());
                //从远程获取后，更新内存和本地缓存然后返回显示
                Log.i("XProject", "PublisherRepository getPublishersByType from remote refreshCache");
                refreshCache(publisherList);
                mIsCachedTypeDirty = false;
                Log.i("XProject", "PublisherRepository getPublishersByType from remote refreshLocal");
                refreshLocal(publisherList);
                loadPublishersCallback.onPublishersLoaded(publisherList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByType from remote onDataNotAvailable");
                loadPublishersCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * 从本地数据源获取指定类型的发布者
     *
     * @param type                   获取指定发布者的类型
     * @param loadPublishersCallback 获取发布者信息回调
     */
    private void getPublishersByTypeFromLocalDataSource(final String type, final LoadPublishersCallback
            loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByType from local");
        mPublisherLocalDataSource.getPublishersByType(type, new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                //从本地获取后，更新内存缓存然后返回显示
                Log.i("XProject", "PublisherRepository getPublishersByType from local onPublishersLoaded" + publisherList.toString());
                Log.i("XProject", "PublisherRepository getPublishersByType from local refreshCache");
                refreshCache(publisherList);
                mIsCachedTypeDirty = false;
                loadPublishersCallback.onPublishersLoaded(publisherList);
            }

            @Override
            public void onDataNotAvailable() {
                //如果本地缓存还没有，则从远程缓存中获取
                Log.i("XProject", "PublisherRepository getPublishersByType from local onDataNotAvailable getPublishersByTypeFromRemoteDataSource");
                getPublishersByTypeFromRemoteDataSource(type, loadPublishersCallback);
            }
        });
    }

    /**
     * 更新或者添加指定发布者信息至内存缓存
     *
     * @param publisherList 要更新或添加到内存缓存的发布者
     */
    private void refreshCache(List<Publisher> publisherList) {
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        //添加或者覆盖更新
        for (Publisher publisher : publisherList) {
            mCachedPublisherMaps.put(publisher.getPublisherId(), publisher);
        }
    }

    /**
     * 更新或者添加指定发布者信息至本地缓存
     *
     * @param publisherList 要更新或添加到本地缓存的发布者
     */
    private void refreshLocal(List<Publisher> publisherList) {
        for (Publisher publisher : publisherList) {
            mPublisherLocalDataSource.savePublisher(publisher);
        }
    }


    @Override
    public void getPublishersByLanguage(final String language, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByLanguage =" + language);
        if (mCachedPublisherMaps != null && !mIsCachedLanguageDirty) {
            List<Publisher> publishersToShow = new ArrayList<>();
            for (Publisher publisher : mCachedPublisherMaps.values()) {
                if (language.equals(publisher.getLanguage())) {
                    publishersToShow.add(publisher);
                }
            }

            if (publishersToShow.size() != 0) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from cache");
                loadPublishersCallback.onPublishersLoaded(publishersToShow);
                return;
            }
        }

        Log.i("XProject", "PublisherRepository getPublishersByLanguage mIsCachedDirty =" + mIsCachedLanguageDirty);
        if (mIsCachedLanguageDirty) {
            getPublishersByLanguageFromRemoteDataSource(language, loadPublishersCallback);
        } else {
            getPublishersByLanguageFromLocalDataSource(language, loadPublishersCallback);
        }
    }

    /**
     * 从远程数据源获取指定语言类型的发布者
     *
     * @param language               获取发布者的指定语言
     * @param loadPublishersCallback 获取发布者信息回调
     */
    private void getPublishersByLanguageFromRemoteDataSource(String language, final LoadPublishersCallback
            loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByLanguage from remote");
        mPublisherRemoteDataSource.getPublishersByLanguage(language, new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from remote onPublishersLoaded" + publisherList.toString());
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from remote refresh cache");
                refreshCache(publisherList);
                mIsCachedLanguageDirty = false;
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from remote refresh local");
                refreshLocal(publisherList);
                loadPublishersCallback.onPublishersLoaded(publisherList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from remote onDataNotAvailable");
                loadPublishersCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * 从本地数据源获取指定语言类型的发布者
     *
     * @param language               获取发布者的指定语言类型
     * @param loadPublishersCallback 获取发布者信息回调
     */
    private void getPublishersByLanguageFromLocalDataSource(final String language, final LoadPublishersCallback
            loadPublishersCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByLanguage from local");
        mPublisherLocalDataSource.getPublishersByLanguage(language, new LoadPublishersCallback() {
            @Override
            public void onPublishersLoaded(List<Publisher> publisherList) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from local onPublishersLoaded" + publisherList.toString());
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from local refreshCache");
                refreshCache(publisherList);
                mIsCachedLanguageDirty = false;
                loadPublishersCallback.onPublishersLoaded(publisherList);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByLanguage from local onDataNotAvailable getPublishersByLanguageFromRemoteDataSource");
                getPublishersByLanguageFromRemoteDataSource(language, loadPublishersCallback);
            }
        });
    }

    @Override
    public void savePublisher(Publisher publisher) {
        Log.i("XProject", "PublisherRepository savePublisher");
        //本地缓存更新数据和远程更新数据
        mPublisherRemoteDataSource.savePublisher(publisher);
        mPublisherLocalDataSource.savePublisher(publisher);

        //内存缓存更新数据
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        mCachedPublisherMaps.put(publisher.getPublisherId(), publisher);
    }

    @Override
    public void subscribePublisherById(String publisherId) {
        Log.i("XProject", "PublisherRepository subscribePublisherById =" + publisherId);
        //订阅远程发布者
        mPublisherRemoteDataSource.subscribePublisherById(publisherId);
        //订阅本地缓存发布者
        mPublisherLocalDataSource.subscribePublisherById(publisherId);

        //订阅内存缓存中的发布者
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        Publisher subscribedPublisher = mCachedPublisherMaps.get(publisherId);
        if (subscribedPublisher != null) {
            subscribedPublisher.setIsSubscribed(true);
        }
    }

    @Override
    public void unSubscribePublisherById(String publisherId) {
        Log.i("XProject", "PublisherRepository unSubscribePublisherById =" + publisherId);
        //取消订阅远程发布者
        mPublisherRemoteDataSource.unSubscribePublisherById(publisherId);
        //取消订阅本地缓存发布者
        mPublisherLocalDataSource.unSubscribePublisherById(publisherId);

        //取消订阅内存缓存发布者
        if (mCachedPublisherMaps == null) {
            mCachedPublisherMaps = new LinkedHashMap<>();
        }
        Publisher unSubscribedPublisher = mCachedPublisherMaps.get(publisherId);
        if (unSubscribedPublisher != null) {
            unSubscribedPublisher.setIsSubscribed(false);
        }
    }

    @Override
    public void refreshPublishers(String type) {
        //将内存和本地缓存设置为“脏”数据，然后就会从远程从新获取
        if(type.equals("ChoiceTypeDialog")){
            mIsCachedTypeDirty = true;
        }else{
            mIsCachedLanguageDirty = true;
        }
    }
}
