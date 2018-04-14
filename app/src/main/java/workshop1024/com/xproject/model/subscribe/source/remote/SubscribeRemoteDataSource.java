package workshop1024.com.xproject.model.subscribe.source.remote;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.subscribe.Subscribe;
import workshop1024.com.xproject.model.subscribe.source.SubscribeDataSource;

/**
 * 已订阅发布者远程数据源
 */
public class SubscribeRemoteDataSource implements SubscribeDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, Subscribe> SUBSCRIBE_SERVICE_DATA;

    private static SubscribeRemoteDataSource INSTANCE;

    static {
        SUBSCRIBE_SERVICE_DATA = new LinkedHashMap<>(2);
        addSubscribe("p001", "/imag1", "The Tech", "", "500+", true);
        addSubscribe("p002", "/imag1", "Engadget", "", "300+", true);
        addSubscribe("p003", "/imag1", "Lifehacker", "", "200+", true);

        addSubscribe("p107", "/imag1", "今日头条", "", "200+", true);
        addSubscribe("p108", "/imag1", "腾讯新闻", "", "200+", true);

        addSubscribe("p205", "/imag1", "FOX News", "", "700+", true);
        addSubscribe("p206", "/imag1", "NRP News", "", "100+", true);

        addSubscribe("p301", "/imag1", "zen habits", "", "500+", true);
        addSubscribe("p309", "/imag1", "NYT", "", "100+", true);


        addSubscribe("p608", "/imag1", "一起游戏", "", "200+", true);
        addSubscribe("p609", "/imag1", "Penny Arcade", "", "100+", true);
    }

    /**
     * 添加已订阅发布者
     *
     * @param subscribeId  已订阅发布者id
     * @param iconUrl      已订阅发布者图标URL
     * @param name         已订阅发布者名称
     * @param customName   已订阅发布者自定义名称
     * @param unreadCount  已订阅发布者未阅读新闻数量
     * @param isSubscribed 已订阅发布者是否订阅
     */
    private static void addSubscribe(String subscribeId, String iconUrl, String name, String customName,
                                     String unreadCount, boolean isSubscribed) {
        Subscribe subscribe = new Subscribe(subscribeId, iconUrl, name, customName, unreadCount, isSubscribed);
        SUBSCRIBE_SERVICE_DATA.put(subscribe.getSubscribeId(), subscribe);
    }

    /**
     * 获取已订阅发布者远程数据源单例对象
     *
     * @return 远程数据源对象
     */
    public static SubscribeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SubscribeRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getSubscribes(final LoadSubscribesCallback loadSubscribesCallback) {
        Log.i("XProject","SubscribeRemoteDataSource getSubscribes");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Subscribe> subscribeList = new ArrayList<>(SUBSCRIBE_SERVICE_DATA.values());
                //FIXME 为什么远程的回调不放在主线程中执行
                loadSubscribesCallback.onPublishersLoaded(subscribeList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void unSubscribeById(final String subscribeId) {
        Log.i("XProject","SubscribeRemoteDataSource unSubscribeById =" + subscribeId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SUBSCRIBE_SERVICE_DATA.remove(subscribeId);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void reNameSubscribeById(final String subscribeId, final String newNameString) {
        Log.i("XProject","SubscribeRemoteDataSource reNameSubscribeById =" + subscribeId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Subscribe subscribe = SUBSCRIBE_SERVICE_DATA.get(subscribeId);
                subscribe.setCustomName(newNameString);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void deleteAllSubscribes() {
        Log.i("XProject","SubscribeRemoteDataSource deleteAllSubscribes");
        //用于删除内存和本地缓存数据接口方法，远程数据源不实现
    }

    @Override
    public void saveSubscribe(Subscribe subscribe) {
        Log.i("XProject","SubscribeRemoteDataSource saveSubscribe");
        //用于保存内存和本地缓存数据接口方法，远程数据源不实现
    }

    @Override
    public void refreshSubscribes() {
        Log.i("XProject","SubscribeRemoteDataSource refreshSubscribes");
        //用于刷新内存和本地缓存数据接口方法，远程数据源不实现
    }
}
