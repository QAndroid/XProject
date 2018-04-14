package workshop1024.com.xproject.model.tag.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.news.News;
import workshop1024.com.xproject.model.news.source.NewsDataSource;
import workshop1024.com.xproject.model.publisher.Publisher;
import workshop1024.com.xproject.model.publisher.source.PublisherDataSource;
import workshop1024.com.xproject.model.tag.Tag;

/**
 * 新闻标记数据源
 */
public class TagRepository implements TagDataSource {
    //新闻标记数据源单例对象
    private static TagRepository INSTANCE = null;

    //发布者数据源
    private PublisherDataSource mPublisherDataSource;
    //新闻数据源
    private NewsDataSource mNewsDataSource;

    //内存中缓存的新闻标记信息
    private Map<String, Tag> mCachedTagMaps;
    //内存和本地缓存新闻标记数据是否为“脏”数据
    private boolean mIsCachedDirty;

    /**
     * 新闻标记数据源构造方法
     *
     * @param publisherDataSource 发布者数据源
     * @param newsDataSource      新闻数据源
     */
    private TagRepository(PublisherDataSource publisherDataSource, NewsDataSource newsDataSource) {
        mPublisherDataSource = publisherDataSource;
        mNewsDataSource = newsDataSource;
    }

    /**
     * 获取新闻标记数据源单例对象
     *
     * @param publisherDataSource 发布者数据源
     * @param newsDataSource      新闻数据源
     * @return
     */
    public static TagRepository getInstance(PublisherDataSource publisherDataSource, NewsDataSource newsDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TagRepository(publisherDataSource, newsDataSource);
        }
        return INSTANCE;
    }


    /**
     * 销毁发布者新闻数据源单例对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTag(final LoadTagCallback loadTagCallback) {
//        mPublisherDataSource.getSubscribes(new PublisherDataSource.LoadPublishersCallback() {
//            @Override
//            public void onPublishersLoaded(List<Publisher> publisherList) {
//                mNewsDataSource.getNewsByPublishers(publisherList, new NewsDataSource.LoadNewsCallback() {
//                    @Override
//                    public void onNewsLoaded(List<News> newsList) {
//                        List<Tag> tagList = new ArrayList<>();
//
//                        //将获取已发布新闻过滤出相同的Tag，每个Tag获取第一个未读新闻的图片，统计未读新闻的数量
//                        for (News news : newsList) {
//                            //如果新闻未阅读，则遍历创建Tag
//                            if (!news.getIsReaded()) {
//                                for (String tagName : news.getTagList()) {
//                                    //创建新Tag，并且创建时第一个未阅读的新闻Banner为头图
//                                    Tag tag = new Tag(tagName, news.getBannerUrl());
//                                    //如果Tag不存在，则添加到Tag集合中，并且未读新闻为1；如果Tag已存在，则增加未读文章数量
//                                    if (!tagList.contains(tag)) {
//                                        tag.setUnReadedCount(1);
//                                        tagList.add(tag);
//                                    } else {
//                                        tag.setUnReadedCount(tag.getUnReadedCount() + 1);
//                                    }
//                                }
//                            }
//                        }
//
//                        loadTagCallback.onTagInfoLoaded(tagList);
//                    }
//
//                    @Override
//                    public void onDataNotAvaiable() {
//                        loadTagCallback.onDataNotAvailable();
//                    }
//                });
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                loadTagCallback.onDataNotAvailable();
//            }
//        });
    }
}
