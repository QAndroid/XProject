package workshop1024.com.xproject.main.publisher.data.source.remote

import android.os.Handler
import android.util.Log
import androidx.databinding.ObservableBoolean
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource

class PublisherRemoteDataSource : PublisherDataSource {

    override fun getPublishersAndPublisherTypes(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRemoteDataSource getPublishersAndPublisherTypes")
        Handler().postDelayed({
            val publisherList = ArrayList(PUBLISHERS_SERVICE_DATA.values)
            val contentTypeList = ArrayList(CONTENT_SERVICE_DATA.values)
            val languageTypeList = ArrayList(LANGUAGE_SERVICE_DATA.values)
            (loadCallback as PublisherDataSource.LoadRemotePubliserAndPublisherTypeCallback).onRemotePublishersLoaded(publisherList)
            loadCallback.onRemotePublisherTypesLoaded(contentTypeList, languageTypeList)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getPublishersByContentType(contentId: String, loadCallback: PublisherDataSource.LoadCallback) {
        //远程接口一次性提供正页面数据，筛选在本地内存中执行
    }

    override fun getPublishersByLanguageType(languageId: String, loadCallback: PublisherDataSource.LoadCallback) {
        //远程接口一次性提供正页面数据，筛选在本地内存中执行
    }

    override fun subscribePublisherById(id: String) {
        Log.i("XProject", "PublisherRemoteDataSource subscribePublisherById, id =$id")
        val subscribedPublisher = PUBLISHERS_SERVICE_DATA[id]
        subscribedPublisher?.mIsSubscribed?.set(true)
    }

    override fun unSubscribePublisherById(id: String) {
        Log.i("XProject", "PublisherRemoteDataSource unSubscribePublisherById, id =$id")
        val subscribedPublisher = PUBLISHERS_SERVICE_DATA[id]
        subscribedPublisher?.mIsSubscribed?.set(false)
    }

    override fun deleteAllPublishers() {
        Log.i("XProject", "PublisherRemoteDataSource deleteAllPublishers")
        PUBLISHERS_SERVICE_DATA.clear()
    }

    override fun savePublisher(publisher: Publisher) {
        Log.i("XProject", "PublisherRemoteDataSource savePublisher, publisher = $publisher")
        PUBLISHERS_SERVICE_DATA.put(publisher.mPublisherId, publisher)
    }

    override fun deleteAllPublisherTypes() {
        Log.i("XProject", "PublisherRemoteDataSource deleteAllPublisherTypes")
        CONTENT_SERVICE_DATA.clear()
        LANGUAGE_SERVICE_DATA.clear()
    }

    override fun savePublisherType(publisherType: PublisherType) {
        Log.i("XProject", "PublisherRemoteDataSource savePublisherType, publisherType = $publisherType")
        if (publisherType.mType.equals("content")) {
            CONTENT_SERVICE_DATA.put(publisherType.mTypeId, publisherType)
        } else if (publisherType.mType.equals("language")) {
            LANGUAGE_SERVICE_DATA.put(publisherType.mTypeId, publisherType)
        }
    }

    override fun getIsRequestRemote(): Boolean {
        //FIXME 不是都需要实现的方法，是不是不放在接口里面
        return false;
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1000

        private var PUBLISHERS_SERVICE_DATA: MutableMap<String, Publisher> = LinkedHashMap(2)
        private var CONTENT_SERVICE_DATA: MutableMap<String, PublisherType> = LinkedHashMap(2)
        private var LANGUAGE_SERVICE_DATA: MutableMap<String, PublisherType> = LinkedHashMap(2)

        private lateinit var INSTANCE: PublisherRemoteDataSource

        val instance: PublisherRemoteDataSource
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = PublisherRemoteDataSource()
                }

                return INSTANCE
            }

        init {
            addContentType("t001", "content", "Tech")
            addContentType("t002", "content", "News")
            addContentType("t003", "content", "Business")
            addContentType("t004", "content", "Health")
            addContentType("t005", "content", "Gaming")
            addContentType("t006", "content", "Design")
            addContentType("t007", "content", "Fashion")
            addContentType("t008", "content", "Cooking")
            addContentType("t009", "content", "Comics")
            addContentType("t010", "content", "DIY")
            addContentType("t011", "content", "Sport")
            addContentType("t012", "content", "Cinema")
            addContentType("t013", "content", "Youtube")
            addContentType("t014", "content", "Funny")
            addContentType("t015", "content", "Esty")

            addLanguageType("l001", "language", "English")
            addLanguageType("l002", "language", "日语")
            addLanguageType("l003", "language", "中文")

            addPublisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", false)
            addPublisher("p002", "t001", "l001", "/imag1", "Engadget", "1348433 subscribers", false)
            addPublisher("p003", "t001", "l001", "/imag1", "Lifehacker", "934275 subscribers", false)
            addPublisher("p004", "t001", "l001", "/imag1", "ReadWrite-mock", "254332 subscribers", false)
            addPublisher("p005", "t001", "l001", "/imag1", "Digital Trends", "145694 subscribers", false)
            addPublisher("p006", "t001", "l001", "/imag1", "Business Insider", "331892 subscribers", false)
            addPublisher("p007", "t001", "l003", "/imag1", "月光博客", "254321 subscribers", false)
            addPublisher("p008", "t001", "l003", "/imag1", "36氪", "125345 subscribers", false)
            addPublisher("p009", "t001", "l001", "/imag1", "TechCrunch-mock", "994287 subscribers", false)


            addPublisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", false)
            addPublisher("p102", "t002", "l001", "/imag1", "Engadget", "1348433 subscribers", false)
            addPublisher("p103", "t002", "l001", "/imag1", "Lifehacker-mock", "934274 subscribers", false)
            addPublisher("p104", "t002", "l001", "/imag1", "ReadWrite", "254332 subscribers", false)
            addPublisher("p105", "t002", "l001", "/imag1", "Digital Trends", "145694 subscribers", false)
            addPublisher("p106", "t002", "l001", "/imag1", "Business Insider", "331892 subscribers", false)
            addPublisher("p107", "t002", "l003", "/imag1", "今日头条", "254321 subscribers", false)
            addPublisher("p108", "t002", "l003", "/imag1", "腾讯新闻", "125345 subscribers", false)
            addPublisher("p109", "t002", "l001", "/imag1", "TechCrunch", "994287 subscribers", false)

            addPublisher("p201", "t003", "l001", "/imag1", "The Business", "970601 subscribers", false)
            addPublisher("p202", "t003", "l001", "/imag1", "The New York Times", "1348433 subscribers", false)
            addPublisher("p203", "t003", "l001", "/imag1", "OZY-mock", "934273 subscribers", false)
            addPublisher("p204", "t003", "l001", "/imag1", "ABC News", "254332 subscribers", false)
            addPublisher("p205", "t003", "l001", "/imag1", "FOX News", "145694 subscribers", false)
            addPublisher("p206", "t003", "l001", "/imag1", "NRP News", "331892 subscribers", false)
            addPublisher("p207", "t003", "l003", "/imag1", "财经周刊", "254321 subscribers", false)
            addPublisher("p208", "t003", "l003", "/imag1", "交易时刻", "125345 subscribers", false)
            addPublisher("p209", "t003", "l001", "/imag1", "BBC", "994287 subscribers", false)

            addPublisher("p301", "t004", "l001", "/imag1", "zen habits", "970601 subscribers", false)
            addPublisher("p302", "t004", "l001", "/imag1", "Skinnytaste-mock", "1348433 subscribers", false)
            addPublisher("p303", "t004", "l001", "/imag1", "Lifehacker", "934273 subscribers", false)
            addPublisher("p304", "t004", "l001", "/imag1", "Mark's Daily Apple", "254332 subscribers", false)
            addPublisher("p305", "t004", "l001", "/imag1", "Oh She Glows", "145694 subscribers", false)
            addPublisher("p306", "t004", "l001", "/imag1", "Health", "331892 subscribers", false)
            addPublisher("p307", "t004", "l003", "/imag1", "健康之路", "254321 subscribers", false)
            addPublisher("p308", "t004", "l003", "/imag1", "星星点灯", "125345 subscribers", false)
            addPublisher("p309", "t004", "l001", "/imag1", "NYT-mock", "994287 subscribers", false)


            addPublisher("p401", "t005", "l001", "/imag1", "The Gaming", "970601 subscribers", false)
            addPublisher("p402", "t005", "l001", "/imag1", "Polygon", "1348433 subscribers", false)
            addPublisher("p403", "t005", "l001", "/imag1", "Kotaku", "934273 subscribers", false)
            addPublisher("p404", "t005", "l001", "/imag1", "Joystiq", "254332 subscribers", false)
            addPublisher("p505", "t005", "l001", "/imag1", "IndieGames", "145694 subscribers", false)
            addPublisher("p606", "t005", "l001", "/imag1", "Game Life", "331892 subscribers", false)
            addPublisher("p607", "t005", "l003", "/imag1", "电竞世界", "254321 subscribers", false)
            addPublisher("p608", "t005", "l003", "/imag1", "一起游戏", "125345 subscribers", false)
            addPublisher("p609", "t005", "l001", "/imag1", "Penny Arcade", "994287 subscribers", false)
        }

        private fun addPublisher(publisherId: String, type: String, language: String, iconUrl: String,
                                 name: String, subscribeNum: String, isSubscribed: Boolean) {
            val newPublisher = Publisher(publisherId, type, language, iconUrl, name,
                    subscribeNum, ObservableBoolean(isSubscribed))
            PUBLISHERS_SERVICE_DATA[newPublisher.mPublisherId] = newPublisher
        }

        private fun addContentType(typeId: String, type: String, name: String) {
            val publisherType = PublisherType(typeId, type, name)
            CONTENT_SERVICE_DATA[publisherType.mTypeId] = publisherType
        }

        private fun addLanguageType(typeId: String, type: String, name: String) {
            val publisherType = PublisherType(typeId, type, name)
            LANGUAGE_SERVICE_DATA[publisherType.mTypeId] = publisherType
        }

    }
}