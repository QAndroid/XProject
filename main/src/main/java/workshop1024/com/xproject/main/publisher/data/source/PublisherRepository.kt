package workshop1024.com.xproject.main.publisher.data.source

import android.util.Log
import rx.Observable
import rx.functions.*
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import java.util.*
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.mutableListOf

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

    override fun getPublishersAndPublisherTypes(): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypes , mIsRequestRemote = $mIsRequestRemote")
        var publisherInfoLocalCacheMapObservabl: Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>>
        var publisherInfoRemoteMapObservabl: Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> = Observable.empty()

        //优先取缓存，有缓存数据立即展示
        if (this::mCachedPublisherMaps.isInitialized && this::mCachedPublisherTypeMaps.isInitialized) {
            val publisherListObservable = getPublishersFromCache()
            val publisherTypeListObservable = getPublisherTypesFromCache()

            //zip操作符：使用一个函数组合做个Observable发射的数据集合，然后再发射这个结果
            publisherInfoLocalCacheMapObservabl = Observable.zip(publisherListObservable, publisherTypeListObservable, object : Func2<List<Publisher>,
                    List<PublisherType>, EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
                override fun call(publisherList: List<Publisher>, publisherTypeList: List<PublisherType>):
                        EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                    var publiserInfoMap = EnumMap<PublisherDataSource.PublisherInfoType, Any>(PublisherDataSource
                            .PublisherInfoType::class.java)

                    if (!publisherList.isEmpty() && !publisherTypeList.isEmpty()) {
                        //filter操作符：过滤数据，将满足条件的数据，单独发射出去
                        val publiserTypeInfoMap = EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>(PublisherDataSource
                                .PublisherTypeInfoType::class.java)
                        val contentTypeLocalCacheList = mutableListOf<PublisherType>()
                        Observable.from(publisherTypeList).filter(object : Func1<PublisherType, Boolean> {
                            override fun call(publisherType: PublisherType): Boolean {
                                return publisherType.mType.equals("content")
                            }
                        }).subscribe(object : Action1<PublisherType> {
                            override fun call(publisherType: PublisherType) {
                                contentTypeLocalCacheList.add(publisherType)
                            }
                        })
                        val languageTypeLocalCacheList = mutableListOf<PublisherType>()
                        Observable.from(publisherTypeList).filter(object : Func1<PublisherType, Boolean> {
                            override fun call(publisherType: PublisherType): Boolean {
                                return publisherType.mType.equals("language")
                            }
                        }).subscribe(object : Action1<PublisherType> {
                            override fun call(publisherType: PublisherType) {
                                languageTypeLocalCacheList.add(publisherType)
                            }
                        })
                        publiserTypeInfoMap.put(PublisherDataSource.PublisherTypeInfoType.CONTENT_TYPES_LOCAL_CACHE, contentTypeLocalCacheList)
                        publiserTypeInfoMap.put(PublisherDataSource.PublisherTypeInfoType.LANGUAGE_TYPES_LOCAL_CACHE, languageTypeLocalCacheList)

                        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, Observable.from(publisherList).toList())
                        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_LOCAL_CACHE, Observable.just(publiserTypeInfoMap))
                    } else {
                        getPublishersAndPublisherTypesFromLocal().map {
                            publiserInfoMap = it;
                        };
                    }

                    return publiserInfoMap
                }
            });
        } else {
            publisherInfoLocalCacheMapObservabl = getPublishersAndPublisherTypesFromLocal()
        }

        if (mIsRequestRemote) {
            publisherInfoRemoteMapObservabl = getPublishersAndPublisherTypesFromRemote()
        }

        return Observable.merge(publisherInfoLocalCacheMapObservabl, publisherInfoRemoteMapObservabl)
    }

    private fun getPublishersFromCache(): Observable<List<Publisher>> {
        Log.i("XProject", "PublisherRepository getPublishersFromCache")
        return Observable.from(mCachedPublisherMaps.values).toList()
    }

    private fun getPublisherTypesFromCache(): Observable<List<PublisherType>> {
        Log.i("XProject", "PublisherRepository getPublisherTypesFromCache")
        return Observable.from(mCachedPublisherTypeMaps.values).toList()
    }

    private fun getPublishersAndPublisherTypesFromLocal(): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal")
        return mPublisherLocalDataSource.getPublishersAndPublisherTypes().map(object : Func1<EnumMap<PublisherDataSource
        .PublisherInfoType, Any>, EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
            override fun call(publiserInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>):
                    EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                //执行完doOnNext之后，将其返回值重新赋值，否则doOnNext不会被执行
                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE,
                        (publiserInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                                ?.doOnNext(object : Action1<List<Publisher>> {
                                    override fun call(publisherList: List<Publisher>) {
                                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal, onCacheOrLocalPublishersLoaded = ${publisherList.toString()}")
                                        refreshPublisherCached(publisherList)
                                    }
                                }))

                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_LOCAL_CACHE,
                        (publiserInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_LOCAL_CACHE)
                                as? Observable<EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>>)
                                ?.doOnNext(object : Action1<EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>> {
                                    override fun call(publisherTypeInfoMap: EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>) {
                                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal, onCacheOrLocalPublisherTypesLoaded")
                                        refreshPublisherTypeCached(publisherTypeInfoMap.get(PublisherDataSource.PublisherTypeInfoType.LANGUAGE_TYPES_LOCAL_CACHE)!!
                                                , publisherTypeInfoMap.get(PublisherDataSource.PublisherTypeInfoType.CONTENT_TYPES_LOCAL_CACHE)!!)
                                    }
                                }))

                return publiserInfoMap
            }
        })
    }

    private fun refreshPublisherCached(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshPublisherCached, publisherList = ${publisherList.size}")
        if (!this::mCachedPublisherMaps.isInitialized) {
            mCachedPublisherMaps = LinkedHashMap()
        }

        mCachedPublisherMaps.clear()

        Observable.from(publisherList).subscribe(object : Action1<Publisher> {
            override fun call(publisher: Publisher) {
                mCachedPublisherMaps.put(publisher.mPublisherId, publisher)
            }
        })
    }


    private fun refreshPublisherTypeCached(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        Log.i("XProject", "PublisherRepository refreshPublisherTypeCached, contentTypeList = ${contentTypeList.size}, languageTyleList = ${languageTyleList.size}")
        if (!this::mCachedPublisherTypeMaps.isInitialized) {
            mCachedPublisherTypeMaps = LinkedHashMap()
        }

        mCachedPublisherTypeMaps.clear()

        Observable.merge(Observable.from(contentTypeList), Observable.from(languageTyleList)).subscribe(object : Action1<PublisherType> {
            override fun call(publisherType: PublisherType) {
                mCachedPublisherTypeMaps.put(publisherType.mTypeId, publisherType)
            }
        })
    }

    private fun getPublishersAndPublisherTypesFromRemote(): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote")
        return mPublisherRemoteDataSource.getPublishersAndPublisherTypes().map(object : Func1<EnumMap<PublisherDataSource.PublisherInfoType, Any>,
                EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
            override fun call(publisherInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>)
                    : EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE,
                        (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as Observable<List<Publisher>>)
                                .doOnNext(object : Action1<List<Publisher>> {
                                    override fun call(publisherList: List<Publisher>) {
                                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onRemotePublishersLoaded, publisherList = ${publisherList.size}")
                                        refreshPublisherCached(publisherList)
                                        refreshPublisherLocal(publisherList)
                                        //请求过一次远程之后，不自动请求远程，除非强制刷新请求
                                        mIsRequestRemote = false
                                    }
                                }))

                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_REMOTE,
                        (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_REMOTE)
                                as? Observable<EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>>)
                                ?.doOnNext(object : Action1<EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>> {
                                    override fun call(publisherTypeInfoMap: EnumMap<PublisherDataSource.PublisherTypeInfoType, List<PublisherType>>) {
                                        val contentTypeRemoteList = publisherTypeInfoMap.get(PublisherDataSource.PublisherTypeInfoType.LANGUAGE_TYPES_REMOTE)!!
                                        val languageTyleRemoteList = publisherTypeInfoMap.get(PublisherDataSource.PublisherTypeInfoType.CONTENT_TYPES_REMOTE)!!
                                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onRemotePublisherTypesLoaded, " +
                                                "contentTypeList = ${contentTypeRemoteList.toString()}, languageTypeList = ${languageTyleRemoteList.toString()}")
                                        refreshPublisherTypeCached(contentTypeRemoteList, languageTyleRemoteList)
                                        refreshPublisherTypeLocal(contentTypeRemoteList, languageTyleRemoteList)
                                    }
                                }))
                return publisherInfoMap
            }
        })
    }

    private fun refreshPublisherLocal(publisherList: List<Publisher>) {
        Log.i("XProject", "PublisherRepository refreshPublisherLocal, publisherList = ${publisherList.size}")
        mPublisherLocalDataSource.deleteAllPublishers()

        Observable.from(publisherList).subscribe(object : Action1<Publisher> {
            override fun call(publisher: Publisher) {
                mPublisherLocalDataSource.savePublisher(publisher)
            }
        })
    }

    private fun refreshPublisherTypeLocal(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        Log.i("XProject", "PublisherRepository refreshPublisherTypeLocal, contentTypeList = $contentTypeList, languageTyleList = ${languageTyleList.size}")
        mPublisherLocalDataSource.deleteAllPublisherTypes()

        Observable.merge(Observable.from(contentTypeList), Observable.from(languageTyleList)).subscribe(object : Action1<PublisherType> {
            override fun call(publisherType: PublisherType) {
                mPublisherLocalDataSource.savePublisherType(publisherType)
            }
        })
    }

    override fun getPublishersByContentType(typeId: String): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersByContentType, mTypeId = $typeId")
        return getPublishersAndPublisherTypes().map(object : Func1<EnumMap<PublisherDataSource.PublisherInfoType, Any>
                , EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
            override fun call(publisherInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>)
                    : EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE, (publisherInfoMap
                        .get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as? Observable<List<Publisher>>)
                        ?.map(object : Func1<List<Publisher>, List<Publisher>> {
                            override fun call(publisherListRemote: List<Publisher>): List<Publisher> {
                                Log.i("XProject", "PublisherRepository getPublishersByContentType onRemotePublishersLoaded, " +
                                        "publisherList = ${publisherListRemote.size}")
                                return filterPublisherByType(publisherListRemote, typeId)
                            }
                        }))

                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, (publisherInfoMap
                        .get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                        ?.map(object : Func1<List<Publisher>, List<Publisher>> {
                            override fun call(publisherListLocalCache: List<Publisher>): List<Publisher> {
                                Log.i("XProject", "PublisherRepository getPublishersByContentType onCacheOrLocalPublishersLoaded, " +
                                        "publisherList = ${publisherListLocalCache.size}")
                                return filterPublisherByType(publisherListLocalCache, typeId)
                            }
                        }))

                return publisherInfoMap
            }
        })
    }

    private fun filterPublisherByType(publisherList: List<Publisher>, typeId: String): List<Publisher> {
        val typePublisherList = mutableListOf<Publisher>()
        Observable.from(publisherList).filter(object : Func1<Publisher, Boolean> {
            override fun call(publisher: Publisher): Boolean {
                return publisher.mType.equals(typeId)
            }
        }).subscribe(object : Action1<Publisher> {
            override fun call(publisher: Publisher) {
                typePublisherList.add(publisher)
            }
        })

        return typePublisherList
    }

    override fun getPublishersByLanguageType(languageId: String): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersByLanguageType, languageId = $languageId")
        return getPublishersAndPublisherTypes().map(object : Func1<EnumMap<PublisherDataSource.PublisherInfoType, Any>
                , EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
            override fun call(publisherInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>)
                    : EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE, (publisherInfoMap
                        .get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as? Observable<List<Publisher>>)
                        ?.map(object : Func1<List<Publisher>, List<Publisher>> {
                            override fun call(publisherListRemote: List<Publisher>): List<Publisher> {
                                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onRemotePublishersLoaded, " +
                                        "publisherList = ${publisherListRemote.size}")
                                return filterPublisherByLanguage(publisherListRemote, languageId)
                            }
                        }))

                publisherInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, (publisherInfoMap
                        .get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                        ?.map(object : Func1<List<Publisher>, List<Publisher>> {
                            override fun call(publisherListLocalCache: List<Publisher>): List<Publisher> {
                                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onCacheOrLocalPublishersLoaded, " +
                                        "publisherList = ${publisherListLocalCache.size}")
                                return filterPublisherByLanguage(publisherListLocalCache, languageId)
                            }
                        }))

                return publisherInfoMap
            }
        })
    }

    private fun filterPublisherByLanguage(publisherList: List<Publisher>, languageId: String): List<Publisher> {
        val languagePublisherList = mutableListOf<Publisher>()
        Observable.from(publisherList).filter(object : Func1<Publisher, Boolean> {
            override fun call(publisher: Publisher): Boolean {
                return publisher.mLanguage.equals(languageId)
            }
        }).subscribe(object : Action1<Publisher> {
            override fun call(publisher: Publisher) {
                languagePublisherList.add(publisher)
            }
        })

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
