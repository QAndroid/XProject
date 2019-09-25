package workshop1024.com.xproject.main.model.publisher.source

import android.util.Log
import androidx.databinding.ObservableBoolean
import workshop1024.com.xproject.main.model.publisher.Publisher
import java.util.LinkedHashMap

//FIXME 这些通用的逻辑，是否可以使用泛型类进行优化和简化
/**
 * 发布者远程数据源
 */
//一个页面的数据，一次性请求回来，本地内存筛选
class PublisherRepository private constructor(private val mPublisherRemoteDataSource: PublisherDataSource,
                                              private val mPublisherLocalDataSource: PublisherDataSource) : PublisherDataSource {

    private lateinit var mCachedPublisherMaps: MutableMap<String, Publisher>
    private var mIsCacheAndLocalDirty: Boolean = false

    override fun getPublishers(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishers, mIsCacheAndLocalDirty = $mIsCacheAndLocalDirty")
        if (!mIsCacheAndLocalDirty) {
            if (this::mCachedPublisherMaps.isInitialized) {
                val publisherList = getPublishersFromCache()
                if (!publisherList.isEmpty()) {
                    (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherCallback).onCacheOrLocalPublishersLoaded(publisherList)
                } else {
                    getPublishersFromLocal(loadCallback)
                }
            } else {
                getPublishersFromLocal(loadCallback)
            }
        } else {
            getPublishersFromRemote(loadCallback)
        }
    }

    private fun getPublishersFromCache(): List<Publisher> {
        Log.i("XProject", "PublisherRepository getPublishersFromCache")
        return ArrayList(mCachedPublisherMaps.values)
    }


    private fun getPublishersFromRemote(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersFromRemote")
        mPublisherRemoteDataSource.getPublishers(object : PublisherDataSource.LoadRemotePublisherCallback {
            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersFromRemote onRemotePublishersLoaded, publisherList = ${publisherList.toString()}")
                refreshCached(publisherList)
                refreshLocal(publisherList)
                (loadCallback as PublisherDataSource.LoadRemotePublisherCallback).onRemotePublishersLoaded(publisherList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersFromRemote onDataNotAvailable")
                //如Case，第一次网络请求失败了，尝试从"Local"获取数据兜底
                getPublishersFromLocal(loadCallback)
            }
        })
    }

    private fun refreshLocal(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshLocal, publisherList = $publisherList")
        mPublisherLocalDataSource.deleteAllPublishers()

        for (publisher in publisherList) {
            mPublisherLocalDataSource.savePublisher(publisher)
        }

        mIsCacheAndLocalDirty = false
    }

    private fun refreshCached(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshCached, publisherList = $publisherList")
        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        mCachedPublisherMaps.clear()

        for (publisher in publisherList) {
            mCachedPublisherMaps.put(publisher.mPublisherId, publisher)
        }

        mIsCacheAndLocalDirty = false
    }

    private fun getPublishersFromLocal(loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersFromLocal")
        mPublisherLocalDataSource.getPublishers(object : PublisherDataSource.LoadCacheOrLocalPublisherCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersFromLocal, onCacheOrLocalPublishersLoaded =" + publisherList.toString())
                refreshCached(publisherList)
                (loadCallback as PublisherDataSource.LoadCacheOrLocalPublisherCallback).onCacheOrLocalPublishersLoaded(publisherList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersFromLocal onDataNotAvailable")
                getPublishersFromRemote(loadCallback)
            }
        })
    }

    override fun getPublishersByContentType(typeId: String, loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByContentType, typeId = $typeId")
        getPublishers(object : PublisherDataSource.LoadPublishersCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onCacheOrLocalPublishersLoaded, publisherList = $publisherList")
                //FIXME 是否可以使用Kotlin高阶函数简化？
                //FIXME Kotlin的集合具体如何分类和区分？
                val typePublisherList = filterPublisherByType(publisherList, typeId)
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onCacheOrLocalPublishersLoaded(typePublisherList)
            }

            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onRemotePublishersLoaded, publisherList = $publisherList")
                val typePublisherList = filterPublisherByType(publisherList, typeId)
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onRemotePublishersLoaded(typePublisherList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByContentType onDataNotAvailable")
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onDataNotAvailable()
            }
        })
    }

    private fun filterPublisherByType(publisherList: List<Publisher>, typeId: String): List<Publisher> {
        val typePublisherList = mutableListOf<Publisher>()
        for (publisher in publisherList) {
            if (publisher.mTypeId.equals(typeId)) {
                typePublisherList.add(publisher)
            }
        }

        return typePublisherList
    }

    override fun getPublishersByLanguageType(languageId: String, loadCallback: PublisherDataSource.LoadCallback) {
        Log.i("XProject", "PublisherRepository getPublishersByLanguageType, languageId = $languageId")
        getPublishers(object : PublisherDataSource.LoadPublishersCallback {
            override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onCacheOrLocalPublishersLoaded, publisherList = $publisherList")
                val languagePublisherList = filterPublisherByLanguage(languageId, publisherList)
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onCacheOrLocalPublishersLoaded(languagePublisherList)
            }

            override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onRemotePublishersLoaded, publisherList = $publisherList")
                val languagePublisherList = filterPublisherByLanguage(languageId, publisherList)
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onRemotePublishersLoaded(languagePublisherList)
            }

            override fun onDataNotAvailable() {
                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onDataNotAvailable")
                (loadCallback as PublisherDataSource.LoadPublishersCallback).onDataNotAvailable()
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

    override fun refresh(isCacheAndLocalDirty: Boolean) {
        mIsCacheAndLocalDirty = isCacheAndLocalDirty
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
