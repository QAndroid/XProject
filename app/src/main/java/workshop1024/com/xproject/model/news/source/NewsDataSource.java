package workshop1024.com.xproject.model.news.source;

import java.util.List;

import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.NewsDetail;

/**
 * 新闻数据源接口，定义了关于新闻数据的接口
 */
public interface NewsDataSource {

    void getNewsListBySubscribe(String publishId, LoadNewsListCallback loadNewsListCallback);

    void getNewsListByTag(String tagId, LoadNewsListCallback loadNewsListCallback);

    void getNewsListByFilter(String filterId, LoadNewsListCallback loadNewsListCallback);

    void getNewsListBySearch(String searchString, LoadNewsListCallback loadNewsListCallback);

    void markNewsesReadedByNewsId(List<String> newsIdList);

    void getNewsDetailByNewsId(String newsId, LoadNewsDetailCallBack loadNewsDetailCallBack);

    void getSavedNewsList(LoadNewsListCallback loadNewsListCallback);

    void saveNewsById(String newsId);

    interface LoadNewsListCallback {
        void onNewsLoaded(List<News> newsList);

        void onDataNotAvaiable();
    }

    interface LoadNewsDetailCallBack {
        void onNewsDetailLoaded(NewsDetail newsDetail);

        void onDataNotAvaiable();
    }
}
