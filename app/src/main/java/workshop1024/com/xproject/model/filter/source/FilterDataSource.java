package workshop1024.com.xproject.model.filter.source;

import java.util.List;

import workshop1024.com.xproject.model.filter.Filter;

/**
 * 新闻过滤器数据源接口，定义了关于新闻过滤器的接口
 */
public interface FilterDataSource {
    /**
     * 获取所有新闻过滤器
     *
     * @param loadFilterCallback 加载新闻过滤器回调
     */
    void getFilters(LoadFilterCallback loadFilterCallback);

    /**
     * 添加新闻过滤器
     *
     * @param filter 要添加的新闻过滤器
     */
    void saveFilter(Filter filter);

    /**
     * 删除指定过滤器id的过滤器
     *
     * @param filterId 要删除的过滤器id
     */
    void deleteFilterById(String filterId);

    /**
     * 删除所有过滤器
     */
    void deleteAllFilter();

    /**
     * 获取新闻过滤器回调
     */
    interface LoadFilterCallback {
        /**
         * 过滤器信息加载完毕
         *
         * @param filterList
         */
        void onFilterLoaded(List<Filter> filterList);

        /**
         * 没有有效的过滤器
         */
        void onDataNotAvailable();
    }
}
