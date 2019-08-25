package workshop1024.com.xproject.model.publisher.source

import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import workshop1024.com.xproject.home.model.publisher.Publisher
import workshop1024.com.xproject.home.model.publisher.source.PublisherDataSource

import java.util.ArrayList
import java.util.LinkedHashMap

class PublisherMockRepository private constructor() : PublisherDataSource {

    override fun getPublishersByContentType(contentId: String, loadPublishersCallback: PublisherDataSource.LoadPublishersCallback) {
        Log.i("XProject", "PublisherRemoteDataSource getPublishersByContentType =$contentId")
        val handler = Handler()
        handler.postDelayed({
            val typedPublishers = ArrayList<Publisher>()
            val publisherList = ArrayList(PUBLISHERS_SERVICE_DATA!!.values)
            for (publisher in publisherList) {
                //获取指定类型的发布者
                if (publisher.typeId == contentId) {
                    typedPublishers.add(publisher)
                }
            }
            Log.i("XProject", "PublisherRemoteDataSource  publisherList =" + Gson().toJson(typedPublishers))
            loadPublishersCallback.onPublishersLoaded(typedPublishers)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getPublishersByLanguageType(languageId: String, loadPublishersCallback: PublisherDataSource.LoadPublishersCallback) {
        Log.i("XProject", "PublisherRemoteDataSource getPublishersByLanguageType =$languageId")
        val handler = Handler()
        handler.postDelayed({
            val languagedPublishers = ArrayList<Publisher>()
            val publisherList = ArrayList(PUBLISHERS_SERVICE_DATA!!.values)
            for (publisher in publisherList) {
                //获取指定语言的发布者
                if (publisher.language == languageId) {
                    languagedPublishers.add(publisher)
                }
            }
            Log.i("XProject", "PublisherRemoteDataSource  languagedPublishers =" + Gson().toJson(languagedPublishers))
            loadPublishersCallback.onPublishersLoaded(languagedPublishers)
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun subscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherRemoteDataSource subscribePublisherById =$publisherId")
        val subscribedPublisher = PUBLISHERS_SERVICE_DATA!![publisherId]
        subscribedPublisher?.isSubscribed?.set(true)
    }

    override fun unSubscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherRemoteDataSource unSubscribePublisherById =$publisherId")
        val subscribedPublisher = PUBLISHERS_SERVICE_DATA!![publisherId]
        subscribedPublisher?.isSubscribed?.set(false)
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1000

        private var PUBLISHERS_SERVICE_DATA: MutableMap<String, Publisher>? = null

        private var INSTANCE: PublisherMockRepository? = null

        init {
            //TODO 如何本地提供Mock环境
            PUBLISHERS_SERVICE_DATA = LinkedHashMap(2)

            addPublisher("p001", "t001", "l001", "/imag1", "The Tech-mock", "970601 subscribers", false)
            addPublisher("p002", "t001", "l001", "/imag1", "Engadget", "1348433 subscribers", false)
            addPublisher("p003", "t001", "l001", "/imag1", "Lifehacker", "934273 subscribers", false)
            addPublisher("p004", "t001", "l001", "/imag1", "ReadWrite-mock", "254332 subscribers", false)
            addPublisher("p005", "t001", "l001", "/imag1", "Digital Trends", "145694 subscribers", false)
            addPublisher("p006", "t001", "l001", "/imag1", "Business Insider", "331892 subscribers", false)
            addPublisher("p007", "t001", "l003", "/imag1", "月光博客", "254321 subscribers", false)
            addPublisher("p008", "t001", "l003", "/imag1", "36氪", "125345 subscribers", false)
            addPublisher("p009", "t001", "l001", "/imag1", "TechCrunch-mock", "994287 subscribers", false)


            addPublisher("p101", "t002", "l001", "/imag1", "The News", "970601 subscribers", false)
            addPublisher("p102", "t002", "l001", "/imag1", "Engadget", "1348433 subscribers", false)
            addPublisher("p103", "t002", "l001", "/imag1", "Lifehacker-mock", "934273 subscribers", false)
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
                    subscribeNum, isSubscribed)
            PUBLISHERS_SERVICE_DATA!![newPublisher.publisherId] = newPublisher
        }

        val instance: PublisherDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = PublisherMockRepository()
                }

                return INSTANCE!!
            }
    }
}
