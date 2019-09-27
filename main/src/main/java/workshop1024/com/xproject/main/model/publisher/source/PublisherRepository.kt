package workshop1024.com.xproject.main.model.publisher.source

import android.util.Log
import workshop1024.com.xproject.main.model.publisher.Publisher
import workshop1024.com.xproject.main.model.publisher.PublisherType
import java.util.LinkedHashMap

//FIXME 这些通用的逻辑，是否可以使用泛型类进行优化和简化
/**
 * 发布者远程数据源
 */
//一个页面的数据，一次性请求回来，本地内存筛选
class PublisherRepository private constructor(private val mPublisherRemoteDataSource: PublisherDataSource,
                                              private val mPublisherLocalDataSource: PublisherDataSource) : PublisherDataSource {
    private lateinit var mCachedPublisherMaps: MutableMap<String, Publisher>
    private lateinit var mCachedPublisherTypeMaps: MutableMap<String, PublisherType>

    private var mIsRequestRemote: Boolean = true

    override fun getPublishersAndPublisherTypes(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypes , mIsRequestRemote = $mIsRequestRemote")
        //优先取缓存，有缓存数据立即展示
        if (this::mCachedPublisherMaps.isInitialized && this::mCachedPublisherTypeMaps.isInitialized) {
            val publisherList = getPublishersFromCache()
            val publisherTypeList = getPublisherTypesFromCache()

            if (!publisherList.isEmpty() && !publisherTypeList.isEmpty()) {
                val contentTypeList = ArrayList<PublisherType>()
                val languageTyleList = ArrayList<PublisherType>()

                for (publisherType in publisherTypeList) {
                    if (publisherType.mType.equals("content")) {
                        contentTypeList.add(publisherType)
                    }
                    if (publisherType.mType.equals("language")) {
                        languageTyleList.add(publisherType)
                    }
                }
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherAndPublisherTypeCallback).onCacheOrLocalPublishersLoaded(publisherList)
                loadCallback.onCacheOrLocalPublisherTypesLoaded(contentTypeList, languageTyleList)
            } else {
                getPublishersAndPublisherTypesFromLocal(loadCallback)
            }
        } else {
            getPublishersAndPublisherTypesFromLocal(loadCallback)
        }

        if (mIsRequestRemote) {
            getPublishersAndPublisherTypesFromRemote(loadCallback)
        }
    }

    private fun getPublishersFromCache(): List<Publisher> {
        Log.i("XProject", "PublisherRepository getPublishersFromCache")
        return ArrayList(mCachedPublisherMaps.values)
    }

    private fun getPublisherTypesFromCache(): List<PublisherType> {
        Log.i("XProject", "PublisherRepository getPublisherTypesFromCache")
        return ArrayList(mCachedPublisherTypeMaps.values)
    }

    private fun getPublishersAndPublisherTypesFromLocal(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal")
        mPublisherLocalDataSource.getPublishersAndPublisherTypes(object : PublisherDataSource.LoadCacheOrLocalPublisherAndPublisherTypeCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal, onCacheOrLocalPublishersLoaded =" + publisherList.toString())
                refreshPublisherCached(publisherList)
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherAndPublisherTypeCallback).onCacheOrLocalPublishersLoaded(publisherList)
            }

            override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal onCacheOrLocalPublisherTypesLoaded")
                refreshPublisherTypeCached(contentTypeList, languageTyleList)
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherAndPublisherTypeCallback).onCacheOrLocalPublisherTypesLoaded(contentTypeList, languageTyleList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal onDataNotAvailable")
            }
        })
    }

    private fun refreshPublisherCached(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshPublisherCached, publisherList = $publisherList")
        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        mCachedPublisherMaps.clear()

        for (publisher in publisherList) {
            mCachedPublisherMaps.put(publisher.mPublisherId, publisher)
        }
    }


    private fun refreshPublisherTypeCached(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        Log.i("XProject", "PublisherRepository refreshPublisherTypeCached, contentTypeList = $contentTypeList, languageTyleList = $languageTyleList")
        if (!this::mCachedPublisherTypeMaps.isInitialized) {
            mCachedPublisherTypeMaps = LinkedHashMap()
        }

        mCachedPublisherTypeMaps.clear()

        for (contentType in contentTypeList) {
            mCachedPublisherTypeMaps.put(contentType.mTypeId, contentType)
        }
        for (languageType in languageTyleList) {
            mCachedPublisherTypeMaps.put(languageType.mTypeId, languageType)
        }
    }

    private fun getPublishersAndPublisherTypesFromRemote(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote")
        mPublisherRemoteDataSource.getPublishersAndPublisherTypes(object : PublisherDataSource.LoadRemotePubliserAndPublisherTypeCallback {
            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onRemotePublishersLoaded, publisherList = ${publisherList.toString()}")
                refreshPublisherCached(publisherList)
                refreshPublisherLocal(publisherList)
                (loadCallback as PublisherDataSource.LoadRemotePubliserAndPublisherTypeCallback).onRemotePublishersLoaded(publisherList)

                //请求过一次远程之后，不自动请求远程，除非强制刷新请求
                mIsRequestRemote = false
            }

            override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onRemotePublisherTypesLoaded, contentTypeList = ${contentTypeList.toString()}, languageTypeList = ${languageTyleList.toString()}")
                refreshPublisherTypeCached(contentTypeList, languageTyleList)
                refreshPublisherTypeLocal(contentTypeList, languageTyleList)
                (loadCallback as PublisherDataSource.LoadRemotePubliserAndPublisherTypeCallback).onRemotePublisherTypesLoaded(contentTypeList, languageTyleList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onDataNotAvailable")
            }
        })
    }

    private fun refreshPublisherLocal(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshPublisherLocal, publisherList = $publisherList")
        mPublisherLocalDataSource.deleteAllPublishers()

        for (publisher in publisherList) {
            mPublisherLocalDataSource.savePublisher(publisher)
        }
    }

    private fun refreshPublisherTypeLocal(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        Log.i("XProject", "PublisherRepository refreshPublisherTypeLocal, contentTypeList = $contentTypeList, languageTyleList = $languageTyleList")
        mPublisherLocalDataSource.deleteAllPublisherTypes()

        for (contentType in contentTypeList) {
            mPublisherLocalDataSource.savePublisherType(contentType)
        }
        for (languageType in languageTyleList) {
            mPublisherLocalDataSource.savePublisherType(languageType)
        }
    }

    override fun getPublishersByContentType(typeId: String, loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByContentType, mTypeId = $typeId")
        getPublishersAndPublisherTypes(object : PublisherDataSource.LoadPublisherAndPublisherTypeCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onCacheOrLocalPublishersLoaded, publisherList = $publisherList")
                //FIXME 是否可以使用Kotlin高阶函数简化？
                //FIXME Kotlin的集合具体如何分类和区分？
                val typePublisherList = filterPublisherByType(publisherList, typeId)
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherCallback).onCacheOrLocalPublishersLoaded(typePublisherList)
            }

            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onRemotePublishersLoaded, publisherList = $publisherList")
                val typePublisherList = filterPublisherByType(publisherList, typeId)
                (loadCallback as PublisherDataSource.LoadRemotePublisherCallback).onRemotePublishersLoaded(typePublisherList)
            }

            override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {

            }

            override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {

            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onDataNotAvailable")
            }
        })
    }

    private fun filterPublisherByType(publisherList: List<Publisher>, typeId: String): List<Publisher> {
        val typePublisherList = mutableListOf<Publisher>()
        for (publisher in publisherList) {
            if (publisher.mType.equals(typeId)) {
                typePublisherList.add(publisher)
            }
        }

        return typePublisherList
    }

    override fun getPublishersByLanguageType(languageId: String, loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByLanguageType, languageId = $languageId")
        getPublishersAndPublisherTypes(object : PublisherDataSource.LoadPublisherAndPublisherTypeCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onCacheOrLocalPublishersLoaded, publisherList = $publisherList")
                val languagePublisherList = filterPublisherByLanguage(languageId, publisherList)
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherCallback).onCacheOrLocalPublishersLoaded(languagePublisherList)
            }

            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onRemotePublishersLoaded, publisherList = $publisherList")
                val languagePublisherList = filterPublisherByLanguage(languageId, publisherList)
                (loadCallback as PublisherDataSource.LoadRemotePublisherCallback).onRemotePublishersLoaded(languagePublisherList)
            }

            override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {

            }

            override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {

            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onDataNotAvailable")
            }
        })
    }

    private fun filterPublisherByLanguage(languageId: String, publisherList: List<Publisher>): List<Publisher> {
        val languagePublisherList = mutableListOf<Publisher>()
        for (publisher in publisherList) {
            if (publisher.mLanguage.equals(languageId)) {
                languagePublisherList.add(publisher)
            }
        }
        return languagePublisherList
    }


    override fun subscribePublisherById(id: String) {
        mPublisherRemoteDataSource.subscribePublisherById(id)
        mPublisherLocalDataSource.subscribePublisherById(id)

        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        val publisher = mCachedPublisherMaps[id]
        if (publisher != null) {
            publisher.mIsSubscribed.set(true)
        }
    }

    override fun unSubscribePublisherById(id: String) {
        mPublisherRemoteDataSource.unSubscribePublisherById(id)
        mPublisherLocalDataSource.unSubscribePublisherById(id)

        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        val publisher = mCachedPublisherMaps[id]
        if (publisher != null) {
            publisher.mIsSubscribed.set(false)
        }
    }

    override fun deleteAllPublishers() {
        mPublisherRemoteDataSource.deleteAllPublishers()
        mPublisherLocalDataSource.deleteAllPublishers()

        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        mCachedPublisherMaps.clear()
    }

    override fun savePublisher(publisher: Publisher) {
        mPublisherRemoteDataSource.savePublisher(publisher)
        mPublisherLocalDataSource.savePublisher(publisher)

        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        mCachedPublisherMaps.put(publisher.mPublisherId, publisher)
    }


    override fun deleteAllPublisherTypes() {
        mPublisherRemoteDataSource.deleteAllPublisherTypes()
        mPublisherLocalDataSource.deleteAllPublisherTypes()

        if (!this::mCachedPublisherTypeMaps.isInitialized) {
            mCachedPublisherTypeMaps = LinkedHashMap()
        }

        mCachedPublisherTypeMaps.clear()
    }

    override fun savePublisherType(publisherType: PublisherType) {
        mPublisherRemoteDataSource.savePublisherType(publisherType)
        mPublisherLocalDataSource.savePublisherType(publisherType)

        if (!this::mCachedPublisherTypeMaps.isInitialized) {
            mCachedPublisherTypeMaps = LinkedHashMap()
        }

        mCachedPublisherTypeMaps.put(publisherType.mTypeId, publisherType)
    }

    override fun refresh(isRequestRemote: Boolean) {
        mIsRequestRemote = isRequestRemote
    }

    override fun getIsRequestRemote(): Boolean {
        return mIsRequestRemote
    }


    companion object {
        private lateinit var INSTANCE: PublisherRepository

        fun getInstance(subInfoRemoteDataSource: PublisherDataSource, subInfoLocalDataSource: PublisherDataSource): PublisherRepository {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = PublisherRepository(subInfoRemoteDataSource, subInfoLocalDataSource)
            }
            return INSTANCE
        }
    }
}
