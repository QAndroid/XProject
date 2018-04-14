package workshop1024.com.xproject.model.news.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.publisher.Publisher;

/**
 * 新闻远程数据源
 */
public class NewsRepository implements NewsDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, News> NEWSES_SERVICE_DATA;

    private static NewsRepository INSTANCE;

    static {
        NEWSES_SERVICE_DATA = new LinkedHashMap<>(2);
        addNews("n001","p001","","news 001 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n002","p001","","news 002 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n003","p002","","news 003 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n004","p002","","news 004 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n005","p002","","news 005 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n006","p003","","news 006 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n007","p003","","news 007 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);

        addNews("n008","p004","","news 008 title","sunny","20120407", Arrays.asList("android","phone"),false);

        addNews("n009","p005","","news 009 title","lilei","20161006", Arrays.asList("android","phone"),true);
        addNews("n010","p005","","news 010 title","mike","20181206", Arrays.asList("android","phone"),false);

        addNews("n011","p006","","news 011 title","tom","20130406", Arrays.asList("android","phone"),true);

        addNews("n012","p007","","news 012 title","hanmeimei","20160406", Arrays.asList("android","phone"),false);
        addNews("n013","p007","","news 013 title","sunny","20150406", Arrays.asList("android","phone"),false);
        addNews("n014","p007","","news 014 title","tom","20010406", Arrays.asList("android","phone"),true);
        addNews("n015","p007","","news 015 title","tom","20180406", Arrays.asList("android","phone"),false);

        addNews("n016","p008","","news 016 title","tom","20150106", Arrays.asList("android","phone"),false);
        addNews("n017","p008","","news 017 title","lilei","20180120", Arrays.asList("android","phone"),true);

        addNews("n018","p009","","news 018 title","mike","20180426", Arrays.asList("android","phone"),false);



        addNews("n101","p101","","news 101 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n102","p101","","news 102 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n103","p102","","news 103 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n104","p102","","news 104 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n105","p102","","news 105 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n106","p103","","news 106 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n107","p103","","news 107 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);

        addNews("n108","p104","","news 108 title","sunny","20120407", Arrays.asList("android","phone"),false);

        addNews("n109","p105","","news 109 title","lilei","20161006", Arrays.asList("android","phone"),true);
        addNews("n110","p105","","news 110 title","mike","20181206", Arrays.asList("android","phone"),false);

        addNews("n111","p106","","news 111 title","tom","20130406", Arrays.asList("android","phone"),true);

        addNews("n112","p107","","news 112 title","hanmeimei","20160406", Arrays.asList("android","phone"),false);
        addNews("n113","p107","","news 113 title","sunny","20150406", Arrays.asList("android","phone"),false);
        addNews("n114","p107","","news 114 title","tom","20010406", Arrays.asList("android","phone"),true);
        addNews("n115","p107","","news 115 title","tom","20180406", Arrays.asList("android","phone"),false);

        addNews("n116","p108","","news 116 title","tom","20150106", Arrays.asList("android","phone"),false);
        addNews("n117","p108","","news 117 title","lilei","20180120", Arrays.asList("android","phone"),true);

        addNews("n118","p109","","news 118 title","mike","20180426", Arrays.asList("android","phone"),false);


        addNews("n201","p201","","news 101 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n202","p201","","news 102 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n203","p202","","news 103 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n204","p202","","news 104 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n205","p202","","news 105 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n206","p203","","news 106 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n207","p203","","news 107 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);

        addNews("n208","p204","","news 108 title","sunny","20120407", Arrays.asList("android","phone"),false);

        addNews("n209","p205","","news 209 title","lilei","20161006", Arrays.asList("android","phone"),true);
        addNews("n210","p205","","news 210 title","mike","20181206", Arrays.asList("android","phone"),false);

        addNews("n211","p206","","news 211 title","tom","20130406", Arrays.asList("android","phone"),true);

        addNews("n212","p207","","news 212 title","hanmeimei","20160406", Arrays.asList("android","phone"),false);
        addNews("n213","p207","","news 213 title","sunny","20150406", Arrays.asList("android","phone"),false);
        addNews("n214","p207","","news 214 title","tom","20010406", Arrays.asList("android","phone"),true);
        addNews("n215","p207","","news 215 title","tom","20180406", Arrays.asList("android","phone"),false);

        addNews("n216","p208","","news 216 title","tom","20150106", Arrays.asList("android","phone"),false);
        addNews("n217","p208","","news 217 title","lilei","20180120", Arrays.asList("android","phone"),true);

        addNews("n218","p209","","news 218 title","mike","20180426", Arrays.asList("android","phone"),false);


        addNews("n301","p301","","news 301 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n302","p301","","news 302 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n303","p302","","news 303 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n304","p302","","news 304 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n305","p302","","news 305 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n306","p303","","news 306 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n307","p303","","news 307 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);

        addNews("n308","p304","","news 308 title","sunny","20120407", Arrays.asList("android","phone"),false);

        addNews("n309","p305","","news 309 title","lilei","20161006", Arrays.asList("android","phone"),true);
        addNews("n310","p305","","news 310 title","mike","20181206", Arrays.asList("android","phone"),false);

        addNews("n311","p306","","news 311 title","tom","20130406", Arrays.asList("android","phone"),true);

        addNews("n312","p307","","news 312 title","hanmeimei","20160406", Arrays.asList("android","phone"),false);
        addNews("n313","p307","","news 313 title","sunny","20150406", Arrays.asList("android","phone"),false);
        addNews("n314","p307","","news 314 title","tom","20010406", Arrays.asList("android","phone"),true);
        addNews("n315","p307","","news 315 title","tom","20180406", Arrays.asList("android","phone"),false);

        addNews("n316","p308","","news 316 title","tom","20150106", Arrays.asList("android","phone"),false);
        addNews("n317","p308","","news 317 title","lilei","20180120", Arrays.asList("android","phone"),true);

        addNews("n318","p309","","news 318 title","mike","20180426", Arrays.asList("android","phone"),false);


        addNews("n401","p401","","news 401 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n402","p401","","news 402 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n403","p402","","news 403 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n404","p402","","news 404 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n405","p402","","news 405 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n406","p403","","news 406 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n407","p403","","news 407 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);

        addNews("n408","p404","","news 408 title","sunny","20120407", Arrays.asList("android","phone"),false);


        addNews("n601","p606","","news 601 title","tom","20180406", Arrays.asList("android","phone"),false);
        addNews("n602","p606","","news 602 title","mike","20180405", Arrays.asList("ios","phone"),true);

        addNews("n603","p608","","news 603 title","lilei","20180306", Arrays.asList("china"),false);
        addNews("n604","p608","","news 604 title","hanmeimei","20180206", Arrays.asList("usa"),false);
        addNews("n605","p608","","news 605 title","sunny","20170406", Arrays.asList("uk"),true);

        addNews("n606","p609","","news 606 title","tom","20160406", Arrays.asList("apple","orange"),true);
        addNews("n607","p609","","news 607 title","hanmeimei","20110806", Arrays.asList("android","phone"),true);
    }

    private NewsRepository() {

    }

    private static void addNews(String newId, String publisherId, String bannerUrl, String title, String author,
                                     String pubDate, List<String> tagList, Boolean isReaded){
        News news = new News(newId,publisherId,bannerUrl,title,author,pubDate,tagList,isReaded);
        NEWSES_SERVICE_DATA.put(news.getPublisherId(), news);
    }

    public static NewsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getNewses(final LoadNewsCallback loadNewsCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNewsCallback.onNewsLoaded(new ArrayList<>(NEWSES_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getNewsByPublishers(final List<Publisher> publisherList, final LoadNewsCallback loadNewsCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<News> resultNewsList = new ArrayList<>();
                List<News> newsList = (List<News>) NEWSES_SERVICE_DATA.values();
                for(News news : newsList){
                    for(Publisher publisher : publisherList){
                        if(news.getPublisherId().equals(publisher.getPublisherId())){
                            resultNewsList.add(news);
                        }
                    }
                }

                loadNewsCallback.onNewsLoaded(resultNewsList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
