package workshop1024.com.xproject.main.publisher.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func2
import rx.schedulers.Schedulers
import workshop1024.com.xproject.main.other.idlingResource.PublisherIdlingResouce
import workshop1024.com.xproject.main.publisher.Event
import workshop1024.com.xproject.main.publisher.PublisherListAdapter
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource
import java.util.*
import kotlin.collections.ArrayList

class PublisherViewModel(private val mPublisherDataSource: PublisherDataSource) : ViewModel(),
        Observer<EnumMap<PublisherDataSource.PublisherInfoType, Any>> {
    //Publisher列表
    private val _PublisherList = MutableLiveData<List<Publisher>>().apply { value = emptyList() }
    //对外公开LiveData，在非VM中修改数据的值
    val mPublisherList: LiveData<List<Publisher>>
        get() = _PublisherList

    //是否Loading
    private val _IsLoading = MutableLiveData<Boolean>()
    val mIsLoading: LiveData<Boolean>
        get() = _IsLoading

    //SnackBar展示的消息文案
    private val _SnackMessage = MutableLiveData<Event<String>>()
    internal val mSnackMessage: LiveData<Event<String>>
        get() = _SnackMessage

    //ToolBar标题文案
    private val _TitleText = MutableLiveData<String>()
    val mTitleText: LiveData<String>
        get() = _TitleText

    //被动获取的数据，不需要实时更新的，不使用LiveData
    //可选择发布者内容类型
    var mContentTypeList: ArrayList<PublisherType>? = null
    //可选择发布者语言类型
    var mLanguageTypeList: ArrayList<PublisherType>? = null
    //选择的发布者索引，默认值-1，没有选中
    var mSelectedTypeIndex = -1
    //选择的语言索引
    var mSelectedLanguageIndex = -1

    //发布者dlingResouce，检测请求发布者列表异步任务
    @VisibleForTesting
    var mPublisherIdlingResouce: PublisherIdlingResouce? = null
        get() {
            if (field == null) {
                field = PublisherIdlingResouce()
            }
            return field
        }

    init {
        start()
    }

    fun start() {
        _IsLoading.value = true
        mPublisherIdlingResouce?.setIdleState(false)
        mPublisherDataSource.getPublishersAndPublisherTypes().subscribe(this)
    }

    private fun refreshPubliserTypeList(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mContentTypeList = contentTypeList as ArrayList<PublisherType>
        mLanguageTypeList = languageTyleList as ArrayList<PublisherType>
    }


    fun subscribePublisher(publisher: Publisher) {
        mPublisherDataSource.subscribePublisherById(publisher.mPublisherId)
        _SnackMessage.value = Event(publisher.mName + " selected")
    }

    fun unSubscribePublisher(publisher: Publisher) {
        mPublisherDataSource.unSubscribePublisherById(publisher.mPublisherId)
        _SnackMessage.value = Event(publisher.mName + " unselected")
    }

    fun searchMenuSelected() {
        _SnackMessage.value = Event("publisher_menu_search")
    }

    fun updateTitleText(titleText:String){
        _TitleText.value = titleText
    }

    fun getPublishersByContentType(contentId: String) {
        _IsLoading.value = true
        mPublisherIdlingResouce?.setIdleState(false)
        mPublisherDataSource.getPublishersByContentType(contentId).subscribe(this)
        mSelectedLanguageIndex = -1
    }

    fun getPublishersByLanguageType(languageId: String) {
        _IsLoading.value = true
        mPublisherIdlingResouce?.setIdleState(false)
        mPublisherDataSource.getPublishersByLanguageType(languageId).subscribe(this)
        mSelectedTypeIndex = -1
    }

    override fun onError(e: Throwable?) {
        Log.i("XProject","onError")
    }

    override fun onNext(publisherInfoMap: EnumMap<PublisherDataSource.PublisherInfoType, Any>) {
        Log.i("XProject", "PublisherViewModel onNext, publisherInfoMap = ${publisherInfoMap.size}")
        //as? “安全的”（可空）转换操作符，参考：https://www.kotlincn.net/docs/reference/typecasts.html
        (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_REMOTE) as? Observable<List<Publisher>>)
                ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Action1<List<Publisher>> {
                    override fun call(publisherListRemote: List<Publisher>) {
                        _PublisherList.value = publisherListRemote
                        _SnackMessage.value = Event("Fetch remote " + publisherListRemote.size + " publishers ...")
                        _IsLoading.value = false
                        //请求发布者类表完毕，IdlingResouce设置为true，执行后面异步指令
                        mPublisherIdlingResouce?.setIdleState(true)
                    }
                })

        (publisherInfoMap.get(PublisherDataSource.PublisherInfoType.PUBLISHERS_LOCAL_CACHE) as? Observable<List<Publisher>>)
                ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Action1<List<Publisher>> {
                    override fun call(publisherListLocalCache: List<Publisher>) {
                        Log.i("XProject", "PublisherViewModel onNext, publisherListLocalCache = ${publisherListLocalCache.size}")
                        _PublisherList.value = publisherListLocalCache
                        _SnackMessage.value = Event("Fetch cacheorlocal " + publisherListLocalCache.size + " publishers ...")
                        if (!mPublisherDataSource.getIsRequestRemote()) {
                            _IsLoading.value = false
                        }
                        mPublisherIdlingResouce?.setIdleState(true)
                    }
                })

//        val contentTypeListRemoteObservable = publisherInfoMap.get(PublisherDataSource
//                .PublisherInfoType.CONTENT_TYPES_REMOTE) as? Observable<List<PublisherType>>
//        val languageTypeListRemoteObservable = publisherInfoMap.get(PublisherDataSource
//                .PublisherInfoType.LANGUAGE_TYPES_REMOTE) as? Observable<List<PublisherType>>
//        Observable.zip(contentTypeListRemoteObservable, languageTypeListRemoteObservable, object : Func2<List<PublisherType>, List<PublisherType>, Any> {
//            override fun call(contentTyleListRemote: List<PublisherType>, languageTyleListRemote: List<PublisherType>): Any {
//                refreshPubliserTypeList(contentTyleListRemote, languageTyleListRemote)
//                return Any()
//            }
//        })?.subscribe()
//
//        val contentTypeListLocalCacheObservable = publisherInfoMap.get(PublisherDataSource
//                .PublisherInfoType.CONTENT_TYPES_LOCAL_CACHE) as? Observable<List<PublisherType>>
//        val languageTypeListLocalCacheObservable = publisherInfoMap.get(PublisherDataSource
//                .PublisherInfoType.LANGUAGE_TYPES_LOCAL_CACHE) as? Observable<List<PublisherType>>
//        Observable.zip(contentTypeListLocalCacheObservable, languageTypeListLocalCacheObservable, object : Func2<List<PublisherType>, List<PublisherType>, Any> {
//            override fun call(contentTyleListRemote: List<PublisherType>, languageTyleListRemote: List<PublisherType>): Any {
//                refreshPubliserTypeList(contentTyleListRemote, languageTyleListRemote)
//                return Any()
//            }
//        })?.subscribe()
    }

    override fun onCompleted() {
        Log.i("XProject","onCompleted")
    }
}