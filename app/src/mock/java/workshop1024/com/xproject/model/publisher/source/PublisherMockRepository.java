package workshop1024.com.xproject.model.publisher.source;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.publisher.Publisher;

public class PublisherMockRepository implements PublisherDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, Publisher> PUBLISHERS_SERVICE_DATA;

    private static PublisherMockRepository INSTANCE;

    static {
        //TODO 如何本地提供Mock环境
        PUBLISHERS_SERVICE_DATA = new LinkedHashMap<>(2);

        addPublisher("p001", "Tech", "English", "/imag1", "The Tech-mock", "970601 subscribers", false);
        addPublisher("p002", "Tech", "English", "/imag1", "Engadget", "1348433 subscribers", false);
        addPublisher("p003", "Tech", "English", "/imag1", "Lifehacker", "934273 subscribers", false);
        addPublisher("p004", "Tech", "English", "/imag1", "ReadWrite-mock", "254332 subscribers", false);
        addPublisher("p005", "Tech", "English", "/imag1", "Digital Trends", "145694 subscribers", false);
        addPublisher("p006", "Tech", "English", "/imag1", "Business Insider", "331892 subscribers", false);
        addPublisher("p007", "Tech", "中文", "/imag1", "月光博客", "254321 subscribers", false);
        addPublisher("p008", "Tech", "中文", "/imag1", "36氪", "125345 subscribers", false);
        addPublisher("p009", "Tech", "English", "/imag1", "TechCrunch-mock", "994287 subscribers", false);


        addPublisher("p101", "News", "English", "/imag1", "The News", "970601 subscribers", false);
        addPublisher("p102", "News", "English", "/imag1", "Engadget", "1348433 subscribers", false);
        addPublisher("p103", "News", "English", "/imag1", "Lifehacker-mock", "934273 subscribers", false);
        addPublisher("p104", "News", "English", "/imag1", "ReadWrite", "254332 subscribers", false);
        addPublisher("p105", "News", "English", "/imag1", "Digital Trends", "145694 subscribers", false);
        addPublisher("p106", "News", "English", "/imag1", "Business Insider", "331892 subscribers", false);
        addPublisher("p107", "News", "中文", "/imag1", "今日头条", "254321 subscribers", false);
        addPublisher("p108", "News", "中文", "/imag1", "腾讯新闻", "125345 subscribers", false);
        addPublisher("p109", "News", "English", "/imag1", "TechCrunch", "994287 subscribers", false);

        addPublisher("p201", "Business", "English", "/imag1", "The Business", "970601 subscribers", false);
        addPublisher("p202", "Business", "English", "/imag1", "The New York Times", "1348433 subscribers", false);
        addPublisher("p203", "Business", "English", "/imag1", "OZY-mock", "934273 subscribers", false);
        addPublisher("p204", "Business", "English", "/imag1", "ABC News", "254332 subscribers", false);
        addPublisher("p205", "Business", "English", "/imag1", "FOX News", "145694 subscribers", false);
        addPublisher("p206", "Business", "English", "/imag1", "NRP News", "331892 subscribers", false);
        addPublisher("p207", "Business", "中文", "/imag1", "财经周刊", "254321 subscribers", false);
        addPublisher("p208", "Business", "中文", "/imag1", "交易时刻", "125345 subscribers", false);
        addPublisher("p209", "Business", "English", "/imag1", "BBC", "994287 subscribers", false);

        addPublisher("p301", "Health", "English", "/imag1", "zen habits", "970601 subscribers", false);
        addPublisher("p302", "Health", "English", "/imag1", "Skinnytaste-mock", "1348433 subscribers", false);
        addPublisher("p303", "Health", "English", "/imag1", "Lifehacker", "934273 subscribers", false);
        addPublisher("p304", "Health", "English", "/imag1", "Mark's Daily Apple", "254332 subscribers", false);
        addPublisher("p305", "Health", "English", "/imag1", "Oh She Glows", "145694 subscribers", false);
        addPublisher("p306", "Health", "English", "/imag1", "Health", "331892 subscribers", false);
        addPublisher("p307", "Health", "中文", "/imag1", "健康之路", "254321 subscribers", false);
        addPublisher("p308", "Health", "中文", "/imag1", "星星点灯", "125345 subscribers", false);
        addPublisher("p309", "Health", "English", "/imag1", "NYT-mock", "994287 subscribers", false);


        addPublisher("p401", "Gaming", "English", "/imag1", "The Gaming", "970601 subscribers", false);
        addPublisher("p402", "Gaming", "English", "/imag1", "Polygon", "1348433 subscribers", false);
        addPublisher("p403", "Gaming", "English", "/imag1", "Kotaku", "934273 subscribers", false);
        addPublisher("p404", "Gaming", "English", "/imag1", "Joystiq", "254332 subscribers", false);
        addPublisher("p505", "Gaming", "English", "/imag1", "IndieGames", "145694 subscribers", false);
        addPublisher("p606", "Gaming", "English", "/imag1", "Game Life", "331892 subscribers", false);
        addPublisher("p607", "Gaming", "中文", "/imag1", "电竞世界", "254321 subscribers", false);
        addPublisher("p608", "Gaming", "中文", "/imag1", "一起游戏", "125345 subscribers", false);
        addPublisher("p609", "Gaming", "English", "/imag1", "Penny Arcade", "994287 subscribers", false);
    }

    private PublisherMockRepository() {

    }

    private static void addPublisher(String publisherId, String type, String language, String iconUrl,
                                     String name, String subscribeNum, boolean isSubscribed) {
        Publisher newPublisher = new Publisher(publisherId, type, language, iconUrl, name,
                subscribeNum, isSubscribed);
        PUBLISHERS_SERVICE_DATA.put(newPublisher.getPublisherId(), newPublisher);
    }

    public static PublisherDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherMockRepository();
        }

        return INSTANCE;
    }

    @Override
    public void getPublishersByType(final String type, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherRemoteDataSource getPublishersByType =" + type);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Publisher> typedPublishers = new ArrayList<>();
                List<Publisher> publisherList = new ArrayList<>(PUBLISHERS_SERVICE_DATA.values());
                for (Publisher publisher : publisherList) {
                    //获取指定类型的发布者
                    if (publisher.getType().equals(type)) {
                        typedPublishers.add(publisher);
                    }
                }
                loadPublishersCallback.onPublishersLoaded(typedPublishers);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getPublishersByLanguage(final String language, final LoadPublishersCallback loadPublishersCallback) {
        Log.i("XProject", "PublisherRemoteDataSource getPublishersByLanguage =" + language);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Publisher> languagedPublishers = new ArrayList<>();
                List<Publisher> publisherList = new ArrayList<>(PUBLISHERS_SERVICE_DATA.values());
                for (Publisher publisher : publisherList) {
                    //获取指定语言的发布者
                    if (publisher.getLanguage().equals(language)) {
                        languagedPublishers.add(publisher);
                    }
                }
                loadPublishersCallback.onPublishersLoaded(languagedPublishers);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void subscribePublisherById(String publisherId) {
        Log.i("XProject", "PublisherRemoteDataSource subscribePublisherById =" + publisherId);
        Publisher subscribedPublisher = PUBLISHERS_SERVICE_DATA.get(publisherId);
        subscribedPublisher.setIsSubscribed(true);
    }

    @Override
    public void unSubscribePublisherById(String publisherId) {
        Log.i("XProject", "PublisherRemoteDataSource unSubscribePublisherById =" + publisherId);
        Publisher subscribedPublisher = PUBLISHERS_SERVICE_DATA.get(publisherId);
        subscribedPublisher.setIsSubscribed(false);
    }
}
