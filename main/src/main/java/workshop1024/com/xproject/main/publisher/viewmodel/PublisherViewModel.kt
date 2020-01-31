package workshop1024.com.xproject.main.publisher.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import workshop1024.com.xproject.main.other.idlingResource.PublisherIdlingResouce
import workshop1024.com.xproject.main.publisher.Event
import workshop1024.com.xproject.main.publisher.PublisherListAdapter
import workshop1024.com.xproject.main.publisher.data.Publisher
import workshop1024.com.xproject.main.publisher.data.PublisherType
import workshop1024.com.xproject.main.publisher.data.source.PublisherDataSource

class PublisherViewModel(private val mPublisherDataSource: PublisherDataSource) : ViewModel(),
        PublisherDataSource.LoadPublisherAndPublisherTypeCallback {
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
    lateinit var mContentTypeList: ArrayList<PublisherType>
    //可选择发布者语言类型
    lateinit var mLanguageTypeList: ArrayList<PublisherType>
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
        mPublisherDataSource.getPublishersAndPublisherTypes(this)
    }

    override fun onRemotePublishersLoaded(publisherList: List<Publisher>) {
        _PublisherList.value = publisherList
        _SnackMessage.value = Event("Fetch remote " + publisherList.size + " publishers ...")
        _IsLoading.value = false
        //请求发布者类表完毕，IdlingResouce设置为true，执行后面异步指令
        mPublisherIdlingResouce?.setIdleState(true)
    }

    override fun onCacheOrLocalPublishersLoaded(publisherList: List<Publisher>) {
        _PublisherList.value = publisherList
        _SnackMessage.value = Event("Fetch cacheorlocal " + publisherList.size + " publishers ...")
        if (!mPublisherDataSource.getIsRequestRemote()) {
            _IsLoading.value = false
        }
        mPublisherIdlingResouce?.setIdleState(true)
    }

    override fun onRemotePublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        refreshPubliserTypeList(contentTypeList, languageTyleList)
    }

    override fun onCacheOrLocalPublisherTypesLoaded(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        refreshPubliserTypeList(contentTypeList, languageTyleList)
    }

    private fun refreshPubliserTypeList(contentTypeList: List<PublisherType>, languageTyleList: List<PublisherType>) {
        mContentTypeList = contentTypeList as ArrayList<PublisherType>
        mLanguageTypeList = languageTyleList as ArrayList<PublisherType>
    }

    override fun onDataNotAvailable() {

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
        mPublisherDataSource.getPublishersByContentType(contentId, this)
        mSelectedLanguageIndex = -1
    }

    fun getPublishersByLanguageType(languageId: String) {
        _IsLoading.value = true
        mPublisherIdlingResouce?.setIdleState(false)
        mPublisherDataSource.getPublishersByLanguageType(languageId, this)
        mSelectedTypeIndex = -1
    }
}