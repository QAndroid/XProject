package workshop1024.com.xproject.home.model.subinfo.source

import android.util.Log
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import java.util.LinkedHashMap

//FIXME 多线程访问数据问题
//三个页面的数据，三次请求，故分别进行脏数据存储
class SubInfoRepository private constructor(private val mSubInfoRemoteDataSource: SubInfoDataSource,
                                            private val mSubInfoLocalDataSource: SubInfoDataSource) : SubInfoDataSource {
    private lateinit var mCachedSubInfoMaps: MutableMap<String, SubInfo>
    //FIXME mIsRequestCacheMaps[infoType]!!强制非空无法保证！
    //默认第一次请求数据，先请求缓存如果有的话快速展示，并且请求远程更新最新的数据
    private val mIsRequestCacheMaps = mutableMapOf<String, Boolean>(SubInfo.SUBSCRIBE_TYPE to true, SubInfo.TAG_TYPE to true, SubInfo.FILTER_TYPE to true)
    private val mIsRequestRemoteMaps = mutableMapOf<String, Boolean>(SubInfo.SUBSCRIBE_TYPE to true, SubInfo.TAG_TYPE to true, SubInfo.FILTER_TYPE to true)

    override fun getSubInfoesByType(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRepository getSubInfoesByType, infoType = $infoType, mIsRequestCache = ${mIsRequestCacheMaps[infoType]}, mIsRequestRemote = ${mIsRequestRemoteMaps[infoType]}")
        if (mIsRequestCacheMaps[infoType]!!) {
            if (this::mCachedSubInfoMaps.isInitialized) {
                val subInfoList = getSubInfoesByTypeFromCache(infoType)
                if (!subInfoList.isEmpty()) {
                    (loadCallback as SubInfoDataSource.LoadCacheOrLocalSubInfoCallback).onCacheOrLocalSubInfosLoaded(subInfoList)
                } else {
                    getSubInfosByInfoTypeFromLocal(infoType, loadCallback)
                }
            } else {
                getSubInfosByInfoTypeFromLocal(infoType, loadCallback)
            }
        }

        if (mIsRequestRemoteMaps[infoType]!!) {
            //从网络数据最新数据，然后进行数据展示或者刷新
            getSubInfosByInfoTypeFromRemote(infoType, loadCallback)
        }
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

                //获取过远程数据后，默认不在请求，除非下拉刷新强制更新时重新请求
                mIsRequestRemoteMaps[infoType] = false
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "SubInfoRepository getSubInfoesByType from remote onDataNotAvailable")
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

        //有缓存的，就可以优先请求缓存，除非下拉刷新强制我不请求缓存
        mIsRequestCacheMaps[infoType] = true
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

        //有缓存的，就可以优先请求缓存，除非下拉刷新强制我不请求缓存
        mIsRequestCacheMaps[infoType] = true
    }

    private fun getSubInfosByInfoTypeFromLocal(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromLocal, infoType = $infoType")
        mSubInfoLocalDataSource.getSubInfoesByType(infoType, object : SubInfoDataSource.LoadCacheOrLocalSubInfoCallback {
            override fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>) {
                Log.i("XProject", "SubInfoRepository getSubInfosByInfoTypeFromLocal, onCacheOrLocalSubInfosLoaded =" + subInfoList.toString())
                refreshCachedByInfoType(infoType, subInfoList)
                (loadCallback as SubInfoDataSource.LoadCacheOrLocalSubInfoCallback).onCacheOrLocalSubInfosLoaded(subInfoList)
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

    override fun refreshByType(infoType: String, isRequestRemote: Boolean, isRequestCache: Boolean) {
        Log.i("XProject", "SubInfoRepository refreshByType, infoType =$infoType")
        mIsRequestRemoteMaps[infoType] = isRequestRemote
        mIsRequestCacheMaps[infoType] = isRequestCache
    }

    override fun getIsRequestRemote(infoType: String): Boolean {
        return mIsRequestRemoteMaps[infoType]!!
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