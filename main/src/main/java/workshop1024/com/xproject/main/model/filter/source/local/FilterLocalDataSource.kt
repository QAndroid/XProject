package workshop1024.com.xproject.main.model.filter.source.local

import android.util.Log
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.main.model.filter.Filter
import workshop1024.com.xproject.main.model.filter.source.FilterDataSource
import workshop1024.com.xproject.main.model.publisher.local.PublisherDao
import workshop1024.com.xproject.main.model.publisher.local.PublisherLocalDataSource
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource

class FilterLocalDataSource private constructor(private val mFilterDao: FilterDao, private val mExecutorUtils: ExecutorUtils) : FilterDataSource {

    override fun getFilters(loadCallback: FilterDataSource.LoadCallback) {
        Log.i("XProject", "FilterLocalDataSource getFilters")
        val getSubInfoesRunnable = Runnable {
            val filterList = mFilterDao.getFilters()
            mExecutorUtils.mMainThreadExecutor.execute(Runnable {
                if (!filterList.isEmpty()) {
                    (loadCallback as FilterDataSource.LoadCacheOrLocalFiltersCallback).onCacheOrLocalFiltersLoaded(filterList)
                } else {
                    loadCallback.onDataNotAvailable()
                }
            })
        }

        mExecutorUtils.mDiskIOExecutor.execute(getSubInfoesRunnable)
    }

    override fun addFilter(filter: Filter) {
        Log.i("XProject", "FilterLocalDataSource addFilter, filter = ${filter.toString()}")
        val addFilterRunnable = Runnable { mFilterDao.addFilter(filter) }
        mExecutorUtils.mDiskIOExecutor.execute(addFilterRunnable)
    }

    override fun deleteAllFilters() {
        Log.i("XProject", "FilterLocalDataSource deleteAllFilters")
        val deleteAllFiltersRunnable = Runnable { mFilterDao.deleteAllFilters() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteAllFiltersRunnable)
    }

    override fun deleteFilterById(filterId: String) {
        Log.i("XProject", "FilterLocalDataSource deleteFilterById, filterId = $filterId")
        val deleteFilterByIdRunnable = Runnable { mFilterDao.deleteFilterById(filterId) }
        mExecutorUtils.mDiskIOExecutor.execute(deleteFilterByIdRunnable)
    }

    override fun getIsRequestRemote(): Boolean {
        return false
    }

    companion object {
        private lateinit var INSTANCE: FilterLocalDataSource

        fun getInstance(filterDao: FilterDao, executorUtils: ExecutorUtils): FilterLocalDataSource {
            synchronized(PublisherLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = FilterLocalDataSource(filterDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}