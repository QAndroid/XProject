package workshop1024.com.xproject.main.publisher.data.source

import android.util.Log
import androidx.core.util.Consumer
import rx.Observable
import rx.Observer
import rx.functions.*
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList
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
                        //toList操作符：?????
                        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.CONTENT_TYPES_LOCAL_CACHE, Observable.from(publisherTypeList).filter(
                                object : Func1<PublisherType, Boolean> {
                                    override fun call(publisherType: PublisherType): Boolean {
                                        return publisherType.mType.equals("content")
                                    }
                                }).toList())
                        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.LANGUAGE_TYPES_LOCAL_CACHE, Observable.from(publisherTypeList).filter(
                                object : Func1<PublisherType, Boolean> {
                                    override fun call(publisherType: PublisherType): Boolean {
                                        return publisherType.mType.equals("language")
                                    }
                                }).toList())
                        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, Observable.from(publisherList).toList())
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

        return Observable.merge(publisherInfoLocalCacheMapObservabl,publisherInfoRemoteMapObservabl)
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

//                val contentTypeListObservable = publiserInfoMap.get(PublisherDataSource.PublisherInfoType.CONTENT_TYPES_LOCAL_CACHE) as? Observable<List<PublisherType>>
//                val languageTypeListObservable = publiserInfoMap.get(PublisherDataSource.PublisherInfoType.LANGUAGE_TYPES_LOCAL_CACHE) as? Observable<List<PublisherType>>
//                //zip操作符：使用一个函数组合多个Observable发射的数据集合，然后在发射这个结果
//                Observable.zip(contentTypeListObservable, languageTypeListObservable, object : Func2<List<PublisherType>, List<PublisherType>, Any> {
//                    override fun call(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>): Any {
//                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromLocal, onCacheOrLocalPublisherTypesLoaded")
//                        refreshPublisherTypeCached(contentTypeList, languageTyleList)
//                        return Any()
//                    }
//                })

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

//                val contentTypeListObservable = publisherInfoMap.get(PublisherDataSource.PublisherInfoType.CONTENT_TYPES_REMOTE) as Observable<List<PublisherType>>
//                val languageTypeListObservable = publisherInfoMap.get(PublisherDataSource.PublisherInfoType.LANGUAGE_TYPES_REMOTE) as Observable<List<PublisherType>>
//                Observable.zip(contentTypeListObservable, languageTypeListObservable, object : Func2<List<PublisherType>, List<PublisherType>, Any> {
//                    override fun call(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>): Any {
//                        Log.i("XProject", "PublisherRepository getPublishersAndPublisherTypesFromRemote onRemotePublisherTypesLoaded, contentTypeList = ${contentTypeList.toString()}, languageTypeList = ${languageTyleList.toString()}")
//                        refreshPublisherTypeCached(contentTypeList, languageTyleList)
//                        refreshPublisherTypeLocal(contentTypeList, languageTyleList)
//
//                        //FIXME 这样处理是否合理？？
//                        return Any()
//                    }
//                })
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

        Observable.merge(Observable.from(contentTypeList), Observable.from(languageTyleList)).doOnNext(object : Action1<PublisherType> {
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
                var publiserInfoMap = EnumMap<PublisherDataSource.PublisherInfoType, Any>(PublisherDataSource
                        .PublisherInfoType::class.java)

                (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as? Observable<List<Publisher>>)
                        ?.doOnNext(object : Action1<List<Publisher>> {
                            override fun call(publisherListRemote: List<Publisher>) {
                                Log.i("XProject", "PublisherRepository getPublishersByContentType onRemotePublishersLoaded, " +
                                        "publisherList = ${publisherListRemote.size}")
                                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE, filterPublisherByType(publisherListRemote, typeId))
                            }
                        })

                (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                        ?.doOnNext(object : Action1<List<Publisher>> {
                            override fun call(publisherListLocalCache: List<Publisher>) {
                                Log.i("XProject", "PublisherRepository getPublishersByContentType onCacheOrLocalPublishersLoaded, " +
                                        "publisherList = ${publisherListLocalCache.size}")
                                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, filterPublisherByType(publisherListLocalCache, typeId))
                            }
                        })

                return publiserInfoMap
            }
        }).doOnError({
            Log.i("XProject", "PublisherRepository getPublishersByContentType onDataNotAvailable")
        })
    }

    private fun filterPublisherByType(publisherList: List<Publisher>, typeId: String): Observable<List<Publisher>> {
        return Observable.from(publisherList).filter(object : Func1<Publisher, Boolean> {
            override fun call(publisher: Publisher): Boolean {
                return publisher.mType.equals(typeId)
            }
        }).toList()
    }

    override fun getPublishersByLanguageType(languageId: String): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherRepository getPublishersByLanguageType, languageId = $languageId")
        return getPublishersAndPublisherTypes().map(object : Func1<EnumMap<PublisherDataSource.PublisherInfoType, Any>
                , EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
            override fun call(publisherInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>)
                    : EnumMap<PublisherDataSource.PublisherInfoType, Any> {
                var publiserInfoMap = EnumMap<PublisherDataSource.PublisherInfoType, Any>(PublisherDataSource
                        .PublisherInfoType::class.java)

                (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as? Observable<List<Publisher>>)
                        ?.doOnNext(object : Action1<List<Publisher>> {
                            override fun call(publisherListRemote: List<Publisher>) {
                                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onRemotePublishersLoaded, " +
                                        "publisherList = ${publisherListRemote.size}")
                                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE, filterPublisherByType(publisherListRemote, languageId))
                            }
                        })

                (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                        ?.doOnNext(object : Action1<List<Publisher>> {
                            override fun call(publisherListLocalCache: List<Publisher>) {
                                Log.i("XProject", "PublisherRepository getPublishersByLanguageType onCacheOrLocalPublishersLoaded, " +
                                        "publisherList = ${publisherListLocalCache.size}")
                                publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, filterPublisherByType(publisherListLocalCache, languageId))
                            }
                        })

                return publiserInfoMap
            }
        }).doOnError({
            Log.i("XProject", "PublisherRepository getPublishersByLanguageType onDataNotAvailable")
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
