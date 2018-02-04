package workshop1024.com.xproject.model.publisher.source.remote;

import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;

/**
 * 发布者远程数据源
 */
public class PublisherRemoteDataSource implements PublisherDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 3000;

    private static Map<String, Publisher> PUBLISHERS_SERVICE_DATA;

    private static PublisherRemoteDataSource INSTANCE;

    static {
        //TODO 如何本地提供Mock环境
        PUBLISHERS_SERVICE_DATA = new LinkedHashMap<>(2);

        addPublisher("001", "Tech", "English", "/imag1", "The Tech", "970601 subscribers", false, "/imag1", "500+");
        addPublisher("002", "Tech", "English", "/imag1", "Engadget", "1348433 subscribers", true, "/imag1", "300+");
        addPublisher("003", "Tech", "English", "/imag1", "Lifehacker", "934273 subscribers", false, "/imag1", "200+");
        addPublisher("004", "Tech", "English", "/imag1", "ReadWrite", "254332 subscribers", false, "/imag1", "400+");
        addPublisher("005", "Tech", "English", "/imag1", "Digital Trends", "145694 subscribers", true, "/imag1",
                "700+");
        addPublisher("006", "Tech", "English", "/imag1", "Business Insider", "331892 subscribers", false, "/imag1",
                "100+");
        addPublisher("007", "Tech", "中文", "/imag1", "月光博客", "254321 subscribers", false, "/imag1", "200+");
        addPublisher("008", "Tech", "中文", "/imag1", "36氪", "125345 subscribers", true, "/imag1", "200+");
        addPublisher("009", "Tech", "English", "/imag1", "TechCrunch", "994287 subscribers", false, "/imag1", "100+");


        addPublisher("101", "News", "English", "/imag1", "The News", "970601 subscribers", false, "/imag1", "500+");
        addPublisher("102", "News", "English", "/imag1", "Engadget", "1348433 subscribers", true, "/imag1", "300+");
        addPublisher("103", "News", "English", "/imag1", "Lifehacker", "934273 subscribers", false, "/imag1", "200+");
        addPublisher("104", "News", "English", "/imag1", "ReadWrite", "254332 subscribers", false, "/imag1", "400+");
        addPublisher("105", "News", "English", "/imag1", "Digital Trends", "145694 subscribers", true, "/imag1",
                "700+");
        addPublisher("106", "News", "English", "/imag1", "Business Insider", "331892 subscribers", false, "/imag1",
                "100+");
        addPublisher("107", "News", "中文", "/imag1", "今日头条", "254321 subscribers", false, "/imag1", "200+");
        addPublisher("108", "News", "中文", "/imag1", "腾讯新闻", "125345 subscribers", true, "/imag1", "200+");
        addPublisher("109", "News", "English", "/imag1", "TechCrunch", "994287 subscribers", false, "/imag1", "100+");

        addPublisher("201", "Business", "English", "/imag1", "The Business", "970601 subscribers", true, "/imag1",
                "500+");
        addPublisher("202", "Business", "English", "/imag1", "The New York Times", "1348433 subscribers", true,
                "/imag1", "300+");
        addPublisher("203", "Business", "English", "/imag1", "OZY", "934273 subscribers", false, "/imag1", "200+");
        addPublisher("204", "Business", "English", "/imag1", "ABC News", "254332 subscribers", false, "/imag1", "400+");
        addPublisher("205", "Business", "English", "/imag1", "FOX News", "145694 subscribers", false, "/imag1",
                "700+");
        addPublisher("206", "Business", "English", "/imag1", "NRP News", "331892 subscribers", false, "/imag1",
                "100+");
        addPublisher("207", "Business", "中文", "/imag1", "财经周刊", "254321 subscribers", true, "/imag1", "200+");
        addPublisher("208", "Business", "中文", "/imag1", "交易时刻", "125345 subscribers", true, "/imag1", "200+");
        addPublisher("209", "Business", "English", "/imag1", "BBC", "994287 subscribers", false, "/imag1", "100+");

        addPublisher("301", "Health", "English", "/imag1", "zen habits", "970601 subscribers", false, "/imag1", "500+");
        addPublisher("302", "Health", "English", "/imag1", "Skinnytaste", "1348433 subscribers", true, "/imag1",
                "300+");
        addPublisher("303", "Health", "English", "/imag1", "Lifehacker", "934273 subscribers", false, "/imag1", "200+");
        addPublisher("304", "Health", "English", "/imag1", "Mark's Daily Apple", "254332 subscribers", false,
                "/imag1", "400+");
        addPublisher("305", "Health", "English", "/imag1", "Oh She Glows", "145694 subscribers", true, "/imag1",
                "700+");
        addPublisher("306", "Health", "English", "/imag1", "Health", "331892 subscribers", true, "/imag1",
                "100+");
        addPublisher("307", "Health", "中文", "/imag1", "健康之路", "254321 subscribers", false, "/imag1", "200+");
        addPublisher("308", "Health", "中文", "/imag1", "星星点灯", "125345 subscribers", true, "/imag1", "200+");
        addPublisher("309", "Health", "English", "/imag1", "NYT", "994287 subscribers", true, "/imag1", "100+");


        addPublisher("401", "Gaming", "English", "/imag1", "The Gaming", "970601 subscribers", false, "/imag1",
                "500+");
        addPublisher("402", "Gaming", "English", "/imag1", "Polygon", "1348433 subscribers", true, "/imag1", "300+");
        addPublisher("403", "Gaming", "English", "/imag1", "Kotaku", "934273 subscribers", false, "/imag1", "200+");
        addPublisher("404", "Gaming", "English", "/imag1", "Joystiq", "254332 subscribers", false, "/imag1", "400+");
        addPublisher("505", "Gaming", "English", "/imag1", "IndieGames", "145694 subscribers", true, "/imag1",
                "700+");
        addPublisher("606", "Gaming", "English", "/imag1", "Game Life", "331892 subscribers", false, "/imag1",
                "100+");
        addPublisher("607", "Gaming", "中文", "/imag1", "电竞世界", "254321 subscribers", false, "/imag1",
                "200+");
        addPublisher("608", "Gaming", "中文", "/imag1", "一起游戏", "125345 subscribers", true, "/imag1", "200+");
        addPublisher("609", "Gaming", "English", "/imag1", "Penny Arcade", "994287 subscribers", false, "/imag1",
                "100+");
    }

    private PublisherRemoteDataSource() {

    }

    private static void addPublisher(String id, String type, String language, String iconUrl, String name, String
            subscribeNum, boolean isSubscribed, String bannerUrl, String newsCount) {
        Publisher newPublisher = new Publisher(id, type, language, iconUrl, name, subscribeNum, isSubscribed,
                bannerUrl, newsCount);
        PUBLISHERS_SERVICE_DATA.put(newPublisher.getId(), newPublisher);
    }

    public static PublisherDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherRemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public void getPublishers(final LoadPublishersCallback loadPublishersCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadPublishersCallback.onPublishersLoaded(new ArrayList<>(PUBLISHERS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getPublishersByType(String type, LoadPublishersCallback loadPublishersCallback) {

    }

    @Override
    public void getPublishersByLanguage(String language, LoadPublishersCallback loadPublishersCallback) {

    }

    @Override
    public void deleteAllPublishers() {

    }

    @Override
    public void savePublisher(Publisher publisher) {

    }

    @Override
    public void subscribePublisher(Publisher publisher) {
        Publisher unSubscribedPublisher = new Publisher(publisher.getId(), publisher.getType(), publisher.getLanguage()
                , publisher.geticonUrl(), publisher.getName(), publisher.getSubscribeNum(), true, publisher
                .getBannerUrl(), publisher.getNewsCount());
        PUBLISHERS_SERVICE_DATA.put(publisher.getId(), unSubscribedPublisher);
    }

    @Override
    public void unSubscribePublisher(Publisher publisher) {
        Publisher subscribedPublisher = new Publisher(publisher.getId(), publisher.getType(), publisher.getLanguage()
                , publisher.geticonUrl(), publisher.getName(), publisher.getSubscribeNum(), false, publisher
                .getBannerUrl(), publisher.getNewsCount());
        PUBLISHERS_SERVICE_DATA.put(publisher.getId(), subscribedPublisher);
    }

    @Override
    public void refreshPublishers() {

    }
}
