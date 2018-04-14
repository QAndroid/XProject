package workshop1024.com.xproject.model.news.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.filter.Filter;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.tag.Tag;

/**
 * 新闻数据源
 * FIXME 是否和发布者数据源通过泛型复用和精简代码
 */
public class NewsRepository implements NewsDataSource {
    //发布者新闻数据源单例对象
    private static NewsRepository INSTANCE = null;

    //发布者新闻远程数据源
    private NewsDataSource mNewsRemoteDataSource;
    //发布者新闻本地数据源
    private NewsDataSource mNewsLocalDataSource;

    //内存中缓存的发布者新闻信息
    private Map<String, News> mCachedNewsMaps;
    //内存和本地缓存发布者新闻数据是否为“脏”数据
    private boolean mIsCachedDirty;

    /**
     * 发布者新闻数据源构造方法
     *
     * @param newsLocalDataSource  发布者新闻远程数据源
     * @param newsRemoteDataSource 发布者新闻本地数据源
     */
    private NewsRepository(NewsDataSource newsRemoteDataSource, NewsDataSource newsLocalDataSource) {
        mNewsRemoteDataSource = newsRemoteDataSource;
        mNewsLocalDataSource = newsLocalDataSource;
    }

    /**
     * 获取发布者新闻数据源单例对象
     *
     * @param newsRemoteDataSource 发布者新闻远程数据源
     * @param newsLocalDataSource  发布者新闻本地数据源
     * @return
     */
    public static NewsRepository getInstance(NewsDataSource newsRemoteDataSource, NewsDataSource newsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository(newsRemoteDataSource, newsLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * 销毁发布者新闻数据源单例对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getNewses(final LoadNewsCallback loadNewsCallback) {
        if (mCachedNewsMaps != null && !mIsCachedDirty) {
            loadNewsCallback.onNewsLoaded(new ArrayList<>(mCachedNewsMaps.values()));
            return;
        }

        if (mIsCachedDirty) {
            getNewsesFromRemoteDataSource(loadNewsCallback);
        } else {
            getNewsesFromLocalDataSource(loadNewsCallback);
        }
    }

    /**
     * 从本地数据源获取新闻数据
     *
     * @param loadNewsCallback 获取新闻数据回调
     */
    private void getNewsesFromLocalDataSource(final LoadNewsCallback loadNewsCallback) {
        mNewsLocalDataSource.getNewses(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> newsList) {
                refreshCache(newsList);
                loadNewsCallback.onNewsLoaded(new ArrayList<>(mCachedNewsMaps.values()));
            }

            @Override
            public void onDataNotAvaiable() {
                getNewsesFromRemoteDataSource(loadNewsCallback);
            }
        });
    }

    /**
     * 从远程数据源获取新闻数据
     *
     * @param loadNewsCallback 获取新闻数据回调
     */
    private void getNewsesFromRemoteDataSource(final LoadNewsCallback loadNewsCallback) {
        mNewsRemoteDataSource.getNewses(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> newsList) {
                refreshCache(newsList);
                refreshLocalDataSource(newsList);
                loadNewsCallback.onNewsLoaded(new ArrayList<>(mCachedNewsMaps.values()));
            }

            @Override
            public void onDataNotAvaiable() {
                loadNewsCallback.onDataNotAvaiable();
            }
        });
    }

    /**
     * 更新本地缓存的新闻信息
     *
     * @param newsList 新闻信息
     */
    private void refreshLocalDataSource(List<News> newsList) {
        mNewsLocalDataSource.deleteAllNewses();
        for (News news : newsList) {
            mNewsLocalDataSource.saveNews(news);
        }
    }

    /**
     * 更新本地缓存的新闻信息
     *
     * @param newsList 要更新的新闻信息
     */
    private void refreshCache(List<News> newsList) {
        if (mCachedNewsMaps == null) {
            mCachedNewsMaps = new LinkedHashMap<>();
        }

        mCachedNewsMaps.clear();
        for (News news : newsList) {
            mCachedNewsMaps.put(news.getNewId(), news);
        }

        mIsCachedDirty = false;
    }

    @Override
    public void getNewsByPublishers(List<Publisher> publisherList, LoadNewsCallback loadNewsCallback) {
        getNewses(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> newsList) {
                
            }

            @Override
            public void onDataNotAvaiable() {

            }
        });
    }

    @Override
    public void getNewsByFilter(Filter filter, LoadNewsCallback loadNewsCallback) {

    }

    @Override
    public void saveNews(News news) {
        mNewsRemoteDataSource.saveNews(news);
        mNewsLocalDataSource.saveNews(news);

        if (mCachedNewsMaps == null) {
            mCachedNewsMaps = new LinkedHashMap<>();
        }

        mCachedNewsMaps.put(news.getNewId(), news);
    }

    @Override
    public void deleteAllNewses() {
        mNewsRemoteDataSource.deleteAllNewses();
        mNewsLocalDataSource.deleteAllNewses();

        if (mCachedNewsMaps == null) {
            mCachedNewsMaps = new LinkedHashMap<>();
        }

        mCachedNewsMaps.clear();
    }
}
