package workshop1024.com.xproject.model.sub.tag.source;

import android.os.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.sub.tag.Tag;

public class TagRepository implements TagDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private static Map<String, Tag> TAG_SERVICE_DATA;

    private static TagRepository INSTANCE;

    static {
        TAG_SERVICE_DATA = new LinkedHashMap<>(2);
        addTag("t001", "/image1", "News", "10");
        addTag("t002", "/image1", "Android", "99");
        addTag("t003", "/image1", "Deals", "24");
        addTag("t004", "/image1", "Google", "45");

        addTag("t101", "/image1", "Books", "13");
        addTag("t102", "/image1", "Popular", "56");

        addTag("t201", "/image1", "Wellness", "75");
        addTag("t202", "/image1", "Samsung", "78");

        addTag("t301", "/image1", "Travel", "23");
    }

    private static void addTag(String tagId, String iconUrl, String name, String unreadCount) {
        Tag tag = new Tag(tagId, iconUrl, name, unreadCount);
        TAG_SERVICE_DATA.put(tag.getInfoId(), tag);
    }

    /**
     * 获取已订阅发布者新闻Tag信息远程数据源单例对象
     *
     * @return 远程数据源对象
     */
    public static TagRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TagRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getTags(final LoadTagsCallback loadTagsCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Tag> tagList = new ArrayList<>(TAG_SERVICE_DATA.values());
                loadTagsCallback.onTagsLoaded(tagList);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
