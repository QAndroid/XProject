package workshop1024.com.xproject.model.news.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.NewsDetail;

/**
 * 新闻远程数据源
 */
public class NewsRepository implements NewsDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, News> NEWSES_SERVICE_DATA;
    private static Map<String, NewsDetail> NEWSDETAILS_SERVICE_DATA;

    private static NewsRepository INSTANCE;

    static {
        NEWSES_SERVICE_DATA = new LinkedHashMap<>(2);
        addNews("n001", "/image1", "news 001 title", "tom", "20180406", false, false);
        addNews("n002", "/image1", "news 002 title", "mike", "20180405", false, false);

        addNews("n003", "/image1", "news 003 title", "lilei", "20180306", false, false);
        addNews("n004", "/image1", "news 004 title", "hanmeimei", "20180206", false, false);
        addNews("n005", "/image1", "news 005 title", "sunny", "20170406", false, false);

        addNews("n006", "/image1", "news 006 title", "tom", "20160406", false, true);
        addNews("n007", "/image1", "news 007 title", "hanmeimei", "20110806", false, false);

        addNews("n008", "/image1", "news 008 title", "sunny", "20120407", false, false);

        addNews("n009", "/image1", "news 009 title", "lilei", "20161006", false, false);
        addNews("n010", "/image1", "news 010 title", "mike", "20181206", false, false);

        NEWSDETAILS_SERVICE_DATA = new LinkedHashMap<>(2);
        addNewsDetail("n001", "/image1", "news 001 title", "The Tech", "20180406", "tom", "news 001 Contentnews 001 Content news 001 Content news 001 Content news 001 Content ", Arrays.asList("android", "ph", "rus", "american", "english", "american", "english", "e", "english"), false, false);
        addNewsDetail("n002", "/image1", "news 002 title", "Engadget", "20180405", "mike", "news 002 Contentnews news 002 Contentnews news 002 Contentnews news 002 Contentnews ", Arrays.asList("ios", "phone"), false, false);

        addNewsDetail("n003", "/image1", "news 003 title", "Lifehacker", "20180306", "lilei", "news 003 Contentnews news 003 Contentnews news 003 Contentnews news 003 Contentnews ", Arrays.asList("china"), false, false);
        addNewsDetail("n004", "/image1", "news 104 title", "ReadWrite", "20180206", "hanmeimei", "news 004 Contentnews news 004 Contentnews news 004 Contentnews news 004 Contentnews ", Arrays.asList("usa"), false, false);

        addNewsDetail("n005", "/image1", "news 005 title", "ReadWrite", "20180206", "sunny", "news 005 Contentnews news 005 Contentnews news 005 Contentnews news 005 Contentnews ", Arrays.asList("usa"), false, true);
        addNewsDetail("n006", "/image1", "news 006 title", "ReadWrite", "20180206", "tom", "news 006 Contentnews news 006 Contentnews news 006 Contentnews news 006 Contentnews ", Arrays.asList("usa"), false, false);
        addNewsDetail("n007", "/image1", "news 007 title", "ReadWrite", "20180206", "hanmeimei", "news 007 Contentnews news 007 Contentnews news 007 Contentnews news 007 Contentnews ", Arrays.asList("usa"), false, false);
        addNewsDetail("n008", "/image1", "news 008 title", "ReadWrite", "20180206", "sunny", "news 008 Contentnews news 008 Contentnews news 008 Contentnews news 008 Contentnews ", Arrays.asList("usa"), false, false);
        addNewsDetail("n009", "/image1", "news 009 title", "ReadWrite", "20180206", "lilei", "news 009 Contentnews news 009 Contentnews news 009 Contentnews news 009 Contentnews ", Arrays.asList("usa"), false, false);
        addNewsDetail("n010", "/image1", "news 010 title", "ReadWrite", "20180206", "mike", "news 010 Contentnews news 010 Contentnews news 010 Contentnews news 010 Contentnews ", Arrays.asList("usa"), false, false);
    }

    private NewsRepository() {

    }

    private static void addNews(String newId, String bannerUrl, String title, String author, String pubDate, boolean isReaded, boolean isSaved) {
        News news = new News(newId, bannerUrl, title, author, pubDate, isReaded, isSaved);
        NEWSES_SERVICE_DATA.put(news.getNewId(), news);
    }

    private static void addNewsDetail(String newId, String bannerUrl, String title, String publisher, String pubDate,
                                      String author, String content, List<String> tagList, boolean isReaded, boolean isSaved) {
        NewsDetail newsDetail = new NewsDetail(newId, bannerUrl, title, publisher, author, pubDate, content, tagList, isReaded, isSaved);
        NEWSDETAILS_SERVICE_DATA.put(newsDetail.getNewId(), newsDetail);
    }

    public static NewsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getNewsListBySubscribe(String publishId, final LoadNewsListCallback loadNewsListCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> newsList = new ArrayList<>(NEWSES_SERVICE_DATA.values());
                loadNewsListCallback.onNewsLoaded(newsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getNewsListByTag(String tagId, final LoadNewsListCallback loadNewsListCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> newsList = new ArrayList<>(NEWSES_SERVICE_DATA.values());
                loadNewsListCallback.onNewsLoaded(newsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getNewsListByFilter(String filterId, final LoadNewsListCallback loadNewsListCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> newsList = new ArrayList<>(NEWSES_SERVICE_DATA.values());
                loadNewsListCallback.onNewsLoaded(newsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getNewsListBySearch(String searchString, final LoadNewsListCallback loadNewsListCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> newsList = new ArrayList<>(NEWSES_SERVICE_DATA.values());
                loadNewsListCallback.onNewsLoaded(newsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void markNewsesReadedByNewsId(final List<String> newsIdList) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> newsList = new ArrayList<>(NEWSES_SERVICE_DATA.values());
                for (News news : newsList) {
                    if (newsIdList.contains(news.getNewId())) {
                        news.setIsReaded(true);
                    }
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getNewsDetailByNewsId(final String newsId, final LoadNewsDetailCallBack loadNewsDetailCallBack) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<NewsDetail> newsDetailList = new ArrayList<>(NEWSDETAILS_SERVICE_DATA.values());
                for (NewsDetail newsDetail : newsDetailList) {
                    if (newsId.equals(newsDetail.getNewId())) {
                        loadNewsDetailCallBack.onNewsDetailLoaded(newsDetail);
                    }
                }

            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getSavedNewsList(final LoadNewsListCallback loadNewsListCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> resultNewsList = new ArrayList<>();
                List<NewsDetail> newsList = new ArrayList<>(NEWSDETAILS_SERVICE_DATA.values());
                for (News news : newsList) {
                    if (news.isIsSaved()) {
                        resultNewsList.add(news);
                    }
                }
                loadNewsListCallback.onNewsLoaded(resultNewsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveNewsById(final String newsId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<NewsDetail> newsList = new ArrayList<>(NEWSDETAILS_SERVICE_DATA.values());
                for (News news : newsList) {
                    if (news.getNewId().equals(newsId)) {
                        news.setIsSaved(true);
                    }
                }
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
