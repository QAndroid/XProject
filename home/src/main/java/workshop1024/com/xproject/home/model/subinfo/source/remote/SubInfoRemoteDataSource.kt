package workshop1024.com.xproject.home.model.subinfo.source.remote

import android.os.Handler
import android.util.Log
import workshop1024.com.xproject.home.model.subinfo.SubInfo
import workshop1024.com.xproject.home.model.subinfo.source.SubInfoDataSource

class SubInfoRemoteDataSource : SubInfoDataSource {
    override fun getSubInfoesByType(infoType: String, loadCallback: SubInfoDataSource.LoadCallback) {
        Log.i("XProject", "SubInfoRemoteDataSource getSubInfoesByType, infoType = $infoType")
        Handler().postDelayed({
            val subInfoList = ArrayList<SubInfo>()
            for (subInfo in SUBINFO_SERVICE_DATA.values) {
                if (subInfo.mInfoType.equals(infoType)) {
                    subInfoList.add(subInfo)
                }
            }

            if (!subInfoList.isEmpty()) {
                (loadCallback as SubInfoDataSource.LoadRemoteSubInfoCallback).onRemoteSubInfosLoaded(subInfoList)
            } else {
                loadCallback.onDataNotAvailable()
            }
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun deleteAllSubInfoesByType(infoType: String) {
        Log.i("XProject", "SubInfoRemoteDataSource deleteAllSubInfoesByType, infoType = $infoType")
        val iterator = SUBINFO_SERVICE_DATA.values.iterator()
        while (iterator.hasNext()) {
            val subInfo = iterator.next()
            if (subInfo.mInfoType.equals(infoType)) {
                iterator.remove()
            }
        }
    }

    override fun saveSubInfo(subInfo: SubInfo) {
        Log.i("XProject", "SubInfoRemoteDataSource saveSubInfo, subInfo = $subInfo")
        SUBINFO_SERVICE_DATA[subInfo.mInfoId] = subInfo
    }

    override fun markedSubInfoesAsRead(subInfoIdList: List<String>) {
        Log.i("XProject", "SubInfoRemoteDataSource markedSubInfoesAsRead, subInfoIdList = $subInfoIdList")
        val subInfos = ArrayList(SUBINFO_SERVICE_DATA.values)
        for (subInfo in subInfos) {
            for (subInfoId in subInfoIdList) {
                if (subInfo.mInfoId == subInfoId) {
                    subInfo.mUnreadCount = "0"
                }
            }

        }
    }

    override fun unSubInfoById(subInfoId: String) {
        Log.i("XProject", "SubInfoRemoteDataSource unSubInfoById, subInfoId = $subInfoId")
        SUBINFO_SERVICE_DATA.remove(subInfoId)
    }

    override fun reNameSubInfoById(subInfoId: String, newName: String) {
        Log.i("XProject", "SubInfoRemoteDataSource reNameSubInfoById, subInfoId = $subInfoId, newName = $newName")
        val subInfo = SUBINFO_SERVICE_DATA[subInfoId]
        subInfo?.mName = newName
    }

    override fun refreshByType(infoType: String, isSubInfoShowMaps: Boolean, isCacheAndLocalDirty: Boolean) {
        //用于刷新内存缓存数据接口方法，远程数据源不实现
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 1500
        private var SUBINFO_SERVICE_DATA: MutableMap<String, SubInfo> = LinkedHashMap(2)

        private lateinit var INSTANCE: SubInfoRemoteDataSource

        val instance: SubInfoRemoteDataSource
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = SubInfoRemoteDataSource()
                }
                return INSTANCE
            }

        init {
            addSubInfo("p001", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712210748&di=2a83bb6619bfa8429c605669a2186905&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F18%2F19%2F87%2F09f58PICBJU_1024.jpg", "The Tech", "500+")
            addSubInfo("p002", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712210898&di=f6d61e27aa68aa00978f7ee4bdc97cdd&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F60%2F67%2F09D58PICzbh_1024.jpg", "Engadget", "300+")
            addSubInfo("p003", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213014&di=362b855b945ae489e31d8895f3bbee85&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F77%2F08%2F56q58PICitI_1024.jpg", "Lifehacker", "200+")
            addSubInfo("p107", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213305&di=7f92f8290b7e62044239e6adf2e33c5d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018ac455ba317d32f87528a1dbc0e5.jpg", "今日头条", "200+")
            addSubInfo("p108", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213372&di=51e79f1e847b60e7174c89fc567590bd&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F96%2F59%2F05f58PICQ7K_1024.jpg", "腾讯新闻", "200+")
            addSubInfo("p205", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712213636&di=b168a1cc86b096293c9d3458f9c939ee&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D08bd1273d7c451dae2fb04a8de943813%2F80cb39dbb6fd5266c37eb639a118972bd4073641.jpg", "FOX News", "700+")
            addSubInfo("p206", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712216287&di=d467bdd8f27042213ec7e5ef2c25daae&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F84%2F71%2F24k58PIC7Uf_1024.jpg", "NRP News", "100+")
            addSubInfo("p301", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712219202&di=3080aab02628a1ae3c7361677d0cecb9&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F80%2F90%2F57D58PICgCd_1024.jpg", "zen habits", "500+")
            addSubInfo("p309", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712219837&di=ccda689f3a5cd851aad65b1d88fa1fde&imgtype=0&src=http%3A%2F%2Fimage.tupian114.com%2F20140416%2F14172759.png", "NYT", "100+")
            addSubInfo("p608", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523712220441&di=1cfb32f694c02e68f14991d44d339c80&imgtype=0&src=http%3A%2F%2Fpic2.16pic.com%2F00%2F19%2F93%2F16pic_1993513_b.jpg", "一起游戏", "200+")
            addSubInfo("p609", SubInfo.SUBSCRIBE_TYPE, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524307078&di=d66eb67bbd7d674400902d3753ffaeab&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F59%2F59%2F95p58PICwYS_1024.jpg", "Penny Arcade", "100+")

            addSubInfo("t001", SubInfo.TAG_TYPE, "/image1", "News", "10")
            addSubInfo("t002", SubInfo.TAG_TYPE, "/image1", "Android", "99")
            addSubInfo("t003", SubInfo.TAG_TYPE, "/image1", "Deals", "24")
            addSubInfo("t004", SubInfo.TAG_TYPE, "/image1", "Google", "45")
            addSubInfo("t101", SubInfo.TAG_TYPE, "/image1", "Books", "13")
            addSubInfo("t102", SubInfo.TAG_TYPE, "/image1", "Popular", "56")
            addSubInfo("t201", SubInfo.TAG_TYPE, "/image1", "Wellness", "75")
            addSubInfo("t202", SubInfo.TAG_TYPE, "/image1", "Samsung", "78")
            addSubInfo("t301", SubInfo.TAG_TYPE, "/image1", "Travel", "23")

            addSubInfo("f001", SubInfo.FILTER_TYPE, "/image1", "China", "53")
            addSubInfo("f002", SubInfo.FILTER_TYPE, "/image1", "USA", "6")
            addSubInfo("f003", SubInfo.FILTER_TYPE, "/image1", "Japanese", "2")
            addSubInfo("f004", SubInfo.FILTER_TYPE, "/image1", "Rusia", "45")
            addSubInfo("f101", SubInfo.FILTER_TYPE, "/image1", "England", "6")
        }

        private fun addSubInfo(subInfoId: String, infoType: String, iconUrl: String, name: String, unreadCount: String) {
            val subInfo = SubInfo(subInfoId, infoType, iconUrl, name, unreadCount)
            SUBINFO_SERVICE_DATA[subInfo.mInfoId] = subInfo
        }
    }
}