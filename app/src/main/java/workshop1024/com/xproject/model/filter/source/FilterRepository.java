package workshop1024.com.xproject.model.filter.source;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import workshop1024.com.xproject.model.filter.Filter;

/**
 * 新闻过滤器数据源
 */
public class FilterRepository implements FilterDataSource {
    //新闻过滤器数据源单例对象
    private static FilterRepository INSTANCE = null;

    //新闻过滤器远程数据源
    private FilterDataSource mFilterRemoteDataSource;
    //新闻过滤器本地数据源
    private FilterDataSource mFilterLocalDataSource;

    //内存中缓存的新闻过滤器信息
    private Map<String, Filter> mCachedFilterMaps;
    //内存和本地缓存新闻过滤器数据是否为“脏”数据
    private boolean mIsCachedDirty;

    /**
     * 新闻过滤器数据源构造方法
     *
     * @param filterRemoteDataSource 新闻过滤器远程数据源
     * @param filterLocalDataSource  新闻过滤器本地数据源
     */
    private FilterRepository(FilterDataSource filterRemoteDataSource, FilterDataSource filterLocalDataSource) {
        mFilterRemoteDataSource = filterRemoteDataSource;
        mFilterLocalDataSource = filterLocalDataSource;
    }

    /**
     * 获取新闻过滤器数据源单例对象
     *
     * @param filterRemoteDataSource 新闻过滤器远程数据源
     * @param filterLocalDataSource  新闻过滤器本地数据源
     * @return 新闻过滤器数据源单例对象
     */
    public static FilterRepository getInstance(FilterDataSource filterRemoteDataSource, FilterDataSource
            filterLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FilterRepository(filterRemoteDataSource, filterLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * 销毁新闻过滤器数据源对象，用于强制下一次调用重新创建对象
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getFilters(LoadFilterCallback loadFilterCallback) {
        //如果存在内存缓存，并且为“干净”数据，则立即返回内存缓存
        if (mCachedFilterMaps != null && !mIsCachedDirty) {
            loadFilterCallback.onFilterLoaded(new ArrayList<>(mCachedFilterMaps.values()));
            return;
        }

        //如果不存在内存缓存，则优先从本地数据缓存中获取，否则从远程获取数据
        if (mIsCachedDirty) {
            getFiltersFromRemoteDataSource(loadFilterCallback);
        } else {
            getFiltersFromLocalDataSource(loadFilterCallback);
        }
    }

    /**
     * 从远程数据源获取新闻过滤器
     *
     * @param loadFilterCallback 加载过滤器回调
     */
    private void getFiltersFromRemoteDataSource(final LoadFilterCallback loadFilterCallback) {
        mFilterRemoteDataSource.getFilters(new LoadFilterCallback() {
            @Override
            public void onFilterLoaded(List<Filter> filterList) {
                refreshCache(filterList);
                refreshLocalDataSource(filterList);
                loadFilterCallback.onFilterLoaded(new ArrayList<>(mCachedFilterMaps.values()));
            }

            @Override
            public void onDataNotAvailable() {
                loadFilterCallback.onDataNotAvailable();
            }
        });
    }

    /**
     * 更新内存缓存的过滤器
     *
     * @param filterList 要更新的过滤器
     */
    private void refreshCache(List<Filter> filterList) {
        if (mCachedFilterMaps == null) {
            mCachedFilterMaps = new LinkedHashMap<>();
        }
        mCachedFilterMaps.clear();

        for (Filter filter : filterList) {
            mCachedFilterMaps.put(filter.getFilterId(), filter);
        }

        mIsCachedDirty = false;
    }

    /**
     * 更新本地缓存的过滤器
     *
     * @param filterList 要更新的过滤器
     */
    private void refreshLocalDataSource(List<Filter> filterList) {
        mFilterLocalDataSource.deleteAllFilter();
        for (Filter filter : filterList) {
            mFilterLocalDataSource.saveFilter(filter);
        }
    }

    /**
     * 从本地数据源获取新闻过滤器
     *
     * @param loadFilterCallback 加载过滤器回调
     */
    private void getFiltersFromLocalDataSource(final LoadFilterCallback loadFilterCallback) {
        mFilterLocalDataSource.getFilters(new LoadFilterCallback() {
            @Override
            public void onFilterLoaded(List<Filter> filterList) {
                refreshCache(filterList);
                loadFilterCallback.onFilterLoaded(new ArrayList<>(mCachedFilterMaps.values()));
            }

            @Override
            public void onDataNotAvailable() {
                getFiltersFromRemoteDataSource(loadFilterCallback);
            }
        });
    }


    @Override
    public void saveFilter(Filter filter) {
        //本地缓存更新数据和远程更新数据
        mFilterRemoteDataSource.saveFilter(filter);
        mFilterLocalDataSource.saveFilter(filter);

        //内存缓存更新数据
        if(mCachedFilterMaps == null){
            mCachedFilterMaps = new LinkedHashMap<>();
        }
        mCachedFilterMaps.put(filter.getFilterId(),filter);
    }

    @Override
    public void deleteFilterById(String filterId) {
        mFilterRemoteDataSource.deleteFilterById(filterId);
        mFilterLocalDataSource.deleteFilterById(filterId);

        if(mCachedFilterMaps == null){
            mCachedFilterMaps = new LinkedHashMap<>();
        }
        //FIXME 是根据key删除么？？
        mCachedFilterMaps.remove(filterId);
    }

    @Override
    public void deleteAllFilter() {
        mFilterRemoteDataSource.deleteAllFilter();
        mFilterLocalDataSource.deleteAllFilter();

        if(mCachedFilterMaps == null){
            mCachedFilterMaps = new LinkedHashMap<>();
        }
        mCachedFilterMaps.clear();
    }
}
