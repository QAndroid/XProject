package workshop1024.com.xproject.model.news.source.local;

import java.util.ArrayList;
import java.util.List;

import workshop1024.com.xproject.model.filter.Filter;
import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.utils.ExecutorUtils;

/**
 * 新闻本地数据源
 */
public class NewsLocalDataSource implements NewsDataSource {
    private static NewsDataSource INSTANCE;

    private NewsDao mNewsDao;
    private ExecutorUtils mExecutorUtils;

    private NewsLocalDataSource(NewsDao newsDao, ExecutorUtils executorUtils) {
        mNewsDao = newsDao;
        mExecutorUtils = executorUtils;
    }

    public static NewsDataSource getInstance(NewsDao newsDao, ExecutorUtils executorUtils) {
        if (INSTANCE == null) {
            INSTANCE = new NewsLocalDataSource(newsDao, executorUtils);
        }
        return INSTANCE;
    }

    @Override
    public void getNewses(final LoadNewsCallback loadNewsCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> newsList = mNewsDao.getNewses();
                mExecutorUtils.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (newsList.isEmpty()) {
                            loadNewsCallback.onNewsLoaded(newsList);
                        } else {
                            loadNewsCallback.onDataNotAvaiable();
                        }
                    }
                });
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void getNewsByPublishers(final List<Publisher> publisherList, final LoadNewsCallback loadNewsCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //遍历分别获取每个发布者的新闻信息
                List<News> newsList = new ArrayList<>();
                for (Publisher publisher : publisherList) {
                    List<News> subNewsList = mNewsDao.getNewsByPublisherId(publisher.getPublisherId());
                    newsList.addAll(subNewsList);
                }

                if (newsList.isEmpty()) {
                    loadNewsCallback.onDataNotAvaiable();
                } else {
                    loadNewsCallback.onNewsLoaded(newsList);
                }
            }
        };

        mExecutorUtils.getDiskIOExecutor().execute(runnable);
    }

    @Override
    public void getNewsByFilter(Filter filter, LoadNewsCallback loadNewsCallback) {

    }

    @Override
    public void saveNews(News news) {

    }

    @Override
    public void deleteAllNewses() {

    }
}
