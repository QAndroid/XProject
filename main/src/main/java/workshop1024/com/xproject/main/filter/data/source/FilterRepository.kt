package workshop1024.com.xproject.main.filter.data.source

import android.util.Log

import workshop1024.com.xproject.main.filter.data.Filter

class FilterRepository private constructor(private val mFilterRemoteDataSource: FilterDataSource,
                                           private val mFilterLocalDataSource: FilterDataSource) : FilterDataSource {
    private lateinit var mCacheFilterMaps: MutableMap<String, Filter>
    private var mIsRequestRemote: Boolean = true

    override fun getFilters(loadCallback: FilterDataSource.LoadCallback) {
        Log.i("XProject", "FilterRepository getFilters, mIsRequestRemote = $mIsRequestRemote")
        if (this::mCacheFilterMaps.isInitialized) {
            val filterList = getFiltersFromCache()
            if (!filterList.isEmpty()) {
                (loadCallback as FilterDataSource.LoadCacheOrLocalFiltersCallback).onCacheOrLocalFiltersLoaded(filterList)
            } else {
                getFiltersFromLocal(loadCallback)
            }
        } else {
            getFiltersFromLocal(loadCallback)
        }

        if (mIsRequestRemote) {
            getFiltersFromRemote(loadCallback)
        }
    }

    private fun getFiltersFromLocal(loadCallback: FilterDataSource.LoadCallback) {
        Log.i("XProject", "FilterRepository getFiltersFromLocal")
        mFilterLocalDataSource.getFilters(object : FilterDataSource.LoadCacheOrLocalFiltersCallback {
            override fun onCacheOrLocalFiltersLoaded(filterList: List<Filter>) {
                Log.i("XProject", "FilterRepository getFiltersFromLocal onCacheOrLocalFiltersLoaded, filterList = ${filterList}")
                refreshCached(filterList)
                (loadCallback as FilterDataSource.LoadCacheOrLocalFiltersCallback).onCacheOrLocalFiltersLoaded(filterList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "FilterRepository getFiltersFromLocal onDataNotAvailable")
            }
        })
    }

    private fun refreshCached(filterList: List<Filter>) {
        Log.i("XProject", "FilterRepository refreshCached, filterList = ${filterList.toString()}")
        if (!this::mCacheFilterMaps.isInitialized) {
            mCacheFilterMaps = LinkedHashMap()
        }

        mCacheFilterMaps.clear()

        for (filter in filterList) {
            mCacheFilterMaps.put(filter.mFilterId, filter)
        }
    }

    private fun getFiltersFromRemote(loadCallback: FilterDataSource.LoadCallback) {
        Log.i("XProject", "FilterRepository getFiltersFromRemote")
        mFilterRemoteDataSource.getFilters(object : FilterDataSource.LoadRemoteFiltersCallback {
            override fun onRemoteFiltersLoaded(filterList: List<Filter>) {
                Log.i("XProject", "FilterRepository getFiltersFromRemote onRemoteFiltersLoaded")
                refreshCached(filterList)
                refreshLocal(filterList)

                (loadCallback as FilterDataSource.LoadRemoteFiltersCallback).onRemoteFiltersLoaded(filterList)

                //请求过一次远程之后，不自动请求远程，除非强制刷新请求
                mIsRequestRemote = false
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "FilterRepository getFiltersFromRemote onDataNotAvailable")

            }
        })
    }

    private fun refreshLocal(filterList: List<Filter>) {
        Log.i("XProject", "FilterRepository refreshLocal, filterList = ${filterList.toString()}")
        mFilterLocalDataSource.deleteAllFilters()

        for (filter in filterList) {
            mFilterLocalDataSource.addFilter(filter)
        }
    }

    private fun getFiltersFromCache(): List<Filter> {
        Log.i("XProject", "FilterRepository getFiltersFromCache")
        return ArrayList(mCacheFilterMaps.values)
    }

    override fun addFilter(filter: Filter) {
        Log.i("XProject", "FilterRepository addFilter, filter = ${filter.toString()}")
        mFilterRemoteDataSource.addFilter(filter)
        mFilterLocalDataSource.addFilter(filter)

        if (!this::mCacheFilterMaps.isInitialized) {
            mCacheFilterMaps = LinkedHashMap()
        }

        mCacheFilterMaps[filter.mFilterId] = filter
    }

    override fun deleteFilterById(filterId: String) {
        Log.i("XProject", "FilterRepository deleteFilterById, filterId = $filterId")
        mFilterRemoteDataSource.deleteFilterById(filterId)
        mFilterLocalDataSource.deleteFilterById(filterId)

        if (!this::mCacheFilterMaps.isInitialized) {
            mCacheFilterMaps = LinkedHashMap()
        }

        mCacheFilterMaps.remove(filterId)
    }

    override fun deleteAllFilters() {
        Log.i("XProject", "FilterRepository deleteAllFilters")
        mFilterRemoteDataSource.deleteAllFilters()
        mFilterLocalDataSource.deleteAllFilters()

        if (!this::mCacheFilterMaps.isInitialized) {
            mCacheFilterMaps = LinkedHashMap()
        }

        mCacheFilterMaps.clear()
    }

    override fun getIsRequestRemote(): Boolean {
        return mIsRequestRemote
    }

    companion object {
        private lateinit var INSTANCE: FilterRepository

        fun getInstance(filterRemoteDataSource: FilterDataSource, filterLocalDataSource: FilterDataSource): FilterRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = FilterRepository(filterRemoteDataSource, filterLocalDataSource)
            }
            return INSTANCE
        }
    }
}
