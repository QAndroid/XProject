package workshop1024.com.xproject.home.model.subinfo.source

import workshop1024.com.xproject.home.model.subinfo.SubInfo

interface SubInfoDataSource {
    fun getSubInfoesByType(infoType: String, loadCallback: LoadCallback)

    fun deleteAllSubInfoesByType(infoType: String)

    fun saveSubInfo(subInfo: SubInfo)

    fun markedSubInfoesAsRead(subInfoIdList: List<String>)

    fun refreshByType(infoType: String, isRequestRemote: Boolean, isRequestCache: Boolean)

    fun unSubInfoById(subInfoId: String)

    fun reNameSubInfoById(subInfoId: String, newName: String)

    fun getIsRequestRemote(infoType: String): Boolean

    interface LoadSubInfoCallback : LoadCacheOrLocalSubInfoCallback, LoadRemoteSubInfoCallback

    interface LoadCacheOrLocalSubInfoCallback : LoadCallback {
        fun onCacheOrLocalSubInfosLoaded(subInfoList: List<SubInfo>)

    }

    interface LoadRemoteSubInfoCallback : LoadCallback {
        fun onRemoteSubInfosLoaded(subInfoList: List<SubInfo>)
    }

    interface LoadCallback {
        fun onDataNotAvailable()
    }
}
