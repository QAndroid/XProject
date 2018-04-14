package workshop1024.com.xproject.model.subscribe.source;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.subscribe.Subscribe;

/**
 * 已订阅发布者远程数据源
 */
public class SubscribeRepository implements SubscribeDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, Subscribe> SUBSCRIBE_SERVICE_DATA;

    private static SubscribeRepository INSTANCE;

    static {
        SUBSCRIBE_SERVICE_DATA = new LinkedHashMap<>(2);
        addSubscribe("p001", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712210748&di=2a83bb6619bfa8429c605669a2186905&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F18%2F19%2F87%2F09f58PICBJU_1024.jpg", "The Tech", "", "500+", true);
        addSubscribe("p002", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712210898&di=f6d61e27aa68aa00978f7ee4bdc97cdd&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F60%2F67%2F09D58PICzbh_1024.jpg", "Engadget", "", "300+", true);
        addSubscribe("p003", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213014&di=362b855b945ae489e31d8895f3bbee85&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F77%2F08%2F56q58PICitI_1024.jpg", "Lifehacker", "", "200+", true);

        addSubscribe("p107", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213305&di=7f92f8290b7e62044239e6adf2e33c5d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018ac455ba317d32f87528a1dbc0e5.jpg", "今日头条", "", "200+", true);
        addSubscribe("p108", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213372&di=51e79f1e847b60e7174c89fc567590bd&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F96%2F59%2F05f58PICQ7K_1024.jpg", "腾讯新闻", "", "200+", true);

        addSubscribe("p205", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213636&di=b168a1cc86b096293c9d3458f9c939ee&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D08bd1273d7c451dae2fb04a8de943813%2F80cb39dbb6fd5266c37eb639a118972bd4073641.jpg", "FOX News", "", "700+", true);
        addSubscribe("p206", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712216287&di=d467bdd8f27042213ec7e5ef2c25daae&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F84%2F71%2F24k58PIC7Uf_1024.jpg", "NRP News", "", "100+", true);

        addSubscribe("p301", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712219202&di=3080aab02628a1ae3c7361677d0cecb9&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F80%2F90%2F57D58PICgCd_1024.jpg", "zen habits", "", "500+", true);
        addSubscribe("p309", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712219837&di=ccda689f3a5cd851aad65b1d88fa1fde&imgtype=0&src=http%3A%2F%2Fimage.tupian114.com%2F20140416%2F14172759.png", "NYT", "", "100+", true);


        addSubscribe("p608", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712220441&di=1cfb32f694c02e68f14991d44d339c80&imgtype=0&src=http%3A%2F%2Fpic2.16pic.com%2F00%2F19%2F93%2F16pic_1993513_b.jpg", "一起游戏", "", "200+", true);
        addSubscribe("p609", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524307078&di=d66eb67bbd7d674400902d3753ffaeab&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F59%2F59%2F95p58PICwYS_1024.jpg", "Penny Arcade", "", "100+", true);
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
    public static SubscribeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SubscribeRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getSubscribes(final LoadSubscribesCallback loadSubscribesCallback) {
        Log.i("XProject", "SubscribeRepository getSubscribes");
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
        Log.i("XProject", "SubscribeRepository unSubscribeById =" + subscribeId);
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
        Log.i("XProject", "SubscribeRepository reNameSubscribeById =" + subscribeId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Subscribe subscribe = SUBSCRIBE_SERVICE_DATA.get(subscribeId);
                subscribe.setCustomName(newNameString);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
