package workshop1024.com.xproject.main.publisher.data.source.local

import android.util.Log
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.functions.Func3
import rx.schedulers.Schedulers
import workshop1024.com.xproject.base.utils.ExecutorUtils
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource.PublisherInfoType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource.PublisherTypeInfoType
import workshop1024.com.xproject.main.publisher.data.source.remote.PublisherRemoteDataSource
import java.util.*
import java.util.function.Consumer
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class PublisherLocalDataSource private constructor(private val mPublisherDao: PublisherDao,
                                                   private val mExecutorUtils: ExecutorUtils) : PublisherDataSource {

    override fun getPublishersAndPublisherTypes(): Observable<EnumMap<PublisherInfoType, Any>> {
        Log.i("XProject", "PublisherLocalDataSource getPublishersAndPublisherTypes")
        val publiserInfoMap = EnumMap<PublisherInfoType, Any>(PublisherInfoType::class.java)

        val publisherListObservable = Observable.create(object : Observable.OnSubscribe<List<Publisher>> {
            override fun call(subscriber: Subscriber<in List<Publisher>>) {
                //异步执行数据库的io
                val publisherList = mPublisherDao.getPublishers()
                if (!publisherList.isEmpty()) {
                    subscriber.onNext(publisherList)
                }
            }
        })

        val publisherTypeObservable = Observable.create(object
            : Observable.OnSubscribe<EnumMap<PublisherTypeInfoType, List<PublisherType>>> {
            override fun call(subscriber: Subscriber<in EnumMap<PublisherTypeInfoType, List<PublisherType>>>) {
                val publiserTypeInfoMap = EnumMap<PublisherTypeInfoType, List<PublisherType>>(PublisherTypeInfoType::class.java)
                //异步执行数据库的io
                val publisherTypeList = mPublisherDao.getPublisherTypes()
                if (!publisherTypeList.isEmpty()) {
                    val contentTypeList = mutableListOf<PublisherType>()
                    Observable.from(publisherTypeList).filter(object : Func1<PublisherType, Boolean> {
                        override fun call(publisherType: PublisherType): Boolean {
                            return publisherType.mType.equals("content")
                        }
                    }).subscribe(object : Action1<PublisherType> {
                        override fun call(publisherType: PublisherType) {
                            contentTypeList.add(publisherType)
                        }
                    })
                    val languageTypeList = mutableListOf<PublisherType>()
                    Observable.from(publisherTypeList).filter(object : Func1<PublisherType, Boolean> {
                        override fun call(publisherType: PublisherType): Boolean {
                            return publisherType.mType.equals("language")
                        }
                    }).subscribe(object : Action1<PublisherType> {
                        override fun call(publisherType: PublisherType) {
                            languageTypeList.add(publisherType)
                        }
                    })
                    publiserTypeInfoMap.put(PublisherTypeInfoType.CONTENT_TYPES_LOCAL_CACHE, contentTypeList)
                    publiserTypeInfoMap.put(PublisherTypeInfoType.LANGUAGE_TYPES_LOCAL_CACHE, languageTypeList)

                    subscriber.onNext(publiserTypeInfoMap)
                }
            }
        })

        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE, publisherListObservable)
        publiserInfoMap.put(PublisherDataSource.PublisherInfoType.PUBLISHERTYPES_LOCAL_CACHE, publisherTypeObservable)

        return Observable.just(publiserInfoMap)
    }

    override fun getPublishersByContentType(contentId: String): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        TODO("远程接口一次性提供正页面数据，筛选在本地内存中执行")
    }

    override fun getPublishersByLanguageType(languageId: String): Observable<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
        TODO("远程接口一次性提供正页面数据，筛选在本地内存中执行")
    }

    override fun subscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherLocalDataSource subscribePublisherById, publisherId = $publisherId")
        val subscribePublisherByIdRunnable = Runnable { mPublisherDao.subscribePublisherById(publisherId) }
        mExecutorUtils.mDiskIOExecutor.execute(subscribePublisherByIdRunnable)
    }

    override fun unSubscribePublisherById(publisherId: String) {
        Log.i("XProject", "PublisherLocalDataSource unSubscribePublisherById, publisherId = $publisherId")
        val unSubscribePublisherByIdRunnable = Runnable { mPublisherDao.unSubscribePublisherById(publisherId) }
        mExecutorUtils.mDiskIOExecutor.execute(unSubscribePublisherByIdRunnable)
    }

    override fun savePublisher(publisher: Publisher) {
        Log.i("XProject", "SubInfoLocalDataSource savePublisher, publisher = $publisher")
        val savePublisherRunnable = Runnable { mPublisherDao.insertPublisher(publisher) }
        mExecutorUtils.mDiskIOExecutor.execute(savePublisherRunnable)
    }

    override fun deleteAllPublishers() {
        Log.i("XProject", "SubInfoLocalDataSource deleteAllPublishers")
        val deleteAllPublishersRunnable = Runnable { mPublisherDao.deleteAllPublishers() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteAllPublishersRunnable)
    }

    override fun deleteAllPublisherTypes() {
        Log.i("XProject", "SubInfoLocalDataSource deleteAllPublisherTypes")
        val deleteAllPublisherTypesRunnable = Runnable { mPublisherDao.deleteAllPublisherTypes() }
        mExecutorUtils.mDiskIOExecutor.execute(deleteAllPublisherTypesRunnable)
    }

    override fun savePublisherType(publisherType: PublisherType) {
        Log.i("XProject", "SubInfoLocalDataSource savePublisherType")
        val savePublisherTypeRunnable = Runnable { mPublisherDao.savePublisherType(publisherType) }
        mExecutorUtils.mDiskIOExecutor.execute(savePublisherTypeRunnable)
    }

    override fun getIsRequestRemote(): Boolean {
        return false
    }


    companion object {
        private lateinit var INSTANCE: PublisherLocalDataSource

        fun getInstance(publisherDao: PublisherDao, executorUtils: ExecutorUtils): PublisherLocalDataSource {
            synchronized(PublisherLocalDataSource::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = PublisherLocalDataSource(publisherDao, executorUtils)
                }
            }
            return INSTANCE
        }
    }
}