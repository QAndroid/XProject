package workshop1024.com.xproject.main.model.filter.source

import workshop1024.com.xproject.main.model.filter.Filter

interface FilterDataSource {
    fun getFilters(loadFiltersCallback: LoadFiltersCallback)

    fun addFilterByName(filterName: String)

    fun deleteFilterById(filterId: String)

    interface LoadFiltersCallback {
        fun onPublishersLoaded(filterList: List<Filter>)

        fun onDataNotAvailable()
    }
}
