package workshop1024.com.xproject.model.tag.source;

import java.util.List;

import workshop1024.com.xproject.model.tag.Tag;

/**
 * 新闻标记数据源接口，定义了关于新闻标记数据的接口
 */
public interface TagDataSource {
    /**
     * 获取新闻标志信息
     *
     * @param loadTagCallback 获取新闻标志信息回调
     */
    void getTag(LoadTagCallback loadTagCallback);

    /**
     * 获取新闻标志信息回调
     */
    interface LoadTagCallback {

        /**
         * 新闻标志信息加载完毕
         *
         * @param tagList
         */
        void onTagInfoLoaded(List<Tag> tagList);

        /**
         * 没有有效的新闻信息
         */
        void onDataNotAvailable();
    }
}
