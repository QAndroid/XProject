package workshop1024.com.xproject.model.news.source;

import java.util.List;

import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.publisher.Publisher;

/**
 * 新闻数据源接口，定义了关于新闻数据的接口
 */
public interface NewsDataSource {

    /**
     * 获取所有的新闻信息
     *
     * @param loadNewsCallback 获取新闻回调
     */
    void getNewses(LoadNewsCallback loadNewsCallback);

    /**
     * 获取指定发布者集合的新闻信息
     *
     * @param publisherList    获取新闻的指定发布者集合
     * @param loadNewsCallback 获取新闻回调
     */
    void getNewsByPublishers(List<Publisher> publisherList, LoadNewsCallback loadNewsCallback);

    /**
     * 获取新闻回调
     */
    interface LoadNewsCallback {
        /**
         * 新闻加载完毕
         *
         * @param newsList 加载返回的新闻
         */
        void onNewsLoaded(List<News> newsList);

        /**
         * 没有有效的新闻
         */
        void onDataNotAvaiable();
    }
}
