package workshop1024.com.xproject.model.tag.source;

import java.util.List;

import workshop1024.com.xproject.model.tag.Tag;

public interface TagDataSource {
    /**
     * 获取已订阅的发布者新闻Taa信息
     *
     * @param loadTagsCallback 加载已订阅发布者新闻Tag信息回调
     */
    void getTags(LoadTagsCallback loadTagsCallback);

    /**
     * 获取已订阅发布者新闻Taa信息回调
     */
    interface LoadTagsCallback {
        /**
         * 加载已订阅发布者新闻Taa信息完毕
         *
         * @param subscribeList 加载返回的有已订阅发布者新闻Taa信息
         */
        void onTagsLoaded(List<Tag> tagList);

        /**
         * 没有有效的已订阅发布者信息
         */
        void onDataNotAvailable();
    }
}
