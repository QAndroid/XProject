package workshop1024.com.xproject.home.model.subinfo.source

import workshop1024.com.xproject.home.model.subinfo.SubInfo

interface SubInfoDataSource {

    fun getSubscribeSubInfos(loadSubInfoCallback: LoadSubInfoCallback)

    fun unSubscribeSubInfoById(subInfoId: String)

    fun reNameSubscribeSubInfoById(subInfoId: String, newName: String)

    fun getTagSubInfos(loadSubInfoCallback: LoadSubInfoCallback)

    fun getFilterSubInfos(loadSubInfoCallback: LoadSubInfoCallback)

    fun markedSubscribeSubInfoesAsRead(subInfoIdList: List<String>)

    fun markedTagSubInfoesAsRead(subInfoIdList: List<String>)

    fun markeFilterSubInfoesAsRead(subInfoIdList: List<String>)

    interface LoadSubInfoCallback {
        fun onSubInfosLoaded(subInfoList: List<SubInfo>)

        fun onDataNotAvailable()
    }

}
