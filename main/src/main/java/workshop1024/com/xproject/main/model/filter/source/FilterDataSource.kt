package workshop1024.com.xproject.main.model.filter.source

import workshop1024.com.xproject.main.model.filter.Filter

interface FilterDataSource {
    fun getFilters(loadCallback: LoadCallback)

    fun addFilter(filter: Filter)

    fun deleteFilterById(filterId: String)

    fun deleteAllFilters()

    fun getIsRequestRemote(): Boolean

    interface LoadFiltersCallback : LoadRemoteFiltersCallback, LoadCacheOrLocalFiltersCallback

    interface LoadCacheOrLocalFiltersCallback : LoadCallback {
        fun onCacheOrLocalFiltersLoaded(filterList: List<Filter>)
    }

    interface LoadRemoteFiltersCallback : LoadCallback {
        fun onRemoteFiltersLoaded(filterList: List<Filter>)
    }

    interface LoadCallback {
        fun onDataNotAvailable()
    }
}
