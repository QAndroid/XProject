package workshop1024.com.xproject.main.model.publisher.local

import android.util.Log
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.main.model.publisher.Publisher
import workshop1024.com.xproject.main.model.publisher.PublisherType
import workshop1024.com.xproject.main.model.publisher.source.PublisherDataSource

class PublisherLocalDataSource private constructor(private val mPublisherDao: PublisherDao, private val mExecutorUtils: ExecutorUtils) : PublisherDataSource {

    override fun getPublishersAndPublisherTypes(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherLocalDataSource getPublishersAndPublisherTypes")
        val getSubInfoesRunnable = Runnable {
            val subinfoList = mPublisherDao.getPublishers()
            mExecutorUtils.mMainThreadExecutor.execute(Runnable {
                if (!subinfoList.isEmpty()) {
                    (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherCallback).onCacheOrLocalPublishersLoaded(subinfoList)
                } else {
                    loadCallback.onDataNotAvailable()
                }
            })
        }

        mExecutorUtils.mDiskIOExecutor.execute(getSubInfoesRunnable)
    }

    override fun getPublishersByContentType(contentId: String, loadCallback: PublisherDataSource.LoadCallback) {
        //本地接口一次性提供正页面数据，筛选在本地内存中执行
    }

    override fun getPublishersByLanguageType(languageId: String, loadCallback: PublisherDataSource.LoadCallback) {
        //本地接口一次性提供正页面数据，筛选在本地内存中执行
    }

    override fun subscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherLocalDataSource subscribePublisherById, publisherId = $publisherId")
        val unSubscribeRunnable = Runnable { mPublisherDao.subscribePublisherById(publisherId) }
        mExecutorUtils.mDiskIOExecutor.execute(unSubscribeRunnable)
    }

    override fun unSubscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherLocalDataSource unSubscribePublisherById, publisherId = $publisherId")
        val unSubscribeRunnable = Runnable { mPublisherDao.unSubscribePublisherById(publisherId) }
        mExecutorUtils.mDiskIOExecutor.execute(unSubscribeRunnable)
    }

    override fun savePublisher(publisher: Publisher) {
        Log.i("XProject", "SubInfoLocalDataSource savePublisher, publisher = $publisher")
        val saveRunnable = Runnable { mPublisherDao.insertPublisher(publisher) }
        mExecutorUtils.mDiskIOExecutor.execute(saveRunnable)
    }

    override fun deleteAllPublishers() {
        Log.i("XProject", "SubInfoLocalDataSource deleteAllPublishers")
        val deleteRunnable = Runnable { mPublisherDao.deleteAllPublishers() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteRunnable)
    }

    override fun deleteAllPublisherTypes() {
        Log.i("XProject", "SubInfoLocalDataSource deleteAllPublisherTypes")
        val deleteRunnable = Runnable { mPublisherDao.deleteAllPublisherTypes() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteRunnable)
    }

    override fun savePublisherType(publisherType: PublisherType) {
        Log.i("XProject", "SubInfoLocalDataSource savePublisherType")
        val deleteRunnable = Runnable { mPublisherDao.savePublisherType(publisherType) }
        mExecutorUtils.mDiskIOExecutor.execute(deleteRunnable)
    }

    override fun refresh(isRequestRemote: Boolean) {

    }

    override fun getIsRequestRemote(): Boolean {
        return false
    }


    companion object {
        private lateinit var INSTANCE: PublisherLocalDataSource

        fun getInstance(publisherDao: PublisherDao, executorUtils: ExecutorUtils): PublisherLocalDataSource {
            synchronized(PublisherLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = PublisherLocalDataSource(publisherDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}