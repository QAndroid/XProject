package workshop1024.com.xproject.home.model.subinfo.source

import android.util.Log
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import java.util.LinkedHashMap

//FIXME 多线程访问数据问题
open class SubInfoRepository private constructor(private val mSubInfoRemoteDataSource: SubInfoDataSource,
                                                 private val mSubInfoLocalDataSource: SubInfoDataSource) : SubInfoDataSource {
    private lateinit var mCachedSubInfoMaps: MutableMap<String, SubInfo>
    //FIXME mIsCacheAndLocalDirtyMaps[infoType]!!强制非空无法保证！
    private val mIsCacheAndLocalDirtyMaps = mutableMapOf<String, Boolean>(SubInfo.SUBSCRIBE_TYPE to false, SubInfo.TAG_TYPE to false, SubInfo.FILTER_TYPE to false)
    private val mIsSubInfoShowMaps = mutableMapOf<String, Boolean>(SubInfo.SUBSCRIBE_TYPE to false, SubInfo.TAG_TYPE to false, SubInfo.FILTER_TYPE to false)

    override fun getSubInfoesByType(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRepository getSubInfoesByType, infoType = $infoType, mIsCacheDirty = ${mIsCacheAndLocalDirtyMaps[infoType]}, mIsSubInfoShow = ${mIsSubInfoShowMaps[infoType]}")
        //如果还没有数据展示，并且缓存或者本地数据不为"脏数据"，快速获取数据进行展示
        if (!mIsSubInfoShowMaps[infoType]!!) {
            if (!mIsCacheAndLocalDirtyMaps[infoType]!!) {
                if (this::mCachedSubInfoMaps.isInitialized) {
                    val subInfoList = getSubInfoesByTypeFromCache(infoType)
                    if (!subInfoList.isEmpty()) {
                        (loadCallback as SubInfoDataSource.LoadCacheOrLocalSubInfoCallback).onCacheOrLocalSubInfosLoaded(subInfoList)
                        mIsSubInfoShowMaps[infoType] = true
                    } else {
                        getSubInfosByInfoTypeFromLocal(infoType, loadCallback)
                    }
                } else {
                    getSubInfosByInfoTypeFromLocal(infoType, loadCallback)
                }
            }
        }

        //从网络数据最新数据，然后进行数据展示或者刷新
        getSubInfosByInfoTypeFromRemote(infoType, loadCallback)
    }

    private fun getSubInfoesByTypeFromCache(infoType: String): List<SubInfo> {
        Log.i("XProject", "SubInfoRepository getSubInfoesByTypeFromCache, infoType = $infoType")
        val subInfoList = mutableListOf<SubInfo>()
        for (subinfo in mCachedSubInfoMaps.values) {
            if (subinfo.mInfoType.equals(infoType)) {
                subInfoList.add(subinfo)
            }
        }

        return subInfoList
    }

    private fun getSubInfosByInfoTypeFromRemote(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromRemote, infoType = $infoType")
        mSubInfoRemoteDataSource.getSubInfoesByType(infoType, object : SubInfoDataSource.LoadRemoteSubInfoCallback {
            override fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>) {
                Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromRemote onRemoteSubInfosLoaded, subInfoList = ${subInfoList.toString()}")
                refreshCachedByInfoType(infoType, subInfoList)
                refreshLocalByInfoType(infoType, subInfoList)
                (loadCallback as SubInfoDataSource.LoadRemoteSubInfoCallback).onRemoteSubInfosLoaded(subInfoList)

                mIsSubInfoShowMaps[infoType] = true
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "SubInfoRepository getSubInfoesByType from remote onDataNotAvailable")
                //如Case，第一次网络请求失败了，尝试从"Local"获取数据兜底
                getSubInfosByInfoTypeFromLocal(infoType, loadCallback)
            }
        })
    }

    private fun refreshCachedByInfoType(infoType: String, subInfoList: List<SubInfo>) {
        Log.i("XProject", "SubInfoRepository refreshCachedByInfoType, infoType = $infoType, subInfoList = ${subInfoList.toString()}")
        if (!this::mCachedSubInfoMaps.isInitialized) {
            mCachedSubInfoMaps = LinkedHashMap()
        }

        deleteCacheAllSubInfoesByType(infoType)

        //重新添加指定类型的SubInfo内存缓存
        for (subInfo in subInfoList) {
            saveCacheSubInfo(subInfo)

        }

        mIsCacheAndLocalDirtyMaps[infoType] = false
    }

    private fun saveCacheSubInfo(subInfo: SubInfo) {
        Log.i("XProject", "SubInfoRepository saveCacheSubInfo, subInfo = $subInfo")
        mCachedSubInfoMaps[subInfo.mInfoId] = subInfo
    }

    private fun refreshLocalByInfoType(infoType: String, subInfoList: List<SubInfo>) {
        Log.i("XProject", "SubInfoRepository refreshLocalByInfoType, infoType = $infoType, subInfoList = ${subInfoList.toString()}")
        mSubInfoLocalDataSource.deleteAllSubInfoesByType(infoType)
        for (subInfo in subInfoList) {
            mSubInfoLocalDataSource.saveSubInfo(subInfo)
        }

        mIsCacheAndLocalDirtyMaps[infoType] = false
    }

    private fun getSubInfosByInfoTypeFromLocal(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromLocal, infoType = $infoType")
        mSubInfoLocalDataSource.getSubInfoesByType(infoType, object : SubInfoDataSource.LoadCacheOrLocalSubInfoCallback {
            override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
                Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromLocal, onCacheOrLocalSubInfosLoaded =" + subInfoList.toString())
                refreshCachedByInfoType(infoType, subInfoList)
                (loadCallback as SubInfoDataSource.LoadCacheOrLocalSubInfoCallback).onCacheOrLocalSubInfosLoaded(subInfoList)

                mIsSubInfoShowMaps[infoType] = true
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromLocal, onDataNotAvailable")
            }
        })
    }


    override fun deleteAllSubInfoesByType(infoType: String) {
        Log.i("XProject", "SubInfoRepository deleteAllSubInfoesByType, infoType = $infoType")
        mSubInfoRemoteDataSource.deleteAllSubInfoesByType(infoType)
        mSubInfoLocalDataSource.deleteAllSubInfoesByType(infoType)

        deleteCacheAllSubInfoesByType(infoType)
    }

    private fun deleteCacheAllSubInfoesByType(infoType: String) {
        Log.i("XProject", "SubInfoRepository deleteCacheAllSubInfoesByType, infoType = $infoType")

        //移除指定类型的SubInfo内存缓存
        //采用iterator的遍历方式，避免java.util.ConcurrentModificationException异常
        //参考：https://www.cnblogs.com/dolphin0520/p/3933551.html
        val iterator = mCachedSubInfoMaps.values.iterator()
        while (iterator.hasNext()) {
            val subinfo = iterator.next()
            if (subinfo.mInfoType.equals(infoType)) {
                iterator.remove()
            }
        }
    }

    override fun saveSubInfo(subInfo: SubInfo) {
        Log.i("XProject", "SubInfoRepository saveSubInfo, subInfo = $subInfo")
        mSubInfoRemoteDataSource.saveSubInfo(subInfo)
        mSubInfoLocalDataSource.saveSubInfo(subInfo)

        if (!this::mCachedSubInfoMaps.isInitialized) {
            mCachedSubInfoMaps = LinkedHashMap()
        }
        saveCacheSubInfo(subInfo)
    }


    override fun markedSubInfoesAsRead(subInfoIdList: List<String>) {
        Log.i("XProject", "SubInfoRepository markedSubInfoesAsRead, subInfoIdList = ${subInfoIdList.toString()}")
        mSubInfoRemoteDataSource.markedSubInfoesAsRead(subInfoIdList)
        mSubInfoLocalDataSource.markedSubInfoesAsRead(subInfoIdList)

        if (!this::mCachedSubInfoMaps.isInitialized) {
            mCachedSubInfoMaps = LinkedHashMap()
        }

        for ((subInfoId, subInfo) in mCachedSubInfoMaps) {
            for (infoId in subInfoIdList) {
                if (subInfoId == infoId) {
                    subInfo.mUnreadCount = "0"
                }
            }
        }
    }

    override fun unSubInfoById(subInfoId: String) {
        Log.i("XProject", "SubInfoRepository unSubInfoById, subInfoId =$subInfoId")
        mSubInfoRemoteDataSource.unSubInfoById(subInfoId)
        mSubInfoLocalDataSource.unSubInfoById(subInfoId)

        if (!this::mCachedSubInfoMaps.isInitialized) {
            mCachedSubInfoMaps = LinkedHashMap()
        }

        mCachedSubInfoMaps.remove(subInfoId)
    }

    override fun reNameSubInfoById(subInfoId: String, newName: String) {
        Log.i("XProject", "SubInfoRepository reNameSubInfoById, subInfoId =$subInfoId, newName = $newName")
        mSubInfoRemoteDataSource.reNameSubInfoById(subInfoId, newName)
        mSubInfoLocalDataSource.reNameSubInfoById(subInfoId, newName)

        if (!this::mCachedSubInfoMaps.isInitialized) {
            mCachedSubInfoMaps = LinkedHashMap()
        }
        val subInfo = mCachedSubInfoMaps[subInfoId]
        if (subInfo != null) {
            subInfo.mName = newName
        }
    }

    override fun refreshByType(infoType: String, isSubInfoShowMaps: Boolean, isCacheAndLocalDirty: Boolean) {
        Log.i("XProject", "SubInfoRepository refreshByType, infoType =$infoType")
        mIsSubInfoShowMaps[infoType] = isSubInfoShowMaps
        mIsCacheAndLocalDirtyMaps[infoType] = isCacheAndLocalDirty
    }

    companion object {
        private lateinit var INSTANCE: SubInfoRepository

        fun getInstance(subInfoRemoteDataSource: SubInfoDataSource, subInfoLocalDataSource: SubInfoDataSource): SubInfoRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = SubInfoRepository(subInfoRemoteDataSource, subInfoLocalDataSource)
            }
            return INSTANCE
        }
    }
}