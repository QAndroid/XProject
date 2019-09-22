package workshop1024.com.xproject.home.model.subinfo.source.local

import android.util.Log
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource
import workshop1024.com.xproject.home.utils.ExecutorUtils

class SubInfoLocalDataSource private constructor(private val mSubInfoDao: SubInfoDao, private val mExecutorUtils: ExecutorUtils)
    : SubInfoDataSource {

    override fun getSubInfoesByType(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoLocalDataSource getSubInfoesByType, infoType = $infoType")
        val getSubInfoesRunnable = Runnable {
            val subinfoList = mSubInfoDao.getSubinfoesByinfoType(infoType)
            mExecutorUtils.mMainThreadExecutor.execute(Runnable {
                if (!subinfoList.isEmpty()) {
                    (loadCallback as SubInfoDataSource.LoadCacheOrLocalSubInfoCallback).onCacheOrLocalSubInfosLoaded(subinfoList)
                } else {
                    loadCallback.onDataNotAvailable()
                }
            })
        }

        mExecutorUtils.mDiskIOExecutor.execute(getSubInfoesRunnable)
    }

    override fun deleteAllSubInfoesByType(infoType: String) {
        Log.i("XProject", "SubInfoLocalDataSource deleteAllSubInfoesByType, infoType = $infoType")
        val deleteRunnable = Runnable { mSubInfoDao.deleteAllSubInfoes(infoType) }

        mExecutorUtils.mDiskIOExecutor.execute(deleteRunnable)
    }

    override fun saveSubInfo(subInfo: SubInfo) {
        Log.i("XProject", "SubInfoLocalDataSource saveSubInfo, subInfo = $subInfo")
        val saveRunnable = Runnable { mSubInfoDao.insertSubInfo(subInfo) }
        mExecutorUtils.mDiskIOExecutor.execute(saveRunnable)
    }

    override fun markedSubInfoesAsRead(subInfoIdList: List<String>) {
        Log.i("XProject", "SubInfoLocalDataSource markedSubInfoesAsRead, subInfoIdList =$subInfoIdList")
        val saveRunnable = Runnable {
            for (subInfoId in subInfoIdList) {
                mSubInfoDao.markedSubInfoAsReadById(subInfoId)
            }
        }
        mExecutorUtils.mDiskIOExecutor.execute(saveRunnable)
    }

    override fun unSubInfoById(subInfoId: String) {
        Log.i("XProject", "SubInfoLocalDataSource unSubscribeById, subInfoId =$subInfoId")
        val unSubscribeRunnable = Runnable { mSubInfoDao.unSubInfoById(subInfoId) }

        mExecutorUtils.mDiskIOExecutor.execute(unSubscribeRunnable)
    }

    override fun reNameSubInfoById(subInfoId: String, newName: String) {
        Log.i("XProject", "SubInfoLocalDataSource reNameSubscribeById, subInfoId=$subInfoId")
        val reNameRunnable = Runnable { mSubInfoDao.reNameSubInfoById(subInfoId, newName) }

        mExecutorUtils.mDiskIOExecutor.execute(reNameRunnable)
    }

    override fun refreshByType(infoType: String, isSubInfoShowMaps: Boolean, isCacheAndLocalDirty: Boolean) {
        //用于刷新内存数据接口方法，本地数据源不实现
    }

    companion object {
        private lateinit var INSTANCE: SubInfoLocalDataSource

        fun getInstance(subInfoDao: SubInfoDao, executorUtils: ExecutorUtils): SubInfoLocalDataSource {
            synchronized(SubInfoLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = SubInfoLocalDataSource(subInfoDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}