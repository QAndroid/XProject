package workshop1024.com.xproject.main.model.filter.source.remote

import android.os.Handler
import android.util.Log
import workshop1024.com.xproject.main.model.filter.Filter
import workshop1024.com.xproject.main.model.filter.source.FilterDataSource

class FilterRemoteDataSource : FilterDataSource {
    override fun getFilters(loadCallback: FilterDataSource.LoadCallback) {
        Log.i("XProject", "FilterRemoteDataSource getFilters")
        Handler().postDelayed({
            val filterList = ArrayList<Filter>(FILTERS_SERVICE_DATA.values)
            (loadCallback as FilterDataSource.LoadRemoteFiltersCallback).onRemoteFiltersLoaded(filterList)
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun addFilter(filter: Filter) {
        Log.i("XProject", "FilterRemoteDataSource addFilter, filter = ${filter.toString()}")
        Handler().postDelayed({
            FILTERS_SERVICE_DATA[filter.mFilterId] = filter
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun deleteAllFilters() {
        Log.i("XProject", "FilterRemoteDataSource deleteAllFilters")
        Handler().postDelayed({
            FILTERS_SERVICE_DATA.clear()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun deleteFilterById(filterId: String) {
        Log.i("XProject", "FilterRemoteDataSource deleteFilterById")
        Handler().postDelayed({ FILTERS_SERVICE_DATA.remove(filterId) }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun getIsRequestRemote(): Boolean {
        return false
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 1000

        private var FILTERS_SERVICE_DATA: MutableMap<String, Filter> = LinkedHashMap(2)

        private lateinit var INSTANCE: FilterRemoteDataSource

        init {
            //TODO 如何本地提供Mock环境
            FILTERS_SERVICE_DATA = LinkedHashMap(2)

            addFilter("f001", "china")
            addFilter("f002", "android")
            addFilter("f003", "usa")
            addFilter("f004", "news")
            addFilter("f005", "rusia")
            addFilter("f006", "fight")
            addFilter("f007", "boate")
            addFilter("f008", "car")
            addFilter("f009", "bycycle")
        }

        private fun addFilter(filterId: String, filterName: String) {
            val filter = Filter(filterId, filterName)
            FILTERS_SERVICE_DATA[filter.mFilterId] = filter
        }

        val instance: FilterRemoteDataSource
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = FilterRemoteDataSource()
                }

                return INSTANCE
            }
    }
}