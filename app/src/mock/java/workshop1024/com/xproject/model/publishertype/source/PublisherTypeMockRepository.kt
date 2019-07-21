package workshop1024.com.xproject.model.publishertype.source

import android.os.Handler

import java.util.ArrayList
import java.util.LinkedHashMap

import workshop1024.com.xproject.model.publishertype.PublisherType

class PublisherTypeMockRepository private constructor() : PublisherTypeDataSource {

    override fun getPublisherContentTypes(loadPublisherTypeCallback: PublisherTypeDataSource.LoadPublisherTypeCallback) {
        val handler = Handler()
        handler.postDelayed({
            val publisherTypeList = ArrayList(CONTENT_SERVICE_DATA!!.values)
            loadPublisherTypeCallback.onPublisherTypesLoaded(publisherTypeList, "content")
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    override fun getPublisherLanguageTypes(loadPublisherTypeCallback: PublisherTypeDataSource.LoadPublisherTypeCallback) {
        val handler = Handler()
        handler.postDelayed({
            val publisherTypeList = ArrayList(LANGUAGE_SERVICE_DATA!!.values)
            loadPublisherTypeCallback.onPublisherTypesLoaded(publisherTypeList, "language")
        }, SERVICE_LATENCY_IN_MILLIS.toLong())
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 1000

        private var CONTENT_SERVICE_DATA: MutableMap<String, PublisherType>? = null
        private var LANGUAGE_SERVICE_DATA: MutableMap<String, PublisherType>? = null

        private var INSTANCE: PublisherTypeDataSource? = null

        init {
            //TODO 如何本地提供Mock环境
            CONTENT_SERVICE_DATA = LinkedHashMap(2)
            addContentType("t001", "Tech")
            addContentType("t002", "News")
            addContentType("t003", "Business")
            addContentType("t004", "Health")
            addContentType("t005", "Gaming")
            addContentType("t006", "Design")
            addContentType("t007", "Fashion")
            addContentType("t008", "Cooking")
            addContentType("t009", "Comics")
            addContentType("t010", "DIY")
            addContentType("t011", "Sport")
            addContentType("t012", "Cinema")
            addContentType("t013", "Youtube")
            addContentType("t014", "Funny")
            addContentType("t015", "Esty")

            LANGUAGE_SERVICE_DATA = LinkedHashMap(2)
            addLanguageType("l001", "English")
            addLanguageType("l002", "日语")
            addLanguageType("l003", "中文")
        }

        private fun addContentType(typeId: String, name: String) {
            val publisherType = PublisherType(typeId, name)
            CONTENT_SERVICE_DATA!![publisherType.typeId] = publisherType
        }

        private fun addLanguageType(typeId: String, name: String) {
            val publisherType = PublisherType(typeId, name)
            LANGUAGE_SERVICE_DATA!![publisherType.typeId] = publisherType
        }

        val instance: PublisherTypeDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = PublisherTypeMockRepository()
                }

                return INSTANCE!!
            }
    }
}
